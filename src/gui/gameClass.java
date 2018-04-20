package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import textfiles.writerClass;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class gameClass extends JFrame {
	private static long startTime = System.nanoTime()/1000000000;
	static writerClass battlelog = new writerClass(".\\battlelog.txt", true);
	private Container contents;
	private static Color missed = Color.BLUE;
	private static Color hit = Color.RED;
	private static Color water = Color.CYAN;
	private static Color ship = Color.DARK_GRAY;
	boolean fleetSunk = true;
	boolean sunk = true;
	boolean dmg = true;
	
	private static JPanel[][] board = new JPanel[10][13];

	//2D array to generate virtual board
	//private static char[][] board = new char[12][9];
	private static int[][] ships = new int[5][10];
	private static int[] ssize = new int [5];
	private static String[] shipClass = new String[5];
	private char[][] verifyShot = new char[10][13];
	private long[] delay = new long[2];


	//Array to store x and y coordinates of a shot
	static int shot[] = new int[2];



	public gameClass() throws IOException {
		battlelog.delete();
		setTitle("Own Board");
		contents = getContentPane();
		contents.setLayout(new GridLayout(10,13));
		board[0][0] = new JPanel();
		contents.add(board[0][0]);
		
		shipClass[4] = "Destroyer";
		shipClass[3] = "Light Cruiser";
		shipClass[2] = "Heavy Cruiser";
		shipClass[1] = "Aircraft Carrier";
		shipClass[0] = "Battleship";
		
		for (int j = 1; j < 10; j++ ) {
			for (int k = 1; k < 13; k++) {
				verifyShot[j][k] = 'N'; 
			}
		}
		
		for (int i = 1; i < 13; i++) {
			JLabel label = new JLabel(Character.toString((char) (i+64)));
			board[0][i] = new JPanel();
			board[0][i].add(label);
			contents.add(board[0][i]);
		}
			
		
		for (int i = 1; i < 10; i++) {
			int k = 0;
			JLabel label = new JLabel(Integer.toString(i));
			board[i][0] = new JPanel();
			board[i][0].add(label);
			contents.add(board[i][k]);
			while (k < 12) {
				k++;
				board[i][k] = new JPanel();
				board[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				board[i][k].setBackground(water);
				contents.add(board[i][k]);
			}
		}
		placeShips();
	}

	static void placeShips() throws IOException {
		//Ship sizes
		int BB = 5;
		int CV = 4;
		int CA = 3;
		int CL = 3;
		int DD = 2;
		ssize[4] = DD;
		ssize[3] = CL;
		ssize[2] = CA;
		ssize[1] = CV;
		ssize[0] = BB;
		
		char[][] verify = new char[10][13];
		Random random = new Random();
		for (int i = 0; i < ships.length; i++ ) {
			for (int j = 1; j < 10; j++ ) {
				for (int k = 1; k < 13; k++) {
					verify[j][k] = 'N'; 
				}
			}
			boolean placed = false;
			while (!placed) {
				ships[i][0] = random.nextInt(9) + 1;
				ships[i][1] = random.nextInt(12) + 1;
				if(verify[ships[i][0]][ships[i][1]] == 'N') {
					verify[ships[i][0]][ships[i][1]] = 'Y';
					if (board[ships[i][0]][ships[i][1]].getBackground().equals(water)) {
						int orientation = random.nextInt(8);
						//0 = N, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW
						if(testPlace(ssize[i], ships[i][0], ships[i][1], orientation)) {
							place(ships[i][0], ships[i][1], orientation, ships, i);
							placed = true;
						}
					}
				}
			}
		}
		final long shipPlacedTime = System.nanoTime()/1000000000 - startTime;
		battlelog.writeToFile("All ships placed, sir! at " + shipPlacedTime + " secs" );
//		for (int i = 0; i < 5; i++) {
//			System.out.println(Arrays.toString(ships[i]));
//	}

	}
	private static void place(int r, int c, int orientation, int ships[][], int currShip) {
		int nCoor = 0;
		if(orientation == 0) {
			for (int i = r; i >= r - (ssize[currShip] - 1); i--){
				board[i][c].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = c;
				nCoor+=2;
			}
		}
		if(orientation == 1) {
			int i = r;
			int k = c;
			while (i >= r - (ssize[currShip] - 1) && k <= c + (ssize[currShip] - 1)) {
				board[i][k].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i--;
				k++;
			}
		}
		if(orientation == 2) {
			for (int i = c; i <= c + (ssize[currShip] -1); i++) {
				board[r][i].setBackground(ship);
				ships[currShip][nCoor] = r;
				ships[currShip][nCoor+1] = i;
				nCoor+=2;
			}
		}
		if(orientation == 3) {
			int i = r;
			int k = c;
			while (i <= r + (ssize[currShip] - 1) && k <= c + (ssize[currShip] - 1)) {
				board[i][k].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i++;
				k++;
			}
		}
		if(orientation == 4) {
			for (int i = r; i <= r + (ssize[currShip] -1); i++) {
				board[i][c].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = c;
				nCoor+=2;
			}
		}
		if(orientation == 5) {
			int i = r;
			int k = c;
			while (k >= c - (ssize[currShip] - 1) && i <= r + (ssize[currShip] - 1)) {
				board[i][k].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i++;
				k--;
			}
		}
		if(orientation == 6) {
			for (int i = c; i >= c - (ssize[currShip] -1); i--) {
				board[r][i].setBackground(ship);
				ships[currShip][nCoor] = r;
				ships[currShip][nCoor+1] = i;
				nCoor+=2;
			}

		}
		if (orientation == 7) {
			int i = r;
			int k = c;
			while (k >= c - (ssize[currShip] - 1) && i >= r - (ssize[currShip] - 1)) {
				board[i][k].setBackground(ship);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i--;
				k--;

			}
		}

	}


	private static boolean testPlace(int size, int r, int c, int orientation) {
		boolean fits = true;
		if(orientation == 0) {
			if (r - (size - 1) < 1) {
				fits = false;
			}else {
				for (int i = r; i >= r - (size - 1) && fits; i-- ) {
					fits = fits & (board[i][c].getBackground().equals(water));
				}
			}
		}
		if(orientation == 1) {
			if (r - (size - 1) < 1 || c + (size - 1) >= 13) {
				fits = false;
			}else {
				int i = r;
				int k = c;
				while (i >= r - (size - 1) && k <= c + (size - 1) && fits) {
					if (i > 0 && k < 12) {
						fits = fits & (board[i][k].getBackground().equals(water)) & !((board[i-1][k].getBackground().equals(ship)) & (board[i][k+1].getBackground().equals(ship)));
					} else {
						fits = fits & (board[i][k].getBackground().equals(water));
					}
					i--;
					k++;
				}
			}		
		}
		if(orientation == 2) {
			if (c + (size - 1) >= 13) {
				fits =  false;
			}else {
				for (int i = c; i <= c + (size -1) && fits; i++) {
					fits = fits & (board[r][i].getBackground().equals(water));
				}
			}
		}
		if(orientation == 3) {
			if (r + (size - 1) >= 10 || c + (size - 1) >= 13){
				fits = false;
			}else {
				int i = r;
				int k = c;
				while (i <= r + (size - 1) && k <= c + (size - 1) && fits) {
					if (i < 8 && k < 12) {
						fits = fits & (board[i][k].getBackground().equals(water)) & !((board[i+1][k].getBackground().equals(ship)) & (board[i][k+1].getBackground().equals(ship)));
					} else {
						fits = fits & (board[i][k].getBackground().equals(water));
					}
					i++;
					k++;
				}
			}
		}
		if(orientation == 4) {
			if (r + (size - 1) >= 10) {
				fits = false;
			}else {
				for (int i = r; i <= r + (size -1) && fits; i++) {
					fits = fits & (board[i][c].getBackground().equals(water));
				}
			}
		}
		if(orientation == 5) {
			if (r + (size - 1) >= 10 || c - (size - 1) < 1){
				fits = false;
			}else {
				int i = r;
				int k = c;
				while (i <= r + (size - 1) && k >= c - (size - 1) && fits) {
					if (i < 8 && k > 0) {
						fits = fits & (board[i][k].getBackground().equals(water)) & !((board[i+1][k].getBackground().equals(ship)) & (board[i][k-1].getBackground().equals(ship)));
					} else {
						fits = fits & (board[i][k].getBackground().equals(water));
					}
					i++;
					k--;
				}
			}
		}
		if (orientation == 6) {
			if (c - (size - 1) < 1) {
				fits = false;
			}else {
				for (int i = c; i >= c - (size -1) && fits; i--) {
					fits = fits & (board[r][i].getBackground().equals(water));
				}
			}
		}
		if (orientation == 7) {
			if (c - (size - 1) < 1 || r - (size - 1) < 1) {
				fits = false;
			}else {
				int i = r;
				int k = c;
				while (i >= r - (size - 1) && k >= c - (size - 1) && fits) {
					if (i > 0 && k > 0) {
						fits = fits & (board[i][k].getBackground().equals(water)) & !((board[i-1][k].getBackground().equals(ship)) & (board[i][k-1].getBackground().equals(ship)));
					} else {
						fits = fits & (board[i][k].getBackground().equals(water));
					}
					i--;
					k--;
				}	
			}
		}
		return fits;
	}
//	public void printBoard() {
//		for (int i = 1; i < 10; i++) {
//			System.out.println(Arrays.toString(board[i]));
//		}
//	}
	
	public boolean[] shotRec(int[] shot) throws IOException {
		boolean[] state = new boolean[6];
		boolean fleetSunk = true;
		boolean sunk = true;
		boolean dmg = true;
		final long registeredShot = System.nanoTime()/1000000000 - startTime;
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ssize[i]*2 - 1; j+=2) {
				if(shot[0] == ships[i][j] && shot[1] == ships[i][j+1] && !board[ships[i][j]][ships[i][j+1]].getBackground().equals(hit)) {
					board[shot[0]][shot[1]].setBackground(hit);
					battlelog.writeToFile("Friendly ship hit, sir! at row " + shot[0] + " column " + shot[1] + " at t + "+ registeredShot + " secs" );
				}
				if(!board[shot[0]][shot[1]].getBackground().equals(hit)){
					board[shot[0]][shot[1]].setBackground(missed);
					dmg = dmg & false;
				}
			}
		}
		if(!dmg ) {
			battlelog.writeToFile("Enemy missed, sir! Hooray! Splash spotted at row " + shot[0] + " column " + shot[1] + " at t + "+ registeredShot + " secs" );
		}
		
		for (int i = 0; i < ships.length; i++) {
			sunk = true;
			for (int j = 0; j < ssize[i]*2 - 1; j+=2) {
				sunk = sunk & board[ships[i][j]][ships[i][j+1]].getBackground().equals(hit);
			}
			state[i] = sunk;
			if(state[i]) {
				battlelog.writeToFile("Friendly " + shipClass[i] + " sunk sir!");
			}
			fleetSunk = fleetSunk & sunk;
			
		}
		state[5] = fleetSunk;
		if(state[5]) {
			battlelog.writeToFile("We lost our entire fleet, sir! The Admiral will surely be disappointed...");
		}
		return state;

	}
	
	public void sitRep(boolean[] state) {
		String[] ships = new String[5];
		ships[4] = "Destroyer";
		ships[3] = "Light Cruiser";
		ships[2] = "Heavy Cruiser";
		ships[1] = "Aircraft Carrier";
		ships[0] = "Battleship";
		if (state[5]) {
			System.out.println();
			System.out.println("Game Over!");
		}
		for (int i = 0; i < 5; i++) {
			if(state[i]) {
				System.out.println(ships[i] + " sunk sir!");
			}
			else {
				System.out.println(ships[i] + " still in the fight sir!");
			}
		}
		System.out.println();
	}
	
	public int[] takeShot(boolean dead) throws IOException, InterruptedException {
		boolean fired = false;
		int[] target = new int[2];
		target[0] = -1;
		target[1] = -1;
		if (!dead) {
			while (!fired) {
				Random random = new Random();
				target[0] = random.nextInt(9) + 1;
				target[1] = random.nextInt(12) + 1;
				if (verifyShot[target[0]][target[1]] == 'N') {
					verifyShot[target[0]][target[1]] = 'Y';
					fired = true;
				}
			}
		}
		Random random = new Random();
		int i = random.nextInt(2);
		delay[0] = (long) 0.1;
		delay[1] = (long) 0.5;
		TimeUnit.SECONDS.sleep(delay[i]);
		final long shotAway = System.nanoTime()/1000000000 - startTime;
		battlelog.writeToFile("Giving them hell at coordinates " + target[0] + "," + target[1] + " at t + "+ shotAway + " secs" + ", sir!");
		return target;
	}
}


