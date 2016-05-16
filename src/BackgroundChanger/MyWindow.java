package BackgroundChanger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by ben on 15.05.2016.
 */
class MyWindow extends JFrame{

	private static final SettingsReader reader = new SettingsReader();

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

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(rootPanel);
		setVisible(true);
		setLocationRelativeTo(null);


		btnSubreddit.addActionListener(e -> setImage(textSubreddit.getText(), new RedditAPI()));
		btnTumblr.addActionListener(e -> setImage(textTumblr.getText(), new TumblrAPI()));
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
			System.out.println(dh.getKey());
			resultDate = new Date(Long.parseLong(dh.getKey()));
			listModel.addElement(dateFormat.format(resultDate)+ ": " + dh.getValue());
		}
		listHistory.setModel(listModel);
		listHistory.updateUI();

	}

	private void setImage(String subUrl, API api) {
		if("".equals(subUrl)){
			JOptionPane.showMessageDialog(getContentPane(), "Sie haben kein Subreddit angegeben.");
		}else{
			//Get the URL to the actual image
			String totalLink = api.makeUrl(subUrl);
			URL linkToImage = api.giveLinkToImage(totalLink);

			//Get the image data from the link
			BufferedImage image = api.requestData(linkToImage);

			if (image != null) {
				try {

					String fileName = String.valueOf(System.currentTimeMillis());

					//Save the image to the local hard disk
					saveImageToDisk(image, linkToImage.toString(), fileName);

					//change the background
					File imageFile = new File(makeLocalPath(linkToImage.toString(), fileName));
					Changer.useLocalImage(imageFile);

					if(!Boolean.parseBoolean(reader.readSettings(Settings.LocalSaveToggle))){
						try{
							System.out.println("Removing image: " + imageFile.getAbsolutePath());
							Files.delete(imageFile.toPath());
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					SettingsWriter writer = new SettingsWriter();
					writer.writeHistory(api.getTypeOfAPI()+ " " + subUrl);
				} catch (IOException ignored) {
				} catch (NullPointerException e) {
					e.printStackTrace();
				}finally{
					updateHistoryList();
				}

			}
		}
	}

	//make the local path to the image
	private static String makeLocalPath(String linkToImage, String fileName) {
		return (reader.readSettings(Settings.SaveLocation)+ "/" + fileName + "." + getSuffix(linkToImage)); }

	//return the suffix of the image (e.g. 'jpg')
	private static String getSuffix(String url) { return url.substring(url.lastIndexOf(".") + 1, url.length()); }

	private void setLocalImage(File selectedFile){

		if (!"".equals(selectedFile.getName())) {

			try {
				if (Changer.useLocalImage(selectedFile)) {

					JOptionPane.showMessageDialog(getContentPane(), "Bild wurde erfolgreich geändert.\n"
							+ "Das neue Bild ist: " + selectedFile.getAbsolutePath());

				} else JOptionPane.showMessageDialog(getContentPane(), "Hintergrund nicht geändert. Bitte geben Sie ein Bild an.");

			} catch (Exception ex) { System.out.println(ex.toString()); }

		} else JOptionPane.showMessageDialog(getContentPane(), "Sie haben noch kein Bild ausgewählt.");

	}

	private static void saveImageToDisk(BufferedImage img, String path, String fileName) throws IOException {

		//configure image correctly and prepare for writing
		BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D bGr = bImage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		File outputFile = new File(makeLocalPath(path, fileName));
		try {
			//save the image into outputfile
			ImageIO.write(bImage, getSuffix(path), outputFile);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	public static File convertToJPG(File f) {
		String directory = f.getParent();
		String name = f.getName();
		System.out.println(name);

		//if the image is jpg already, return it
		try {
			if (".jpg".equals(name.substring(name.indexOf("."), name.length()))) return f;
		} catch (StringIndexOutOfBoundsException e) { e.printStackTrace();}


		//save the file name
		name = name.substring(0, name.indexOf("."));
		try {
			//read image file
			BufferedImage bufferedImage = ImageIO.read(f);

			// create a blank, RGB, same width and height, and a white background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
															   bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			// write to jpeg file
			File newFile = new File(directory + "\\" + name + ".jpg");
			ImageIO.write(newBufferedImage, "jpg", newFile);
			System.out.println("Done");
			return newFile;
		} catch (IOException e) { e.printStackTrace(); }

		return null;

	}

}
