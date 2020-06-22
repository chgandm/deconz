package me.tresch.deconz.device.light;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import me.tresch.deconz.client.DeConzClient;
import me.tresch.deconz.device.DeConzDevice;
import me.tresch.deconz.dto.ResourceType;
import me.tresch.deconz.dto.light.LightDto;
import me.tresch.deconz.dto.light.LightStateDto;
import me.tresch.deconz.dto.light.LightStateUpdateDto;

import java.util.Map;

@Slf4j
public class DeConzLight extends DeConzDevice<LightStateEvent> {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Builder
  public DeConzLight(DeConzClient deConzClient, String deConzId, String deviceId, String manufacturer, String modelId) {
    super(deConzClient, deConzId, deviceId, manufacturer, modelId);
  }

  @Override
  public String getName() {
    return fetchCurrentDeviceState().getName();
  }

  @Override
  public LightStateEvent getState() {
    return LightStateEvent.fromDto(fetchCurrentDeviceState().getState());
  }

  @Override
  public boolean isReachable() {
    return fetchCurrentDeviceState().getState().getReachable();
  }

  @Override
  public Integer getBattery() {
    return null;
  }

  @Override
  protected ResourceType getResourceType() {
    return ResourceType.LIGHTS;
  }

  public void turnOn() {
    this.updateState(LightStateUpdateDto.builder().on(true).build());
  }

  public void turnOff() {
    this.updateState(LightStateUpdateDto.builder().on(false).build());
  }

  public void updateState(LightStateUpdateDto stateUpdateDto) {
    this.getDeConzClient().setLightState(this.getDeConzId(), stateUpdateDto);
  }

  @Override
  protected LightStateEvent parseStateUpdate(Map<String, Object> state) {
    LightStateDto lightStateDto = objectMapper.convertValue(state, LightStateDto.class);
    return LightStateEvent.fromDto(lightStateDto);
  }

  private LightDto fetchCurrentDeviceState() {
    return getDeConzClient().getLight(this.getDeConzId());
  }
}
