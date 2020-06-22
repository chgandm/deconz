package me.tresch.deconz.device;

import java.util.Objects;
import java.util.stream.Stream;

public interface DeconzSimpleEventType<T> extends DeConzEventValue {
  abstract T getValue();

  @SuppressWarnings("unchecked")
  static <E extends DeconzSimpleEventType<?>> E fromValue(DeconzSimpleEventType<?>[] values, Object value) {
    return (E) Stream.of(values)
            .filter(e -> Objects.equals(e.getValue(), value))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
  }
}
