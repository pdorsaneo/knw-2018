// This example shows how to move the DC motors.

import rxtxrobot.*;

public class RunOneMotor
{
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3"); // Set port to COM3
		r.connect();
		
		//Run motor on channel 8 at speed 400 for 2000 milliseconds
		//r.runPCAMotor(8, 400, 2000);

		//Run motor on channel 5 at speed 250 for infinite time
		//r.runPCAMotor(5, 400, 2000);

		//Runs a motor on channel 2 at speed 100 and a motor on channel 3 at speed -200 for 5000 milliseconds
		r.runTwoPCAMotor(5/*channel#*/, 203, 8/*channel#*/, 207, 16000);
		r.close();
	}
}