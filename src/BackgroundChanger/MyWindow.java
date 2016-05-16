package BackgroundChanger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

class MyWindow extends JFrame{

	private final Changer changer = new Changer();

	private JLabel titleLabel;
	private JPanel outerPanel;
	private JPanel actionPanel;
	private JFileChooser chooseLocalFile;
	private JButton btnSubreddit;
	private JTextField textSubreddit;
	private JTextField textTumblr;
	private JButton btnTumblr;
	private JButton btnSettings;
	private JList listHistory;
	private JPanel historyPanel;
	private JPanel rootPanel;


	public MyWindow(){
		super("Background Changer");

		try{
			ImageIcon icon = new ImageIcon("./appicon.png");
			setIconImage(icon.getImage());
		}catch(NullPointerException e){
			e.printStackTrace();
		}


		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(rootPanel);
		setVisible(true);
		setLocationRelativeTo(null);


		btnSubreddit.addActionListener(e -> setInternetImage(textSubreddit.getText(), new RedditAPI()));
		btnTumblr.addActionListener(e -> setInternetImage(textTumblr.getText(), new TumblrAPI()));
		chooseLocalFile.addActionListener(e -> setLocalImage(chooseLocalFile.getSelectedFile()));
		btnSettings.addActionListener(e -> new SettingsWindow());

		chooseLocalFile.setFileFilter(new FileNameExtensionFilter("Bilddateien", "jpg", "png"));
		chooseLocalFile.setControlButtonsAreShown(false);

		updateHistoryList();

		pack();

	}


	private void updateHistoryList(){

		SettingsReader reader = new SettingsReader();
		ArrayList<DataHelper> iniFileList = (ArrayList) reader.readHistory();
		Collections.reverse(iniFileList);
		DefaultListModel listModel = new DefaultListModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date resultDate;
		for(DataHelper dh : iniFileList){
			resultDate = new Date(Long.parseLong(dh.getKey()));
			listModel.addElement(dateFormat.format(resultDate)+ ": " + dh.getValue());
		}
		listHistory.setModel(listModel);
		listHistory.updateUI();

	}

	public void setInternetImage(String subUrl, API api){
		if("".equals(subUrl)){
			JOptionPane.showMessageDialog(getContentPane(), "Sie haben kein Subreddit angegeben.");
		}else {
			if(changer.setImage(subUrl, api)){
				updateHistoryList();
			}

		}

	}

	private void setLocalImage(File selectedFile){
		if (!"".equals(selectedFile.getName())) {

			try {
				if (changer.useLocalImage(selectedFile)) {
					JOptionPane.showMessageDialog(getContentPane(), "Bild wurde erfolgreich geändert.\n"
							+ "Das neue Bild ist: " + selectedFile.getAbsolutePath());
				} else JOptionPane.showMessageDialog(getContentPane(), "Hintergrund nicht geändert. Bitte geben Sie ein Bild an.");
			} catch (Exception ex) { ex.printStackTrace(); }

		} else JOptionPane.showMessageDialog(getContentPane(), "Sie haben noch kein Bild ausgewählt.");
	}



}
