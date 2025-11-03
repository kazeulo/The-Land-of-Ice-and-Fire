/*
 * Classname: GameBuilder
 * Description: Contains methods to build and run the main game loop
 * @Author KS Manejo
 */

package main.game;

import main.charactermanager.Enemy;
import main.charactermanager.EnemyFactory;
import main.charactermanager.House;
import main.charactermanager.HouseFactory;

import java.util.Scanner;

public class GameBuilder {

    private final Utilities utility = new Utilities();
    private final HouseFactory houseFactory = new HouseFactory();
    private final EnemyFactory enemyFactory = new EnemyFactory();
    private final Scanner in = new Scanner(System.in);
    private final BattleManager battleManager = new BattleManager();

    /**
     * Prompts the player to choose a house (1–3).
     * @return the selected House
     */
    public House chooseHouse() {
        int choice = 0;
        boolean notValid = true;

        utility.insertSpace();
        utility.sleep(1000);
        utility.insertLine();

        System.out.println("\n************  SELECT HOUSE  ************");
        while (notValid) {
            System.out.println("\nChoose a house:");
            System.out.println("\t(1) Targaryen");
            System.out.println("\t(2) Lannister");
            System.out.println("\t(3) Stark");
            System.out.print("[Int] You choose: ");

            if (in.hasNextInt()) {
                choice = in.nextInt();
                if (choice >= 1 && choice <= 3) {
                    notValid = false;
                } else {
                    System.out.println("\nCannot find house. Enter again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                in.next(); // clear invalid input
            }
        }
        return houseFactory.createHouse(choice);
    }

    /**
     * Runs the entire game loop (4 levels).
     */
    public void gameLoop() {
        Tavern tavern = new Tavern();
        House house = chooseHouse();
        Enemy enemy;
        int level = 1;

        utility.intro();

        while (level <= 4) {
            enemy = enemyFactory.createEnemy(level);

            // Player fights current enemy
            if (battleManager.duelToDeath(house, enemy, level)) {
                // Player won the battle
                level++;

                // Before final boss
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

            } else {
                // Player ran or died
                break;
            }
        }

        // End of game
        utility.sleep(800);
        System.out.print("\nYour journey ends now.");
        utility.sleep(1500);

        if (level > 4 && !enemyFactory.createEnemy(4).isAlive()) {
            System.out.println(" You have saved Westeros!\n");
        } else {
            System.out.println(" You have failed to save Westeros.\n");
        }

        utility.sleep(800);
        utility.insertLine();
    }
}