package game.creature;

import game.creature.stat.BaseStat;
import game.creature.stat.VitalStat;


/**
 * Represents a hero.
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class Character extends Creature
{
	// ****************************************************
	// Default Values
	// ****************************************************
	
	private static final int	MIN_VITAL			= 5;
	private static final int	RAND_VITAL_BOUND	= 6;
	
	// ****************************************************
	// Fields
	// ****************************************************
	
	/**The total amount of earned experience by this Character.**/
	public int experience;
	
	/**The current level of this Character.**/
	public int level;
	
	/**The Class of the Character (Fighter, Thief, Cleric, or Wizard, etc). **/
	public CharacterClass characterClass;
	
	/**
	 * Create a new Character with randomly generated stats.
	 * 
	 * @param name The name of the Character.
	 * 
	 */
	public Character(String name) {
		super(name, VitalStat.generateRandomSet(MIN_VITAL, RAND_VITAL_BOUND),
				BaseStat.generateRandomSet(MIN_VITAL, RAND_VITAL_BOUND));
		level = 1;
		experience = 0;
	}
	
}
