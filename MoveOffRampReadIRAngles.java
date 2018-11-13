import rxtxrobot.ArduinoUno;
import rxtxrobot.RXTXRobot;


public class MoveOffRampReadIRAngles {
	
public static void main(String[] args) {
	RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object
	r.setPort("COM3"); // Set port to COM3
	r.connect(); 
	r.runTwoPCAMotor(5,-300,8,320,3700);
	r.close();
	
}
}
