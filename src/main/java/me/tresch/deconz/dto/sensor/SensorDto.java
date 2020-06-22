package me.tresch.deconz.dto.sensor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.tresch.deconz.device.SensorType;
import me.tresch.deconz.dto.AbstractDeviceDto;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SensorDto extends AbstractDeviceDto {
  /**
   * The config of the sensor.
   */
  private SensorConfigDto config;

  /**
   * The Endpoint of the sensor.
   */
  private Long ep;

  /**
   * The type of the sensor.
   */
  private SensorType type;

  /**
   * The state of the sensor.
   * "lastupdated" holds the timestamp when the sensor was last updated.
   */
  private Map<String, Object> state;
}
