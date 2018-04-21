package gui;
import java.awt.GridLayout;


import javax.swing.JFrame;

public class menuClass extends JFrame {
	public static Address textB1 = new Address();
	public static Address textB2 = new Address();
	public static readyBTN rdyBTN = new readyBTN();

	public menuClass(){
		setTitle("Battleship Menu");
		setLayout(new GridLayout(3,1));
		add(textB1);
		add(textB2);
		add(rdyBTN);
		
	}
}
