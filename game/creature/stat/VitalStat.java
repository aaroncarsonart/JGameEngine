package game.creature.stat;

import java.util.Random;

/**
 * Vital Stats represent the four stats that keep you alive. Too much of any of
 * these will kill you; low amount of all and you're healthy.
 * <p>
 * This may represent the current state of a character, or a bonus to the stats
 * from weapons, armor, or a class bonus.
 * <p>
 * By splitting up a traditional "Hunger clock" from other roguelikes into four
 * base stats that are all effected by stamina, but are reduced in various ways,
 * it adds more depth and interest to the game (more things to manage).
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class VitalStat
{
	/**
	 * Damage represents how much physical damage has been accumulated by a
	 * Creature. No damage shows the Creature is healthy and nominal; high
	 * Damage means that you are more and more Injured. You can die from
	 * critical damage.
	 * <p>
	 * Damage is slowly recovered while resting or sleeping.  Magic Potions,
	 * healing items, and healing spells may also recover damage.
	 */
	public int	damage;
	
	/**
	 * Hunger represents how much the creature needs food. A hunger of Zero
	 * means you recently ate. You can die of critical hunger.
	 * <p>
	 * Hunger is slowly increasing whenever time passes.  Hunger is recovered
	 * by consuming Food.  Magic cannot fix hunger.
	 */
	public int	hunger;
	
	/**
	 * Thirst represents how much the creature needs water. A thirst of Zero
	 * means you recently drank and are not thirsty. You can die of critical 
	 * thirst.  In general, you get thirsty before you get hungry.
	 * <p>
	 * Thirst is slowly increasing whenever time passes.  Thirst is recovered
	 * by consuming Drinks.  Magic cannot fix thirst.
	 */
	public int	thirst;
	
	/**
	 * Stress represents how much mental and physical stress a Creature is 
	 * currently experiencing.  A low stress means you are completely relaxed
	 * and rested. A high stress level means things are tense; you may be in 
	 * battle, sleep deprived, or have been adventuring for hours without rest.
	 * <p>
	 * High stress generally decreases performance in all areas.  Moderate
	 * stress actually gives a decent bonus to physical attacks, especially for
	 * Fighters.  Barbarian and Berserker types benefit most from high stress,
	 * and although they will die if they are at max stress for too long, They
	 * get insane bonuses to battle stats.
	 * <p>
	 * Stress increases with moderately strenuous physical activity, or battle.
	 * It increases slowly with general actions.  Stress decreases with rest,
	 * sleep, seeing sunlight, and consuming food or drink.
	 * <p>
	 * You cannot die directly from critical stress levels; however, with too
	 * much stress you begin to take damage.  This damage can kill you, and 
	 * cannot be redirected or reduced by armor or magic (though can be 
	 * recovered repeatedly).
	 */
	public int	stress;
	
	/**
	 * Initialize VitalStats to zero.
	 */
	public VitalStat() {}
	
	/**
	 * Initialize VitalStats to the given starting values.
	 * 
	 * @param damage The initial value.
	 * @param hunger The initial value.
	 * @param thirst The initial value.
	 * @param stress The initial value.
	 */
	public VitalStat(int damage, int hunger, int thirst, int stress) {
		this.damage = damage;
		this.hunger = hunger;
		this.thirst = thirst;
		this.stress = stress;
	}
	
	/**
	 * Generate a randomly created VitalStats by setting each value to the min
	 * value, added to two randomly generated numbers ranging from zero to
	 * (maxRand - 1).
	 * 
	 * @param min The minimum value.
	 * @param maxRand The upper bound for random numbers that are generated.
	 * @return A set of VitalStats that are randomly generated.
	 */
	public static VitalStat generateRandomSet(int min, int maxRand) {
		Random r = new Random();
		VitalStat stat = new VitalStat(); // initialize randomly.
		stat.damage = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.hunger = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.thirst = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		stat.stress = min + r.nextInt(maxRand) + r.nextInt(maxRand);
		return stat;
	}
}
