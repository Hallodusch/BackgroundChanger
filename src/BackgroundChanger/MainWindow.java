package BackgroundChanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

class MainWindow extends JFrame {

	private final JPanel outerPanel = new JPanel(new GridBagLayout());
	private File selectedFile = new File("");
	private final JFileChooser fileDialog = new JFileChooser("Bild auswählen");
	private final JTextField localFileText;
	private final JTextField subredditEntry;

	public MainWindow(Dimension dim) {

		GridBagConstraints c = new GridBagConstraints();

		JLabel titleLabel = new JLabel("Hintergrundbild ändern");
		JButton useLocalImage = new JButton("Hintergrund ändern");
		JButton chooseFile = new JButton("Bild auswählen");
		JButton subredditButton = new JButton("Bild aus Subreddit nehmen");
		JButton settingsButton = new JButton("Einstellungen");


		JPanel innerPanel = new JPanel(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel(new GridLayout(3, 1));
		JPanel localImagePanel = new JPanel(new GridLayout(1, 2));
		JPanel subredditPanel = new JPanel(new GridLayout(1, 2));
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		//set settings for the window
		setWindow(dim);

		fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));

		localFileText = createTextField(new Dimension(150, 20), false, false);
		subredditEntry = createTextField(new Dimension(150, 20), true, true);

		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));

		localFileText.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				chooseFile(e);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});

		chooseFile.addActionListener(this::chooseFile);

		settingsButton.addActionListener(e -> new SettingsWindow());

		useLocalImage.addActionListener(ae -> {

			if (!"".equals(selectedFile.getName())) {

				try {

					if (Changer.useLocalImage(selectedFile)) {

						JOptionPane.showMessageDialog(getContentPane(), "Bild wurde erfolgreich geändert.\n"
								+ "Das neue Bild ist: " + selectedFile.getAbsolutePath());

					} else
						JOptionPane.showMessageDialog(getContentPane(), "Hintergrund nicht geändert. Bitte geben Sie ein Bild an.");

				} catch (Exception ex) { System.out.println(ex.toString()); }

			} else JOptionPane.showMessageDialog(getContentPane(), "Sie haben noch kein Bild ausgewählt.");

		});

		subredditButton.addActionListener(e -> setImage(subredditEntry.getText()));

		titleLabel.setHorizontalAlignment(JLabel.CENTER);


		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		c.gridwidth = 1;
		outerPanel.add(titleLabel, c);
		c.gridy = 1;
		outerPanel.add(innerPanel, c);


		outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		localImagePanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		subredditPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

		innerPanel.add(leftPanel);

		leftPanel.add(localImagePanel);
		leftPanel.add(subredditPanel);
		leftPanel.add(buttonPanel);

		localImagePanel.add(localFileText);
		localImagePanel.add(useLocalImage);

		subredditPanel.add(subredditEntry);
		subredditPanel.add(subredditButton);

		buttonPanel.add(settingsButton);

		pack();
		setVisible(true);
	}

	private void setImage(String url) {
		if("".equals(url)){
			JOptionPane.showMessageDialog(getContentPane(), "Sie haben kein Subreddit angegeben.");
		}else{
			BackgroundChanger.setImageFromReddit(url);
		}
	}

	private void setWindow(Dimension dim) {
		setTitle("Background Changer");
		setMaximumSize(new Dimension(1600, 900));
		setSize(dim);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(outerPanel);

		//Set Window look and feel (=style)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	private JTextField createTextField(Dimension dim, boolean focusable, boolean enabled) {
		JTextField localFileText = new JTextField();

		localFileText.setPreferredSize(dim);
		localFileText.setMinimumSize(new Dimension(80, 20));
		localFileText.setMaximumSize(new Dimension(300, 20));
		localFileText.setBackground(Color.WHITE);
		localFileText.setFocusable(focusable);
		localFileText.setEnabled(enabled);
		localFileText.setDisabledTextColor(Color.BLACK);

		return localFileText;
	}

	private File chooseFile(MouseEvent e) {
		int result = fileDialog.showOpenDialog((Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			localFileText.setText(selectedFile.getAbsolutePath());
			localFileText.setPreferredSize(new Dimension((int) (localFileText.getText().length() * 5.6), 20));
			pack();
			setLocationRelativeTo(null);
			return selectedFile;
		}
		return null;
	}

	private File chooseFile(ActionEvent e) {
		int result = fileDialog.showOpenDialog((Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			localFileText.setText(selectedFile.getAbsolutePath());
			localFileText.setPreferredSize(new Dimension((int) (localFileText.getText().length() * 5.6), 20));
			pack();
			setLocationRelativeTo(null);
			return selectedFile;

		}
		return null;
	}

}