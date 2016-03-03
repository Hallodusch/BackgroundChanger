package BackgroundChanger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends GUI{

	private JButton changeBackground = new JButton("Hintergrund ändern");
	private JButton chooseFile= new JButton("Bild auswählen");
	private JFileChooser fileDialog = new JFileChooser("Bild auswählen");
	private JTextField fileText = new JTextField();
	private JLabel fileChooseLabel = new JLabel();
	private File selectedFile;

	public MainWindow(Dimension dim){
		super("Background Changer", dim);

		JPanel upperLeft = new JPanel(new FlowLayout());
		JPanel upperRight = new JPanel(new FlowLayout());
		JPanel lowerLeft = new JPanel(new FlowLayout());
		JPanel lowerRight = new JPanel(new FlowLayout());

		panel.add(upperLeft);
		panel.add(upperRight);
		panel.add(lowerLeft);
		panel.add(lowerRight);

		selectedFile = new File("");

		fileChooseLabel.setText("Wählen Sie ein Bild aus");
		fileChooseLabel.setFont(new Font(fileText.getFont().getName(), 1, fileText.getFont().getSize() + 4));

		fileText.setPreferredSize(new Dimension(150,20));
		fileText.setMinimumSize(new Dimension(80,20));
		fileText.setMaximumSize(new Dimension(300,20));
		fileText.setBackground(Color.WHITE);
		fileText.setFocusable(false);
		fileText.setEnabled(false);
		fileText.setDisabledTextColor(Color.BLACK);

		fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));

		fileText.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				chooseFile(e);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});

		chooseFile.addActionListener( e -> chooseFile(e));

		changeBackground.addActionListener(ae -> {

			if(!"".equals(selectedFile.getName())){

				try{

					if(Changer.changeBackground(selectedFile)){

						JOptionPane.showMessageDialog(panel, "Bild wurde erfolgreich geändert.\n"
								+ "Das neue Bild ist: " + selectedFile.getAbsolutePath());

					}else JOptionPane.showMessageDialog(panel, "Hintergrund nicht geändert. Bitte geben Sie ein Bild an.");

				}catch(Exception ex) { System.out.println(ex.toString()); }

			}else JOptionPane.showMessageDialog(panel, "Sie haben noch kein Bild ausgewählt.");

		});

		upperLeft.add(fileChooseLabel);
		lowerLeft.add(fileText);
		lowerLeft.add(chooseFile);
		lowerRight.add(changeBackground);

		pack();
		setVisible(true);
	}


	private void chooseFile(MouseEvent e){
		int result = fileDialog.showOpenDialog( (Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			fileText.setText(selectedFile.getAbsolutePath());
			fileText.setPreferredSize(new Dimension((int) (fileText.getText().length() * 5.6),20));
			pack();
			setLocationRelativeTo(null);

		}
	}

	private void chooseFile(ActionEvent e){
		int result = fileDialog.showOpenDialog( (Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			fileText.setText(selectedFile.getAbsolutePath());
			fileText.setPreferredSize(new Dimension((int) (fileText.getText().length() * 5.6),20));
			pack();
			setLocationRelativeTo(null);

		}
	}


}
