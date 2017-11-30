#ifndef __TINYSMARTAUTOMATION_C__
#define __TINYSMARTAUTOMATION_C__

#include <stdint.h>
#include <inttypes.h>
#include <stdlib.h>
#include <math.h>
#include <stdio.h>
#include <string.h>

#include "TinySmartAutomation.h"


/**
 * Convert voltage val (humidity) in humidity %
 */
float convertVoltToHumidity (u_int16_t humidity) {
	return -2.0468+0.0367 * humidity - 1.5955*pow(10,-6) * pow(humidity,2) ;
}

/**
 * Convert voltage val (temperature) in Celsius degree %
 */
float convertVoltToTemperature (u_int16_t Celsius) {
	return -39.6 + 0.01 * Celsius ;
}


/**
 * Convert voltage val (LightVoltage) in Lux
 */
float convertVoltToLight (u_int16_t LightVoltage) {
	return 3 * (LightVoltage/4096.0) * 6250.0;
}

/**
 * Convert voltage val (voltage) in Volt %
 */
float convertVoltageToVolt (u_int16_t voltage) {
	return 3 * (voltage/4096.0);
}

void printfFloat(float toBePrinted) {
   uint32_t fi, f0, f1, f2;
   char c;
   float f = toBePrinted;

   if (f<0){
     c = '-'; f = -f;
   } else {
     c = ' ';
   }

   // integer portion.
   fi = (uint32_t) f;

   // decimal portion...get index for up to 3 decimal places.
   f = f - ((float) fi);
   f0 = f*10;   f0 %= 10;
   f1 = f*100;  f1 %= 10;
   f2 = f*1000; f2 %= 10;
   printf("%c%ld.%d%d%d", c, fi, (uint8_t) f0, (uint8_t) f1, (uint8_t) f2);
 }

#endif
