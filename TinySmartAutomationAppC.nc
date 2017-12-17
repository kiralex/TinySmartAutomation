#include "utils.h"

configuration TinySmartAutomationAppC {}
implementation {
  components MainC; // Main, needed to lauch app
	components LedsC; // Leds manage
	components BaseStationM as AppBaseStation; // My base station modules
	components SlaveM as AppSlave; // My base station modules

  components BootM as App;
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
  App.baseStation -> AppBaseStation;
  App.slave -> AppSlave;

  AppBaseStation.RadioControl -> Radio;
  AppBaseStation.RadioAMSend -> AMSenderC;
  AppBaseStation.RadioReceive -> AMReceiverC;
  AppBaseStation.RadioPacket -> AMSenderC;
  AppBaseStation.RadioAMPacket -> Radio;

  // Load leds manage component
  AppBaseStation.Leds -> LedsC;

  // load serial components
  AppBaseStation.SerialControl -> Serial;
  AppBaseStation.SerialReceive -> Serial.Receive[AM_SERIAL_MSG];
  AppBaseStation.SerialAMSend -> Serial.AMSend[AM_SERIAL_MSG];
  AppBaseStation.SerialPacket -> Serial;

  AppSlave.RadioControl -> Radio;
  AppSlave.RadioAMSend -> AMSenderC;
  AppSlave.RadioReceive -> AMReceiverC;
  AppSlave.RadioPacket -> AMSenderC;
  AppSlave.RadioAMPacket -> Radio;

  // Use timers module, toggle light led
  AppSlave.Timer0 -> Timer0;

  // Temperature Humidity and light, voltage components voltage value.
  AppSlave.TempRead -> TempAndHumidSensor.Temperature;
  AppSlave.HumidityRead -> TempAndHumidSensor.Humidity;
  AppSlave.LightRead -> LightSensor;
  AppSlave.VoltageRead -> VoltageSensor;

  // Load leds manage component
  AppSlave.Leds -> LedsC;


}
