package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Class to construct a board for every player this computer is shooting at
 * @author jonathangilbert
 */
public class plyrBoard extends JFrame {
	
	//Components of the board, same as the main board 
	private Container contents;
	private static Color missed = Color.BLUE;
	private static Color hit = Color.RED;
	private static Color water = Color.CYAN;
	private static Color ship = Color.DARK_GRAY;

	//Create the board with the grid
	static JPanel[][] board = new JPanel[10][13];

	//Array to store x and y coordinates of a shot
	static int shot[] = new int[2];


	/**
	 * Constructor
	 *
	 */
	public plyrBoard() {
		
		//Set the general interface
		setTitle("Enemy Player");
		contents = getContentPane();
		contents.setLayout(new GridLayout(10,13));
		//Empty panel for top left corner
		board[0][0] = new JPanel();
		contents.add(board[0][0]);
		
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
			board[i][0] = new JPanel();
			board[i][0].add(label);
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
	}
	/**
	 * Method to process the results of a shot and update the enemy's board
	 * @param result - array with row and column and if it destroyed a ship and the entire fleet 
	 */
	public void shotResults(int[] result) {

		//If the enemy fleet has been destroyed, update title
		if(result[3] == 1) {
		}
		
		//If the shot missed, colour spot blue
		if(result[2] == 0) {
			plyrBoard.board[result[0]][result[1]].setBackground(missed);
			
		//If the shot hit, colour it red
		}if(result[2] == 1) {
			plyrBoard.board[result[0]][result[1]].setBackground(hit);
		}
	}
}



