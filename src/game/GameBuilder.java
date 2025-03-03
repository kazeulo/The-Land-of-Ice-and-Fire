/*
 * Classname: GameBuilder
 * Description: contains methods to build the game
 * @Author KS Manejo
 */

package game;

import charactermanager.Enemy;
import charactermanager.EnemyFactory;
import charactermanager.House;
import charactermanager.HouseFactory;
import java.util.Scanner;

public class GameBuilder {
	
	Utilities utility = new Utilities();
	HouseFactory hFactory = new HouseFactory();
	EnemyFactory eFactory = new EnemyFactory();
	Scanner in = new Scanner(System.in);
	
	// player will choose which house they want
	public House chooseHouse() {
		boolean notValid = true;
		int choice = 0;
		
    	utility.insertSpace();
		utility.sleep(1000);
		utility.insertLine();
		
		System.out.print("\n                 ************  SELECT HOUSE  ************");
		while (notValid) {
			System.out.println("\nChoose a house.");
			System.out.println("\t(1) Targaryen");
			System.out.println("\t(2) Lannister");
			System.out.println("\t(3) Stark");
			System.out.print("[Int] You choose: ");
			choice = in.nextInt();
			
			if ((choice > 0) && (choice <= 3)) {
				notValid = false;
			}else {
				System.out.println("\nCannot find house. Enter again.");
			}
		}
		return hFactory.createHouse(choice);
	}
	
	// coin toss with 12.5% chance,
	// if true, character's attack will miss
	public boolean coinToss() {
		return utility.randInt(1, 8) == 8;
	}
	
	// show health values for both characters
	public void promptStatus(House house, Enemy enemy) {
		utility.sleep(800);
		System.out.println("\n*********** STATUS BAR ***********\n");
		System.out.println("\tHouse: " + house.getName() + "\n\tHP: " + house.getHp() + "\n\tArmor: " + house.getArmor());
		System.out.println("\n\tEnemy: " + enemy.getName() + "\n\tHP: " + enemy.getHp());
		System.out.println("\n***********************************");
	}
	
	// players gets to choose whether to fight or run every turn
	public boolean runOrFight() {
		int choice = 0;
		boolean fight = false;
		boolean notValid = true;
		
		utility.sleep(600);
		while (notValid) {
			System.out.println("\nChoose an action.");
			System.out.println("\t(1) Attack");
			System.out.println("\t(2) Run");
			System.out.print("[Int] You choose: ");
			choice = in.nextInt();
			
			switch (choice) {
				case 1: 
					fight =  true; 
					notValid = false;
					break;
				case 2: 
					utility.sleep(1000);
					System.out.println("\nYou chose to run."); 
					fight = false; 
					notValid = false;
					break;
				default: 
					System.out.println("Invalid Input.");
			}
		}
		return fight;
	}
	
	// characters' duel
	public void duel (House house, Enemy enemy, int level) {
		int takenDamage = 0;
		
		// player's turn
		System.out.println("\nYou attacked the " + enemy.getName() + ".");
		utility.sleep(800);
		if (coinToss()) {
			System.out.println("You missed!");
		}else {
			takenDamage = enemy.takenDamage(house.attack());
			utility.sleep(800);
			System.out.println("You dealt " + takenDamage + " damage to the " + enemy.getName() + ".");
		}
	
		utility.sleep(1200);
		
		// enemy's turn if it is still alive
		if (enemy.isAlive()){
			System.out.println("\n" + enemy.getName() + " attacked you.");
			utility.sleep(800);
			
			if (coinToss()) {
				System.out.println("You managed to dodge!");
			}else {
				takenDamage = house.takenDamage(enemy.attack());
				System.out.println(enemy.getName() + " dealt " + takenDamage + " damage to you.");
				house.reduceArmor(house.getHp());
			}
		}
	}
	
	// characters' will combat with each other until one of them is dead
	public boolean duelToDeath(House house, Enemy enemy, int level) {
		boolean fight = false;
		
		utility.insertSpace();
		utility.insertLine();
		utility.sleep(1000);
		
		// show location and enemy type
		System.out.println("\nLEVEL: " + level);
		System.out.println("\nLocation: " + enemy.getLocation() + " \t|\t Enemy: " + enemy.getName());
		
		// characters will attack each other until one of them is dead
		while ((house.isAlive()) && (enemy.isAlive())) {
			// show character's status
			promptStatus(house, enemy);
			
			// check whether the player wants to fight or run first
			fight = runOrFight();
			if (fight) {
				duel(house, enemy, level);
			}else {
				break;	
			}
		}
		
		// check if enemy is dead
		if (enemy.getHp() < 1) {
			promptStatus(house, enemy);
			utility.sleep(800);
			System.out.println("\nYou killed the " + enemy.getName() + "!");
			utility.sleep(1000);
			System.out.println("You won the battle!");
		}else {
			if (house.getHp()<1) {
				promptStatus(house, enemy);
				utility.sleep(800);
			}
			System.out.println("\nYou lose.");
			utility.sleep(1000);
		}
		
		return fight;
	}
	
	// game will go through 4 levels
	public void gameLoop() {
		
		Tavern tavern = new Tavern();
		House house = chooseHouse();
		Enemy enemy = null;
		int level = 1;
		
		utility.intro();
		while (level<=4) {
			
			enemy = eFactory.createEnemy(level);
			if (duelToDeath(house, enemy, level)) {
				level += 1;
			}else {
				break;
			}
			
			// player will enter the tavern before battling the boss
			if (level == 4) {
				utility.insertSpace();
				utility.sleep(1000);
				utility.insertLine();
				System.out.println("\nYou are now on the last stage of your journey.");
				utility.sleep(700);
				System.out.println("You must prepare before facing the Night King.");
				utility.sleep(1000);
				System.out.println("\nEntering a tavern...\n");
				utility.sleep(2000);
				tavern.tavern(house);
			}
		}
		
		// once the loop ends
		utility.sleep(800);
		System.out.print("\nYour journey ends now.");
		utility.sleep(1500);
		
		if (enemy.isAlive()){
			System.out.println(" You have failed to save Westeros.\n");
		}else {
			System.out.println(" You have saved Westeros!\n");
		}
		
		utility.sleep(800);
		utility.insertLine();
	}	
}
