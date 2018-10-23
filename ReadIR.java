// This example shows how to move the DC motors.

import rxtxrobot.*;

public class ReadIR
{
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); //Create RXTXRobot object
			r.setPort("COM3"); //sets the port to COM3
			r.connect();
			r.refreshDigitalPins(); //Cache the analog pin information
			
		for (int x=0; x < 10; ++x)
		{
			//DigitalPin c = r.getIRChar(x);
			//String IR = r.getIRChar();
			System.out.println("Sensor has value " + r.getIRChar());
			
		}//end for
		r.close();
	}//end main
}//end class ReadIR