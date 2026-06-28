/*
 * 	Description: contains classes and methods for enemy types and attributes
 * 	@Author KS Manejo
 */

package main.charactermanager;

import main.game.Utilities;

public class Enemy extends Character{

	private String location;
	Utilities utility =  new Utilities();

	public Enemy (String name, String location, int hp) {
		super(name, hp);
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
		super("Wildling", "Haunted Forest", 80);
	}

	@Override
	public int attack () {
		return utility.randInt(6, 10);
	}
}


// EnemyType: Giant
class Giant extends Enemy{
	public Giant() {
		super("Giant", "Fist of the First Men", 130);
	}

	@Override
	public int attack () {
		return utility.randInt(7, 13);
	}
}


// EnemyType: WhiteWalker
class WhiteWalker extends Enemy{
	public WhiteWalker() {
		super("White Walker", "Frostfangs", 165);
	}

	@Override
	public int attack () {
		return utility.randInt(9, 16);
	}
}


// EnemyType: Night King (Boss)
class NightKing extends Enemy{
	public NightKing() {
		super("Night King", "The Land of Always Winter", 220);
	}

	@Override
	public int attack () {
		return utility.randInt(11, 23);
	}
}
