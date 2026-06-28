/*
 *  Class name: House
 *  Description: contains methods and attributes for house
 *  @Author KS Manejo
 */

package main.charactermanager;

import main.game.Utilities;

public abstract class House extends Character {
	
	Utilities utility = new Utilities();

	private int baseAttackDamage = 12;
	int armor;
	protected String lastMoveName = "";
	private final int maxHp;

	public House (String name, int hp, int armor) {
		super (name, hp);
		this.armor = armor;
		this.maxHp = hp;
	}
	
	/*
	 * 	will be overriden in subclass since
	 * 	different house can deal different damages
	 */
	@Override
	public int attack() {
		return baseAttackDamage;
	}

	/** Display name for this house's special ability (shown on the button). */
	public abstract String specialName();

	/** Execute the special ability against the enemy and return the full result for the UI. */
	public abstract SpecialResult useSpecial(Enemy enemy);
	
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

	public String getLastMoveName() {
		return lastMoveName;
	}
	
	/** Reduces armor by 2 when the player blocks an attack. Returns the new armor value. */
	public int block() {
		armor = Math.max(0, armor - 2);
		return armor;
	}

	/** @deprecated Armor only degrades on block now; use {@link #block()} instead. */
	@Deprecated
	public int reduceArmor(int hp) {
		return armor;
	}

	
	/*
	 * these are actions a player can choose once entering the tavern
	 * player will enter the tavern before battling the night king
	 */
	public int rest() {
		hp = Math.min(hp + 40, maxHp);
		return hp;
	}

	public int repairArmor() {
		// repair armor by 5
		armor += 7;
		return armor;
	}
					
	public int drinkStrengthPotion() {
		int additionalAttackDamage = utility.randInt(5, 10);
		baseAttackDamage += additionalAttackDamage;
		return additionalAttackDamage;
	}
}

