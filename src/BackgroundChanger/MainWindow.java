package BackgroundChanger;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MainWindow extends GUI{

	private JButton changeBackground = new JButton("Hintergrund �ndern");
	private JButton chooseFile= new JButton("Bild ausw�hlen");
	private JFileChooser fileDialog = new JFileChooser("Bild ausw�hlen");
	private File selectedFile;
	
	public MainWindow(Dimension dim){
		super("Background Changer", dim);
//		panel.setBackground(Color.BLUE);
		fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));;
		chooseFile.addMouseListener((MouseListener) new ActionAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = fileDialog.showOpenDialog((JButton) e.getSource());
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileDialog.getSelectedFile();
					System.out.println(selectedFile.getName());
				}
			}
		});
		changeBackground.addMouseListener((MouseListener) new ActionAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"".equals(selectedFile.getName())){
					try{
						if(Changer.changeBackground(selectedFile)){
							JOptionPane.showMessageDialog(panel, "Bild wurde erfolgreich ge�ndert.\n"
									+ "Das Neue Bild ist: " + selectedFile.getAbsolutePath());
						}else{
							JOptionPane.showMessageDialog(panel, "Hintergrund nicht ge�ndert. Bitte geben Sie ein Bild an.");
						}
					}catch(Exception ex){
						System.out.println(ex.toString());
					}
				}
			}
		});
		panel.add(chooseFile);
		panel.add(changeBackground);
		pack();
		setVisible(true);
	}
	
	
}
