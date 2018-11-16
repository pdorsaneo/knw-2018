

// THIS PROGRAM IS FOR NAVIGATING A QUADRANT USING ANGLES BETWEEN MN AND NS
// QUADRANT IV

import java.util.ArrayList;

import rxtxrobot.*;

// CINDERBLOCK QUADRANT

public class cinderBlock {
  public static void main(String args[]) {
	  
	  RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object

		r.setPort("COM3"); // sets the port to COM3

		r.connect();
		r.runTwoPCAMotor(5,-300,8,320,3000);
		r.sleep(3000);
		
		r.refreshDigitalPins(); // Cache the analog pin information


			ArrayList<Integer> K = new ArrayList<Integer>();
			ArrayList<Integer> M = new ArrayList<Integer>();
			ArrayList<Integer> S = new ArrayList<Integer>();
			
			r.refreshDigitalPins();
			
			for(int x = 0; x <= 180; x += 3)
			{
				
				//run servo (channel, position, duration)
				r.runPCAServo(0, x);
				char character = r.getIRChar();
				String letter = Character.toString(character);
				//System.out.println(letter);
				
				
				switch(letter) {

				case "K":
					System.out.println(letter);
					K.add(x);
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
			for(int w = 0; w < M.size(); w++)
			{
				sumM = sumM + M.get(w);
			}
			double averageM = sumM / M.size();
			System.out.println("The average of M is " + averageM);
			
			
			double sumK = 0;
			for(int w = 0; w < K.size(); w++)
			{
				sumK = sumK + K.get(w);
			}
			double averageK = sumK / K.size();
			System.out.println("The average of K is " + averageK);
			
			
			

			
			
			

			
			
			
			//Find the angle between M and N
			double angleKM = 0;
			angleKM = Math.abs(averageK - averageM);
			System.out.println("The angle between K and M is " + angleKM);
			
			
			
			//Find the angle between N and S
			double angleSK = 0;
			angleSK = Math.abs(averageS - averageK);
			System.out.println("The angle between S and K is " + angleSK);
			
			
			

			
	
			
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
    nav.setAngles(angleSK,angleKM); // input the angles OneIR method receives

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

	r.refreshDigitalPins(); // Cache the analog pin information






// bump sensor navigation (awful)		
	for(int i = 0; i < 2; i++)
			{
			
			while(r.getDigitalPin(7).getValue() == 1 && r.getDigitalPin(6).getValue() == 1)
			{
				
				r.refreshDigitalPins();
				
				r.runTwoPCAMotor(5, -200, 8, 250, 20);
				
				System.out.println(r.getDigitalPin(7).getValue());	
				
				System.out.println(r.getDigitalPin(6).getValue());
				

				
			}//end while
			
			r.sleep(1000);
			
			r.refreshDigitalPins();
			
			System.out.println(r.getDigitalPin(7).getValue());
				
			System.out.println(r.getDigitalPin(6).getValue());
			
			
			
			r.sleep(1000);
			
			
			r.runTwoPCAMotor(5, 200, 8, -250, 250);//move backwards
			
			r.sleep(1000);
			
			// swap with 
			r.runTwoPCAMotor(5, -800, 8, -800, 220);
			 //90 degree left
			//swap with
			
			r.sleep(1000);


			r.refreshDigitalPins();
			
			System.out.println(r.getDigitalPin(7).getValue());
				
			System.out.println(r.getDigitalPin(6).getValue());
			
			r.runTwoPCAMotor(5, -200, 8, 250, 5000 );//move forward straight
			
			r.sleep(1000);
			
			// swap with
			r.runTwoPCAMotor(5, 800, 8, 800, 220);
			 // 90 degree right
			// swap with
			
			r.sleep(1000);
			
			
			
			}//end while
			
			
			r.close();

	
	//end class
  }
}