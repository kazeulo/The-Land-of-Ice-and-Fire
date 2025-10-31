/*
 * Classname: GameInfo
 * Description: Provides detailed information about the game, including its lore, houses, and enemies.
 * @Author KS Manejo
 */

package main.game;

import java.util.Scanner;

public class GameInfo {

    private final Utilities utility = new Utilities();
    private final Scanner in = new Scanner(System.in);

    /**
     * Main menu for viewing game-related information.
     */
    public void chooseGameInfo() {
        boolean running = true;

        while (running) {
            utility.insertSpace();
            utility.insertLine();
            System.out.println("\n                  ************  GAME INFORMATION  ************");
            System.out.println("\nWhat would you like to explore?");
            System.out.println("\t(1) Noble Houses of Westeros");
            System.out.println("\t(2) The Enemies Beyond the Wall");
            System.out.println("\t(3) The Legend of the Long Night");
            System.out.println("\t(4) Return to Main Menu");
            System.out.print("[Int] Your choice: ");

            int select = getValidatedInput();

            utility.insertSpace();
            utility.insertLine();

            switch (select) {
                case 1:
                    displayHouses();
                    break;
                case 2:
                    displayEnemies();
                    break;
                case 3:
                    displayGameLore();
                    break;
                case 4:
                    System.out.println("\nReturning to main menu...");
                    utility.sleep(1000);
                    Main.mainMenu();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Please select an option between 1 and 4.");
            }
        }
    }

    /**
     * Reads validated integer input from the player.
     */
    private int getValidatedInput() {
        while (!in.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            in.next(); // clear invalid input
            System.out.print("[Int] Your choice: ");
        }
        return in.nextInt();
    }

    /**
     * Displays information about the noble houses of Westeros.
     */
    private void displayHouses() {
        String[] houseNames = {"House Targaryen", "House Lannister", "House Stark"};
        String[] mottos = {"Fire and Blood", "Hear Me Roar!", "Winter is Coming"};
        String[] specialAbilities = {
            "Mastery of dragons, granting increased attack power.",
            "Unyielding wealth and armor, providing additional defense.",
            "Resilience in the harshest winters, enhancing health and endurance."
        };

        System.out.println("\n                  *************  NOBLE HOUSES  *************");
        for (int i = 0; i < houseNames.length; i++) {
            utility.sleep(700);
            System.out.println("\nHouse: " + houseNames[i]);
            System.out.println("Motto: \"" + mottos[i] + "\"");
            System.out.println("Special Ability: " + specialAbilities[i]);
        }

        utility.sleep(1000);
        System.out.println("\nPress Enter to return to the Game Info menu...");
        in.nextLine(); // consume leftover newline
        in.nextLine(); // wait for Enter
    }

    /**
     * Displays information about the enemies encountered in the game.
     */
    private void displayEnemies() {
        String[] enemyNames = {"Wildling Raider", "Mountain Giant", "White Walker", "The Night King"};
        String[] locations = {"Haunted Forest", "Fist of the First Men", "Frostfangs", "Land of Always Winter"};
        String[] attackPower = {"6 - 10 damage", "7 - 13 damage", "8 - 16 damage", "11 - 23 damage"};
        String[] descriptions = {
            "Savage fighters from beyond the Wall, skilled in ambush tactics.",
            "Colossal beings with immense strength, feared even by seasoned warriors.",
            "Ancient undead creatures with terrifying frost magic.",
            "The supreme leader of the White Walkers, a being of unparalleled power."
        };

        System.out.println("\n          *************  THE ENEMIES BEYOND THE WALL  *************");
        for (int i = 0; i < enemyNames.length; i++) {
            utility.sleep(700);
            System.out.println("\nEnemy: " + enemyNames[i]);
            System.out.println("Location: " + locations[i]);
            System.out.println("Attack Power: " + attackPower[i]);
            System.out.println("Description: " + descriptions[i]);
        }

        utility.sleep(1000);
        System.out.println("\nPress Enter to return to the Game Info menu...");
        in.nextLine(); // consume leftover newline
        in.nextLine(); // wait for Enter
    }

    /**
     * Displays the main story and lore of the game.
     */
    private void displayGameLore() {
        System.out.println("\n                  **********  THE LEGEND OF THE LONG NIGHT  **********");
        utility.sleep(1000);
        System.out.println("\nLong ago, during the Age of Heroes, a great darkness fell upon Westeros.");
        System.out.println("An endless winter, known as the Long Night, brought death and despair.");
        System.out.println("From the Land of Always Winter, the White Walkers emerged, bringing fear to the realm.");
        System.out.println("Only the unity of Westeros' greatest warriors could drive them back.");
        utility.sleep(1000);
        System.out.println("\nNow, the darkness returns. The Night King has risen once more.");
        System.out.println("You must forge your path, choose your house, and fight for the survival of mankind.");
        System.out.println("Will you lead Westeros to victory, or will winter consume all?");
        System.out.println("The choice is yours.");
        utility.sleep(1200);

        System.out.println("\nPress Enter to return to the Game Info menu...");
        in.nextLine(); // consume leftover newline
        in.nextLine(); // wait for Enter
    }
}
