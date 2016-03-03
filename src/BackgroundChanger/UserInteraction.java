package BackgroundChanger;


import java.util.Scanner;


public class UserInteraction implements Runnable{

	private String userInput = "";
	private Scanner scan = new Scanner(System.in);
	

	


	public String getUserInput(){ return this.userInput; }


	@Override
	public void run() {
		boolean isWaiting = true;
		System.out.println("Willkommen zum Windows Background Changer. Geben Sie einen Pfad zu einem Bild an und drücken Sie die Entertaste.\n"
				+ "Um diesen Modus zu verlassen, drücken Sie die Taste 'q'.");
		while (isWaiting) {

			userInput = scan.next();

			//System.out.println(userInput);
			if("q".equals(userInput)){
				Thread.currentThread().interrupt();
				return;
			}else{
				if(Changer.changeBackground(userInput)){
					System.out.println("Hintergrund erfolgreich geändert!");
				}else{
					System.out.println("Das hat nicht funktioniert.");
				}
			}

		}




	}


}
