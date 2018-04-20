package gui;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Address extends JPanel{
	//Global variable
	JTextField address;
	JLabel label;
	public Address () {
		setLayout(new GridLayout(2,1));
		address = new JTextField(22);
		label = new JLabel ("Enter an address: ");
		label.setLayout(new FlowLayout());
		address.setLayout(new FlowLayout());
		address.setToolTipText("Enter the address of an enemy you wish to annihilate!");
		add(label);
		add(address);
	}

}
