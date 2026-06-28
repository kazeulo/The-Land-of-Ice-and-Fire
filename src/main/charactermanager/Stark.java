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
		super("Stark", HP+25, ARMOR);				// additional HP for house stark
	}
	
	@Override
	public String specialName() { return "PACK HUNT"; }

	@Override
	public SpecialResult useSpecial(Enemy enemy) {
		boolean miss1 = utility.randInt(1, 8) == 8;
		int dmg1 = miss1 ? 0 : enemy.takenDamage(attack());

		boolean miss2 = !enemy.isAlive() || utility.randInt(1, 8) == 8;
		int dmg2 = miss2 ? 0 : enemy.takenDamage(attack());

		return new SpecialResult(
			"PACK HUNT",
			"Ghost joins the hunt! PACK HUNT — two strikes incoming!",
			new int[]{dmg1, dmg2},
			new boolean[]{miss1, miss2},
			new String[]{"First strike dealt " + dmg1 + " damage!", "Ghost struck for " + dmg2 + " damage!"},
			new String[]{"First strike missed!", "Ghost's strike missed!"},
			0, null
		);
	}

	@Override
	public int attack() {
		// attack type will be picked by random
		Utilities utility = new Utilities();
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1:
				lastMoveName = "Flurry";
				System.out.println("You used Flurry!");
				attackDmg = 4;
				break;
			case 2:
				lastMoveName = "Dark Bane";
				System.out.println("You used Dark Bane!");
				attackDmg = 7;
				break;
			case 3:
				lastMoveName = "Millionth Edge";
				System.out.println("You use Millionth Edge!");
				attackDmg = 10;
				break;
		}
		return super.attack()+attackDmg;
	}	

}
