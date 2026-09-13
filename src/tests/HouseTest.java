/*
 * 	@Author KS Manejo 
 */

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.charactermanager.House;
import main.charactermanager.HouseFactory;

public class HouseTest {
	
	HouseFactory hFactory = new HouseFactory();
	House house;
	
	@Test
	public void testTakenDamage() {

		house = hFactory.createHouse(1);
		assertEquals(6, house.takenDamage(10));
		assertEquals(16, house.takenDamage(20));

		house = hFactory.createHouse(2);
		assertEquals(4, house.takenDamage(10));
		assertEquals(12, house.takenDamage(18));
	}

	@Test
	public void testReduceArmor() {
		// reduceArmor is deprecated and now a no-op; armor only degrades via block()
		house = hFactory.createHouse(1);
		assertEquals(13, house.reduceArmor(100));
		assertEquals(13, house.reduceArmor(84));
		assertEquals(13, house.reduceArmor(86));
	}
}
