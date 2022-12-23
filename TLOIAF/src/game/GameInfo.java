package game;
/*
 * Classnane: GameInfo
 * Description: Contains information about the game, enemies, houses
 * @Author KS Manejo
 */


import java.util.Scanner;

public class GameInfo {
	
	Utilities utility = new Utilities();
	
	void chooseGameInfo () {
		
		Scanner in = new Scanner(System.in);
		int select;
		boolean info = true;
		
		while (info == true) {
			utility.insertSpace();
			utility.insertLine();
			System.out.println ("\n                  ************  GAME INFO  ************");
			
			System.out.println ("\nWhich would you like to explore?");
			System.out.println ("\t (1) Houses ");
			System.out.println ("\t (2) Enemies ");
			System.out.println ("\t (3) About the game");
			System.out.println ("\t (4) Back to main menu");
			System.out.print ("[Int] You choose: ");
			select = in.nextInt();
			
			utility.insertSpace();
			utility.insertLine();
			
			switch (select) {
				case 1: houses(); break;
				case 2: enemies(); break;
				case 3: aboutTheGame(); break;
				case 4: Main.mainMenu(); break;
				default: System.out.println("Invalid input.");
			}
		}
	}
	
	void houses(){
		
		String[] houseNames = {"Targaryen", "Lannister", "Stark"};
		String[] specialAbilities = {"Additional Attack Damage", "Additional Armor value", "Additional HP value"};
		
    	System.out.println ("\n                  *************  HOUSES  *************");
		
		for (int i = 0; i < houseNames.length; i++) {
			utility.sleep(700);
			System.out.println("\nHouse Name: " + houseNames[i]);
			System.out.println("Special Ability: " + specialAbilities[i]);
		}
	}
	
	void enemies(){
		
		String[] enemies = {"Wilding", "Giant", "White Walker", "Night King"};
		String[] location = {"Haunted Forest", "Fist of the First Men", "Frostfangs", "Land of Always Winter"};
		String[] attackDamageRange = {"6 - 10", "7 - 13", "8 - 16", "11 - 23"};
		
    	System.out.println ("\n                  *************  ENEMIES  *************");

		for (int i = 0; i < enemies.length; i++) {
			utility.sleep(700);
			System.out.println("\nHouse Name: " + enemies[i]);
			System.out.println("Attack Damage Range: " + attackDamageRange[i]);
			System.out.println("Location: " + location[i]);
		}
	}
	
	void aboutTheGame(){
		
    	System.out.println ("\n                  **********  ABOUT THE GAME **********");
    	utility.sleep(1000);		
    	System.out.println("\nThe game takes place in a continent named Westeros, where winter could last for decades.");
		System.out.println("The player will go through four levels. Each level, a different enemy will be encountered.");
		System.out.println("The main goal of the game is to defeat the Night King.");
    	utility.sleep(1000);		
	}
}


