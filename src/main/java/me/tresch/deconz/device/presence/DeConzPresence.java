package me.tresch.deconz.device.presence;

import lombok.Builder;
import lombok.Getter;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.SensorType;

import java.util.Map;

@Getter
public class DeConzPresence extends DeConzSensor<PresenceEventType> {
  public static final String EVENT_NAME = "presence";

  @Builder
  public DeConzPresence(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, type, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  protected PresenceEventType parseStateUpdate(Map<String, Object> state) {
    Object event = state.get(EVENT_NAME);
    if (event instanceof Boolean) {
      return PresenceEventType.fromValue((Boolean) event);
    }
    // At times the DeCONZ websocket events were observed to not contain the "presence" event name in its payload:
    return PresenceEventType.ABSENCE_SUSPECTED;
  }
}
