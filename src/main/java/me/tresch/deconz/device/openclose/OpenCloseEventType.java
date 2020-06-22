package me.tresch.deconz.device.openclose;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tresch.deconz.device.DeconzSimpleEventType;

@Getter
@RequiredArgsConstructor
public enum OpenCloseEventType implements DeconzSimpleEventType<Boolean> {
  OPEN(true),
  CLOSED(false);

  private final Boolean value;

  public static OpenCloseEventType fromValue(Boolean val) {
    return DeconzSimpleEventType.fromValue(OpenCloseEventType.values(), val);
  }
}
