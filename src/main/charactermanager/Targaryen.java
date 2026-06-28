/*
 * Classname: Targaryen
 * Description: contains methods and attributes of house Targaryen
 * @Author KS Manejo
 */

package main.charactermanager;

import main.game.Utilities;

public class Targaryen extends House{
	
	private static final int HP = 100;
	private static final int ARMOR = 13;
	
	// constructor
	public Targaryen () {
		super("Targaryen", HP, ARMOR);
	}
	
	@Override
	public String specialName() { return "DRAGONFIRE"; }

	@Override
	public SpecialResult useSpecial(Enemy enemy) {
		int dmg = enemy.takenDamage(attack() * 2);
		return new SpecialResult(
			"DRAGONFIRE",
			"Drogon descends! DRAGONFIRE engulfs the " + enemy.getName() + "!",
			new int[]{dmg},
			new boolean[]{false},
			new String[]{"Dragonfire dealt " + dmg + " damage!"},
			new String[]{},
			0, null
		);
	}

	@Override
	public int attack() {
		Utilities utility = new Utilities();
		
		// attack type will be chosen randomly
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1:
				lastMoveName = "Wildfire";
				System.out.println("You used Wildfire!");
				attackDmg = 6;
				break;
			case 2:
				lastMoveName = "Ignimancy";
				System.out.println("You used Ignimancy!");
				attackDmg = 10;
				break;
			case 3:
				lastMoveName = "Pyromania";
				System.out.println("You used Pyromania!");
				attackDmg = 13;
				break;
		}
		return super.attack()+attackDmg;
	}
}
