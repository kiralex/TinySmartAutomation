TinySmartAutomation
==================

This is a WSN app. The goal of this app is to make a home automation system.

# Installation
## Setup

```bash
sudo tos-install-jni
make telosb
make telosb reinstall
cd javaUI/
java TinySmartAutomation -comm serial@/dev/ttyUSB0:115200
```
