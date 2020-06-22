package me.tresch.deconz.device.button;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum SwitchModelType {
    // Feller (Friends of Hue, FOH) 4 Button Switch
    FELLER_2005FH("FOHSWITCH"),
    // (Philips) Hue Dimmer Switch
    RWL020("RWL020"),
    RWL021("RWL021"),
    // (IKEA) TRÅDFRI
    TRADFRI("TRADFRI remote control"),
    // (Xiaomi) Smart Switch
    LUMI_SENSOR_SWITCH("lumi.sensor_switch");

    private final String modelId;

    public static SwitchModelType fromModelId(String modelId) {
        return Stream.of(SwitchModelType.values())
                .filter(e -> Objects.equals(e.modelId, modelId))
                .findFirst()
                .orElse(null);
    }
}
