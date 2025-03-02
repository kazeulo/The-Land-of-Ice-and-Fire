/*
 * Classname: Targaryen
 * Description: contains methods and attributes of house Targaryen
 * @Author KS Manejo
 */

package charactermanager;

import game.Utilities;

public class Targaryen extends House{
	
	private static final int HP = 100;
	private static final int ARMOR = 13;
	
	// constructor
	public Targaryen () {
		super("Targaryen", HP, ARMOR);
	}
	
	
	// higher attack values for house targaryen
	@Override
	public int attack() {
		Utilities utility = new Utilities();
		
		// attack type will be chosen randomly
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1: 
				System.out.println("You used Wildfire!"); 
				attackDmg = 6; 
				break;
			case 2: 	
				System.out.println("You used Ignimancy!"); 
				attackDmg = 10;
				break;
			case 3: 
				System.out.println("You used Pyromania!"); 
				attackDmg = 13;
				break;
		}
		return super.attack()+attackDmg;
	}
}
