package me.tresch.deconz.device;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.dto.EventDto;
import me.tresch.deconz.dto.EventType;
import me.tresch.deconz.dto.ResourceType;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Getter
public abstract class DeConzDevice<V extends DeConzEventValue> {
  private final DeConzClient deConzClient;
  private final String deConzId;
  private final String deviceId;
  private final String manufacturer;
  private final String modelId;

  private Consumer<V> stateUpdateHandler;
  private Consumer<EventDto> websocketEventConsumer;

  protected DeConzDevice(DeConzClient deConzClient, String deConzId, String deviceId, String manufacturer, String modelId) {
    this.deConzClient = deConzClient;
    this.deConzId = deConzId;
    this.deviceId = deviceId;
    this.manufacturer = manufacturer;
    this.modelId = modelId;

    listenForStateUpdates(deConzClient);
  }

  public void subscribeForStateUpdate(Consumer<V> stateUpdateHandler) {
    this.stateUpdateHandler = stateUpdateHandler;
  }

  /**
   * Stop listening for state through the web socket and unsubscribe the registered handler from state updates.
   */
  public void deInit() {
    this.stateUpdateHandler = null;
    this.deConzClient.unsubscribe(this.websocketEventConsumer);
  }

  abstract public String getName();

  abstract public V getState();

  abstract public boolean isReachable();

  abstract public Integer getBattery();

  abstract protected V parseStateUpdate(Map<String, Object> state);

  abstract protected ResourceType getResourceType();

  private void listenForStateUpdates(DeConzClient deConzClient) {
    websocketEventConsumer = eventDto -> {
      if (EventType.CHANGED.equals(eventDto.getEventType()) &&
              eventDto.getResourceType().equals(this.getResourceType()) &&
              eventDto.getDeConzId().equals(this.deConzId)) {
        handleStateUpdateEvent(eventDto);
      }
    };
    deConzClient.subscribe(websocketEventConsumer);
  }

  private void handleStateUpdateEvent(EventDto eventDto) {
    log.debug("Received {} for handling", eventDto);
    if (eventDto.getState() != null) {
      try {
        V stateUpdate = parseStateUpdate(eventDto.getState());
        log.info("Device {} changed: {}", this.deConzId, stateUpdate);
        if (stateUpdateHandler != null) {
          log.debug("Delegated state up date to handler");
          stateUpdateHandler.accept(stateUpdate);
        }
      } catch (Exception e) {
        log.error("An error occurred while trying to parse the update: ", e);
      }
    } else {
      log.warn("An update for device {} that did not contain a state was received for handling, ignoring...",
              this.deConzId);
    }
  }

}
