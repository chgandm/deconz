package me.tresch.deconz.device;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.dto.ResourceType;
import me.tresch.deconz.dto.sensor.SensorDto;

@Slf4j
@Getter
public abstract class DeConzSensor<V extends DeConzEventValue> extends DeConzDevice<V> {
  private final SensorType type;

  protected DeConzSensor(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, deConzId, deviceId, manufacturer, modelId);
    this.type = type;
  }

  @Override
  public String getName() {
    return fetchCurrentDeviceState().getName();
  }

  @Override
  public V getState() {
    return parseStateUpdate(fetchCurrentDeviceState().getState());
  }

  @Override
  public boolean isReachable() {
    return Boolean.TRUE.equals(fetchCurrentDeviceState().getConfig().getReachable());
  }

  @Override
  public Integer getBattery() {
    return fetchCurrentDeviceState().getConfig().getBattery();
  }

  @Override
  protected ResourceType getResourceType() {
    return ResourceType.SENSORS;
  }

  protected SensorDto fetchCurrentDeviceState() {
    return getDeConzClient().getSensor(this.getDeConzId());
  }
}
