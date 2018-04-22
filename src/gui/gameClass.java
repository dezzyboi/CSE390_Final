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
/**
 * 
 * @author jonathangilbert
 *
 */
public class gameClass extends JFrame {
	//All shared variables for this class
	
	//Time variable to store time elapse for specific events
    public static long startTime = System.nanoTime()/1000000000;
	
	//Writing constructor for the log of events during the game
	static writerClass battlelog = new writerClass(".\\battlelog.txt", true);
	
	//Main object that will hold the entire grid for the own player board
	private Container contents;
	private static JPanel[][] board = new JPanel[10][13];
	
	//Colors use to reflect game progress
	private static Color missed = Color.BLUE;
	private static Color hit = Color.RED;
	private static Color water = Color.CYAN;
	private static Color ship = Color.DARK_GRAY;
	
	//Array to track current state of each ship and the entire fleet
	boolean[] state = new boolean[6];
	boolean fleetSunk = true;
	boolean sunk = true;
	boolean dmg = true;
	
	//Proof of concept code
	//private static char[][] board = new char[12][9];
	
	//2D array to track all the ships and their row and column coordinates
	private static int[][] ships = new int[5][10];
	//Array for ship size
	private static int[] ssize = new int [5];
	//String array to store the ship classifications
	private static String[] shipClass = new String[10];
	
	//Virtual board to track whether or not shot has been fired at location
	private char[][] verifyShot = new char[10][13];


	//Array to store x and y coordinates of a shot
	static int shot[] = new int[2];

	/**
	 * Constructor for own board
	 * 
	 */
	public gameClass() throws IOException {
		
		//Clear the battlelog with any left overs from any previous game
		battlelog.delete();
		
		//Set the window title
		setTitle("Own Board");
		
		//Create a panel that already uses at 10x13 grid with gridlayout
		contents = getContentPane();
		contents.setLayout(new GridLayout(10,13));
		//Empty panel in top left corner
		board[0][0] = new JPanel();
		//Add everything to the board
		contents.add(board[0][0]);
		
		//Set all the ship classification
		shipClass[4] = "Destroyer";
		shipClass[3] = "Light Cruiser";
		shipClass[2] = "Heavy Cruiser";
		shipClass[1] = "Aircraft Carrier";
		shipClass[0] = "Battleship";
		//Nicknames for board
		shipClass[9] = "DD";
		shipClass[8] = "CL";
		shipClass[7] = "CH";
		shipClass[6] = "CV";
		shipClass[5] = "BB";
		
		//Loop to initiate the verification array
		for (int j = 1; j < 10; j++ ) {
			for (int k = 1; k < 13; k++) {
				verifyShot[j][k] = 'N'; 
			}
		}
		
		//Create labels for columns
		for (int i = 1; i < 13; i++) {
			JLabel label = new JLabel(Character.toString((char) (i+64)));
			board[0][i] = new JPanel();
			board[0][i].add(label);
			contents.add(board[0][i]);
		}
			
		//Fill the remainder of the board
		//For every row
		for (int i = 1; i < 10; i++) {
			int k = 0;
			//Insert a label in column zero with current row number
			JLabel label = new JLabel(Integer.toString(i));
			board[i][k] = new JPanel();
			board[i][k].add(label);
			contents.add(board[i][k]);
			//Colour the rest of the row with water
			while (k < 12) {
				k++;
				board[i][k] = new JPanel();
				board[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				board[i][k].setBackground(water);
				contents.add(board[i][k]);
			}
		}
		
		//Place the ships in a random pattern
		placeShips();
	}

	/**
	 * Method to place ships on the board by updating the board colors
	 * 
	 */
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
		
		//Create 2D array to track where method has already tried to place a ship
		char[][] verify = new char[10][13];
		for (int i = 0; i < ships.length; i++ ) {
			for (int j = 1; j < 10; j++ ) {
				for (int k = 1; k < 13; k++) {
					verify[j][k] = 'N'; 
				}
			}
			
			//Create a random construct
			Random random = new Random();
			//Track whether a ship has been placed
			boolean placed = false;
			while (!placed) {
				//Randomly select row and column location and store it in the ship location array
				ships[i][0] = random.nextInt(9) + 1;
				ships[i][1] = random.nextInt(12) + 1;
				//Check if spot has already been tried
				if(verify[ships[i][0]][ships[i][1]] == 'N') {
					verify[ships[i][0]][ships[i][1]] = 'Y';
					//If the colour is water
					if (board[ships[i][0]][ships[i][1]].getBackground().equals(water)) {
						//Generate a random orientation
						int orientation = random.nextInt(8);
						//0 = N, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW
						//Use method to test whether or not the ship fits
						if(testPlace(ssize[i], ships[i][0], ships[i][1], orientation)) {
							//Place the ship
							place(ships[i][0], ships[i][1], orientation, ships, i);
							placed = true;
						}
					}
				}
			}
		}
		
		//Check time it took to place the entire fleet & update the log
		final long shipPlacedTime = System.nanoTime()/1000000000 - startTime;
		battlelog.writeToFile("All ships placed, sir! at " + shipPlacedTime + " secs" );

	}
	
