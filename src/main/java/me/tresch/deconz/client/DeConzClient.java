package me.tresch.deconz.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import me.tresch.deconz.dto.AbstractDeviceDto;
import me.tresch.deconz.dto.ConfigDto;
import me.tresch.deconz.dto.EventDto;
import me.tresch.deconz.dto.ResourceType;
import me.tresch.deconz.dto.light.LightDto;
import me.tresch.deconz.dto.light.LightStateUpdateDto;
import me.tresch.deconz.dto.sensor.SensorDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * Provides means of accessing the deCONZ REST API synchronously as well as listening for real-time updates through the
 * deCONZ WebSocket server (see {@link DeConzClient#subscribe(Consumer)}).
 */
@Slf4j
public class DeConzClient {
  private static final String ENDPOINT_CONFIG = "/config";
  private static final String ENDPOINT_SENSORS = "/sensors";
  private static final String ENDPOINT_LIGHTS = "/lights";
  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private final String baseUrl;
  private final HttpClient webClient;
  private final Cache<DeviceCacheKey, AbstractDeviceDto> cache;
  private final WebSocket.Listener webSocketClient;
  private final Collection<Consumer<EventDto>> subscriptions = new ArrayList<>();

  public DeConzClient(String url, String apiKey, long cacheSize) {
    this.webClient = HttpClient.newBuilder().build();
    this.baseUrl = url + String.format("/api/%s", apiKey);
    this.cache = Caffeine.newBuilder()
            .maximumSize(cacheSize)
            .build();
    this.webSocketClient = new DeConzWebSocketClient(new CountDownLatch(1));
    listenToEventStream(this.webSocketClient).join();
  }

  public ConfigDto getConfig() {
    HttpRequest request = createRequest(ENDPOINT_CONFIG)
            .GET()
            .build();
    return getResponse(request, new TypeReference<>() {});
  }

  public Map<String, SensorDto> getSensors() {
    HttpRequest request = createRequest(ENDPOINT_SENSORS)
            .GET()
            .build();
    Map<String, SensorDto> response = getResponse(request, new TypeReference<>() {});
    response.forEach((key, value) -> cache.put(DeviceCacheKey.of(ResourceType.SENSORS, key), value));
    return response;
  }

  public SensorDto getSensor(String deConzId) {
    return (SensorDto) cache.get(DeviceCacheKey.of(ResourceType.SENSORS, deConzId), (id) -> {
      HttpRequest request = createRequest(ENDPOINT_SENSORS, "/", deConzId)
              .GET()
              .build();
      return getResponse(request, new TypeReference<SensorDto>() {});
    });
  }

  public Map<String, LightDto> getLights() {
    HttpRequest request = createRequest(ENDPOINT_LIGHTS)
            .GET()
            .build();
    Map<String, LightDto> response = getResponse(request, new TypeReference<>() {});
    response.forEach((key, value) -> cache.put(DeviceCacheKey.of(ResourceType.LIGHTS, key), value));
    return response;
  }

  public LightDto getLight(String deConzId) {
    return (LightDto) cache.get(DeviceCacheKey.of(ResourceType.LIGHTS, deConzId), (id) -> {
      HttpRequest request = createRequest(ENDPOINT_LIGHTS, "/", deConzId)
              .GET()
              .build();
      return getResponse(request, new TypeReference<LightDto>() {});
    });
  }

  public void setLightState(String deConzId, LightStateUpdateDto stateUpdate) {
    HttpRequest request = createRequest(ENDPOINT_LIGHTS, "/", deConzId, "/state")
            .PUT(body(stateUpdate))
            .build();
    cache.invalidate(DeviceCacheKey.of(ResourceType.LIGHTS, deConzId));
    getResponse(request, new TypeReference<Void>() {});
  }

  public synchronized void subscribe(Consumer<EventDto> eventDtoConsumer) {
    subscriptions.add(eventDtoConsumer);
  }

  public void unsubscribe(Consumer<EventDto> eventDtoConsumer) {
    subscriptions.remove(eventDtoConsumer);
  }

  private CompletableFuture<WebSocket> listenToEventStream(WebSocket.Listener listener) {
    String host = getConfig().getIpaddress();
    Integer port = getConfig().getWebsocketport();
    String wsUri = String.format("ws://%s:%d", host, port);
    return HttpClient.newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(URI.create(wsUri), listener);
  }

  private <T> T getResponse(HttpRequest request, TypeReference<T> responseType) {
    try {
      HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
      if (isSuccess(response)) {
        return OBJECT_MAPPER.readValue(response.body(), responseType);
      }
      throw new DeConzClientException(response.statusCode(), response.body());
    } catch (Exception e) {
      throw new DeConzClientException(e);
    }
  }

  private HttpRequest.BodyPublisher body(Object body) {
    try {
      return HttpRequest.BodyPublishers.ofString(OBJECT_MAPPER.writeValueAsString(body));
    } catch (JsonProcessingException e) {
      throw new DeConzClientException(e);
    }
  }

  private HttpRequest.Builder createRequest(String... requestElements) {
    String uri = baseUrl + String.join("", requestElements);
    return HttpRequest.newBuilder().uri(URI.create(uri));
  }

  private boolean isSuccess(HttpResponse<?> response) {
    return response.statusCode() >= 200 && response.statusCode() < 300;
  }

  private class DeConzWebSocketClient implements WebSocket.Listener {
    private final CountDownLatch latch;

    public DeConzWebSocketClient(CountDownLatch latch) { this.latch = latch; }

    @Override
    public void onOpen(WebSocket webSocket) {
      log.info("Opened websocket connection to DeConz");
      WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
      log.debug("Received a websocket text message from DeConz: {}", data);
      latch.countDown();
      EventDto eventDto = messageToEventDto(data.toString());
      if (eventDto != null) {
        cache.invalidate(DeviceCacheKey.of(eventDto.getResourceType(), eventDto.getDeConzId()));
        DeConzClient.this.subscriptions.forEach(subscriber -> subscriber.accept(eventDto));
      }
      return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
      log.error("An error occurred while communicating with DeConz", error);
      WebSocket.Listener.super.onError(webSocket, error);
    }

    private EventDto messageToEventDto(String eventData)  {
      try {
        return OBJECT_MAPPER.readValue(eventData, EventDto.class);
      } catch (JsonProcessingException ex) {
        log.error("An error occurred while converting the text message to an event", ex);
        return null;
      }
    }
  }

  @Value(staticConstructor = "of")
  private static class DeviceCacheKey {
    ResourceType type;
    String deConzId;
  }
}
