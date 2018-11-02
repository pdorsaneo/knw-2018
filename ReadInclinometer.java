import rxtxrobot.*;

public class ReadInclinometer {
	
	public static void main(String[] args) {

		// connect to the Arduino
		r = new ArduinoUno();

		// COM3 or COM4?
		r.setPort("COM3");

		r.connect();

		// Get the inclinometer reading
		double inclinometerReading = getInclinometerReading();

		// Print the results

		System.out.println("The inclinometer reads the value: " + inclinometerReading);
	
		r.close();

	}// end main

	public static int getInclinometerReading() {

		r.refreshAnalogPins();

		int reading = r.getAnalogPin(0).getValue();

		return reading;

	}// end getInclinometerReading

	public static RXTXRobot r;

	
}//end inclinometer class