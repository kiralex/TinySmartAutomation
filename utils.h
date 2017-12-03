#ifndef TINY_SMART_AUTOMATION_H
#define TINY_SMART_AUTOMATION_H

#include <stdint.h>
#include <inttypes.h>

// max radio message size : 28 Bytes
typedef nx_struct radio_msg_type {
								nx_uint8_t sensorID;
								nx_uint8_t roomID;
								nx_uint32_t temperature;
								nx_uint32_t humidity;
								nx_uint32_t brightness;
								nx_uint32_t voltage;
} radio_msg_t;

typedef nx_struct serial_msg {
								nx_uint8_t sensorID;
								nx_uint8_t roomID;
								nx_uint32_t temperature;
								nx_uint32_t humidity;
								nx_uint32_t brightness;
								nx_uint32_t voltage;
} serial_msg_t;

enum {
								AM_RADIO_MSG = 0xFF,
								AM_SERIAL_MSG = 0x89,
};

float convertVoltToHumidity (u_int16_t); // Convert voltage val (humidity) in humidity %
float convertVoltToTemperature (u_int16_t); // Convert voltage val (temperature) in Celsius degree %
float convertVoltToLight (u_int16_t); // Convert voltage val (LightVoltage) in Lux
float convertVoltageToVolt (u_int16_t); // Convert voltage val (voltage) in Volt %
void printfFloat(float); // print a float
void printfMessagePlusFloat(char *, float);


#endif
