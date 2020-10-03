# Java Test & Measurement Control

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
[![Build Status](https://github.com/javatmc/javatmc-core/workflows/build/badge.svg)](https://github.com/javatmc/javatmc-core/actions)

> JavaTMC is a Java API for Test & Measurement devices, intended to be used for test automation. It's purpose is to extend continuous integration workflows with hardware tests.

---

## Using JavaTMC

Currently JavaTMC is not published anywhere, in the future it will be available either at JCenter or MavenCentral.

To install the library locally first clone this repository then run `./gradlew publishToMavenLocal`

## Example

Using sockets to directly communicate with instruments

```java
public void test() {
    try(VXI11Socket socket = new VXI11Socket(InetAddress.getByName("192.168.1.100"))) {
        ISCPISocket scpi = new RawSCPISocket(socket);
        System.out.println(scpi.getDeviceIdentifier());

        scpi.send(SCPICommand.builder().command(":SOUR:FREQ:START").with("100").build());

        System.out.println("Current voltage: " + scpi.query(SCPICommand.builder().query(":MEAS:VOLT:DC") + "V");
    } catch(IOException e) {
        System.err.println("Error connecting to instrument");
    }
}
```

Using drivers to control instruments

```java
public void test() {
    try(VXI11Socket socket = new VXI11Socket(InetAddress.getByName("192.168.1.100"))) {
        Oscilloscope scope = new SDSLegacyDriver(new RawSCPISocket(socket));
        scope.acquisition().setTimespan(Units.milli(14));
        scope.acquisition().setTimeOffset(0);
        scope.getAnalogInput(2).setEnabled(true);
        scope.getAnalogInput(2).setProbeAttenuation(10);
        scope.getAnalogInput(2).setRange(3.1);
        scope.getAnalogInput(2).setOffset(-1.5);
    } catch(IOException e) {
        System.err.println("Error connecting to instrument");
    }
}
```

## Command line interface

For basic measurements and automations there's a command line interface, see the [CLI Documentation](measure-cli/docs) for more information.

## Architecture

The core consists of abstract interfaces for devices, socket implementations like VXI11, Serial and USB. Interfaces are similiar to those specified in the IVIFoundation's [instrument class specifications](http://www.ivifoundation.org/specifications/)

## Features

### Sockets

The following socket types are currently supported

+ Raw TCP, VXI-11, Serial

*Planned support for the following*

+ *USB Raw, USBTMC, HiSLIP, Modbus/TCP, Modbus/Serial*

### Instruments

The following interfaces are present currently, with more coming in the future

+ Oscillscope, Function Generator, Logic Analyzer, DC Power Supply

### Drivers

Currently supported instruments:

+ Siglent SDS oscilloscopes, exluding the 2000X+, 5000X and 6000X series

+ Siglent SDG function generators

## Building

Run `./gradlew build`

## Contributing

[Contribution guidelines](CONTRIBUTING.md)

---

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
