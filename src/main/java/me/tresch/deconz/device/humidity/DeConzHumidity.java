package me.tresch.deconz.device.humidity;

import lombok.Builder;
import lombok.Getter;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.SensorType;

import java.util.Map;

@Getter
public class DeConzHumidity extends DeConzSensor<HumidityEventType> {
  public static final String EVENT_NAME = "humidity";

  @Builder
  public DeConzHumidity(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, type, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  protected HumidityEventType parseStateUpdate(Map<String, Object> state) {
    Object event = state.get(EVENT_NAME);
    if (event instanceof Integer) {
      return HumidityEventType.of((Integer) event);
    }
    return HumidityEventType.of(0);
  }
}
