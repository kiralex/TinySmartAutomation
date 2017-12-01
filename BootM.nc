#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

#define TRUE 1
#define FALSE 0

module BootM {
  uses interface Boot;
  uses interface switchInterface as baseStation; // My base station modules
  uses interface switchInterface as slave; // My base station modules
}
implementation {
  event void Boot.booted() {
    if(TOS_NODE_ID == 0)
      call baseStation.start();
    else
      call slave.start();

    /*call SerialControl.start();*/
  }
}
