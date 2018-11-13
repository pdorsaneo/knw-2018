

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT III


import java.util.ArrayList;

import rxtxrobot.*;

public class NavAngleGVS {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();

		r.refreshDigitalPins(); // Cache the analog pin information


			ArrayList<Integer> G = new ArrayList<Integer>();
			ArrayList<Integer> K = new ArrayList<Integer>();
			ArrayList<Integer> S = new ArrayList<Integer>();
			
			r.refreshDigitalPins();
			
			for(int x = 0; x <= 180; x += 5)
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
			double sumG = 0;
			for(int w = 0; w < G.size(); w++)
			{
				sumG = sumG + G.get(w);
			}
			double averageG = sumG / G.size();
			System.out.println("The average of G is " + averageG);
			
			
			double sumK = 0;
			for(int w = 0; w < K.size(); w++)
			{
				sumK = sumK + K.get(w);
			}
			double averageK = sumK / K.size();
			System.out.println("The average of K is " + averageK);
			
			
			

			
			
			

			
			
			
			//Find the angle between G and V
			double angleGK = 0;
			angleGK = Math.abs(averageG - averageK);
			System.out.println("The angle between G and V is " + angleGK);
			
			
			
			//Find the angle between V and S
			double angleKS = 0;
			angleKS = Math.abs(averageK - averageS);
			System.out.println("The angle between V and S is " + angleKS);
			
			
			

			
	
			
    int retval;
    double[] soln;
    Navigation nav;
/*   if (args.length != 2) 
    {
      System.err.println("Usage: Example <theta a in degrees> <theta b in degrees>");
      return;
    }*/
// Create an instance of the Naigation object class
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
                         soln[0] + "," + soln[1] + ")");
    }
    else if (retval == Navigation.RETURN_RANGE) {
      System.err.println("Angle out of range");
    }
    else if (retval == Navigation.RETURN_SINGULAR) {
      System.err.println("Singular Jacobian matrix");
    }
    else if (retval == Navigation.RETURN_DIVERGENCE) {
      System.err.println("Convergence failure in 100 iterations");
    }
  }
}