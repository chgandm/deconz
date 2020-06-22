package me.tresch.deconz.dto.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AlertType {
  /**
   * light is not performing an alert
   */
  @JsonProperty("none")
  NONE,

  /**
   * light is blinking a short time
   */
  @JsonProperty("select")
  SELECT,

  /**
   * light is blinking a longer time
   */
  @JsonProperty("lselect")
  LSELECT
}
