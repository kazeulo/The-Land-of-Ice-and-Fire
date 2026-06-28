/*
 * Classname: Lannister
 * Description: Contains methods and attributes of house Lannister
 * @Author KS Manejo
 */

package main.charactermanager;

import main.game.Utilities;

public class Lannister extends House{
	
	private static final int HP = 100;
	private static final int ARMOR = 13;
	
	// constructor
	public Lannister () {
		super("Lannister", HP, ARMOR+5);			// additional armor for house Lannister	
	}
	
	@Override
	public String specialName() { return "IRON BANK"; }

	@Override
	public SpecialResult useSpecial(Enemy enemy) {
		boolean miss = utility.randInt(1, 8) == 8;
		if (miss) {
			return new SpecialResult(
				"IRON BANK",
				"A Lannister always pays his debts! IRON BANK activated!",
				new int[]{0}, new boolean[]{true},
				new String[]{}, new String[]{"Attack missed!"},
				10, "But gold sustains you — recovered 10 HP."
			);
		}
		int dmg  = enemy.takenDamage(attack());
		int heal = dmg / 2;
		return new SpecialResult(
			"IRON BANK",
			"A Lannister always pays his debts! IRON BANK activated!",
			new int[]{dmg}, new boolean[]{false},
			new String[]{"You used " + lastMoveName + "! Dealt " + dmg + " damage."},
			new String[]{},
			heal, "Recovered " + heal + " HP!"
		);
	}

	@Override
	public int attack() {
		// attack type will be chosen randomly
		Utilities utility = new Utilities();
		
		int attackType = utility.randInt(1,3);
		int attackDmg = 0;
		
		switch(attackType) {
			case 1:
				lastMoveName = "Aura Cutter";
				System.out.println("You used Aura Cutter!");
				attackDmg = 3;
				break;
			case 2:
				lastMoveName = "Blood Strike";
				System.out.println("You used Blood Strike!");
				attackDmg = 6;
				break;
			case 3:
				lastMoveName = "Crescent Slash";
				System.out.println("You used Cresent Slash!");
				attackDmg = 9;
				break;
		}
		return super.attack()+attackDmg;
	}
}
