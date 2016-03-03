package BackgroundChanger;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {

	protected JPanel panel = new JPanel();
	
	public GUI(String windowTitle, Dimension dim){
		setSize(dim);
		FlowLayout layout = new FlowLayout();
		panel.setLayout(layout);
		panel.setSize(this.getWidth(), this.getHeight());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(panel);
		
	}
	
	
	
}
