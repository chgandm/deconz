package me.tresch.deconz.dto.sensor;

import lombok.Data;

@Data
public class SensorConfigDto {
  /**
   * Specifies if the sensor is on or off
   */
  private Boolean on;

  /**
   * Specifies if the sensor is reachable
   */
  private Boolean reachable;

  /**
   * (0..100)	The battery status of the sensor.
   */
  private Integer battery;
}
