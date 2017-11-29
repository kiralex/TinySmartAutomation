#include <stdio.h>
#include <string.h>

#include "Timer.h"
#include "message.h"

// My own files
#include "utils.h"
#include "utils.c"


#define TRUE 1
#define FALSE 0


module Extends_Module{
	uses {
		// Leds and initialize module
		interface Leds;
		interface Boot;

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

		// serial interface
		interface SplitControl as SerialControl;
		interface Receive as SerialReceive;
		interface AMSend as SerialAMSend;
		interface Packet as SerialPacket;

	}
}
implementation {
	message_t packetRadio;
	message_t packetSerial;

	radio_msg_t* rcmReceived;
	serial_msg_t* rcmSerialReceived;

	radio_msg_t* rcmSend;
	serial_msg_t* rcmSerialSend;

	int lockedRadio = FALSE;
	int lockedSerial = FALSE;

/***
	* My OWN FUNCTIONS
	*/
	int sendMessage(char * text, int nbChar){
		if (lockedRadio) {
			 return -1;
		 } else {
			 rcmSend = (radio_msg_t*)call RadioPacket.getPayload(&packetRadio, sizeof(radio_msg_t));
			 if (rcmSend == NULL) {
				 printf("La taille des donnees est trop grande\n");
				 return -1;
			 }

			 memcpy(rcmSend->text, text, sizeof(char)*nbChar);
			 if (call RadioAMSend.send(AM_BROADCAST_ADDR, &packetRadio, sizeof(radio_msg_t)) == SUCCESS) {
				 lockedRadio = TRUE;
				 return 0;
			 }else{
				 return -1;
			 }
		 }
	}

	// send a message by serial port
	int sendSerialMessage(char * text, int nbChar){
		if (lockedSerial) {
			 return -1;
		 } else {
			 rcmSerialSend = (serial_msg_t*)call SerialPacket.getPayload(&packetSerial, sizeof(serial_msg_t));
			 if (rcmSerialSend == NULL) {
				 printf("La taille des donnees est trop grande\n");
				 return -1;
			 }

			 memcpy(rcmSerialSend->text, text, sizeof(char)*nbChar);
			 if (call SerialAMSend.send(AM_BROADCAST_ADDR, &packetSerial, sizeof(serial_msg_t)) == SUCCESS) {
				 lockedSerial = TRUE;
				 return 0;
			 }else{
				 return -1;
			 }
		 }
	}


/***
 * FUNCTIONS TO LISTEN AND COMMUNICATE WITH sensors
 */

	// Initiate
	event void Boot.booted(){
		/*call Timer0.startPeriodic(500);*/
		/*call Timer1.startPeriodic(1000);*/
		call Timer2.startPeriodic(1500);

		// start radio command
		/*call RadioControl.start();*/

		// start Serial
		call SerialControl.start();

	}

	// Timers
	event void Timer0.fired(){
		call Leds.led0Toggle();
	}
	event void Timer1.fired(){
		call TempRead.read();
		call HumidityRead.read();
		call LightRead.read();
		call VoltageRead.read();

		printf ("\n--------------------\n\n");

		call Leds.led1Toggle();
	}
	event void Timer2.fired(){
		/*if(sendMessage("c pa fo", 7) < 0)
			printf("Error while sending the message\n");*/

		if(sendSerialMessage("c pa fo\n", 8) < 0)
			printf("Error while sending the message\n");
		call Leds.led2Toggle();
	}

	event void TempRead.readDone(error_t result, u_int16_t val){
		if (result == SUCCESS) {
			printf ("Temperature : ");
			printfFloat(convertVoltToTemperature(val));
			printf (" C\n");
		} else {
			printf ("Error in temperature getting\n");
		}
	}

	event void HumidityRead.readDone(error_t result, u_int16_t val){
		if (result == SUCCESS) {
			printf ("Humidity : ");
			printfFloat(convertVoltToHumidity(val));
			printf (" %%\n");
		} else {
			printf ("Error in humidity getting\n");
		}
	}

	event void LightRead.readDone(error_t result, u_int16_t val){
		if (result == SUCCESS) {
			printf ("Light : ");
			printfFloat(convertVoltToLight(val));
			printf (" Lux\n");
		} else {
			printf ("Error in light getting\n");
		}
	}

	event void VoltageRead.readDone(error_t result, u_int16_t val){
		if (result == SUCCESS) {
			printf ("Voltage : ");
			printfFloat(convertVoltageToVolt(val));
			printf (" Volt\n");
		} else {
			printf ("Error in Voltage getting\n");
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

		rcmReceived = (radio_msg_t*)payload;
		printf("%s \n", (char *) rcmReceived->text);
		return bufPtr;
	}

	// needed to serial control
	event void SerialControl.startDone(error_t err) {
	}

	event void SerialControl.stopDone(error_t err) {
		printf("Le port serial est arretee");
	}

	event void SerialAMSend.sendDone(message_t* bufPtr, error_t error) {
		if (&packetSerial == bufPtr) {
			lockedSerial = FALSE;
		}
	}

	event message_t* SerialReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
		if (len != sizeof(serial_msg_t))
			return bufPtr;

		rcmSerialReceived = (serial_msg_t*)payload;
		printf("%s \n", (char *) rcmSerialReceived->text);
		return bufPtr;
	}



}
