package BackgroundChanger;

import javax.swing.*;

class BackgroundChanger{



	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}

		new MyWindow();

	}





}
