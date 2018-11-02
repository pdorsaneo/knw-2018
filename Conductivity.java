import rxtxrobot.*;

public class Conductivity {

	public static void main(String[] args) {

		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();
		
		r.refreshAnalogPins();

		r.refreshDigitalPins();

		System.out.print(r.getConductivity());

		r.close();

	}// end main
	
	public static RXTXRobot r;

}// end class Conductivity