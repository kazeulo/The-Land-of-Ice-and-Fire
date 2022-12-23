/*
 *	Class Name: EnemyFactory
 *	Description: in charge of creating an enemy 
 *	@Author KS Manejo
 */

package charactermanager;

public class EnemyFactory {
	// the type of enemy will depend on level
	public Enemy createEnemy(int level) {
		Enemy enemy = null;
		
		switch (level) {
			case 1: enemy = new Wildling (); break;
			case 2: enemy = new Giant(); break;
			case 3: enemy = new WhiteWalker (); break;
			case 4: enemy = new NightKing (); break;
		}
		return enemy;
	}
}