	/**
	 * Method to place a ship on the board by updating the board colors
	 * 
	 */
	private static void place(int r, int c, int orientation, int ships[][], int currShip) {
		//Int to track if row(0) or column(1)
		int nCoor = 0;
		
		//If ship is pointing North
		if(orientation == 0) {
			//For length of the ship
			for (int i = r; i >= r - (ssize[currShip] - 1); i--){
				//Create label for ship class
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				//Update the board color and add the label
				board[i][c].setBackground(ship);
				board[i][c].add(label);
				//Track where the one grid of the ship was placed
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = c;
				nCoor+=2;
			}
		}
		
		//If ship is pointing North East
		if(orientation == 1) {
			int i = r;
			int k = c;
			//While row and column total are less then ship length
			while (i >= r - (ssize[currShip] - 1) && k <= c + (ssize[currShip] - 1)) {
				//Create label for ship class
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				//Update the board color and add the label
				board[i][k].setBackground(ship);
				board[i][k].add(label);
				//Track where the one grid of the ship was placed
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i--;
				k++;
			}
		}
		
		//If ship is pointing East
		if(orientation == 2) {
			for (int i = c; i <= c + (ssize[currShip] -1); i++) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[r][i].setBackground(ship);
				board[r][i].add(label);
				ships[currShip][nCoor] = r;
				ships[currShip][nCoor+1] = i;
				nCoor+=2;
			}
		}
		
