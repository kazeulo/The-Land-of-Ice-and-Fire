/*
 * Classname: Tavern
 * Description: Contains methods for actions that player may choose when entering the tavern
 * @Author KS Manejo
 */

package main.game;

import main.charactermanager.House;
import java.util.Scanner;

public class Tavern {

	Utilities utility = new Utilities();
	Scanner in = new Scanner(System.in);

	void tavern(House house) {
		boolean valid = false;

		utility.insertLine();
		System.out.println("\n              ***********  WELCOME TO THE TAVERN  ***********");

		while (!valid) {
			System.out.println ("\nWhat would you like to do?");
			System.out.println ("\t(1) Rest (Restores 40 HP value)");
			System.out.println ("\t(2) Repair armor (Restores 7 armor value)");
			System.out.println ("\t(3) Drink strength potion (Up to 10 additional damage points to any attack type)");
			System.out.print("[int] You choose: ");

			if (in.hasNextInt()) {
				int choice = in.nextInt();
				switch (choice) {
					case 1:
						rest(house);
						valid = true;
						break;
					case 2:
						repairArmor(house);
						valid = true;
						break;
					case 3:
						drinkStrengthPotion(house);
						valid = true;
						break;
					default:
						System.out.println("Invalid input. Enter 1, 2, or 3.");
				}
			} else {
				System.out.println("Invalid input. Please enter a number.");
				in.next();
			}
		}
	}

	void rest(House house) {
		System.out.println("\nYou chose to rest.");
		utility.sleep(1000);
		System.out.println("\nResting...");
		utility.sleep(1700);

		int newHp = house.rest();
		System.out.println("\nYour HP is now " + newHp + " !");
	}

	void repairArmor(House house) {
		System.out.println("\nYou chose to repair your armor.");
		utility.sleep(1000);
		System.out.println("\nRepairing...");
		utility.sleep(1700);

		int newArmor = house.repairArmor();
		System.out.println("\nYour armor is now " + newArmor + " !");
	}

	void drinkStrengthPotion(House house) {
		System.out.println("\nYou chose to drink strength potion.");
		utility.sleep(1000);
		System.out.println("\nDrinking...");
		utility.sleep(1700);

		int addAttackDamage = house.drinkStrengthPotion();
		System.out.println("\nYou will now have additional " + addAttackDamage + " damage to all your attack types!");
	}
}
