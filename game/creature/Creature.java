package game.creature;

import game.creature.stat.BaseStat;
import game.creature.stat.Stat;
import game.creature.stat.VitalStat;
import game.engine.Game;
import game.inventory.Inventory;
import game.inventory.ListInventory;
import game.inventory.SlotInventory;
import game.item.Item;

/**
 * Creatures are the base class for anything that moves and can be encountered
 * in the game. Think living creature, monster, ghost or player? A creature.
 * 
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class Creature
{
	public static int		STAT_SCALE			= Game.FRAME_RATE;	// Game.FRAME_RATE;
	// ***********************************************************************
	// DEFAULTS
	// ***********************************************************************
	public static int		DAMAGE_INT			= 30 * STAT_SCALE;
	public static int		HUNGER_INT			= 120 * STAT_SCALE;
	public static int		THIRST_INT			= 60 * STAT_SCALE;
	public static int		STRESS_INT			= 42 * STAT_SCALE;
	public static int		DAMAGE_INC			= -1;
	public static int		HUNGER_INC			= 1;
	public static int		THIRST_INC			= 1;
	public static int		STRESS_INC			= -1;
	public static int		INVENTORY_CAPACITY	= 10;
	
	// ***********************************************************************
	// FIELDS
	// ***********************************************************************
	/**
	 * Contains the time intervals to update each VitalStat. When the Creature's
	 * time stat reaches a multiple of the interval for a given VitalStat, that
	 * stat is updated according to its corresponding increment.
	 */
	public VitalStat		interval;
	/**
	 * Contains the increment values for each VitalStat. These are added when
	 * the interval for that stat is reached. If the increment is positive, it
	 * increases that VitalStat; if it is negative, it decreases it.
	 */
	public VitalStat		increment;
	
	/** The name of this Creature. **/
	public String			name;
	
	/** Contains the current values for this creature's VitalStats. **/
	public VitalStat		current;
	/** Contains the maximum values for this creature's VitalStats. **/
	public VitalStat		max;
	
	/** The six primary, base stats for this Creature. **/
	public BaseStat			base;
	
	/**
	 * The current time step for the Creature. This effects the Creature's
	 * updates and cool-down times.
	 */
	public long				time;
	public boolean			isDead;
	public Stat				causeOfDeath;
	
	/**
	 * Contains any items that this Creature has.
	 */
	public Inventory<Item>	inventory;
	
	/**
	 * Create a new Creature with randomly rolled stats.
	 * 
	 * @param name the name of the character
	 * @param vitalStats the base vitalStats for this Creature.
	 */
	public Creature(String name, VitalStat vitalStats, BaseStat baseStats) {
		this.name = name;
		max = vitalStats;
		current = new VitalStat();
		base = baseStats;
		// inventory = new ListInventory(INVENTORY_CAPACITY);
		inventory = new SlotInventory(INVENTORY_CAPACITY);
		
		// set VitalStat time intervals and increments.
		interval = new VitalStat(DAMAGE_INT, HUNGER_INT, THIRST_INT, STRESS_INT);
		increment = new VitalStat(DAMAGE_INC, HUNGER_INC, THIRST_INC,
				STRESS_INC);
	}
	
	/**
	 * Get all four VitalStats (current / max), combined into one string.
	 * 
	 * @return A string about vital stats.
	 */
	public String getVitalStats() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("damage: %3d / %-3d\n", current.damage,
				max.damage));
		sb.append(String.format("hunger: %3d / %-3d\n", current.hunger,
				max.hunger));
		sb.append(String.format("thirst: %3d / %-3d\n", current.thirst,
				max.thirst));
		sb.append(String.format("stress: %3d / %-3d\n", current.stress,
				max.stress));
		return sb.toString();
	}
	
	private static final String	VITAL_FORMAT	= "%s: %3d / %-3d\n";
	
	/**
	 * Get a formatted string displaying information about damage.
	 * 
	 * @return A string about damage.
	 */
	public String getDamageVitals() {
		return String.format(VITAL_FORMAT, Stat.DAMAGE, current.damage,
				max.damage);
	}
	
	/**
	 * Get a formatted string displaying information about hunger.
	 * 
	 * @return A string about hunger.
	 */
	public String getHungerVitals() {
		return String.format(VITAL_FORMAT, Stat.HUNGER, current.hunger,
				max.hunger);
	}
	
	/**
	 * Get a formatted string displaying information about thirst.
	 * 
	 * @return A string about thirst.
	 */
	public String getThirstVitals() {
		return String.format(VITAL_FORMAT, Stat.THIRST, current.thirst,
				max.thirst);
	}
	
	/**
	 * Get a formatted string displaying information about stress.
	 * 
	 * @return A string about stress.
	 */
	public String getStressVitals() {
		return String.format(VITAL_FORMAT, Stat.STRESS, current.stress,
				max.stress);
	}
	
	/**
	 * Make the Timer wait a number of ticks.
	 * 
	 * @param ticks
	 */
	public void wait(int ticks) {
		for (int i = 0; i < ticks; i++) {
			tick();
		}
	}
	
	/**
	 * Update the character's state. They may die after a call to this method.
	 */
	public void tick() {
		time++;
		if (time % interval.damage == 0 && current.damage > 0) {
			current.damage += increment.damage;
		}
		if (time % interval.hunger == 0 && current.hunger < max.hunger) {
			current.hunger += increment.hunger;
		}
		if (time % interval.thirst == 0 && current.thirst < max.thirst) {
			current.thirst += increment.thirst;
		}
		if (time % interval.stress == 0 && current.stress > 0) {
			current.stress += increment.stress;
		}
		
		if (hasCriticalDamage()) death(Stat.DAMAGE);
		if (hasCriticalHunger()) death(Stat.HUNGER);
		if (hasCriticalThirst()) death(Stat.THIRST);
		if (hasCriticalStress()) death(Stat.STRESS);
	}
	
	/**
	 * Check if the creature currently has critical damage.
	 * 
	 * @return
	 */
	public boolean hasCriticalDamage() {
		return current.damage >= max.damage;
	}
	
	/**
	 * Check if the creature currently has critical hunger.
	 * 
	 * @return
	 */
	public boolean hasCriticalHunger() {
		return current.hunger >= max.hunger;
	}
	
	/**
	 * Check if the creature currently has critical thirst.
	 * 
	 * @return
	 */
	public boolean hasCriticalThirst() {
		return current.thirst >= max.thirst;
	}
	
	/**
	 * Check if the creature currently has critical stress.
	 * 
	 * @return
	 */
	public boolean hasCriticalStress() {
		return current.stress >= max.stress;
	}
	
	/**
	 * Handle the death of the character.
	 */
	public void death(Stat cause) {
		this.causeOfDeath = cause;
		this.isDead = true;
	}
	
	/**
	 * Get the death message of this creature.
	 * 
	 * @return
	 */
	public String getDeathMessage() {
		if (causeOfDeath == null)
			return String.format("%s is not dead.", name);
		return String.format("%s has died due to severe %s.", name,
				causeOfDeath);
	}
	
	/**
	 * Get the cause of death.
	 * 
	 * @return
	 */
	public String getCauseOfDeath() {
		return "severe " + causeOfDeath == null ? "" : causeOfDeath.toString();
	}
	
	/**
	 * Check if the creature is dead.
	 * 
	 * @return
	 */
	public boolean isDead() {
		return isDead;
	}
	
}
