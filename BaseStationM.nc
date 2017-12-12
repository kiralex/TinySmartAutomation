#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

#include "utils.h"
#include "utils.c"

#define TRUE 1
#define FALSE 0

module BaseStationM {
  uses {
    // Leds and initialize module
    interface Leds;

    // Timer module
    interface Timer<TMilli> as Timer0;
    interface Timer<TMilli> as Timer1;
    interface Timer<TMilli> as Timer2;

    // Temperature and humidity sensors
    interface Read<u_int16_t> as TempRead;
    interface Read<u_int16_t> as HumidityRead;

    // light, voltage sensor
    interface Read<u_int16_t> as LightRead;
    interface Read<u_int16_t> as VoltageRead;

    // radio interface sensor
    interface SplitControl as RadioControl;
    interface Receive as RadioReceive;
    interface AMSend as RadioAMSend;
    interface Packet as RadioPacket;
    interface AMPacket as RadioAMPacket;

    // serial interface sensor
    interface SplitControl as SerialControl;
    interface Receive as SerialReceive;
    interface AMSend as SerialAMSend;
    interface Packet as SerialPacket;
  }

  provides{
    // to know base or slave sensor
    interface SwitchInterface;
  }
}
implementation {
  message_t packetRadio;
  radio_msg_t* rcmSend;
  bool lockedRadio = FALSE;

  message_t packetSerial;
  serial_msg_t* rcmSerialReceived;
  serial_msg_t* rcmSerialSend;
  bool lockedSerial = FALSE;

  bool enabled = FALSE;

  command void SwitchInterface.start(){
    enabled = TRUE;
    call SerialControl.start();
    call RadioControl.start();
  }

    /***
	* My OWN FUNCTIONS
	*/
	int sendMessage(char * text, int nbChar){
		if (lockedRadio) {
			 return -1;
		 } else {
			 rcmSend = (radio_msg_t*)call RadioPacket.getPayload(&packetRadio, sizeof(radio_msg_t));
			 if (rcmSend == NULL) {
				 return -1;
			 }

			 if (call RadioAMSend.send(AM_BROADCAST_ADDR, &packetRadio, sizeof(radio_msg_t)) == SUCCESS) {
				 lockedRadio = TRUE;
				 return 0;
			 }else{
				 return -1;
			 }
		 }
	}

    // send a message by serial port
    int sendSerial(radio_msg_t * msg){
        if (lockedSerial) {
            return -1;
        } else {
            rcmSerialSend = (serial_msg_t*)call SerialPacket.getPayload(&packetSerial, sizeof(serial_msg_t));
            if (rcmSerialSend == NULL) {
                return -1;
            }
            if (call SerialPacket.maxPayloadLength() < sizeof(serial_msg_t)) {
                return -1;
            }

            memcpy(&rcmSerialSend->temperature, &msg->temperature, sizeof(nx_uint32_t));
            memcpy(&rcmSerialSend->humidity, &msg->humidity, sizeof(nx_uint32_t));
            memcpy(&rcmSerialSend->sensorID, &msg->sensorID, sizeof(nx_uint8_t));
            memcpy(&rcmSerialSend->roomID, &msg->roomID, sizeof(nx_uint8_t));
            memcpy(&rcmSerialSend->voltage, &msg->voltage, sizeof(nx_uint32_t));
            memcpy(&rcmSerialSend->brightness, &msg->brightness, sizeof(nx_uint32_t));

            if (call SerialAMSend.send(AM_BROADCAST_ADDR, &packetSerial, sizeof(serial_msg_t)) == SUCCESS) {
                lockedSerial = TRUE;
                return 0;
            }else{
                return -1;
            }
        }
    }

	// Timers
  event void Timer0.fired() {
    if(enabled){
    }
  }

  event void Timer1.fired(){
    if(enabled){
      call Leds.led1Toggle();
    }
  }
	event void Timer2.fired(){
        if(enabled){
            call Leds.led2Toggle();
        }
	}



  event void TempRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
      } else {
      }
    }
  }

  event void HumidityRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
      } else {
      }
    }
  }

  event void LightRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
      } else {
      }
    }
  }

  event void VoltageRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
      } else {
      }
    }
  }


  // needed to radio control
  event void RadioControl.startDone(error_t err) {
    if (enabled)  {}
  }

  event void RadioControl.stopDone(error_t err) {
    if(enabled) {
    }
  }

  event void RadioAMSend.sendDone(message_t* bufPtr, error_t error) {
    if (enabled && &packetRadio == bufPtr) {
      lockedRadio = FALSE;
    }
  }

   event message_t* RadioReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
   if (enabled) {
        radio_msg_t * rcmRadioReceived;
        call Leds.led2Toggle();

        if (len != sizeof(radio_msg_t))
          return bufPtr;

        rcmRadioReceived = (radio_msg_t*)payload;
        sendSerial(rcmRadioReceived);

        return bufPtr;
    }
  }



  event void SerialControl.startDone(error_t err) {
    if (err == SUCCESS) {
    }
  }
  event void SerialControl.stopDone(error_t err) {}

  event void SerialAMSend.sendDone(message_t* bufPtr, error_t error) {
    if (&packetSerial == bufPtr) {
      lockedSerial = FALSE;
    }
  }

  event message_t* SerialReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
    if (len != sizeof(serial_msg_t)) {return bufPtr;}
    else {
      serial_msg_t* rcm = (serial_msg_t*)payload;
      return bufPtr;
    }
  }


}
