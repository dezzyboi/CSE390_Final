package gui;
import java.awt.GridLayout;


import javax.swing.JFrame;
/**
 * Class to construct the game menu
 * @author jonathangilbert
 */
public class menuClass extends JFrame {
	
	//Construct the two textfields and the two buttons
	public static Address textB1 = new Address();
	public static Address textB2 = new Address();
	public static connectBTN conBTN = new connectBTN();
	public static readyBTN rdyBTN = new readyBTN();
	
	//Boolean to determine when the game is started
	public static boolean gameStart;
	
	//Constructor
	public menuClass(){
		
		//Add components to the menu
		setTitle("Battleship Menu");
		setLayout(new GridLayout(3,1));
		add(textB1);
		add(textB2);
		add(conBTN);
		
	}
}
