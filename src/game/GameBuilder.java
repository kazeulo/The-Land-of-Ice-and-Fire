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

    private final Utilities utility = new Utilities();
    private final HouseFactory houseFactory = new HouseFactory();
    private final EnemyFactory enemyFactory = new EnemyFactory();
    private final Scanner in = new Scanner(System.in);

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
     * 12.5% chance (1/8) to return true — simulates a miss.
     */
    public boolean coinToss() {
        return utility.randInt(1, 8) == 8;
    }

    /**
     * Displays current HP and Armor status for the player and enemy.
     */
    public void promptStatus(House house, Enemy enemy) {
        utility.sleep(800);
        System.out.println("\n*********** STATUS BAR ***********\n");
        System.out.printf("\tHouse: %s%n\tHP: %d%n\tArmor: %d%n", house.getName(), house.getHp(), house.getArmor());
        System.out.printf("%n\tEnemy: %s%n\tHP: %d%n", enemy.getName(), enemy.getHp());
        System.out.println("\n***********************************");
    }

    /**
     * Asks the player whether to attack or run.
     * @return true if player chooses to attack, false otherwise
     */
    public boolean runOrFight() {
        boolean notValid = true;
        boolean fight = false;

        utility.sleep(600);
        while (notValid) {
            System.out.println("\nChoose an action:");
            System.out.println("\t(1) Attack");
            System.out.println("\t(2) Run");
            System.out.print("[Int] You choose: ");

            if (in.hasNextInt()) {
                int choice = in.nextInt();
                switch (choice) {
                    case 1:
                        fight = true;
                        notValid = false;
                        break;
                    case 2:
                        utility.sleep(1000);
                        System.out.println("\nYou chose to run.");
                        fight = false;
                        notValid = false;
                        break;
                    default:
                        System.out.println("Invalid input. Enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                in.next(); // clear invalid input
            }
        }
        return fight;
    }

    /**
     * Executes one round of combat between player and enemy.
     */
    public void duel(House house, Enemy enemy) {
        System.out.println("\nYou attacked the " + enemy.getName() + ".");
        utility.sleep(800);

        if (coinToss()) {
            System.out.println("You missed!");
        } else {
            int damage = enemy.takenDamage(house.attack());
            utility.sleep(800);
            System.out.println("You dealt " + damage + " damage to the " + enemy.getName() + ".");
        }

        utility.sleep(1200);

        // Enemy's turn
        if (enemy.isAlive()) {
            System.out.println("\n" + enemy.getName() + " attacked you.");
            utility.sleep(800);

            if (coinToss()) {
                System.out.println("You managed to dodge!");
            } else {
                int damage = house.takenDamage(enemy.attack());
                System.out.println(enemy.getName() + " dealt " + damage + " damage to you.");
            }
        }
    }

    /**
     * Conducts a full duel until one side dies or player runs.
     * @return true if player keeps fighting, false if player runs
     */
    public boolean duelToDeath(House house, Enemy enemy, int level) {
        boolean fight;

        utility.insertSpace();
        utility.insertLine();
        utility.sleep(1000);

        System.out.println("\nLEVEL: " + level);
        System.out.println("\nLocation: " + enemy.getLocation() + " \t|\t Enemy: " + enemy.getName());

        do {
            promptStatus(house, enemy);
            fight = runOrFight();
            if (fight) {
                duel(house, enemy);
            } else {
                break;
            }
        } while (house.isAlive() && enemy.isAlive());

        utility.sleep(800);
        promptStatus(house, enemy);

        if (!enemy.isAlive()) {
            System.out.println("\nYou killed the " + enemy.getName() + "!");
            utility.sleep(1000);
            System.out.println("You won the battle!");
        } else if (!house.isAlive()) {
            System.out.println("\nYou have been defeated...");
        } else {
            System.out.println("\nYou chose to flee.");
        }

        return fight;
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

            if (duelToDeath(house, enemy, level)) {
                level++;
            } else {
                break;
            }

            // Before boss fight
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