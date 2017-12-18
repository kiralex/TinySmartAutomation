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
  radio_msg_t * rcmRadioReceived;
  bool lockedRadio = FALSE;

  message_t packetSerial;
  uint8_t typeSendSerial = 0;
  serial_msg_t* rcmSerialReceived;
  serial_msg_t* rcmSerialSend;
  bool lockedSerial = FALSE;

  bool enabled = FALSE;

  uint8_t nbSensors;
  uint32_t maxSensors = 99; // if greater than 600, not enough memory !
  uint8_t * knownSensors;
	uint8_t * roomIDs;
	float * temperatures;
	float * humidities;
	float * brightnesses;

  command void SwitchInterface.start(){
    enabled = TRUE;
    call SerialControl.start();
    call RadioControl.start();

    knownSensors = (uint8_t *) malloc(sizeof(uint8_t) * maxSensors);
    roomIDs = (uint8_t *) malloc(sizeof(uint8_t) * maxSensors);
    temperatures = (float *) malloc(sizeof(float) * maxSensors);
    humidities = (float *) malloc(sizeof(float) * maxSensors);
    brightnesses = (float *) malloc(sizeof(float) * maxSensors);
    call Leds.led1Toggle();
    if( knownSensors != NULL && roomIDs!= NULL && temperatures != NULL && humidities != NULL && brightnesses != NULL)
      call Leds.led1Toggle();
  }

  // send the sensors values received by radio to the serial port
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

      typeSendSerial = 1;
      if (call SerialAMSend.send(AM_BROADCAST_ADDR, &packetSerial, sizeof(serial_msg_t)) == SUCCESS) {
          lockedSerial = TRUE;
          return 0;
      }else{
          return -1;
      }
    }
  }

  // compute sensor information means of the radio message roomID
  void computeMeans(radio_msg_t * msg, float * meanHumidities, float * meanTemperatures, float * meanBrightnesses){
    uint8_t i, nbInRoom, id = msg->sensorID, roomID = msg->roomID;
    float sumHumidities = 0, sumTemperatures = 0, sumBrightnesses = 0;
    bool found = FALSE;

    // search if sensor already exist (same sensor id in room and same room id)
    for(i = 0; !found && i < nbSensors; i++)
      if(knownSensors[i] == id && roomIDs[i] == roomID)
        found = TRUE;

    // if found, remove one because for loop add 1
    if(found)
      i--;
    // else new sensor
    else nbSensors++;

    // copy sensors value to arrays
    memcpy(&knownSensors[i], &msg->sensorID, sizeof(nx_uint8_t));
    memcpy(&roomIDs[i], &msg->roomID, sizeof(nx_uint8_t));
    memcpy(&temperatures[i], &msg->temperature, sizeof(nx_uint32_t));
    memcpy(&humidities[i], &msg->humidity, sizeof(nx_uint32_t));
    memcpy(&brightnesses[i], &msg->brightness, sizeof(nx_uint32_t));

    nbInRoom = 0;
    // sum all values of all sensors 
    for(i = 0; i < nbSensors; i++){
      if(roomIDs[i] == roomID){
        sumHumidities+= humidities[i];
        sumTemperatures+= temperatures[i];
        sumBrightnesses+= brightnesses[i];
        nbInRoom++;
      }
    }
    // divide by the number of sensors in the room
    if(nbInRoom > 0){
      *meanHumidities = sumHumidities / (nbInRoom + 0.0);
      *meanTemperatures = sumTemperatures / (nbInRoom + 0.0);
      *meanBrightnesses = sumBrightnesses / (nbInRoom + 0.0);
    }
  }
  // send means of the specified roomID
  int sendMeans(uint8_t roomID, float meanHumidities, float meanTemperatures, float meanBrightnesses){
    // in the message, to detect it is a mean, use sensorID = 254
    uint8_t sensorID = 254;
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

      memcpy(&rcmSerialSend->sensorID, &sensorID, sizeof(nx_uint8_t));
      memcpy(&rcmSerialSend->roomID, &roomID, sizeof(nx_uint8_t));
      memcpy(&rcmSerialSend->humidity, &meanHumidities, sizeof(nx_uint32_t));
      memcpy(&rcmSerialSend->brightness, &meanBrightnesses, sizeof(nx_uint32_t));
      memcpy(&rcmSerialSend->temperature, &meanTemperatures, sizeof(nx_uint32_t));

      if (call SerialAMSend.send(AM_BROADCAST_ADDR, &packetSerial, sizeof(serial_msg_t)) == SUCCESS) {
        lockedSerial = TRUE;
        return 0;
      }else{
          return -1;
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
      call Leds.led2Toggle();

      if (len != sizeof(radio_msg_t))
        return bufPtr;

      rcmRadioReceived = (radio_msg_t*)payload;
      sendSerial(rcmRadioReceived);

    }
    return bufPtr;
  }



  event void SerialControl.startDone(error_t err) {
    if (err == SUCCESS) {
    }
  }
  event void SerialControl.stopDone(error_t err) {}

  event void SerialAMSend.sendDone(message_t* bufPtr, error_t error) {
    float meanBrightnesses = -99, meanHumidities = -99, meanTemperatures = -99;
    if (&packetSerial == bufPtr) {
      if(typeSendSerial == 1)
        computeMeans(rcmRadioReceived, &meanHumidities, &meanTemperatures, &meanBrightnesses);
      
      lockedSerial = FALSE;
      if(typeSendSerial == 1){
        typeSendSerial = 0;
        sendMeans(rcmRadioReceived->roomID, meanHumidities, meanTemperatures, meanBrightnesses);
      }
    }
  }

  event message_t* SerialReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
    return bufPtr;
  }


}
