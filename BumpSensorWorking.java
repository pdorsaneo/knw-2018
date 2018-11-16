
import java.util.Scanner;

import rxtxrobot.*;

public class BumpSensorWorking
{

	public static void main(String[] args)
	{

		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // Set the port to COM3

		r.connect();

		
		for(int i = 0; i < 4; i++)
		{
		
		while(r.getDigitalPin(7).getValue() == 1 && r.getDigitalPin(6).getValue() == 1)
		{
			
			r.refreshDigitalPins();
			
			r.runTwoPCAMotor(5, -200, 8, 250, 20);
			
			System.out.println(r.getDigitalPin(7).getValue());	
			
			System.out.println(r.getDigitalPin(6).getValue());
			

			
		}//end while
		
		r.sleep(1000);
		
		r.refreshDigitalPins();
		
		System.out.println(r.getDigitalPin(7).getValue());
			
		System.out.println(r.getDigitalPin(6).getValue());
		
		
		
		r.sleep(1000);
		
		
		r.runTwoPCAMotor(5, 200, 8, -250, 150);//move backwards
		
		r.sleep(1000);
		
		r.runTwoPCAMotor(5, 800, 8, 800, 220); //90 degree right
		
		r.sleep(1000);


		r.refreshDigitalPins();
		
		System.out.println(r.getDigitalPin(7).getValue());
			
		System.out.println(r.getDigitalPin(6).getValue());
		
		r.runTwoPCAMotor(5, -200, 8, 250, 400 );//move forward straight
		
		r.sleep(1000);
		
		r.runTwoPCAMotor(5, -800, 8, -800, 220); // 90 degree left
		
		r.sleep(1000);
		
		
		
		}//end while
		
		
		r.close();

	}//end main

	public static RXTXRobot r;

}//end class