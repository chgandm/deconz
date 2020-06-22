package me.tresch.deconz.dto.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EffectType {
  /**
   * no effect
   */
  @JsonProperty("none")
  NONE,

  /**
   * colorloop
   */
  @JsonProperty("colorloop")
  COLORLOOP
}
