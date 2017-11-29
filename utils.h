#ifndef UTILS_H_   /* Include guard */
#define UTILS_H_

#include <stdint.h>
#include <inttypes.h>
#include "message.h"

typedef nx_struct radio_msg_type {
	nx_int8_t text[20];
} radio_msg_t;

// to serial
typedef nx_struct serial_msg_type {
	nx_int8_t text[8];
} serial_msg_t;

enum {
  AM_RADIO_MSG = 0x88,
  AM_SERIAL_MSG = 0x89,
};



float convertVoltToHumidity (u_int16_t); // Convert voltage val (humidity) in humidity %
float convertVoltToTemperature (u_int16_t); // Convert voltage val (temperature) in Celsius degree %
float convertVoltToLight (u_int16_t); // Convert voltage val (LightVoltage) in Lux
float convertVoltageToVolt (u_int16_t); // Convert voltage val (voltage) in Volt %
void printfFloat(float); // print a float



#endif
