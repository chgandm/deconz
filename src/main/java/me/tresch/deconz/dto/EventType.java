package me.tresch.deconz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {
  /**
   * resource has been added
   */
  @JsonProperty("added")
  ADDED,
  /**
   * resource attributes have changed
   */
  @JsonProperty("changed")
  CHANGED,
  /**
   * resource has been deleted
   */
  @JsonProperty("deleted")
  DELETED,

  /**
   *  a scene has been recalled.
   */
  @JsonProperty("scene-called")
  SCENE_CALLED;
}
