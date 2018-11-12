
import rxtxrobot.*;

public class BumpSensor
{

	public static void main(String[] args)
	{

		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // Set the port to COM3

		r.connect();
		
		while (r.getDigitalPin(5).getValue() != 0 && r.getDigitalPin(6).getValue() != 0)
		{
			r.runTwoPCAMotor(5, -200, 8, 250, 7000);
			 
			r.refreshAnalogPins();
			r.refreshDigitalPins();
			
			System.out.println(r.getDigitalPin(5).getValue());
			
			System.out.println(r.getDigitalPin(6).getValue());	

		}//end while

		r.close();

	}//end main
	
	public static RXTXRobot r;

}//end class