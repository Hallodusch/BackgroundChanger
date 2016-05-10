package BackgroundChanger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class BackgroundChanger extends Thread {

	private static final String SAVE_LOCATION = "..";


	public static void main(String[] args) {

		new MainWindow(new Dimension(450, 200));
		new SettingsReader();
	}

	public static File convertToJPG(File f) {
		String directory = f.getParent();
		String name = f.getName();
		boolean isJpg;
		System.out.println(name);

		try {
			isJpg = ".jpg".equals(name.substring(name.indexOf("."), name.length()));
		} catch (StringIndexOutOfBoundsException e) {
			isJpg = false;
			System.out.println("not jpg");
		}

		//if the image is jpg already
		if (isJpg) {
			return f;
		} else {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	//make the local path to the image
	public static String makeLocalPath(String linkToImage) {
		return SAVE_LOCATION.concat("/wallpaper.").concat(getSuffix(linkToImage));
	}

	//return the suffix of the image (e.g. 'jpg')
	public static String getSuffix(String url) {
		return url.substring(url.lastIndexOf(".") + 1, url.length());
	}

	public static void setImageFromReddit(String subreddit) {

		API api = new RedditAPI();

		//Get the URL to the actual image
		String subredditLink = api.makeUrl(subreddit);
		URL linkToImage = api.giveLinkToImage(subredditLink);

		//Get the image data from the link
		BufferedImage image = api.requestData(linkToImage);

		if (image != null) {
			try {
				//Save the image to the local hard disk
				saveToDisk(image, linkToImage);

				//change the background
				Changer.useLocalImage(new File(makeLocalPath(linkToImage.toString())));
				SettingsWriter writer = new SettingsWriter();
				writer.writeHistory(subredditLink);
			} catch (IOException e) {
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}

	}

	public static void setImageFromTumblr(String blog) {

		API api = new TumblrAPI();

		//Get the URL to the actual image
		String blogLink = api.makeUrl(blog);
		URL linkToImage = api.giveLinkToImage(blogLink);

		//Get the image data from the link
		BufferedImage image = api.requestData(linkToImage);

		if (image != null) {
			try {
				//Save the image to the local hard disk
				saveToDisk(image, linkToImage);

				//change the background
				Changer.useLocalImage(new File(makeLocalPath(linkToImage.toString())));
				SettingsWriter writer = new SettingsWriter();
				writer.writeHistory(blogLink);
			} catch (IOException e) {
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}

	}

	private static void saveToDisk(BufferedImage img, URL path) throws IOException {

		//configure image correctly and prepare for writing
		BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D bGr = bImage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		File outputFile = new File(makeLocalPath(path.toString()));
		try {
//			System.out.println(makeLocalPath(path.toString()));

			//save the image into outputfile
			ImageIO.write(bImage, getSuffix(path.toString()), outputFile);
		} catch (IOException e) {
			throw new IOException();
		}
	}


}
