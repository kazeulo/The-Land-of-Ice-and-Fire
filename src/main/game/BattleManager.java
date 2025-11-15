/*
 * Classname: BattleManager
 * Description: Handles all battle-related logic
 * @Author KS Manejo
 */

package main.game;

import main.charactermanager.Enemy;
import main.charactermanager.House;
import main.game.UI;

import java.util.Scanner;

public class BattleManager {

    private final Utilities utility = new Utilities();
    private final Scanner in = new Scanner(System.in);

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
        utility.sleep(500);

        System.out.println("\n+============ " + UI.BOLD + "STATUS BAR" + UI.RESET + " ============+");
        System.out.printf(UI.CYAN + "    House: %s\n", house.getName());
        System.out.printf(UI.CYAN + "    HP: %d\n", house.getHp());
        System.out.printf(UI.CYAN + "    Armor: %d\n", house.getArmor());
        System.out.println(UI.RESET + "--------------------------------------");
        System.out.printf(UI.RED + "    Enemy: %s\n", enemy.getName());
        System.out.printf(UI.RED + "    HP: %d\n", enemy.getHp());
        System.out.println(UI.RESET + "+====================================+");
    }

    /**
     * Asks the player whether to attack or run.
     * @return true if player chooses to attack, false otherwise
     */
    public boolean runOrFight() {
        boolean notValid = true;
        boolean fight = false;
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
                        utility.sleep(500);
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
    public int duelToDeath(House house, Enemy enemy, int level) {
        boolean fight;

        utility.insertSpace();
        utility.insertLine();
        utility.sleep(500);

        System.out.println(UI.BOLD + "\nLEVEL: "+ UI.BOLD + level);
        System.out.println("Location: " + enemy.getLocation());
        System.out.println("Enemy: " + enemy.getName());

        do {
            promptStatus(house, enemy);
            fight = runOrFight();
            if (fight) {
                duel(house, enemy);
            } else {
                // Player chose to run - decrease level if possible and exit
                if (level > 1) {
                    level--;
                    // System.out.println("You retreat to level " + level + ".");
                }
                break;
            }
        } while (house.isAlive() && enemy.isAlive());

        // utility.sleep(800);
        // promptStatus(house, enemy);

        if (!enemy.isAlive()) {
            System.out.println("\nYou killed the " + enemy.getName() + "!");
            utility.sleep(1000);
            System.out.println("You won the battle!");
        } else if (!house.isAlive()) {
            System.out.println("\nYou have been defeated...");
        } 

        return level;  // Return updated level
    }
}
