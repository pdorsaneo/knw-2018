

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT IV

import java.util.ArrayList;

import rxtxrobot.*;

public class NavAngleMNS {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();

		r.refreshDigitalPins(); // Cache the analog pin information


			ArrayList<Integer> N = new ArrayList<Integer>();
			ArrayList<Integer> M = new ArrayList<Integer>();
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

				case "N":
					System.out.println(letter);
					N.add(x);
					break;
				case "S":
					System.out.println(letter);
					S.add(x);
					break;
				case "M":
					System.out.println(letter);
					M.add(x);
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
			double sumM = 0;
			for(int w = 0; w < N.size(); w++)
			{
				sumM = sumM + M.get(w);
			}
			double averageM = sumM / M.size();
			System.out.println("The average of M is " + averageM);
			
			
			double sumN = 0;
			for(int w = 0; w < N.size(); w++)
			{
				sumN = sumN + N.get(w);
			}
			double averageN = sumN / N.size();
			System.out.println("The average of N is " + averageN);
			
			
			

			
			
			

			
			
			
			//Find the angle between M and N
			double angleMN = 0;
			angleMN = Math.abs(averageM - averageN);
			System.out.println("The angle between M and N is " + angleMN);
			
			
			
			//Find the angle between N and S
			double angleNS = 0;
			angleNS = Math.abs(averageN - averageS);
			System.out.println("The angle between N and S is " + angleNS);
			
			
			

			
	
			
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
    nav.setAngles(angleMN,angleNS); // input the angles OneIR method receives

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