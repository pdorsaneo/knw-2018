import rxtxrobot.*;

public class pingandmotor 
{
	final private static int PING_PIN = 13;

	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3"); // Set the port to COM3
		r.connect();
		//int x = 0;
		while (r.getPing(PING_PIN) > 20)
		{
			//Read the ping sensor value, which is connected to pin 13
			System.out.println("Response: " + r.getPing(PING_PIN) + " cm");
			r.sleep(300);
			//if (r.getPing(PING_PIN) > 20)
			
			
			
			r.refreshDigitalPins();
			//if(r.getPing(PING_PIN) > 20)
			//{
				r.runTwoPCAMotor(5/*channel#*/, 200, 8/*channel#*/, 200, 500);
			//}
			//else
			//r.close();
			
		}
		r.close();
	}
}
