package gui;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

public class GUI_Main {
	public static String IP1;
	public static String IP2;
	public static int Port1;
	public static int Port2;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int[] shot = new int[2];
//		gameClass battlegame = new gameClass();
//		battlegame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	battlegame.setSize(600, 425);
//		battlegame.setVisible(true);
//		plyrBoard plyr1 = new plyrBoard();
//		plyr1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		plyr1.setSize(600, 425);
//		plyr1.setVisible(true);
//		plyrBoard plyr2 = new plyrBoard();
//		plyr2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		plyr2.setSize(600, 425);
//		plyr2.setVisible(true);
		menuClass menu = new menuClass();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(300, 225);
		menu.setVisible(true);
/*		for (int i = 0; i < 10; i++) {
			shot = battlegame.takeShot(false);
			battlegame.shotRec(shot);
		}*/
	
	}
}


