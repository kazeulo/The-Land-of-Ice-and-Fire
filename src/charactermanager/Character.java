/*
 * 	Class name: Character
 * 	Description: contains common methods for enemy and hosue
 * 	@Author KS Manejo 
 */

package charactermanager;

public abstract class Character {
	
	// chosen house or type of enemy
	private String name;
	int hp;
	
	// constructor
	public Character (String name, int hp) {
		this.name = name;	
		this.hp = hp;
	}
	
	// implement in subclass
	public abstract int attack();
	
	// check if character is still alive
	public boolean isAlive() {
		return hp > 0;
	}
	
	// subtract amount of attack damage from character's hp
	public int takenDamage(int attackDamage) {
		hp -= attackDamage;
		return attackDamage;
	}
	
	// GETTERS AND SETTERS //
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
		
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getHp (){
		// keep the value of the hp restricted to 0 if it becomes negative
		if (hp < 0) {
			hp = 0;
		}
		return hp;
	}
}
