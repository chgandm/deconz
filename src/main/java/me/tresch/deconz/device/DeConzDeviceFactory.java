package me.tresch.deconz.device;

import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.button.DeConzSwitch;
import me.tresch.deconz.device.light.DeConzLight;
import me.tresch.deconz.device.openclose.DeConzOpenClose;
import me.tresch.deconz.device.presence.DeConzPresence;
import me.tresch.deconz.dto.light.LightDto;
import me.tresch.deconz.dto.sensor.SensorDto;

public class DeConzDeviceFactory {
  public static DeConzLight createLight(DeConzClient deConzClient, String deConzId, LightDto lightDto) {
    // for some reason, the latest deconz API exposes the conbee II as a light
    if (lightDto.getType().equals("Configuration tool")) {
      return null;
    }
    return createDeConzLight(deConzClient, deConzId, lightDto);
  }

  public static DeConzSensor<?> createSensor(DeConzClient deConzClient, String deConzId, SensorDto sensorDto) {
    switch (sensorDto.getType()) {
      case ZGPSwitch:
      case ZHASwitch:
        return createDeConzSwitch(deConzClient, deConzId, sensorDto);
      case ZHAOpenClose:
        return createDeConzOpenClose(deConzClient, deConzId, sensorDto);
      case ZHAPresence:
        return createDeConzPresence(deConzClient, deConzId, sensorDto);
      default:
        return null;
    }
  }

  private static DeConzLight createDeConzLight(DeConzClient deConzClient, String deConzId, LightDto lightDto) {
    return DeConzLight.builder()
            .deConzClient(deConzClient)
            .deConzId(deConzId)
            .deviceId(lightDto.getUniqueid())
            .manufacturer(lightDto.getManufacturername())
            .modelId(lightDto.getModelid())
            .build();
  }

  private static DeConzSwitch createDeConzSwitch(DeConzClient deConzClient, String deConzId, SensorDto sensorDto) {
    return DeConzSwitch.builder()
            .deConzClient(deConzClient)
            .type(sensorDto.getType())
            .deConzId(deConzId)
            .deviceId(sensorDto.getUniqueid())
            .manufacturer(sensorDto.getManufacturername())
            .modelId(sensorDto.getModelid())
            .build();
  }

  private static DeConzSensor createDeConzOpenClose(DeConzClient deConzClient, String deConzId, SensorDto sensorDto) {
    return DeConzOpenClose.builder()
            .deConzClient(deConzClient)
            .type(sensorDto.getType())
            .deConzId(deConzId)
            .deviceId(sensorDto.getUniqueid())
            .manufacturer(sensorDto.getManufacturername())
            .modelId(sensorDto.getModelid())
            .build();
  }

  private static DeConzPresence createDeConzPresence(DeConzClient deConzClient, String deConzId, SensorDto sensorDto) {
    return DeConzPresence.builder()
            .deConzClient(deConzClient)
            .type(sensorDto.getType())
            .deConzId(deConzId)
            .deviceId(sensorDto.getUniqueid())
            .manufacturer(sensorDto.getManufacturername())
            .modelId(sensorDto.getModelid())
            .build();
  }
}
