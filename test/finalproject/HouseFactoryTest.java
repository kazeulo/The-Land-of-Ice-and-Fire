/*
 * 	@Author KS Manejo 
 */

package finalproject;

import static org.junit.Assert.*;

import org.junit.Test;

import charactermanager.HouseFactory;

public class HouseFactoryTest {

	@Test
	public void testCreatedHouse() {
		HouseFactory hFactory = new HouseFactory();
		
		assertEquals("Targaryen", hFactory.createHouse(1).getName());
		assertEquals("Lannister", hFactory.createHouse(2).getName());
		assertEquals("Stark", hFactory.createHouse(3).getName());
		assertEquals(18, hFactory.createHouse(2).getArmor());
		assertEquals(115, hFactory.createHouse(3).getHp());
	}
}
