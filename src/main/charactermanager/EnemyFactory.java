/*
 *	Class Name: EnemyFactory
 *	Description: in charge of creating an enemy 
 *	@Author KS Manejo
 */

package main.charactermanager;

public class EnemyFactory {

    /**
     * Creates an enemy instance depending on the given level.
     *
     * @param level the difficulty or level of the enemy
     * @return an Enemy instance corresponding to the given level
     * @throws IllegalArgumentException if the level does not correspond to a valid enemy type
     */
    public Enemy createEnemy(int level) {
        switch (level) {
            case 1:
                return new Wildling();
            case 2:
                return new Giant();
            case 3:
                return new WhiteWalker();
            case 4:
                return new NightKing();
            default:
                throw new IllegalArgumentException("Invalid enemy level: " + level);
        }
    }
}