package game.creature.stat;

import java.util.Random;

/**
 * BaseStats provide a simple wrapper class to store BaseStat info in.
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class BaseStat
{	
	/**
	 * Strength represents the physical strength and power for a Creature.
	 * This forms the base values for:
	 * <li>Damage dealt by physical attacks</li>
	 * <li>How much weight in items can be carried</li>
	 * <li>How heavy of items can be moved/lifted</li>
	 */
	public int strength;

	/**
	 * Stamina represents the physical fortitude and resilience for a Creature.
	 * This forms the base values and modifiers for:
	 * <li>Recovery rate for VitalStat Damage</li>
	 * <li>Reducing damage received by physical attacks.</li>
	 * <li>Decay rate for VitalStats Hunger and Thirst.</li>
	 * <li>Consumption rate of Energy spent to take actions.</li>
	 */
	public int stamina;
	
	/**
	 * Agility represents how quick, agile, and accurate you are.
	 * A high Agility provides:
	 * <li>Increased likelihood of dodging attacks.</li>
	 * <li>Decreased likelihood of enemies dodging your attacks.</li>
	 * <li>Increased movement speed.</li>
	 * <li>Move quieter, avoid detection.</li>
	 */
	public int agility;
	
	/**
	 * Intelligence represents how intellectually intelligent you are (your IQ).
	 * Intelligence provides:
	 * <li>Easier to learn Skills<li>
	 * <li>Benefit from Experience quicker (faster level-ups)<li>
	 */
	public int intelligence;


	/**
	 * Wisdom represents your emotional intelligence, and street smarts.
	 * Wisdom provides:
	 * <li>Increased recovery rate of VitalStat stress<li>
	 * <li>Benefit from Experience quicker (faster level-ups)<li>
	 */
	public int wisdom;
	
	/**
	 * Charisma represents how charismatic you are - social skills and similar.
	 * Charisma lets you:
	 * <li>Convince creatures, enemies, and characters to do things.</li>
	 * <li>Get lower prices in stores (better at haggling).</li>
	 * <li>Easier to recruit followers and companions</li>
	 */
	public int charisma;
	
	/**
	 * Create a new BaseStat with the given starting values.
	 * @param strength The initial value.
	 * @param stamina The initial value.
	 * @param agility The initial value.
	 * @param intelligence The initial value.
	 * @param wisdom The initial value.
	 * @param charisma The initial value.
	 */
	public BaseStat(int strength, int stamina, int agility, int intelligence, int wisdom, int charisma){
		this.strength = strength;
		this.stamina = stamina;
		this.agility = agility;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
	}
	
	/**
	 * Create a new BaseStat with initial values of all zero.
	 */
	public BaseStat(){}
	
	/**
	 * Generate a randomly created VitalStats by setting each value to the min
	 * value, added to two randomly generated numbers ranging from zero to
	 * (maxRand - 1).
	 * 
	 * @param min The minimum value.
	 * @param maxRand The upper bound for random numbers that are generated.
	 * @return A set of VitalStats that are randomly generated.
	 */
	public static BaseStat generateRandomSet(int min, int maxRand) {
		Random r = new Random();
		BaseStat stat = new BaseStat(); // initialize randomly.
		stat.strength = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.stamina = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.agility = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.intelligence = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.wisdom = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.charisma = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		return stat;
	}
}
