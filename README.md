TinySmartAutomation
==================

This is a WSN app. The goal of this app is to make a home automation system.

# Installation
## Setup

```bash
sudo tos-install-jni
make telosb
# for each sensor
make telosb install,XY

# for the base station
make telosb install,1
```
Where :
* X is the roomID
* Y is the sensorID
```
cd javaUI/
java TinySmartAutomation -comm serial@/dev/ttyUSB0:115200
```
