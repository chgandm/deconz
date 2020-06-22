package me.tresch.deconz.device.presence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tresch.deconz.device.DeconzSimpleEventType;

@Getter
@RequiredArgsConstructor
public enum PresenceEventType implements DeconzSimpleEventType<Boolean> {
  PRESENCE_DETECTED(true),
  ABSENCE_SUSPECTED(false);

  private final Boolean value;

  public static PresenceEventType fromValue(Boolean val) {
    return DeconzSimpleEventType.fromValue(PresenceEventType.values(), val);
  }
}
