configuration TinySmartAutomation{}
implementation{
	components MainC; // Main, needed to lauch app
	components LedsC; // Leds manage
	components Extends_Module; // My personnal modul where I test all components
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


	// Load executable components
	Extends_Module -> MainC.Boot;

	Extends_Module.RadioControl -> Radio;
	Extends_Module.RadioAMSend -> AMSenderC;
	Extends_Module.RadioReceive -> AMReceiverC;
	Extends_Module.RadioPacket -> AMSenderC;
	Extends_Module.RadioAMPacket -> Radio;


	// Use timers module, toggle light led
	Extends_Module.Timer0 -> Timer0;
	Extends_Module.Timer1 -> Timer1;
	Extends_Module.Timer2 -> Timer2;


	// Temperature Humidity and light, voltage components voltage value.
	Extends_Module.TempRead -> TempAndHumidSensor.Temperature;
	Extends_Module.HumidityRead -> TempAndHumidSensor.Humidity;
	Extends_Module.LightRead -> LightSensor;
	Extends_Module.VoltageRead -> VoltageSensor;

	// Load leds manage component
	Extends_Module.Leds -> LedsC;

	// load serial components
	Extends_Module.SerialControl -> Serial;
	Extends_Module.SerialReceive -> Serial.Receive[AM_SERIAL_MSG];
	Extends_Module.SerialAMSend -> Serial.AMSend[AM_SERIAL_MSG];
	Extends_Module.SerialPacket -> Serial;


}
