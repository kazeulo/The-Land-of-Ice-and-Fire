/*
 * 	@Author KS Manejo 
 */

package finalproject;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import charactermanager.EnemyFactory;

public class EnemyFactoryTest {

    private EnemyFactory enemyFactory;

    @Before
    public void setUp() {
        enemyFactory = new EnemyFactory();
    }

    @Test
    public void testCreateEnemyById() {
        assertEquals("Wildling", enemyFactory.createEnemy(1).getName());
        assertEquals("Giant", enemyFactory.createEnemy(2).getName());
        assertEquals("White Walker", enemyFactory.createEnemy(3).getName());
        assertEquals("Night King", enemyFactory.createEnemy(4).getName());
    }

    @Test
    public void testCreateEnemyNotNull() {
        for (int id = 1; id <= 4; id++) {
            assertNotNull("Enemy with ID " + id + " should not be null",
                          enemyFactory.createEnemy(id));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEnemyIdThrowsException() {
        enemyFactory.createEnemy(99);
    }
}