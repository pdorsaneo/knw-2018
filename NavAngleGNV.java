

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT IV

import java.util.ArrayList;

import rxtxrobot.*;

public class NavAngleGNV {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();

		r.refreshDigitalPins(); // Cache the analog pin information


			ArrayList<Integer> G = new ArrayList<Integer>();
			ArrayList<Integer> N = new ArrayList<Integer>();
			ArrayList<Integer> V = new ArrayList<Integer>();
			
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
				case "N":
					System.out.println(letter);
					N.add(x);
					break;
				case "V":
					System.out.println(letter);
					V.add(x);
					break;
				
				}//end switch

				
			}//end for
			
			
			//Finding average angle of G
			double sumG = 0;
			for(int w = 0; w < G.size(); w++)
			{
				sumG = sumG + G.get(w);
			}
			double averageG = sumG / G.size();
			System.out.println("The average of G is " + averageG);
			
			
			
			//Finding average angle of N
			double sumN = 0;
			for(int w = 0; w < N.size(); w++)
			{
				sumN = sumN + N.get(w);
			}
			double averageN = sumN / N.size();
			System.out.println("The average of N is " + averageN);
			
			
			double sumV = 0;
			for(int w = 0; w < V.size(); w++)
			{
				sumV = sumV + V.get(w);
			}
			double averageV = sumV / V.size();
			System.out.println("The average of V is " + averageV);
			
			
			

			
			
			

			
			
			
			//Find the angle between M and N
			double angleGN = 0;
			angleGN = Math.abs(averageG - averageN);
			System.out.println("The angle between G and N is " + angleGN);
			
			
			
			//Find the angle between N and S
			double angleNV = 0;
			angleNV = Math.abs(averageN - averageV);
			System.out.println("The angle between N and V is " + angleNV);
			
			
			

			
	
			
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
    nav.setAngles(angleGN,angleNV); // input the angles OneIR method receives

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