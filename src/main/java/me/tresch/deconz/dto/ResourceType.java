package me.tresch.deconz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResourceType {
  @JsonProperty("groups")
  GROUPS,

  @JsonProperty("lights")
  LIGHTS,

  @JsonProperty("scenes")
  SCENES,

  @JsonProperty("sensors")
  SENSORS;
}
