package me.tresch.deconz.device.openclose;

import lombok.Builder;
import lombok.Getter;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.SensorType;

import java.util.Map;
import java.util.function.Consumer;

@Getter
public class DeConzOpenClose extends DeConzSensor<OpenCloseEventType> {
  public static final String EVENT_NAME = "open";

  @Builder
  public DeConzOpenClose(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, type, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  public OpenCloseEventType parseStateUpdate(Map<String, Object> state) {
    Object event = state.get(EVENT_NAME);
    if (event instanceof Boolean) {
      return OpenCloseEventType.fromValue((Boolean) event);
    }
    throw new IllegalStateException();
  }

  public void subscribeForStateUpdate(Consumer<OpenCloseEventType> stateUpdateHandler) {
    super.subscribeForStateUpdate(stateUpdateHandler);
  }
}
