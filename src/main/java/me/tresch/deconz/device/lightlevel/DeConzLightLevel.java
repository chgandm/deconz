package me.tresch.deconz.device.lightlevel;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.SensorType;

import java.util.Map;

@Getter
public class DeConzLightLevel extends DeConzSensor<LightLevelStateEvent> {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Builder
  public DeConzLightLevel(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, type, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  protected LightLevelStateEvent parseStateUpdate(Map<String, Object> state) {
    return objectMapper.convertValue(state, LightLevelStateEvent.class);
  }
}
