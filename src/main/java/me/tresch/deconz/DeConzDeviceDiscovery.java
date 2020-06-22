package me.tresch.deconz;

import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzDeviceFactory;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.light.DeConzLight;
import me.tresch.deconz.dto.light.LightDto;
import me.tresch.deconz.dto.sensor.SensorDto;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DeConzDeviceDiscovery {
  private final DeConzClient deConzClient;

  /**
   * A facade through which the deCONZ devices can be discovered. Calls to the underlying REST API will be cached
   * according to the provided settings.
   *
   * @param url the (base) URL at which the deCONZ REST API can be reached
   * @param apiKey the API key used to authenticate against the deCONT REST API
   * @param cacheSize the number of devices to cache. Set to 0 in order to disable caching.
   */
  public DeConzDeviceDiscovery(String url, String apiKey, long cacheSize) {
    this.deConzClient = new DeConzClient(url, apiKey, cacheSize);
  }

  public Set<DeConzLight> discoverLights() {
    Map<String, LightDto> sensors = deConzClient.getLights();
    return sensors.entrySet().stream()
            .map((entry) -> DeConzDeviceFactory.createLight(deConzClient, entry.getKey(), entry.getValue()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
  }

  public Set<DeConzSensor<?>> discoverSensors() {
    Map<String, SensorDto> sensors = deConzClient.getSensors();
    return sensors.entrySet().stream()
            .map((entry) -> DeConzDeviceFactory.createSensor(deConzClient, entry.getKey(), entry.getValue()))
            .collect(Collectors.toSet());
  }
}
