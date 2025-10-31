/*
 * 	@Author KS Manejo 
 */

package tests;

import main.charactermanager.HouseFactory;
import main.charactermanager.House;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HouseFactoryTest {

    private HouseFactory houseFactory;

    @Before
    public void setUp() {
        houseFactory = new HouseFactory();
    }

    @Test
    public void testCreateHouseById() {
        assertEquals("Targaryen", houseFactory.createHouse(1).getName());
        assertEquals("Lannister", houseFactory.createHouse(2).getName());
        assertEquals("Stark", houseFactory.createHouse(3).getName());
    }

    @Test
    public void testCreateHouseAttributes() {
        House lannister = houseFactory.createHouse(2);
        House stark = houseFactory.createHouse(3);

        assertEquals("Lannister", lannister.getName());
        assertEquals(18, lannister.getArmor());
        assertEquals("Stark", stark.getName());
        assertEquals(115, stark.getHp());
    }

    @Test
    public void testCreatedHousesNotNull() {
        for (int id = 1; id <= 3; id++) {
            assertNotNull("House with ID " + id + " should not be null",
                    houseFactory.createHouse(id));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHouseIdThrowsException() {
        houseFactory.createHouse(99);
    }
}