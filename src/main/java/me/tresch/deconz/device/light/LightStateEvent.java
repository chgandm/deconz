package me.tresch.deconz.device.light;

import lombok.Getter;
import lombok.Value;
import me.tresch.deconz.device.DeConzEventValue;
import me.tresch.deconz.dto.light.AlertType;
import me.tresch.deconz.dto.light.ColorMode;
import me.tresch.deconz.dto.light.EffectType;
import me.tresch.deconz.dto.light.LightStateDto;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class LightStateEvent implements DeConzEventValue {
  /**
   * true if the light is on.
   */
  Boolean on;

  /**
   * Brightness of the light (0...255).
   * Depending on the light type 0 might not mean visible "off" but minimum brightness.
   */
  Integer brightness;

  /**
   * Color hue of the light.
   * The hue parameter in the HSV color model is between 0Â°-360Â° and is mapped to 0..65535 to get 16-bit resolution.
   */
  Integer colorHue;

  /**
   * Color saturation of the light. There 0 means no color at all and 255 is the greatest saturation of the color.
   */
  Integer colorSaturation;

  /**
   * Mired color temperature of the light. (2000K - 6500K) (153..500)
   */
  Integer colorTemperature;

  /**
   * CIE xy color space coordinates as array [x, y] of real values (0..1).
   */
  List<Double> colorCieXyCoordinates;

  /**
   * Temporary alert effect.
   */
  AlertType alert;

  /**
   * The current color mode of the light:
   */
  ColorMode colormode;

  /**
   * Effect of the light:
   */
  EffectType effect;

  /**
   * True if the light is reachable and accepts commands.
   */
  Boolean reachable;

  /**
   * If light does not support colors, neither hue nor xy will be available.
   */
  public boolean supportsColorHue() {
    return (colorHue != null && colorCieXyCoordinates != null);
  }

  /**
   * If light does not support color temperature, ct will not be available.
   */
  public boolean supportsColorTemperature() {
    return colorTemperature != null;
  }

  public boolean supportsBrightness() {
    return brightness != null;
  }

  public static LightStateEvent fromDto(LightStateDto stateDto) {
    return LightStateEvent.of(
            stateDto.getOn(),
            stateDto.getBri(),
            stateDto.getHue(),
            stateDto.getSat(),
            stateDto.getCt(),
            stateDto.getXy(),
            stateDto.getAlert(),
            stateDto.getColormode(),
            stateDto.getEffect(),
            stateDto.getReachable()
    );
  }
}
