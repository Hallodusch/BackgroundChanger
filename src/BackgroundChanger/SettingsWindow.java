package BackgroundChanger;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {

	public SettingsWindow() {
		String[] intervals = {"Intervall", "10min", "30min", "60min", "2h", "24h", "Neustart"};

		GridBagConstraints c = new GridBagConstraints();
		JPanel outerPanel = new JPanel(new GridBagLayout());
		JPanel innerPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		JPanel intervalPanel = new JPanel(new GridLayout(1, 2));
		JPanel autostartPanel = new JPanel(new GridLayout(1, 2));
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		JButton confirmButton = new JButton("OK");
		JButton cancelButton = new JButton("Abbrechen");
		JCheckBox autostart = new JCheckBox();
		JComboBox interval = new JComboBox(intervals);
		JLabel titleLabel = new JLabel("Einstellungen");
		JLabel intervalLabel = new JLabel("Intervall: ");
		JLabel autostartLabel = new JLabel("Autostart: ");


		setContentPane(outerPanel);
		setTitle("Einstellungen");
		setMaximumSize(new Dimension(1000, 500));
		setSize(250, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		interval.setSelectedIndex(0);
		intervalPanel.add(intervalLabel);
		intervalPanel.add(interval);

		autostartPanel.add(autostartLabel);
		autostartPanel.add(autostart);


		titleLabel.setFont(new Font(titleLabel.getFont().toString(), 1, 20));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);


		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		c.gridwidth = 1;
		outerPanel.add(titleLabel, c);
		c.gridy = 1;
		outerPanel.add(innerPanel, c);

		innerPanel.add(intervalPanel);
		innerPanel.add(autostartPanel);


		innerPanel.add(buttonPanel);

		setVisible(true);
	}


}
