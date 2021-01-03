# deCONZ Java Client

A simple Java library for the [deCONZ REST API](https://dresden-elektronik.github.io/deconz-rest-doc),
offering support for interacting with lights and sensors, as well as receiving real-time through its
WebSocket API.

## Maven Dependency

    <dependency>
      <groupId>me.tresch</groupId>
      <artifactId>deconz-java-client</artifactId>
      <version>[version]</version>
    </dependency>

## Usage

In order to communicate with the deCONZ REST API, you'll first need to obtain an API key as explained in the
[getting started guide](https://dresden-elektronik.github.io/deconz-rest-doc/getting_started)

`DeConzDeviceDiscovery deConzDisovery = new DeConzDeviceDiscovery(<url>, <apikey>);`

## Devices
All discovered deCONZ devices extend the `DeConzDevice` class.

### Listening for real-time state updates

Obtain a reference to any deCONZ device, and register a consumer to handle state updates as follows:
`device.subscribeForStateUpdate(Consumer<V> stateUpdateHandler)`

### Lights
Obtain instances of class `DeConzLight` through: `deConzDiscovery.discoverLights()`

### Sensors
Supported sensor types:
* Switches (to detect button presses): class `DeConzSwitch`
* Open / close sensors (for doors and windows):  class `DeConzOpenClose`
* Presence sensors (to detect motion): class `DeConzPresence`

Obtain sensors through `deConzDiscovery.discoverSensors()`.