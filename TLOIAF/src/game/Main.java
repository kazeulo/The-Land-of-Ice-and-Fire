/*
 * 	Class name: Main
 * 	Description: contains main method and executes the whole game 
 * 	@Author KS Manejo
 */

package game;

import java.util.Scanner;

public class Main {
	
	static Utilities utility = new Utilities();

	public static void main(String[] args) {
		utility.promptTitle();
		mainMenu();
		
	}
	
	// main menu
	public static void mainMenu() {
		
		Scanner in = new Scanner(System.in);
		int choice;
		boolean game = true;
		
		GameInfo gameInfo = new GameInfo();
		GameBuilder gameBuilder = new GameBuilder();
		
        utility.sleep(900);
        while (game == true) {
        	System.out.println ("\n                  *************  MAIN MENU  *************");
        	System.out.println("\nWhat would you like to do?");
        	System.out.println("\t(1) Play");
        	System.out.println("\t(2) Game info");
        	System.out.println("\t(3) Exit");
        	System.out.print("[Int] You choose: ");
        	choice = in.nextInt();
        	
        	switch(choice) {
        		case 1: gameBuilder.gameLoop(); break;
        		case 2: gameInfo.chooseGameInfo(); break;
        		case 3: game = false; break;
        		default: System.out.println("Invalid input!");
        	}
        }
	}
}
