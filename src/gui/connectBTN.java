package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import network.Network_Main.Main_Thread;
/**
 * Class to create the establish connection button
 * @author jonathangilbert
 */
public class connectBTN extends JPanel{
	
	//Variables to share IP addresses betwqeen classes
	public static String IP1;
	public static String IP2;
	
	//Constructor
	public connectBTN () {
		// Declare and instantiate button
		setLayout(new FlowLayout());
		JButton connect;
		connect = new JButton("Establish Connections");
		connect.setToolTipText("Click to establish connections");
		connect.addActionListener(new conBTNLI());
		// Add button to the panel:
		add(connect);
	}
}

/**
 * Action listener for button
 *
 */
class conBTNLI implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
		//Grabs the two IP addresses from the textfields and sends it to the networking threads
		network.Network_Main.IP[0] = menuClass.textB1.address.getText();
		network.Network_Main.IP[1] = menuClass.textB2.address.getText();
		//Starts the client thread, one for each connection
		Main_Thread thread = new Main_Thread();
		try {
			thread.run2();

			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
