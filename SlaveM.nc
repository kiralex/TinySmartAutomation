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
  float brightness = -1;
  float voltage = -1;
  uint8_t roomID = 1;
  uint8_t sensorID = -1;

/***
	* My OWN FUNCTIONS
	*/
	int sendSensorsInformation(){
		if (lockedRadio)
			 return -1;
		else {
			 rcmSend = (radio_msg_t*)call RadioPacket.getPayload(&packetRadio, sizeof(radio_msg_t));
			 if (rcmSend == NULL) {
				 printf("La taille des donnees est trop grande\n");
				 return -1;
			 }

        // get the number of unity
       sensorID = TOS_NODE_ID % 10;
       roomID = TOS_NODE_ID / 10;

			 memcpy(&rcmSend->sensorID, &sensorID, sizeof(nx_uint8_t));
       memcpy(&rcmSend->roomID, &roomID, sizeof(nx_uint8_t));
       memcpy(&rcmSend->humidity, &humidity, sizeof(nx_uint32_t));
       memcpy(&rcmSend->brightness, &brightness, sizeof(nx_uint32_t));
       memcpy(&rcmSend->temperature, &temperature, sizeof(nx_uint32_t));
       memcpy(&rcmSend->voltage, &voltage, sizeof(nx_uint32_t));
			 if (call RadioAMSend.send(AM_BROADCAST_ADDR, &packetRadio, sizeof(radio_msg_t)) == SUCCESS) {
				 lockedRadio = TRUE;
				 return 0;
			 }else
				 return -1;

		 }
	}

  command void SwitchInterface.start(){
    call Leds.led0On();
    enabled = TRUE;
    call RadioControl.start();
    call Timer0.startPeriodic(1000);
  }

  event void Timer0.fired(){
    if(enabled){
      call TempRead.read();
      call HumidityRead.read();
      call LightRead.read();
      call VoltageRead.read();

      sendSensorsInformation();
      call Leds.led0Toggle();
    }
  }

  event void TempRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        temperature = convertVoltToTemperature(val);
      } else
        printf ("Error in temperature getting\n");
    }
  }

  event void HumidityRead.readDone(error_t result, u_int16_t val){
    if(enabled){
          if (result == SUCCESS) {
            humidity = convertVoltToHumidity(val);
            printf ("Error in humidity getting\n");
        }
    }
  }

  event void LightRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        brightness = convertVoltToLight(val);
      } else {
        printf ("Error in light getting\n");
      }
    }
  }

  event void VoltageRead.readDone(error_t result, u_int16_t val){
    if(enabled){
      if (result == SUCCESS) {
        voltage = convertVoltageToVolt(val);
      } else {
        printf ("Error in Voltage getting\n");
      }
    }
  }


  // needed to radio control
  event void RadioControl.startDone(error_t err) {
    if (enabled) {}
  }

  event void RadioControl.stopDone(error_t err) {
    if (enabled) {}
  }

  event void RadioAMSend.sendDone(message_t* bufPtr, error_t error) {
    if (enabled) {
        if (&packetRadio == bufPtr) {
          lockedRadio = FALSE;
        }
    }
  }

  event message_t* RadioReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
    if (enabled) {
        float humidityL = -1;
        float temperatureL = -1;
        float brightnessL = -1;
        float voltageL = -1;
        uint8_t roomIDL = -1;
        uint8_t sensorIDL = -1;


        if (len != sizeof(radio_msg_t))
          return bufPtr;

        rcmRadioReceived = (radio_msg_t*)payload;
        memcpy(&humidityL, &rcmRadioReceived->humidity, sizeof(nx_uint32_t));
        memcpy(&temperatureL, &rcmRadioReceived->temperature, sizeof(nx_uint32_t));
        memcpy(&brightnessL, &rcmRadioReceived->brightness, sizeof(nx_uint32_t));
        memcpy(&voltageL, &rcmRadioReceived->voltage, sizeof(nx_uint32_t));
        memcpy(&roomIDL, &rcmRadioReceived->roomID, sizeof(nx_uint8_t));
        memcpy(&sensorIDL, &rcmRadioReceived->sensorID, sizeof(nx_uint8_t));

        printf("Sensor ID : %d\n", sensorIDL);
        printf("Room ID : %d\n", roomIDL);
        printfMessagePlusFloat("Humidity (%%) : ", humidityL);
        printfMessagePlusFloat("Temperature (Celcius) : ", temperatureL);
        printfMessagePlusFloat("Brightness (Lux) : ", brightnessL);
        printfMessagePlusFloat("voltage (V) : ", voltageL);

        printf("----------------------------------\n\n");
    }

    return bufPtr;
  }

}
