package me.tresch.deconz.dto;

import lombok.Data;

@Data
public abstract class AbstractDeviceDto {
  /**
   * HTTP etag which changes whenever the me.tresch.deconz.device changes.
   */
  private String etag;

  /**
   * Software version of the me.tresch.deconz.device.
   */
  private String swversion;

  /**
   * The manufacturer name of the me.tresch.deconz.device
   */
  private String manufacturername;

  /**
   * The model id of the sensor.
   */
  private String modelid;

  /**
   * The name of the sensor
   */
  private String name;

  /**
   * String	The unique identifiers (MAC address) of the sensor
   */
  private String uniqueid;
}
