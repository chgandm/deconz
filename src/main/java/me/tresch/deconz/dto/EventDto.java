package me.tresch.deconz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
  /**
   * The **event type** of the message
   */
  @JsonProperty("e")
  private EventType eventType;
  /**
   * The id of the resource to which the message relates, e.g. `5` for `/sensors/5`.
   * Not for `scene-called` events.
   */
  @JsonProperty("id")
  private String deConzId;

  /**
   * The `uniqueid` of the resource to which the message relates, e.g. `00:0d:6f:00:10:65:8a:6e-01-1000`.
   * Only for light and sensor resources.
   */
  private String uniqueId;

  /**
   * The **resource type** to which the message belongs.
   */
  @JsonProperty("r")
  private ResourceType resourceType;

  /**
   * Depending on the `websocketnotifyall` setting: a map containing all or only the changed `state` attributes of a
   * group, light, or sensor resource.
   * Only for `changed` events
   */
  private Map<String, Object> state;
}
