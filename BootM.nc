#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

#define TRUE 1
#define FALSE 0

module BootM {
  uses interface Boot;
  uses interface SwitchInterface as baseStation; // My base station modules
  uses interface SwitchInterface as slave; // My slave modules
}
implementation {
  event void Boot.booted() {
    if(TOS_NODE_ID == 1)
      call baseStation.start();
    else
      call slave.start();
  }
}
