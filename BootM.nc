#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

#define TRUE 1
#define FALSE 0

module BootM {
  uses interface Boot;
  uses interface SwitchInterface as baseStation; // My base station modules
  uses interface SwitchInterface as slave; // My base station modules
}
implementation {
  event void Boot.booted() {
    printf("TOS_NODE_ID=%d\n", TOS_NODE_ID);
    if(TOS_NODE_ID == 1){
      printf("Démarrage en tant que station de base");
      call baseStation.start();
    }
    else{
      printf("Démarrage en tant qu'esclave");
      call slave.start();
    }

    /*call SerialControl.start();*/
  }
}
