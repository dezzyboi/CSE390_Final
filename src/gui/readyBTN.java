package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class readyBTN extends JPanel{
	public readyBTN () {
		setLayout(new FlowLayout());
		// Declare and instantiate button
		JButton rdy;
		rdy = new JButton("Ready?!");
		rdy.setToolTipText("Click to establish connections and start the game!");
		rdy.addActionListener(new rdyBTNLI());
		//rdy.setForeground(Color.BLUE);
		// Add button to the panel:
		add(rdy);
	}
}

//Listener
class rdyBTNLI implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
		//Grabs the two dates from textfield
		String add1 = menuClass.textB1.address.getText();
		String add2 = menuClass.textB2.address.getText();
		
//		//Grabs the format selected form the radio buttons
//		String type = slect.formatBtn.getText();
//		
//		//String to change statement based on which date is larger
//		String state;
//		
//		//Create special & colour font for output textfield
//		Font bold = new Font(outputTB.result.getFont().getFontName(), Font.BOLD, outputTB.result.getFont().getSize());
//		outputTB.result.setFont(bold);
//		Color displ = new Color(240,230,140);
//		outputTB.result.setForeground(displ);
//		
//		//Call subtraction method on two dates and receive array with all the answers
//		float result[] = subtract.subtract(date1, date2);
//		
//		//Determine which date is the largest
//		if(result[7] == 0) {
//			state = "First date is bigger than second date by: ";
//		} else {
//			state = "First date is smaller than second date by: ";
//		}
//		
//		//Return years if years selected
//		if (type.equals("Years")) {
//			
//			//Specify rounding format
//			DecimalFormat roundedyr = new DecimalFormat("#.###");
//			roundedyr.setRoundingMode(RoundingMode.CEILING);
//			
//			//Enable output field
//			outputTB.result.setEnabled(true);
//			outputTB.result.setText(state + roundedyr.format(result[4]) + " years.");
//			outputTB.result.setBackground(Color.WHITE);
//		}
//		
//		//Return days if days selected
//		if (type.equals("Days")) {
//			
//			//Enable output field
//			outputTB.result.setEnabled(true);
//			
//			//Use math.round to convert to int
//			outputTB.result.setText(state + Math.round(result[6]) + " days.");
//			outputTB.result.setBackground(Color.WHITE);
//		}
//		
//		//Return months if months selected
//		if (type.equals("Months")) {
//			
//			//Specify rounding format
//			DecimalFormat roundedmo = new DecimalFormat("#.##");
//			roundedmo.setRoundingMode(RoundingMode.CEILING);
//			
//			//Enable output field
//			outputTB.result.setEnabled(true);
//			
//			outputTB.result.setText(state + roundedmo.format(result[5]) + " months.");
//			outputTB.result.setBackground(Color.WHITE);
//		}
//		
//		//Return weeks if weeks selected
//		if (type.equals("Weeks")) {
//			
//			//Specify rounding format
//			DecimalFormat roundedwe = new DecimalFormat("#.#");
//			roundedwe.setRoundingMode(RoundingMode.CEILING);
//			
//			//Enable output field
//			outputTB.result.setEnabled(true);
//			
//			outputTB.result.setText(state + roundedwe.format(result[3]) + " weeks.");
//			outputTB.result.setBackground(Color.WHITE);
//		}
//		
//		//Return y/m/d if y/m/d selected
//		if (type.equals("Years/Months/Days")) {
//			
//			//Specify rounding format
//			DecimalFormat roundedtot = new DecimalFormat("#");
//			roundedtot.setRoundingMode(RoundingMode.CEILING);
//			
//			//Enable output field
//			outputTB.result.setEnabled(true);
//			
//			outputTB.result.setText(state + roundedtot.format(result[0]) + " years, " + roundedtot.format(result[1]) + " months and " + roundedtot.format(result[2]) + " days.");
//			outputTB.result.setBackground(Color.WHITE);
//		}
	}
}
