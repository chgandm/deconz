package me.tresch.deconz.device.humidity;

import lombok.Value;
import me.tresch.deconz.device.DeConzEventValue;

@Value(staticConstructor = "of")
public class HumidityEventType implements DeConzEventValue {
  Integer humidity;
}
