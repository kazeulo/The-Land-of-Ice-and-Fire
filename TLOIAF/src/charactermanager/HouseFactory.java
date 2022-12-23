/*
 * Class name: HouseFactory
 * Description: in charge of creating house
 * @Author KS Manejo
 */

package charactermanager;

public class HouseFactory {
	// player gets to choose a house
	public House createHouse(int choice) {
		House house = null;
		
		switch (choice) {
			case 1: house = new Targaryen(); break;
			case 2: house = new Lannister(); break;
			case 3: house = new Stark(); break;
		}
		return house;
	}
	
}
