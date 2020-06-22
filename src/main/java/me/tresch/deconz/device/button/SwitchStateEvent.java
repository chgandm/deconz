package me.tresch.deconz.device.button;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import me.tresch.deconz.device.DeConzEventValue;

import java.util.Map;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SwitchStateEvent implements DeConzEventValue {
  public static final Map<SwitchModelType, Map<Integer, SwitchButtonStateType>> MODEL_STATE_EVENT_MAP = Map.of(
          SwitchModelType.LUMI_SENSOR_SWITCH, Map.of(
                  1000, SwitchButtonStateType.PRIMARY_BUTTON_TAP_INITIATED,
                  1002, SwitchButtonStateType.PRIMARY_BUTTON_TAP,
                  1001, SwitchButtonStateType.PRIMARY_BUTTON_LONG_PRESS,
                  1003, SwitchButtonStateType.PRIMARY_BUTTON_LONG_PRESS_RELEASED,
                  1004, SwitchButtonStateType.PRIMARY_BUTTON_DOUBLE_TAP
          ),
          SwitchModelType.TRADFRI, Map.of(
                  1002, SwitchButtonStateType.PRIMARY_BUTTON_TAP,
                  4002, SwitchButtonStateType.LEFT_BUTTON_TAP,
                  5002, SwitchButtonStateType.RIGHT_BUTTON_TAP,
                  2002, SwitchButtonStateType.DIM_UP_BUTTON_TAP,
                  3002, SwitchButtonStateType.DIM_DOWN_BUTTON_TAP
          ),
          SwitchModelType.RWL020, Map.of(
                  1002, SwitchButtonStateType.ON_BUTTON_TAP,
                  4002, SwitchButtonStateType.OFF_BUTTON_TAP,
                  2002, SwitchButtonStateType.DIM_UP_BUTTON_TAP,
                  3002, SwitchButtonStateType.DIM_DOWN_BUTTON_TAP
          ),
          SwitchModelType.RWL021, Map.of(
                  1002, SwitchButtonStateType.ON_BUTTON_TAP,
                  4002, SwitchButtonStateType.OFF_BUTTON_TAP,
                  2002, SwitchButtonStateType.DIM_UP_BUTTON_TAP,
                  3002, SwitchButtonStateType.DIM_DOWN_BUTTON_TAP
          ),
          SwitchModelType.FELLER_2005FH, Map.of(
                  1000, SwitchButtonStateType.ON_BUTTON_TAP,
                  2000, SwitchButtonStateType.OFF_BUTTON_TAP,
                  3000, SwitchButtonStateType.DIM_UP_BUTTON_TAP,
                  4000, SwitchButtonStateType.DIM_DOWN_BUTTON_TAP
          )
  );

  private final SwitchButtonStateType buttonStateType;
  private final Integer value;
  private final Boolean generic;

  public static SwitchStateEvent fromModelAndCode(String modelId, Integer val) {
    try {
      log.debug("Attempting to determine switch state of device with model '{}' and code '{}'", modelId, val);
      SwitchModelType modelType = SwitchModelType.fromModelId(modelId);
      SwitchButtonStateType switchEventType = MODEL_STATE_EVENT_MAP.get(modelType).get(val);
      return new SwitchStateEvent(switchEventType, val, false);
    } catch (Exception e) {
      // generic click
      return new SwitchStateEvent(SwitchButtonStateType.PRIMARY_BUTTON_TAP, val, true);
    }
  }
}
