package me.tresch.deconz.dto.light;

import lombok.Data;

import java.util.List;

@Data
public class LightStateDto {
  /**
   * true if the light is on.
   */
  private Boolean on;

  /**
   * Brightness of the light (0...255).
   * Depending on the light type 0 might not mean visible "off" but minimum brightness.
   */
  private Integer bri;

  /**
   * Color hue of the light.
   * The hue parameter in the HSV color model is between 0Â°-360Â° and is mapped to 0..65535 to get 16-bit resolution.
   */
  private Integer hue;

  /**
   * Color saturation of the light. There 0 means no color at all and 255 is the greatest saturation of the color.
   */
  private Integer sat;

  /**
   * Mired color temperature of the light. (2000K - 6500K) (153..500)
   */
  private Integer ct;

  /**
   * CIE xy color space coordinates as array [x, y] of real values (0..1).
   */
  private List<Double> xy;

  /**
   * Temporary alert effect.
   */
  private AlertType alert;

  /**
   * The current color mode of the light:
   */
  private ColorMode colormode;

  /**
   * Effect of the light:
   */
  private EffectType effect;

  /**
   * True if the light is reachable and accepts commands.
   */
  private Boolean reachable;
}
