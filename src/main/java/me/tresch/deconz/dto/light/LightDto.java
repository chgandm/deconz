package me.tresch.deconz.dto.light;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.tresch.deconz.dto.AbstractDeviceDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LightDto extends AbstractDeviceDto {
  /**
   * Human readable type of the light.
   */
  private String type;

  /**
   * The current state of the light.
   */
  private LightStateDto state;
}
