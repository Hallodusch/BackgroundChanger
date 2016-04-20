package BackgroundChanger;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import java.io.File;
import java.util.HashMap;

public class Changer {

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

	public static boolean useLocalImage(String path) throws NullPointerException {

		if (new File(path).canRead()) {
			return (SPI.INSTANCE.SystemParametersInfo(
					new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
					new UINT_PTR(0),
					BackgroundChanger.convertToJPG(new File(path)).getAbsolutePath(),
					new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE)));
		} else {
			throw new NullPointerException("Hier ist die Exception");
		}
	}

	public static boolean useLocalImage(File file) throws NullPointerException {
		System.out.println("Setze Hintergrund: " + file.getAbsolutePath());
		if (file.canRead()) {
			return (SPI.INSTANCE.SystemParametersInfo(
					new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
					new UINT_PTR(0),
					BackgroundChanger.convertToJPG(file).getAbsolutePath(),
					new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE)));
		} else {
			throw new NullPointerException("Hier ist die Exception");
		}

	}

}
