/*
 * Classname: BattleManager
 * Description: Handles all battle-related logic
 * @Author KS Manejo
 */

package main.game;

import main.charactermanager.Enemy;
import main.charactermanager.House;
import main.charactermanager.SpecialResult;

import java.util.Scanner;

public class BattleManager {

    private static final int BAR_WIDTH = 20;

    private final Utilities utility = new Utilities();
    private final Scanner in = UI.IN;

    // Special ability cooldown (turns remaining), reset at the start of every duel
    private int specialCooldown = 0;

    /**
     * 12.5% chance (1/8) to return true — simulates a miss.
     */
    public boolean coinToss() {
        return utility.randInt(1, 8) == 8;
    }

    /**
     * 15% chance (3/20) to return true — simulates a critical hit.
     */
    public boolean critCheck() {
        return utility.randInt(1, 20) <= 3;
    }

    /**
     * Renders a fixed-width block bar, e.g. [##########----------].
     */
    private String bar(int current, int max, String color) {
        int clamped = Math.max(0, Math.min(current, max));
        int filled = max <= 0 ? 0 : (int) Math.round(BAR_WIDTH * (clamped / (double) max));
        StringBuilder sb = new StringBuilder();
        sb.append(color).append('[');
        for (int i = 0; i < BAR_WIDTH; i++) sb.append(i < filled ? '#' : '-');
        sb.append(']').append(UI.RESET);
        return sb.toString();
    }

    /**
     * Displays current HP and Armor status for the player and enemy.
     */
    public void promptStatus(House house, Enemy enemy, int maxEnemyHp) {
        utility.sleep(500);

        System.out.println("\n+============ " + UI.BOLD + "STATUS BAR" + UI.RESET + " ============+");
        System.out.println(UI.CYAN + UI.BOLD + "    " + house.getName() + UI.RESET);
        System.out.println("    HP:    " + bar(house.getHp(), house.getMaxHp(), UI.GREEN)
            + " " + house.getHp() + "/" + house.getMaxHp());
        System.out.println("    Armor: " + UI.BLUE + house.getArmor() + UI.RESET);
        System.out.println(UI.RESET + "--------------------------------------");
        System.out.println(UI.RED + UI.BOLD + "    " + enemy.getName() + UI.RESET);
        System.out.println("    HP:    " + bar(enemy.getHp(), maxEnemyHp, UI.RED)
            + " " + Math.max(0, enemy.getHp()) + "/" + maxEnemyHp);
        System.out.println(UI.RESET + "+====================================+");
    }

