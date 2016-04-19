package BackgroundChanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MainWindow extends JFrame{

    private File selectedFile;
	private JFileChooser fileDialog = new JFileChooser("Bild auswählen");
	private JTextField fileText;
	private JTextField urlEntry;
	private JPanel panel = new JPanel(new GridBagLayout());
	
	public MainWindow(Dimension dim){

        JLabel fileChooseLabel = new JLabel();

        JButton changeBackground = new JButton("Hintergrund ändern");
        JButton chooseFile= new JButton("Bild auswählen");
        JButton useUrl = new JButton("URL benutzen");

        JCheckBox activated = new JCheckBox("aktiviert");

        JPanel upperRight = new JPanel(new FlowLayout());
        JPanel upperLeft = new JPanel(new FlowLayout());
        JPanel lowerRight = new JPanel(new FlowLayout());
        JPanel lowerLeft = new JPanel(new FlowLayout());

        //set settings for the window
		setWindow(dim);

		selectedFile = new File("");

		fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));

		fileText = createTextField(new Dimension(150, 20), false, false);
		urlEntry = createTextField(new Dimension(150, 20), true, true);
		
		fileChooseLabel.setText("Wählen Sie ein Bild aus");
		fileChooseLabel.setFont(new Font(fileText.getFont().getName(), 1, fileText.getFont().getSize() + 4));
		
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
		
		useUrl.addActionListener(e -> setImage(urlEntry.getText()));
		
		panel.setLayout(new GridLayout(2,2));
		panel.setSize(this.getWidth(), this.getHeight());
		
		panel.add(upperLeft);
		panel.add(upperRight);
		panel.add(lowerLeft);
		panel.add(lowerRight);
		
		
		upperLeft.add(fileChooseLabel);
		lowerLeft.add(fileText);
		lowerLeft.add(chooseFile);
		
		lowerRight.add(changeBackground);
		lowerLeft.add(activated);
		upperRight.add(urlEntry);
		upperRight.add(useUrl);
		
		pack();
		setVisible(true);
	}


    private void setImage(String url){
        BackgroundChanger.setImageFromReddit(url);
    }

    private void setWindow(Dimension dim){
        setTitle("Background Changer");
        setMaximumSize(new Dimension(1600, 900));
        setSize(dim);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);

        //Set Window look and feel (=style)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
	
	private JTextField createTextField(Dimension dim, boolean focusable, boolean enabled){
		JTextField fileText = new JTextField();
		
		fileText.setPreferredSize(dim);
		fileText.setMinimumSize(new Dimension(80,20));
		fileText.setMaximumSize(new Dimension(300,20));
		fileText.setBackground(Color.WHITE);
		fileText.setFocusable(focusable);
		fileText.setEnabled(enabled);
		fileText.setDisabledTextColor(Color.BLACK);
		
		return fileText;
	}
	
	private File chooseFile(MouseEvent e){
		int result = fileDialog.showOpenDialog( (Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			fileText.setText(selectedFile.getAbsolutePath());
			fileText.setPreferredSize(new Dimension((int) (fileText.getText().length() * 5.6),20));
			pack();
			setLocationRelativeTo(null);
			return selectedFile;
		}
		return null;
	}

	private File chooseFile(ActionEvent e){
		int result = fileDialog.showOpenDialog( (Component) e.getSource());
		if (result == JFileChooser.APPROVE_OPTION) {

			selectedFile = fileDialog.getSelectedFile();
			//			System.out.println(selectedFile.getName());
			fileDialog.setCurrentDirectory(selectedFile);
			fileText.setText(selectedFile.getAbsolutePath());
			fileText.setPreferredSize(new Dimension((int) (fileText.getText().length() * 5.6),20));
			pack();
			setLocationRelativeTo(null);
			return selectedFile;

		}
		return null;
	}

}