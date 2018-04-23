package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import network.Network_Main.Main_Thread;
/**
 * Class to create the start button button
 * @author jonathangilbert
 */
public class readyBTN extends JPanel{
	
	//Constructor
	public readyBTN () {
		// Declare and instantiate button
		setLayout(new FlowLayout());
		JButton rdy;
		rdy = new JButton("Start game!");
		rdy.setToolTipText("Click to start the game!");
		rdy.addActionListener(new rdyBTNLI());
		add(rdy);
	}
}

/**
 * Action listener for the button
 * 
 */
class rdyBTNLI implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		gui.menuClass.gameStart = true;


	}
}
