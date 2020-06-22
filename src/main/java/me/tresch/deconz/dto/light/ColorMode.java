package me.tresch.deconz.dto.light;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Smart bulbs can have their color set by either changes to
 * - hue and saturation (hs),
 * - their color temperature (ct)
 * - or their xy color space (xy).
 * The choice is mutually exclusive, hence the colormode will show if you are using hs, ct or xy. It is set
 * automatically depending on posting which values (hs, ct, xy pairs) are posted to the bulb.
 */
public enum ColorMode {
  /**
   * hue and saturation
   */
  @JsonProperty("hs")
  HS,

  /**
   * CIE xy values
   */
  @JsonProperty("xy")
  XY,

  /**
   * Color temperature
   */
  @JsonProperty("ct")
  CT
}
