/*
 * Classname: Utilities
 * Description: contains helper methods and prompts to tidy up the code
 * @Author: KS Manejo
 */

package game;

import java.util.Random;

public class Utilities{
	
	Random rand = new Random();
	
	// inclusive random integer
	public int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	// print lines
	public void insertLine() {
		System.out.println("==========================================================================");
	}
	
	// pause the game for awhile for dramatic effect
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	// print spaces
	public void insertSpace() {
		System.out.print ("\n");
	}
	
	// prompt title
	public void promptTitle() {
		insertLine();
        System.out.println(">>>------------>>>  Welcome to The Land of Ice and Fire!  <<<----------<<<");
        insertLine();
	}
	
	// story intro
	public void intro() {
		insertSpace();
		insertLine();
		sleep(1200);
		System.out.println("\nWinter is coming...");
		sleep(1000);
		System.out.println("The night grows darker and the unknown lurks in the shadows.");
		sleep(1000);
		System.out.println("You must go north to find and kill the Night King.");
		sleep(1000);
		System.out.println("Yours will be the hand that will save Westeros. ");
		sleep(1000);
		System.out.println("\nYour journey begins now...");
		sleep(1300);
	}
}
