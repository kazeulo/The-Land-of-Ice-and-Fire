/*
 * Classname: Stark
 * Description: Contains methods and attributes of house Stark
 * @Author KS Manejo
 */

package main.charactermanager;

import main.game.Utilities;

public class Stark extends House{
	
	private static final int HP = 100;
	private static final int ARMOR = 13;
	
	// constructor
	public Stark () {
		super("Stark", HP+15, ARMOR);				// additional HP for house stark
	}
	
	@Override
	public int attack() {
		// attack type will be picked by random
		Utilities utility = new Utilities();
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1: 
				System.out.println("You used Flurry!"); 
				attackDmg = 4; 
				break;
			case 2: 	
				System.out.println("You used Dark Bane!"); 
				attackDmg = 7;
				break;
			case 3: 
				System.out.println("You use Millionth Edge!"); 
				attackDmg = 10;
				break;
		}
		return super.attack()+attackDmg;
	}	

}
