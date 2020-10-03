# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.0-alpha] - 2020-10-03

Initial version, previously known as [JMeasure](https://github.com/c4deszes/jmeasure)

### Added

- Support for the following socket types
  - Raw TCP Socket
  - Serial Socket
  - VXI11 Socket
- Interfaces for the following instruments
  - DCPowerSupply
  - FunctionGenerator
  - LogicAnalyzer
  - Oscilloscope
- Support for instrument discovery
  - RPC Portmap based discovery
- Objects for SCPI communication
- Signals
- CLI
  - LXI discovery command
- Siglent Drivers
  - SDS scope driver
  - SDG wavegen driver
