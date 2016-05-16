package BackgroundChanger;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;

public class Changer {

	private static final SettingsReader reader = new SettingsReader();

	public interface SPI extends StdCallLibrary {

		long SPI_SETDESKWALLPAPER = 20;
		long SPIF_UPDATEINIFILE = 0x01;
		long SPIF_SENDWININICHANGE = 0x02;


		SPI INSTANCE = (SPI) Native.loadLibrary("user32", SPI.class, new HashMap<Object, Object>() {
			{
				put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
				put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
			}
		});

		boolean SystemParametersInfo(
				UINT_PTR uiAction,
				UINT_PTR uiParam,
				String pvParam,
				UINT_PTR fWinIni
		);


	}

	public boolean useLocalImage(File file) {
		System.out.println("Setze Hintergrund: " + file.getAbsolutePath());
		File jpgFile = convertToJPG(file);
		String pathToFile = "";
		try {
			pathToFile = jpgFile.getAbsolutePath();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return !"".equals(pathToFile) &&
				(SPI.INSTANCE.SystemParametersInfo(
						new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
						new UINT_PTR(0),
						pathToFile,
						new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE)));


	}


	public boolean setImage(String subUrl, API api) {

		System.out.println("changer in function");
		//Get the URL to the actual image
		String totalLink = api.makeUrl(subUrl);
		URL linkToImage = api.giveLinkToImage(totalLink);
		//Get the image data from the link
		BufferedImage image = api.requestData(linkToImage);
		if (image != null) {
			try {
				System.out.println("changer in if");
				String fileName = String.valueOf(System.currentTimeMillis());
				//Save the image to the local hard disk
				saveImageToDisk(image, linkToImage.toString(), fileName);
				//change the background
				File imageFile = new File(makeLocalPath(linkToImage.toString(), fileName));
				useLocalImage(imageFile);
				System.out.println("chagner after change");
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
				return true;
			} catch (IOException ignored) {
			} catch (NullPointerException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	//make the local path to the image
	private String makeLocalPath(String linkToImage, String fileName) {
		return (reader.readSettings(Settings.SaveLocation)+ "/" + fileName + "." + getSuffix(linkToImage)); }

	//return the suffix of the image (e.g. 'jpg')
	private static String getSuffix(String url) { return url.substring(url.lastIndexOf(".") + 1, url.length()); }

	private void saveImageToDisk(BufferedImage img, String path, String fileName) throws IOException {

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

	private File convertToJPG(File f) {
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
