import network.Network_Main;
import network.Network_Main.Main_Thread;

import java.io.IOException;
import java.util.Scanner;

import gui.GUI_Main;


public class Project_Main {
	public static String IP1;
	public static String IP2;
	public static int Port1;
	public static int Port2;
	
	public static void main(String[] args) throws InterruptedException, IOException {

//		GUI_Main skrt = new GUI_Main();
//		skrt.main(args);
    	Network_Main start = new Network_Main();
		start.main(args);
		

	}
}
