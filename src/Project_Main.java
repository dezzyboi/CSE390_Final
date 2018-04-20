import network.Network_Main;

import java.util.Scanner;

import gui.GUI_Main;


public class Project_Main {
	public static String IP1;
	public static String IP2;
	public static int Port1;
	public static int Port2;
	
	public static void main(String[] args) throws InterruptedException {
		//Network_Main start = new Network_Main(IP1, IP2, Port1, Port2);
				//start.main(args);
				int[] ships = new int[5];
				//Ship sizes
				int BB = 5;
				int CV = 4;
				int CA = 3;
				int CL = 3;
				int DD = 2;
				ships[4] = DD;
				ships[3] = CL;
				ships[2] = CA;
				ships[1] = CV;
				ships[0] = BB;
				
				gameClass battlegame = new gameClass();
				battlegame.setBoard();
				battlegame.placeShips(ships);
				battlegame.printBoard();
				
		Network_Main start = new Network_Main();
		start.main(args);
		
	}
}
