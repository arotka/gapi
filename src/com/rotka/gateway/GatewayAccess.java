package com.rotka.gateway;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GatewayAccess 
{
  public static void unlockDoor()
	{
    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();
    
    // provision gpio pin #01 as an output pin and turn on
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "DOOR_STRIKE", PinState.HIGH);
    System.out.println("--> Door strike should be open for only 5 second");
    pin.pulse(5000, true); 
    // stop all GPIO activity/threads by shutting down the GPIO controller
    // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
    gpio.shutdown();
	}
}