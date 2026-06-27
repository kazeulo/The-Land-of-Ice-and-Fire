/*
 * 	Class name: Main
 * 	Description: contains main method and executes the whole game 
 * 	@Author KS Manejo
 */

package main.game;

import java.util.Scanner;

import main.gui.StartMenu;

public class Main {
	
	static Utilities utility = new Utilities();

	public static void main(String[] args){
		new StartMenu("The land of ice and fire");
	}

	// main menu
	public static void mainMenu() {
		GameInfo gameInfo = new GameInfo();
		GameBuilder gameBuilder = new GameBuilder();

		System.out.println("\n                  *************  MAIN MENU  *************");

		utility.sleep(900);
		try (Scanner in = new Scanner(System.in)) {
			boolean game = true;
			while (game) {
				System.out.println("\nWhat would you like to do?");
				System.out.println("\t(1) Play");
				System.out.println("\t(2) Game info");
				System.out.println("\t(3) Exit");
				System.out.print("[Int] You choose: ");

				if (in.hasNextInt()) {
					switch (in.nextInt()) {
						case 1 -> gameBuilder.gameLoop();
						case 2 -> gameInfo.chooseGameInfo();
						case 3 -> game = false;
						default -> System.out.println("Invalid input! Please enter 1, 2, or 3.");
					}
				} else {
					System.out.println("Invalid input! Please enter a number.");
					in.next();
				}
			}
		}
	}
}
