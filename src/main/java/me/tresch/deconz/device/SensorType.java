package me.tresch.deconz.device;

public enum SensorType {
  /**
   * Zigbee Green Power Switches
   */
  ZGPSwitch,
  /**
   * Zigbee Home Automation Switches
   */
  ZHASwitch,
  ZHAOpenClose,
  ZHAPresence,
  /**
   * A virtual sensor built into the deCONZ software since version 2.05.12.
   */
  Daylight
}
