package me.tresch.deconz.device.lightlevel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import me.tresch.deconz.device.DeConzEventValue;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LightLevelStateEvent implements DeConzEventValue {
  Boolean dark;
  Boolean daylight;
  Integer lightlevel;
  Integer lux;
}
