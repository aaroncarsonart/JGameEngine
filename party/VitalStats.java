package party;

/**
 * Vital Stats represent the four stats that keep you alive.  Too much
 * of any of these will kill you; low amount of all and you're healthy.
 * <p>
 * This may represent the current state of a character, or a bonus to the
 * stats from weapons, armor, or a class bonus.
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class VitalStats
{	
	
	public int damage;
	public int hunger;
	public int thirst;
	public int stress;

	/**
	 * Initialize VitalStats to zero.
	 */
	public VitalStats(){}
	
	/**
	 * Initialize VitalStats to the given starting values.
	 * @param damage
	 * @param hunger
	 * @param thirst
	 * @param stress
	 */
	public VitalStats(int damage, int hunger, int thirst, int stress){
		this.damage = damage;
		this.hunger = hunger;
		this.thirst = thirst;
		this.stress = stress;
	}
}
