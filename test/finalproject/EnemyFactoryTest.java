/*
 * 	@Author KS Manejo 
 */

package finalproject;

import static org.junit.Assert.*;

import org.junit.Test;

import charactermanager.EnemyFactory;

public class EnemyFactoryTest {

	@Test
	public void testCreatedEnemy() {
		EnemyFactory eFactory = new EnemyFactory();
		
		assertEquals("Wildling", eFactory.createEnemy(1).getName());
		assertEquals("Giant", eFactory.createEnemy(2).getName());
		assertEquals("White Walker", eFactory.createEnemy(3).getName());
		assertEquals("Night King", eFactory.createEnemy(4).getName());
	}

}
