package me.tresch.deconz.device.button;

import lombok.Builder;
import lombok.Getter;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzSensor;
import me.tresch.deconz.device.SensorType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class DeConzSwitch extends DeConzSensor<SwitchStateEvent> {
  public static final String EVENT_NAME = "buttonevent";

  @Builder
  public DeConzSwitch(DeConzClient deConzClient, SensorType type, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, type, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  public SwitchStateEvent parseStateUpdate(Map<String, Object> state) {
    Object event = state.get(EVENT_NAME);
    if (event instanceof Integer) {
      return SwitchStateEvent.fromModelAndCode(this.getModelId(), (Integer) event);
    }
    throw new IllegalStateException();
  }

  public Set<SwitchButtonStateType> supportedSwitchStateTypes() {
    SwitchModelType modelId = SwitchModelType.fromModelId(this.getModelId());
    Map<Integer, SwitchButtonStateType> supportedSwitchStateTypes = SwitchStateEvent.MODEL_STATE_EVENT_MAP.get(modelId);
    if (supportedSwitchStateTypes == null) {
      return Set.of();
    }
    return new HashSet<>(supportedSwitchStateTypes.values());
  }
}