		//If ship is pointing South East
		if(orientation == 3) {
			int i = r;
			int k = c;
			while (i <= r + (ssize[currShip] - 1) && k <= c + (ssize[currShip] - 1)) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[i][k].setBackground(ship);
				board[i][k].add(label);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i++;
				k++;
			}
		}
		
		//If ship is pointing South
		if(orientation == 4) {
			for (int i = r; i <= r + (ssize[currShip] -1); i++) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[i][c].setBackground(ship);
				board[i][c].add(label);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = c;
				nCoor+=2;
			}
		}
		
		//If ship is pointing South West
		if(orientation == 5) {
			int i = r;
			int k = c;
			while (k >= c - (ssize[currShip] - 1) && i <= r + (ssize[currShip] - 1)) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[i][k].setBackground(ship);
				board[i][k].add(label);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i++;
				k--;
			}
		}
		
		//If ship is pointing West
		if(orientation == 6) {
			for (int i = c; i >= c - (ssize[currShip] -1); i--) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[r][i].setBackground(ship);
				board[r][i].add(label);
				ships[currShip][nCoor] = r;
				ships[currShip][nCoor+1] = i;
				nCoor+=2;
			}

		}
		
		//If ship is pointing North Westt
		if (orientation == 7) {
			int i = r;
			int k = c;
			while (k >= c - (ssize[currShip] - 1) && i >= r - (ssize[currShip] - 1)) {
				JLabel label = new JLabel(shipClass[currShip + 5]);
				label.setForeground(Color.RED);
				board[i][k].setBackground(ship);
				board[i][k].add(label);
				ships[currShip][nCoor] = i;
				ships[currShip][nCoor+1] = k;
				nCoor+=2;
				i--;
				k--;

			}
		}

	}

	/**
	 * Method to test if a ship fits in a specified orientation on a spot
	 * 
	 */
	private static boolean testPlace(int size, int r, int c, int orientation) {
		
		//To track if a ship fits
		boolean fits = true;
		
		//If it will point North
		if(orientation == 0) {
			//Tests if it goes out of the grid boundaries
			if (r - (size - 1) < 1) {
				fits = false;
			//Test if there are other ships along the ships length
			}else {
				for (int i = r; i >= r - (size - 1) && fits; i-- ) {
					//Updates whether it fits
					fits = fits & (board[i][c].getBackground().equals(water));
				}
			}
		}
		
		//If it will point North East
		if(orientation == 1) {
			//Tests if it goes out of the grid boundaries
			if (r - (size - 1) < 1 || c + (size - 1) >= 13) {
				fits = false;
			//Checks if there's a ship along the way, it will also check if a ship crosses in a perpendicular path
			}else {
				int i = r;
				int k = c;
				while (i >= r - (size - 1) && k <= c + (size - 1) && fits) {
					//Checks one above, one to the side then the diagonal
					if (i > 0 && k < 12) {
						fits = fits & (board[i][k].getBackground().equals(water)) & !((board[i-1][k].getBackground().equals(ship)) & (board[i][k+1].getBackground().equals(ship)));
					//Checks diagonal if in a corner or on the side
					} else {
						fits = fits & (board[i][k].getBackground().equals(water));
					}
					i--;
					k++;
				}
			}		
		}
		
		//If it will point East
		if(orientation == 2) {
			if (c + (size - 1) >= 13) {
				fits =  false;
			}else {
				for (int i = c; i <= c + (size -1) && fits; i++) {
					fits = fits & (board[r][i].getBackground().equals(water));
				}
			}
		}
		
		//If it will point South East
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
		
		//If it will point South
		if(orientation == 4) {
			if (r + (size - 1) >= 10) {
				fits = false;
			}else {
				for (int i = r; i <= r + (size -1) && fits; i++) {
					fits = fits & (board[i][c].getBackground().equals(water));
				}
			}
		}
		
		//If it will point South West
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
		
		//If it will point West
		if (orientation == 6) {
			if (c - (size - 1) < 1) {
				fits = false;
			}else {
				for (int i = c; i >= c - (size -1) && fits; i--) {
					fits = fits & (board[r][i].getBackground().equals(water));
				}
			}
		}
		
		//If it will point North West
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
		
		//Return whether it fits
		return fits;
	}

	/**
	 * Method to update own board with a shot received
	 * 
	 */
	public int[] shotRec(int[] shot) throws IOException {
		
		//Create a communication array, contains (Row)(Column)(Hit/Miss)(Fleet Sunk)(Player who shot)
		int[] result = new int[5];
		result[0] = shot[0];
		result[1] = shot[1];
		//Save at what time the shot was received
		final long registeredShot = System.nanoTime()/1000000000 - startTime;
		
		//Test whether it hit at every ships single grid coordinate
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ssize[i]*2 - 1; j+=2) {
				//Tests if the shot is on a ship target and whether that section has already been hit
				if(shot[0] == ships[i][j] && shot[1] == ships[i][j+1] && !board[ships[i][j]][ships[i][j+1]].getBackground().equals(hit)) {
					//Update board to show ship on fire
					board[shot[0]][shot[1]].setBackground(hit);
					//Save to the log
					battlelog.writeToFile("Friendly ship hit, sir! at row " + shot[0] + " column " + shot[1] + " at t + "+ registeredShot + " secs" );
					//Update result array
					result[2] = 1;
				}
				//If ship section was already hit
				if(!board[shot[0]][shot[1]].getBackground().equals(hit)){
					board[shot[0]][shot[1]].setBackground(missed);
					//Track whether a ship in the fleet has been hit
					dmg = dmg & false;
					result[2] = 0;
				}
			}
		}
		//If none of the ships were damaged, save as a miss
		if(!dmg ) {
			battlelog.writeToFile("The enemy missed, sir! Hooray! Splash spotted at row " + shot[0] + " column " + shot[1] + " at t + "+ registeredShot + " secs" );
		}
		//Check to see if any of the ships were sunk
		for (int i = 0; i < ships.length; i++) {
			sunk = true;
			//For every pair of coordinate
			for (int j = 0; j < ssize[i]*2 - 1; j+=2) {
				//Test if any of the ships' sections is still unscathed
				sunk = sunk & board[ships[i][j]][ships[i][j+1]].getBackground().equals(hit);
			}
			//Update whether a ship has been sunk
			state[i] = sunk;
			//Save to log
			if(state[i]) {
				battlelog.writeToFile("Friendly " + shipClass[i] + " sunk sir!");
			}
			//Updates whether the entire fleet has been sunk
			fleetSunk = fleetSunk & sunk;
			
		}
		//Updates fleet state
		state[5] = fleetSunk;
		result[3] = 0;
		if(state[5]) {
			battlelog.writeToFile("We lost our entire fleet, sir! The Admiral will surely be disappointed...");
			result[3] = 1;
		}
		return result;

	}
}


