/*
 * 	Class name: Main
 * 	Description: contains main method and executes the whole game 
 * 	@Author KS Manejo
 */

package main.game;

import java.util.Scanner;
import javax.swing.SwingUtilities;

import main.ui.StartMenu;

public class Main {
	
	static Utilities utility = new Utilities();
	
	// No GUI
	public static void main(String[] args) {
		utility.promptTitle();
		mainMenu();
	}
	
//	// with GUI
//    public static void main(String[] args) {
//        Utilities utility = new Utilities();
//        utility.promptTitle();
//
//        SwingUtilities.invokeLater(() -> {
//            new StartMenu().setVisible(true);
//        });
//    }
	
	// main menu
	public static void mainMenu() {
		Scanner in = new Scanner(System.in);
		int choice;
		boolean game = true;

		GameInfo gameInfo = new GameInfo();
		GameBuilder gameBuilder = new GameBuilder();

		System.out.println("\n                  *************  MAIN MENU  *************");

		utility.sleep(900);
		while (game) {
			System.out.println("\nWhat would you like to do?");
			System.out.println("\t(1) Play");
			System.out.println("\t(2) Game info");
			System.out.println("\t(3) Exit");
			System.out.print("[Int] You choose: ");

			if (in.hasNextInt()) {
				choice = in.nextInt();

				switch (choice) {
					case 1: gameBuilder.gameLoop(); break;
					case 2: gameInfo.chooseGameInfo(); break;
					case 3: game = false; break;
					default: System.out.println("Invalid input! Please enter 1, 2, or 3.");
				}
			} else {
				System.out.println("Invalid input! Please enter a number.");
				in.next(); // consume the invalid input so loop can continue
			}
		}
		in.close();
	}
}
