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
		Random random = new Random();
		int[] shot = new int[2];
		boolean state[] = new boolean[6];
		gameClass battlegame = new gameClass();
		battlegame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	battlegame.setSize(600, 425);
		battlegame.setVisible(true);
		plyrBoard plyr1 = new plyrBoard();
		plyr1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plyr1.setSize(600, 425);
		plyr1.setVisible(true);
		plyrBoard plyr2 = new plyrBoard();
		plyr2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plyr2.setSize(600, 425);
		plyr2.setVisible(true);
		menuClass menu = new menuClass();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(300, 225);
		menu.setVisible(true);
//		shot[0] = random.nextInt(9)+1;
//		shot[1] = random.nextInt(12)+1;
//		int[][] verify = new int[10][13];
//		for (int j = 1; j < 10; j++ ) {
//			for (int k = 1; k < 13; k++) {
//				verify[j][k] = 'N'; 
//			}
//		}
//		while(!state[5]) {
//			shot[0] = random.nextInt(9) + 1;
//			shot[1] = random.nextInt(12) + 1;
//			if (verify[shot[0]][shot[1]] == 'N') {
//				verify[shot[0]][shot[1]] = 'Y';
//				state = battlegame.shotRec(shot);
//			}
//		}

	
	}
}


