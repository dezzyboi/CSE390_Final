import network.Network_Main;
import network.Network_Main.Main_Thread;

import java.io.IOException;
import java.util.Scanner;

/**
 * This program creates a three way battleship game
 * that only requires the users to type in the 
 * host addresseses and click launch before it launches the game
 * and runs it. The winner is determined by the last man standing.
 * @author Gilbert, Jain, Wong
 *
 */
public class Project_Main {
	
	public static void main(String[] args) throws InterruptedException, IOException {

    	Network_Main start = new Network_Main();
		start.main(args);//runs the main of Network_Main
		

	}
}
