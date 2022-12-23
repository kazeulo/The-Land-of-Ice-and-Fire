/*
 * 	Description: contains classes and methods for enemy types and attributes
 * 	@Author KS Manejo
 */

package charactermanager;

import game.Utilities;

public class Enemy extends Character{
	
	private String location;
	Utilities utility =  new Utilities();	// in order to access randInt method
	
	private static final int HP = 100;
	
	public Enemy (String name, String location) {
		super(name, HP);
		this.location = location;
	}
	
	/*	
	 * will be overriden in subclass since 
	 * different enemy types can deal different damages
	 */
	@Override
	public int attack () {
		return 0;
	}
	
	// Setter and getter for location
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation (){
		return location;
	}
}


// EnemyType: Wildling
class Wildling extends Enemy {
	public Wildling() {
		super("Wildling", "Haunted Forest");
	}
	
	@Override
	public int attack () {
		int attackDmg = utility.randInt(6, 10);
		return attackDmg;
	}
}


// EnemyType: Giant
class Giant extends Enemy{
	public Giant() {
		super("Giant", "Fist of the First Men");
	}
	
	@Override
	public int attack () {
		int attackDmg = utility.randInt(7, 13);
		return attackDmg;
	}
}


// EnemyType: WhiteWalker
class WhiteWalker extends Enemy{
	public WhiteWalker() {
		super("White Walker", "Frostfangs");
	}
	
	@Override
	public int attack () {
		int attackDmg = utility.randInt(9, 16);
		return attackDmg;
	}
}


// EnemyType: Nigh King (Boss)
// has higher attack damage
class NightKing extends Enemy{
	public NightKing() {
		super("Night King", "The Land of ALways Winter");
	}
	
	@Override
	public int attack () {
		int attackDmg = utility.randInt(11, 23);
		return attackDmg;
	}
}
