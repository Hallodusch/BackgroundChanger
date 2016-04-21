package BackgroundChanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SettingsWindow extends JFrame {

	public static final File SETTINGS = new File("src/settings.ini");
	public static final String[] INTERVALS = {"10min", "30min", "60min", "2h", "24h", "Neustart"};
	static JComboBox interval;
	static JCheckBox autostart;
	static SettingsReader reader = new SettingsReader();
	static SettingsWriter writer = new SettingsWriter();

	public SettingsWindow() {


		GridBagConstraints c = new GridBagConstraints();
		JPanel outerPanel = new JPanel(new GridBagLayout());
		JPanel innerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		JPanel intervalPanel = new JPanel(new GridLayout(1, 2));
		JPanel autostartPanel = new JPanel(new GridLayout(1, 2));
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		JButton confirmButton = new JButton("OK");
		JButton cancelButton = new JButton("Abbrechen");
		autostart = new JCheckBox();
		interval = new JComboBox(INTERVALS);
		JLabel titleLabel = new JLabel("Einstellungen");
		JLabel intervalLabel = new JLabel("Intervall: ");
		JLabel autostartLabel = new JLabel("Autostart: ");


		setContentPane(outerPanel);
		setTitle("Einstellungen");
		setMaximumSize(new Dimension(1000, 500));
		setSize(250, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		interval.setSelectedItem(reader.readInterval());
		intervalPanel.add(intervalLabel);
		intervalPanel.add(interval);

		autostart.setSelected(reader.readAutostart());
		autostartPanel.add(autostartLabel);
		autostartPanel.add(autostart);


		titleLabel.setFont(new Font(titleLabel.getFont().toString(), Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);


		cancelButton.addActionListener(this::closeWindow);
		confirmButton.addActionListener(this::confirmAction);
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);


		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
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
		pack();
		setVisible(true);

	}

	private void closeWindow(ActionEvent e){
		SwingUtilities.getWindowAncestor((JButton) e.getSource()).dispose();
	}

	private void confirmAction(ActionEvent e){
		String currentInterval = interval.getSelectedItem().toString();
		writer.writeInterval(currentInterval);
		boolean currentAutostart = autostart.isSelected();
		writer.writeAutostart(currentAutostart);

		closeWindow(e);
	}


}
