package gui;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class board {
	public class Grid extends JFrame{
		private Container contents;
		private Color missed = Color.BLUE;
		private Color hit = Color.RED;
		private Color sunk = Color.BLACK;
		private Color unknown = Color.GRAY;
		
		private JPanel[][] board = new JPanel[13][10];
	}
}
