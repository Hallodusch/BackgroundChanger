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

/**
 * Ermöglicht den Wechsel des Hintergrundbilds.
 *
 */
public class Changer {

	private static final SettingsReader reader = new SettingsReader();

    /**
     * Ermöglicht Zugriff auf Windows Registry
     */
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

    /**
     * Nimmt ein File und wechselt das Hintergrundbild von Windows zu diesem File.
     *
     * @param file Das File, das zum neuen Hintergrund wird.
     * @return true Wenn der Wechsel des Hintergrundbilds erfolgreich war.
     * @return false Wenn der Wechsel nicht funktioniert hat.
     */
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


    /**
     * Wechselt den Windowshintergrund zu einem Bild einer Website.
     *
     * <p>
     *     Nimmt einen Teil einer URL und den Typ von Website.
     *     Lädt das Bild herunter.
     *     Setzt den neuen Windowshintergrund.
     *     Löscht das Bild, wenn Speicherfunktion nicht aktiviert ist.
     * </p>
     *
     * @param subUrl Der Link zum Blog oder Subreddit.
     * @param api Die API um das Bild zu holen.
     * @return true Wenn der Wechsel des Hintergrundbildes erfolgreich war.
     * @return false Wenn der Wechsel nicht funktioniert hat.
     */
	public boolean setImage(String subUrl, API api) {

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
				useLocalImage(imageFile);
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

    /**
     * Baut den Speicherort für ein heruntergeladenes Bild.
     *
     * @param linkToImage Ursprünglicher Link zum Bild.
     * @param fileName Name des Bildes
     * @return Den Speicherort eines Bildes.
     */
	private String makeLocalPath(String linkToImage, String fileName) {
		return (reader.readSettings(Settings.SaveLocation)+ "/" + fileName + "." + getSuffix(linkToImage)); }

    /**
     * Nimmt einen Link zu einem Bild und gibt den Filetyp zurück.
     *
     * @param url Den Link zum Bild.
     * @return Den Filetyp eines Bildes als String.
     */
	private static String getSuffix(String url) { return url.substring(url.lastIndexOf(".") + 1, url.length()); }

    /**
     * Speichert ein angegebenes Bild lokal auf die Festplatte.
     *
     * @param img Das zu speichernde Bild.
     * @param path Der Pfad zum Speicherort.
     * @param fileName Der Name des Bilds nach dem Speichern.
     * @throws IOException
     */
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

    /**
     * Konvertiert Bilder zum .jpg-Format.
     *
     * @param f Das File, das konvertiert wird.
     * @return @code newFile Das Bild in JPG-Form.
     * @return @code f Wenn das File bereits .jpg ist.
     * @return null Wenn das speichern nicht funktioniert hat.
     */
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
