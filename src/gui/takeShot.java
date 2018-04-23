package gui;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import textfiles.writerClass;
/**
 * Class to randomly take a shot
 * @author jonathangilbert
 */
public class takeShot {

	/**
	 * Method
	 * 
	 */
	public static int[] fire(char[][] verifyShot) throws IOException, InterruptedException {
		
		//Writing constructor for the log of events during the game
		writerClass battlelog = new writerClass(".\\battlelog.txt", true);
		
		//Array to store the two possible delay times: 0.1 & 0.5
		long[] delay = new long[2];
		
		//Tracker for whether the shot was taken
		boolean fired = false;
		
		//Array to store shot coordinates
		int[] target = new int[4];
		target[2] = -1;
		target[3] = -1;


//		System.out.println("Generating shot");
		//While not an unused coordinate, generate a shot
		while (!fired) {
			Random random = new Random();
			target[0] = random.nextInt(9) + 1;
			target[1] = random.nextInt(12) + 1;
			if (verifyShot[target[0]][target[1]] == 'N') {
				verifyShot[target[0]][target[1]] = 'Y';
				fired = true;
			}
		}
//		System.out.println("Shot generated");
		
		//Randomly select the delay for the shot
		Random random = new Random();
		int i = random.nextInt(2);
		delay[0] = (long) 0.1;
		delay[1] = (long) 0.5;
		
		//Delay the shot
		TimeUnit.SECONDS.sleep(delay[i]);
		
		//Record the shot and it's firing time
		final long shotAway = System.nanoTime()/1000000000 - gameClass.startTime;
		battlelog.writeToFile("Giving them hell at coordinates " + target[0] + "," + target[1] + " at t + "+ shotAway + " secs" + ", sir!");
		return target;
	}
}
