/*
 * Classname: Lannister
 * Description: Contains methods and attributes of house Lannister
 * @Author KS Manejo
 */

package charactermanager;

import game.Utilities;

public class Lannister extends House{
	
	private static final int HP = 100;
	private static final int ARMOR = 13;
	
	// constructor
	public Lannister () {
		super("Lannister", HP, ARMOR+5);			// additional armor for house Lannister	
	}
	
	@Override
	public int attack() {
		// attack type will be chosen randomly
		Utilities utility = new Utilities();
		
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1: 
				System.out.println("You used Aura Cutter!"); 
				attackDmg = 3; 
				break;
			case 2: 	
				System.out.println("You used Blood Strike!"); 
				attackDmg = 6;
				break;
			case 3: 
				System.out.println("You used " + super.getName() + " used Cresent Slash!"); 
				attackDmg = 9;
				break;
		}
		return super.attack()+attackDmg;
	}
}
