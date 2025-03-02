/*
 * Classnamae: Tavern
 * Description: Contains methods for actions that player may choose when entering the tavern
 * @Author KS Manejo 
 */

package game;

import java.util.Scanner;

import charactermanager.House;

public class Tavern {
	
	Utilities utility = new Utilities();
	Scanner in = new Scanner(System.in);
	
	void tavern(House house) {
		Tavern tavern = new Tavern();
		int choice;
		boolean valid = false;
				
		utility.insertLine();
		System.out.println("\n              ***********  WELCOME TO THE TAVERN  ***********");
		
		while (valid == false) {
			System.out.println ("\nWhat would you like to do?");
			System.out.println ("\t(1) Rest (Restores 40 HP value)");
			System.out.println ("\t(2) Repair armor (Restores 10 armor value)");
			System.out.println ("\t(3) Drink strength potion (Upto 10 additional damage points to any attack type)");
			System.out.print("[int] You choose: ");
			choice = in.nextInt();
		
			switch (choice) {
				case 1: tavern.rest(house); valid = true; break;
				case 2: tavern.repairArmor(house); valid = true; break;
				case 3: tavern.drinkStrengthPotion(house); valid = true; break;
				default: System.out.print("Invalid Input.");
			}
		}
	}

	// if player wants to rest
	void rest(House house) {
		int newHp;
		
		System.out.println("\nYou chose to rest.");
		utility.sleep(1000);
		System.out.println("\nResting...");
		utility.sleep(1700);
		
		newHp = house.rest();
	
		System.out.println("\nYour HP is now " + newHp + " !");
	}
	
	// if player wants to upgrade armor
	void repairArmor(House house) {
		int newArmor;
		
		System.out.println("\nYou chose to repair your armor.");
		utility.sleep(1000);
		System.out.println("\nRepairing...");
		utility.sleep(1700);
		
		newArmor = house.repairArmor();
		
		System.out.println("\nYour armor is now " + newArmor + " !");
	}
	
	// if player wants to upgrade armor
	void drinkStrengthPotion(House house) {
		int addAttackDamage;
			
		System.out.println("\nYou chose to drink strength potion.");
		utility.sleep(1000);
		System.out.println("\nDrinking...");
		utility.sleep(1700);
			
		addAttackDamage = house.drinkStengthPotion();  
			
		System.out.println("\nYou will now have additional " + addAttackDamage + " damage to all your attack types!");
	}
}
