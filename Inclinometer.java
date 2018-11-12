import rxtxrobot.*;

public class Inclinometer {
	
	public static int getInclinometerReading() {
		
		int sum = 0;
		int readingCount = 50;
		int average = 0;

		//read the analog pin values ten times, adding to sum each time
		for (int i = 0; i <= readingCount; i++) {
			
			r.refreshAnalogPins();

			int reading = r.getAnalogPin(1).getValue();
			sum += reading;
			average = sum / readingCount;
			
		} // end for

		return average;

	}// end getInclinometerReading

	public static RXTXRobot r;

	public static void main(String[] args) {
		
		// connect to the Arduino
		r = new ArduinoUno();

		r.setPort("COM3");

		r.connect();

		r.refreshAnalogPins();

		// Get the average inclinometer reading
		double inclinometerReading = getInclinometerReading();

		// Print the results

		System.out.println("The inclinometer reads the value: " + inclinometerReading);
		
		double  angle = (0.186813186813 * inclinometerReading) - 89.2967032967;
		
		System.out.println("The angle is: " + angle);
	
		r.close();
		
	}// end main

}//end inclinometer class