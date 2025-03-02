/*
 *  Class name: House
 *  Description: contains methods and attributes for house
 *  @Author KS Manejo
 */

package charactermanager;

import game.Utilities;

public class House extends Character{
	
	Utilities utility = new Utilities();
	
	private int base_attackDamage = 12;
	int armor;
	
	public House (String name, int hp, int armor) {
		super (name, hp);
		this.armor = armor;
	}
	
	/*	
	 * 	will be overriden in subclass since 
	 * 	different house can deal different damages
	 */
	@Override
	public int attack() {
		return base_attackDamage;				// base attack damage
	}
	
	@Override
	public int takenDamage(int attackDamage) {
		// attack is reduced by 30% of armor
		attackDamage -= (int)armor * 0.30;					
		return super.takenDamage(attackDamage);
	}	
	
	//GETTER AND SETTER//		
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public int getArmor() {
		return armor;
	}
	
	// reducing value of armor every time enemy attacks
	// but only if the player's hp is less than 85
	public int reduceArmor(int hp) {
		if (hp < 85) {
			armor -= 1;
		}
		// notify player if the armor is broken
		if (armor <= 0) {
			System.out.println("\nYour armor is broken!");
			armor = 0;
		}
		return armor;
	}

	
	/*
	 * these are actions a player can choose once entering the tavern
	 * player will enter the tavern before battling the night king
	 */
	public int rest() {
		// restore hp by 50
		hp += 40;
		return hp;
	}

	public int repairArmor() {
		// repair armor by 5
		armor += 7;
		return armor;
	}
					
	public int drinkStengthPotion() {
		int additionalAttackDamage;
		
		// increases base attack damage
		// which means players attack damage will increase for all  the attack type
		additionalAttackDamage = utility.randInt (5, 10);
		base_attackDamage += additionalAttackDamage;
		
		return additionalAttackDamage;
	}
}

