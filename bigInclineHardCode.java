

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT IV

import java.util.ArrayList;

import rxtxrobot.*;

public class bigInclineHardCode {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();

		r.refreshDigitalPins(); // Cache the analog pin information
		r.runTwoPCAMotor(5,-300,8,320,3000);
		r.sleep(3000);

			ArrayList<Integer> G = new ArrayList<Integer>();
			ArrayList<Integer> K = new ArrayList<Integer>();
			ArrayList<Integer> S = new ArrayList<Integer>();
			
			r.refreshDigitalPins();
			
			for(int x = 0; x <= 180; x += 4)
			{
				
				//run servo (channel, position, duration)
				r.runPCAServo(0, x);
				char character = r.getIRChar();
				String letter = Character.toString(character);
				//System.out.println(letter);
				
				
				switch(letter) {

				case "G":
					System.out.println(letter);
					G.add(x);
					break;
				case "S":
					System.out.println(letter);
					S.add(x);
					break;
				case "K":
					System.out.println(letter);
					K.add(x);
					break;
				
				}//end switch

				
			}//end for
			
			
			//Finding average angle of G
			double sumS = 0;
			for(int w = 0; w < S.size(); w++)
			{
				sumS = sumS + S.get(w);
			}
			double averageS = sumS / S.size();
			System.out.println("The average of S is " + averageS);
			
			
			
			//Finding average angle of N
			double sumK = 0;
			for(int w = 0; w < K.size(); w++)
			{
				sumK = sumK + K.get(w);
			}
			double averageK = sumK / K.size();
			System.out.println("The average of K is " + averageK);
			
			
			double sumG = 0;
			for(int w = 0; w < G.size(); w++)
			{
				sumG = sumG + G.get(w);
			}
			double averageG = sumG / G.size();
			System.out.println("The average of G is " + averageG);
			
			
			
			//Find the angle between M and N
			double angleGK = 0;
			angleGK = Math.abs(averageG - averageK);
			System.out.println("The angle between G and K is " + angleGK);
			
			
			
			//Find the angle between N and S
			double angleKS = 0;
			angleKS = Math.abs(averageK - averageS);
			System.out.println("The angle between K and S is " + angleKS);	
			
    int retval;
    double[] soln;
    Navigation nav;
/*   if (args.length != 2) 
    {
      System.err.println("Usage: Example <theta a in degrees> <theta b in degrees>");
      return;
    }*/
// Create an instance of the Navigation object class
    nav = new Navigation();
// The two beacon angle differences can be set and the solver run any number of times
    nav.setAngles(angleGK,angleKS); // input the angles OneIR method receives

// Run solver to find unknown robot coordinates
// RETURN_RANGE, RETURN_SUCCESS, RETURN_SINGULAR, and RETURN_DIVERGENCE are error codes from our code, don't worry about them
    retval = nav.newton_raphson();
    if (retval == Navigation.RETURN_SUCCESS) {
// Retrieve solution of coordinates
      soln = nav.getSolution();
      System.out.println("(x,y) coordinates of robot = (" +
                         (soln[0]) + "," + soln[1] + ")");
    }
   // double y2 = 0;
   // double x2 = 0;
   // double rotateAngle = 0;
    
    //double a = ((y2 - soln[1])/ (x2 - soln[0]));
    //rotateAngle =  Math.atan(a);

     if (retval == Navigation.RETURN_RANGE) {
      System.err.println("Angle out of range");
    }
    else if (retval == Navigation.RETURN_SINGULAR) {
      System.err.println("Singular Jacobian matrix");
    }
    else if (retval == Navigation.RETURN_DIVERGENCE) {
      System.err.println("Convergence failure in 100 iterations");
    }
     
     r.sleep(2000);
     
     
     // start directions
     double angleDifference = angleGK - angleKS;
     System.out.println("The angle difference is:"+angleDifference);
		if(angleDifference > 0 && angleDifference > -10) {
			r.runTwoPCAMotor(5,-800,8,-800,50); 
			r.sleep(2000);
			r.runTwoPCAMotor(5, -200, 8, 250, 20);
			r.sleep(2000);
			// turn 45 degrees left
		}
		
		else if(angleDifference < 0 && angleDifference < 10) {
			r.runTwoPCAMotor(5,800,8,800,30);
			// turn 45 degrees right
			r.sleep(2000);
			r.runTwoPCAMotor(5, -200, 8, 250, 400 );
		}
		else if (angleDifference > 0 && angleDifference < -10) {
			r.runTwoPCAMotor(5, -800, 8, -800, 220);
			// turn 90 degrees left
			r.sleep(2000);
			r.runTwoPCAMotor(5, -200, 8, 250, 400 );
		}
		else if(angleDifference < 0 && angleDifference > 10) {
			r.runTwoPCAMotor(5, 800, 8, 800, 220);
			// turn 90 degrees right
			r.sleep(2000);
			r.runTwoPCAMotor(5, -200, 8, 250, 400 );
		}
		else if(angleDifference == 0) {
			r.runTwoPCAMotor(5,-300,8,320,1000);
			r.sleep(2000);
	
		}
		

	 // end directions
		
     // start inclinometer
     r.sleep(3000);
		int sum = 0;
		int readingCount = 50;
		int average = 0;
	
		for (int i = 0; i <= readingCount; i++) {
			r.refreshAnalogPins();
			int reading = r.getAnalogPin(1).getValue();
			sum += reading;
			average = sum / readingCount;
		} // end for
		//read the analog pin values ten times, adding to sum each time
		System.out.println("The inclinometer reads the value: " + average);
		double  angle = (0.186813186813 * average) - 89.2967032967;
		System.out.println("The angle is: " + angle);
		
	// end inclinometer
	    // hard code
		 r.runTwoPCAMotor(5, -800, 8, -800, 220); // 90 degree left
		 r.sleep(2000);
			r.runTwoPCAMotor(5, -200, 8, 250, 3700 ); // move straight
		r.sleep(2000);
		 r.runTwoPCAMotor(5, 800, 8, 800, 210); //90 degree right
		 r.sleep(2000);
		    // hard code
		r.close();
}
  
  {
	
  } // end get
}