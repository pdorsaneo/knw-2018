

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT IV

import java.util.ArrayList;

import rxtxrobot.*;

public class tempBoxHardCode {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();

		r.refreshDigitalPins(); // Cache the analog pin information
		r.runTwoPCAMotor(5,-300,8,320,3000);
		r.sleep(3000);

			ArrayList<Integer> V = new ArrayList<Integer>();
			ArrayList<Integer> K = new ArrayList<Integer>();
			ArrayList<Integer> S = new ArrayList<Integer>();
			
			r.refreshDigitalPins();
			
			for(int x = 0; x <= 180; x += 2)
			{
				
				//run servo (channel, position, duration)
				r.runPCAServo(0, x);
				char character = r.getIRChar();
				String letter = Character.toString(character);
				//System.out.println(letter);
				
				
				switch(letter) {

				case "V":
					System.out.println(letter);
					V.add(x);
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
			
			
			double sumV = 0;
			for(int w = 0; w < V.size(); w++)
			{
				sumV = sumV + V.get(w);
			}
			double averageV = sumV / V.size();
			System.out.println("The average of V is " + averageV);
			
			
			

			
			
			

			
			
			
			//Find the angle between M and N
			double angleKS = 0;
			angleKS = Math.abs(averageK - averageS);
			System.out.println("The angle between K and S is " + angleKS);
			
			
			
			//Find the angle between N and S
			double angleVS = 0;
			angleVS = Math.abs(averageS - averageV);
			System.out.println("The angle between S and V is " + angleVS);
			
			 // start directions
		     double angleDifference = angleKS - angleVS;
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
    nav.setAngles(angleKS,angleVS); // input the angles OneIR method receives

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
  // hard code
  		 r.runTwoPCAMotor(5, -800, 8, -800, 220); // 90 degree left
  		 r.sleep(2000);
  			r.runTwoPCAMotor(5, -200, 8, 250, 3700 ); // move straight
  		r.sleep(2000);
  		 r.runTwoPCAMotor(5, 800, 8, 800, 210); //90 degree right
  		 r.sleep(2000);
  		    // hard code
		r.runPCAServo(1, 140);
		r.sleep(3000);
		System.out.print(r.getConductivity());
		r.runPCAServo(1, 40);
  }
}