    /**
     * Asks the player to choose an action for this turn.
     * @return 1 = Attack, 2 = Block, 3 = Special, 4 = Run
     */
    public int chooseAction(House house) {
        boolean notValid = true;
        int action = 0;

        while (notValid) {
            System.out.println("\nChoose an action:");
            System.out.println("\t(1) Attack");
            System.out.println("\t(2) Block");
            if (specialCooldown == 0) {
                System.out.println("\t(3) " + house.specialName() + " (Special)");
            } else {
                System.out.println("\t(3) " + house.specialName() + " " + UI.YELLOW
                    + "(recovering, " + specialCooldown + " more turn" + (specialCooldown == 1 ? "" : "s") + ")" + UI.RESET);
            }
            System.out.println("\t(4) Run");
            System.out.print("[Int] You choose: ");

            if (in.hasNextInt()) {
                int choice = in.nextInt();
                if (choice == 3 && specialCooldown > 0) {
                    System.out.println(UI.YELLOW + "That ability is still recovering." + UI.RESET);
                } else if (choice >= 1 && choice <= 4) {
                    action = choice;
                    notValid = false;
                } else {
                    System.out.println("Invalid input. Enter 1-4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                in.next(); // clear invalid input
            }
        }
        return action;
    }

    private void performAttack(House house, Enemy enemy) {
        System.out.println("\nYou charge at the " + enemy.getName() + "!");
        utility.sleep(600);

        if (coinToss()) {
            System.out.println(UI.YELLOW + "Your attack missed!" + UI.RESET);
            return;
        }

        int atk = house.attack();
        String move = house.getLastMoveName();
        boolean crit = critCheck();
        if (crit) atk *= 2;
        int dmg = enemy.takenDamage(atk);

        if (crit) {
            System.out.println(UI.BOLD + UI.YELLOW + "CRITICAL HIT! " + move + " dealt " + dmg + " damage!" + UI.RESET);
        } else {
            System.out.println(UI.GREEN + "You used " + move + "! Dealt " + dmg + " damage to " + enemy.getName() + "." + UI.RESET);
        }
    }

    private void performBlock(House house) {
        System.out.println("\nYou raise your shield and brace for impact!");
        int newArmor = house.block();
        if (newArmor <= 0) {
            System.out.println(UI.BLUE + "Shield raised!" + UI.RESET + " Your armor is destroyed — you can no longer block.");
        } else {
            System.out.println(UI.BLUE + "Shield raised! Armor now at " + newArmor + "." + UI.RESET);
        }
    }

    private void performSpecial(House house, Enemy enemy) {
        SpecialResult r = house.useSpecial(enemy);
        System.out.println("\n" + UI.PURPLE + r.callout + UI.RESET);
        utility.sleep(500);

        for (int i = 0; i < r.damages.length; i++) {
            if (r.missed[i]) {
                System.out.println(UI.YELLOW + r.missLogs[i] + UI.RESET);
            } else {
                System.out.println(UI.GREEN + r.hitLogs[i] + UI.RESET);
            }
            if (!enemy.isAlive()) break;
            utility.sleep(400);
        }

        if (r.healing > 0) {
            house.setHp(Math.min(house.getHp() + r.healing, house.getMaxHp()));
            if (r.healLog != null) System.out.println(UI.CYAN + r.healLog + UI.RESET);
        }

        specialCooldown = 3;
    }

    private void resolveEnemyTurn(House house, Enemy enemy, boolean blocked) {
        if (!enemy.isAlive()) return;

        System.out.println("\n" + enemy.getName() + " retaliates!");
        utility.sleep(600);

        if (blocked) {
            System.out.println(UI.BLUE + "The attack is fully blocked!" + UI.RESET);
            return;
        }

        if (coinToss()) {
            System.out.println(UI.CYAN + "You dodged the attack!" + UI.RESET);
        } else {
            int dmg = house.takenDamage(enemy.attack());
            System.out.println(UI.RED + enemy.getName() + " dealt " + dmg + " damage to you." + UI.RESET);
        }
    }

    /**
     * Conducts a full duel until one side dies or the player runs.
     * @return the updated level (decremented if the player ran)
     */
    public int duelToDeath(House house, Enemy enemy, int level) {
        specialCooldown = 0;
        int maxEnemyHp = enemy.getHp();

        utility.insertSpace();
        utility.insertLine();
        utility.sleep(500);

        System.out.println(UI.BOLD + "\nLEVEL: " + level + UI.RESET);
        System.out.println("Location: " + enemy.getLocation());
        System.out.println("Enemy: " + enemy.getName());

        while (house.isAlive() && enemy.isAlive()) {
            promptStatus(house, enemy, maxEnemyHp);
            int action = chooseAction(house);

            if (action == 4) {
                utility.sleep(500);
                System.out.println("\nYou chose to run.");
                if (level > 1) level--;
                break;
            }

            boolean blocked = false;
            switch (action) {
                case 1 -> performAttack(house, enemy);
                case 2 -> { performBlock(house); blocked = true; }
                case 3 -> performSpecial(house, enemy);
            }

            if (!enemy.isAlive()) break;

            utility.sleep(600);
            resolveEnemyTurn(house, enemy, blocked);

            if (specialCooldown > 0) specialCooldown--;
        }

        if (!enemy.isAlive()) {
            System.out.println("\n" + UI.GREEN + "You killed the " + enemy.getName() + "!" + UI.RESET);
            utility.sleep(1000);
            System.out.println("You won the battle!");
        } else if (!house.isAlive()) {
            System.out.println("\n" + UI.RED + "You have been defeated..." + UI.RESET);
        }

        return level; // Return updated level
    }
}
