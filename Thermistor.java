// This example shows how to get the Analog pin sensor data from the Arduino.  It shows the value of every Analog pin once after connecting to the Arduino.
import rxtxrobot.*;

public class Thermistor
{   
	public static int getThermistorReading()
	{
		int sum = 0;
		int readingCount = 10;
		
		//Read the analog pin values ten times, adding to sum each time
		for (int i = 0; i < readingCount; i++)
		{
			
			robot.refreshAnalogPins();
			int reading = robot.getAnalogPin(0).getValue();
			sum += reading;
			
		}//end for
		
		//return the average reading
		return sum / readingCount;
	}//end getThermistor
	
	public static RXTXRobot robot;
	
	//Your main method, where your program starts
	public static void main(String[] args)
	{
		//Connect to the arduino
		robot = new ArduinoUno();
		robot.setPort("COM3");
		robot.connect();
		
		//Get the average thermistor reading
		int thermistorReading = getThermistorReading();
		
		//Print the results
		System.out.println("The probe reads the value: " + thermistorReading);
		System.out.println("In volts: " + (thermistorReading * (5.0/1023.0)));
		double temperatureCelcius = (thermistorReading - 836.6879) / -12.2251;
		System.out.printf("The temperature is %f degrees celcius.",temperatureCelcius+1);
	}//end main
}//end class Thermistor