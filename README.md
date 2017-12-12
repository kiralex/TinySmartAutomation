TinySmartAutomation
==================

This is a WSN app. The goal of this app is to make a home automation system.

# Installation
## Setup

```bash
make telosb
make telosb reinstall
```
OR
```
make telosb install,XY
```
Where :
* X is the roomID
* Y is the sensorID
```
cd javaUI/
java TinySmartAutomation -comm serial@/dev/ttyUSB0:115200
```
