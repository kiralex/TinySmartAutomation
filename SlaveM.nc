#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

#include "utils.h"
#include "utils.c"

#define TRUE 1
#define FALSE 0

module SlaveM {
  uses {
    // Leds and initialize module
    interface Leds;

    // Timer module
		interface Timer<TMilli> as Timer0;

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
  }
  provides{
    interface SwitchInterface;
  }
}
implementation {
  message_t packetRadio;
  radio_msg_t* rcmRadioReceived;
  radio_msg_t* rcmSend;
  bool lockedRadio = FALSE;
  bool enabled = FALSE;

  float humidity = -1;
  float temperature = -1;
  float light = -1;
  float voltage = -1;

/***
	* My OWN FUNCTIONS
	*/
	int sendMessage(char * text, int nbChar){
    int32_t testdd = -3.5;

		if (lockedRadio) {
			 return -1;
		 } else {
			 rcmSend = (radio_msg_t*)call RadioPacket.getPayload(&packetRadio, sizeof(radio_msg_t));
			 if (rcmSend == NULL) {
				 printf("La taille des donnees est trop grande\n");
				 return -1;
			 }

			 memcpy(rcmSend->text, text, sizeof(char)*nbChar);
       //memcpy(&rcmSend->toto, &testdd, sizeof(nx_int32_t));
			 if (call RadioAMSend.send(AM_BROADCAST_ADDR, &packetRadio, sizeof(radio_msg_t)) == SUCCESS) {
				 lockedRadio = TRUE;
				 return 0;
			 }else{
				 return -1;
			 }
		 }
	}

  command void SwitchInterface.start(){
    call Leds.led2On();
    enabled = TRUE;
    call RadioControl.start();
    printf("taille %d \n", sizeof(float));
    call Timer0.startPeriodic(1000);
  }

  event void Timer0.fired(){
    if(enabled){
      call TempRead.read();
      call HumidityRead.read();
      call LightRead.read();
      call VoltageRead.read();

      printf ("\n----------slave----------\n\n");
      if(sendMessage("test",4) >= 0){
        printf("messsage envoyé");
      }else{
        printf("messsage non envoyé !!! ");
      }

      call Leds.led1Toggle();
    }
  }

  event void TempRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        printf ("Temperature : ");
        printfFloat(convertVoltToTemperature(val));
        printf (" C\n");
      } else {
        printf ("Error in temperature getting\n");
      }
    }
  }

  event void HumidityRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        printf ("Humidity : ");
        printfFloat(convertVoltToHumidity(val));
        printf (" %%\n");
      } else {
        printf ("Error in humidity getting\n");
      }
    }
  }

  event void LightRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        printf ("Light : ");
        printfFloat(convertVoltToLight(val));
        printf (" Lux\n");
      } else {
        printf ("Error in light getting\n");
      }
    }
  }

  event void VoltageRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        printf ("Voltage : ");
        printfFloat(convertVoltageToVolt(val));
        printf (" Volt\n");
      } else {
        printf ("Error in Voltage getting\n");
      }
    }
  }


  // needed to radio control
  event void RadioControl.startDone(error_t err) {
  }

  event void RadioControl.stopDone(error_t err) {
    printf("La radio est arretee");
  }

  event void RadioAMSend.sendDone(message_t* bufPtr, error_t error) {
    if (&packetRadio == bufPtr) {
      lockedRadio = FALSE;
    }
  }

  event message_t* RadioReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
    if (len != sizeof(radio_msg_t))
      return bufPtr;

    rcmRadioReceived = (radio_msg_t*)payload;
    printf("%s \n", (char *) rcmRadioReceived->text);
    /*printfFloat(rcmRadioReceived->toto);*/
    return bufPtr;
  }

}
