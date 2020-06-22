# deCONZ Java Client

A simple Java library for the [deCONZ REST API](https://dresden-elektronik.github.io/deconz-rest-doc),
offering support for interacting with lights and sensors, as well as receiving real-time through its
WebSocket API.

## Usage

In order to communicate with the deCONZ REST API, you'll first need to obtain an API key as explained in the
[getting started guide](https://dresden-elektronik.github.io/deconz-rest-doc/getting_started)

`DeConzDeviceDiscovery deConzDisovery = new DeConzDeviceDiscovery(<url>, <apikey>);`

### Listening for real-time state updates

Obtain a reference to any deCONZ device, and register a consumer to handle state updates as follows:
`subscribeForStateUpdate(Consumer<V> stateUpdateHandler)`

### Lights
`deConzDiscovery.discoverLights()`

### Sensors
Supported sensor types:
* Switches (to detect button presses)
* Open / close sensors (for doors and windows)
* Presence sensors (to detect motion)

`deConzDiscovery.discoverSensors()`