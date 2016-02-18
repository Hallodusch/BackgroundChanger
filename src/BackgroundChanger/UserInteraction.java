package BackgroundChanger;


import java.util.Scanner;


public class UserInteraction {

	private String userInput = "";
	private Scanner scan = new Scanner(System.in);
	private Changer myChanger = new Changer();
	
	public void waitForInput(){
		boolean isWaiting = true;
		System.out.println("Willkommen zum Windows Background Changer. Geben Sie einen Pfad zu einem Bild an und drücken Sie die Entertaste.\n"
				+ "Um diesen Modus zu verlassen, drücken Sie die Taste 'q'.");
			while (isWaiting) {
				
				userInput = scan.next();
				if("q".equals(userInput)){
					isWaiting = false;
				}else{	
					System.out.println(userInput);
					if(myChanger.changeBackground(userInput)){
						System.out.println("Hintergrund erfolgreich geändert!");
					}else{
						System.out.println("Das hat nicht funktioniert.");
					}
				}
			}
		
		
	}
	
	
	public String getUserInput(){ return this.userInput; }
	
	
}
