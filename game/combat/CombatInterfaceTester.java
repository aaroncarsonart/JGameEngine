package game.combat;

import java.util.Scanner;

import utility.Gender;
import utility.Orientation;
import utility.Pronoun;
import utility.Quad;

/**
 * Test the combat system for some battlers.
 * 
 * @author Aaron Carson
 * @version Aug 5, 2015
 */
public class CombatInterfaceTester
{
	private static final String	DASHED_LINE	= "------------------------------------------------------------";
	
	// ***********************************************************************
	// FIELDS
	// ***********************************************************************
	
	Battler						hero;
	Battler						monster;
	
	// ***********************************************************************
	// CONSTRUCTOR
	// ***********************************************************************
	
	/**
	 * Initialize values or a new simulation.
	 */
	public CombatInterfaceTester() {
		hero = createHero("John");
		monster = createGenericWeakMonster("goblin");
	}
	
	// ***********************************************************************
	// METHODS
	// ***********************************************************************
	
	/**
	 * Create the Hero to use in this simulation.
	 * 
	 * @param name The name of the hero.
	 * @return a new, unfortunate Hero.
	 */
	public Battler createHero(String name) {
		TestBattler hero = new TestBattler();
		
		// vital stats.
		hero.setName(name);
		hero.setGender(Gender.MALE);
		hero.setHealth(65);
		
		// attack statistics (# of attacks, range, critical, miss chance)
		hero.setAttacks(2);
		hero.setRange(16);
		hero.setMissChance(5);
		hero.setCritRate(5);
		hero.setCritMultiplier(2);
		
		// battle-related stats
		hero.setPower(8);
		hero.setDefense(1);
		hero.setAccuracy(1);
		hero.setEvasion(1);
		hero.setLuck(10);
		
		// all done
		return hero;
	}
	
	/**
	 * Create a generic, weak monster to use in this simulation.
	 * 
	 * @param name The name.
	 * @return A new monster of the given name, with default TestBattler stats.
	 */
	public Battler createGenericWeakMonster(String name) {
		TestBattler monster = new TestBattler();
		monster.setName(name);
		monster.setGender(Gender.NEUTER);
		monster.setGeneric(true);
		monster.setHealth(40);
		monster.setRange(16);
		monster.setAttacks(3);
		
		// set battle stats
		monster.setPower(7);
		monster.setDefense(5);
		monster.setAccuracy(10);
		monster.setEvasion(10);
		monster.setLuck(1);
		
		// set position
		monster.setQuad(TestBattler.getAdjacentQuad(hero.getQuad(),
				Orientation.ABOVE));
		// all other stats are
		return monster;
		
	}
	
	// ***********************************************************************
	// Simulations
	// ***********************************************************************
	
	/**
	 * All that happens is the hero and monster in question duke it out until
	 * one side wins.
	 */
	public int runAttackSimulation(boolean waitForInput) {
		Scanner scanner = new Scanner(System.in);
		boolean gameOver = false;
		int time = 1;
		
		// get a local copy of the monster and hero.
		Battler hero = this.hero.getCopy();
		Battler monster = this.monster.getCopy();
		String heroName = Battler.getNameFor(hero);
		String monsterName = Battler.getNameFor(monster);
		System.out.println(DASHED_LINE);
		System.out.printf("Begin Simulation: %s vs %s ...\n", heroName,
				monsterName);
		System.out.println(DASHED_LINE);
		System.out.println("Pre-battle statistics:");
		System.out.println();
		Battler.printStatistics(hero, monster);
		System.out.println();
		Battler.printStatistics(monster, hero);
		
		// simulation loop
		while (!gameOver) {
			System.out.println(DASHED_LINE);
			System.out.printf("turn :%d\n", time);
			System.out.println(DASHED_LINE);
			System.out.printf("%s: (%d/%d) %s: (%d/%d)\n",
					heroName,			// John
					hero.getHealth() - hero.getDamage(),	// 0
					hero.getHealth(),	// 10
					monsterName,		// Goblin
					monster.getHealth() - monster.getDamage(),// 0
					monster.getHealth() // 5
					);
			if (waitForInput) {
				System.out.print("> ");
				scanner.nextLine();
			}
			System.out.println();
			
			// take turns fighting.
			if (hero.isReadyToAttack()) Battler.attack(hero, monster);
			if (monster.isReadyToAttack()) Battler.attack(monster, hero);
			time++;
			System.out.println();
			
			// check if game over.
			if (hero.hasLethalDamage() || monster.hasLethalDamage()) {
				gameOver = true;
			}
		}
		
		// report the results
		Battler winner = hero.hasLethalDamage() ? monster : hero;
		Battler loser = winner == hero ? monster : hero;
		System.out.println(DASHED_LINE);
		System.out.println("The battle is over.");
		System.out.printf("%s has defeated %s.\n",
				Battler.getCapitalizedString(Battler.getNameFor(winner)),
				Battler.getNameFor(loser));
		System.out.println();
		// print the CombatStatistics for each hero.
		System.out.println(hero.getCombatStatistics().getStatReadout());
		System.out.println(monster.getCombatStatistics().getStatReadout());
		
		// hold to exit.
		if (waitForInput) {
			System.out.print("> ");
			scanner.nextLine();
		}
		
		if (winner == hero) return 1;
		else return 0;
	}
	
	// ***********************************************************************
	// MAIN
	// ***********************************************************************
	
	/**
	 * Run a simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CombatInterfaceTester tester = new CombatInterfaceTester();
		boolean waitForInput = false;
		int tests = 1;
		int wins = 0;
		for (int i = 0; i < tests; i++){
			wins += tester.runAttackSimulation(waitForInput);
		}
		System.out.println();
		System.out.println("Battles won by hero:    " + wins);
		System.out.println("Battles won by monster: " + (tests - wins));
	}
}
