#ifndef TINY_SMART_AUTOMATION_H
#define TINY_SMART_AUTOMATION_H

#include <stdint.h>
#include <inttypes.h>

typedef nx_struct radio_msg_type {
	nx_int8_t text[20];
} radio_msg_t;

typedef nx_struct serial_msg {
  nx_uint16_t counter;
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
