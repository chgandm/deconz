package me.tresch.deconz.device.button;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SwitchButtonStateType {
  // Primary/Power Toggle-Button
  PRIMARY_BUTTON_TAP_INITIATED,
  PRIMARY_BUTTON_TAP,
  PRIMARY_BUTTON_LONG_PRESS,
  PRIMARY_BUTTON_LONG_PRESS_RELEASED,
  PRIMARY_BUTTON_DOUBLE_TAP,
  // On-Button
  ON_BUTTON_TAP,
  // Off-Button
  OFF_BUTTON_TAP,
  // Directional Buttons
  LEFT_BUTTON_TAP,
  RIGHT_BUTTON_TAP,
  // Dimmer Control
  DIM_UP_BUTTON_TAP,
  DIM_DOWN_BUTTON_TAP;
}
