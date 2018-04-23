package gui;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Class to create the a text field to input an address
 * @author jonathangilbert
 */
public class Address extends JPanel{
	//Global variables address and labels
	JTextField address;
	JLabel label;
	
	/**
	 * Constructor
	 * 
	 */
	public Address () {
		
		//Specifies the panel layout
		setLayout(new GridLayout(2,1));
		//Creates the text field
		address = new JTextField(22);
		//Creates the label
		label = new JLabel ("Enter an address: ");
		label.setLayout(new FlowLayout());
		//fills the panel
		address.setLayout(new FlowLayout());
		address.setToolTipText("Enter the address of an enemy you wish to annihilate!");
		add(label);
		add(address);
	}

}
