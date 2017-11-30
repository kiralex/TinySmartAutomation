#include "TinySmartAutomation.h"

configuration TinySmartAutomationAppC {}
implementation {
  components MainC; // Main, needed to lauch app
	components LedsC; // Leds manage
	components TinySmartAutomationC as App; // My personnal modul where I test all components
	components SerialPrintfC; // to use printf
	components SerialActiveMessageC as Serial; // to serial port

  // Timers (need to toggle on & off leds) manage module
	components new TimerMilliC() as Timer0, new TimerMilliC() as Timer1, new TimerMilliC() as Timer2;

  // Temperature and Humidity sensor
	components new SensirionSht11C() as TempAndHumidSensor;
	components new HamamatsuS10871TsrC() as LightSensor; // Light sensor components
	components new VoltageC() as VoltageSensor; // Voltage battery sensor

  // Radio manage
  components ActiveMessageC as Radio;
  components new AMSenderC(AM_RADIO_MSG);
  components new AMReceiverC(AM_RADIO_MSG);

  App.Boot -> MainC.Boot;

  App.RadioControl -> Radio;
  App.RadioAMSend -> AMSenderC;
  App.RadioReceive -> AMReceiverC;
  App.RadioPacket -> AMSenderC;
  App.RadioAMPacket -> Radio;

  // Use timers module, toggle light led
  App.Timer0 -> Timer0;
  App.Timer1 -> Timer1;
  App.Timer2 -> Timer2;

  // Temperature Humidity and light, voltage components voltage value.
  App.TempRead -> TempAndHumidSensor.Temperature;
  App.HumidityRead -> TempAndHumidSensor.Humidity;
  App.LightRead -> LightSensor;
  App.VoltageRead -> VoltageSensor;

  // Load leds manage component
  App.Leds -> LedsC;

  // load serial components
  App.SerialControl -> Serial;
  App.SerialReceive -> Serial.Receive[AM_SERIAL_MSG];
  App.SerialAMSend -> Serial.AMSend[AM_SERIAL_MSG];
  App.SerialPacket -> Serial;
}
