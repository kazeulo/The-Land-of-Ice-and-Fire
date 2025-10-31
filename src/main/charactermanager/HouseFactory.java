/*
 * Class name: HouseFactory
 * Description: in charge of creating house
 * @Author KS Manejo
 */

package main.charactermanager;

public class HouseFactory {

    /**
     * Creates a house instance based on the player's choice.
     *
     * @param choice the selected house option
     * @return a House instance corresponding to the choice
     * @throws IllegalArgumentException if the choice does not correspond to a valid house
     */
    public House createHouse(int choice) {
        switch (choice) {
            case 1:
                return new Targaryen();
            case 2:
                return new Lannister();
            case 3:
                return new Stark();
            default:
                throw new IllegalArgumentException("Invalid house choice: " + choice);
        }
    }
}