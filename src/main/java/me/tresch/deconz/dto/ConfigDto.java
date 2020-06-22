package me.tresch.deconz.dto;

import lombok.Data;

@Data
public class ConfigDto {
  private Integer websocketport;
  private String ipaddress;
}
