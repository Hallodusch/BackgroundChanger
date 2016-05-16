package BackgroundChanger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;


class SettingsWindow extends JFrame {

	public static final File SETTINGS = new File("settings.ini");
	private static final SettingsWriter writer = new SettingsWriter();
	private static final SettingsReader reader = new SettingsReader();

	private final ButtonGroup bg;
	private File selectedFile;
	private JPanel rootPanel;
	private JLabel titleLabel;
	private JPanel settingsPanel;
	private JCheckBox chkAutostart;
	private JCheckBox chkSaveImages;
	private JTextField saveLocation;
	private JButton btnCancel;
	private JButton btnSave;
	private JLabel intervalLabel;
	private JLabel saveLocationLabel;
	private JLabel saveImageLabel;
	private JLabel autostartLabel;
	private JLabel intervalEnableLabel;
	private JCheckBox chkInterval;
	private JRadioButton rad10;
	private JRadioButton rad30;
	private JRadioButton rad60;
	private JRadioButton rad2h;
	private JRadioButton rad10h;
	private JRadioButton radRestart;

	public SettingsWindow(){
		super("Einstellungen");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setContentPane(rootPanel);
		setVisible(true);
		setLocationRelativeTo(getParent());

		bg = new ButtonGroup();
		bg.add(rad10);
		bg.add(rad30);
		bg.add(rad60);
		bg.add(rad2h);
		bg.add(rad10h);
		bg.add(radRestart);

		chkInterval.setSelected(Boolean.parseBoolean(reader.readSettings(Settings.IntervalToggle)));
		chkAutostart.setSelected(Boolean.parseBoolean(reader.readSettings(Settings.Autostart)));
		chkSaveImages.setSelected(Boolean.parseBoolean(reader.readSettings(Settings.LocalSaveToggle)));

		String interval = reader.readSettings(Settings.Interval);
		for(Enumeration<AbstractButton> b = bg.getElements(); b.hasMoreElements();) {
			AbstractButton ab = b.nextElement();
			if(ab.getText().equals(interval)) ab.setSelected(true);
		}

		selectedFile = new File(reader.readSettings(Settings.SaveLocation));
		saveLocation.setText(selectedFile.getAbsoluteFile().toString());

		saveLocation.addMouseListener(new ActionAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(selectedFile);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					saveLocation.setText(fileChooser.getSelectedFile().getAbsolutePath());
					selectedFile = fileChooser.getSelectedFile();
				}
			}
		});
		btnCancel.addActionListener(this::closeWindow);
		btnSave.addActionListener(this::confirmAction);

		pack();
	}

	private void closeWindow(ActionEvent e){
		SwingUtilities.getWindowAncestor((JButton) e.getSource()).dispose();
	}

	private void confirmAction(ActionEvent e){

		for(Enumeration<AbstractButton> b = bg.getElements(); b.hasMoreElements();) {
			AbstractButton ab = b.nextElement();
			if(ab.isSelected()){ writer.writeSettings(ab.getText(), Settings.Interval); break;}
		}
		writer.writeSettings(String.valueOf(chkAutostart.isSelected()), Settings.Autostart);
		writer.writeSettings(selectedFile.toString(), Settings.SaveLocation);
		writer.writeSettings(String.valueOf(chkSaveImages.isSelected()), Settings.LocalSaveToggle);
		writer.writeSettings(String.valueOf(chkInterval.isSelected()), Settings.IntervalToggle);

		BackgroundChanger.scheduleImageChange(true);

		closeWindow(e);
	}




}
