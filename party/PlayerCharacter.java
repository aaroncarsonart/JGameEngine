package party;

import java.util.Random;

/**
 * Represents a hero.
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class PlayerCharacter
{	
	public static int MIN = 5;
	public static int MAX_RAND = 6;
	public static int DAMAGE_INT = 20 * 16;
	public static int HUNGER_INT = 200 * 16;
	public static int THIRST_INT = 100 * 16;
	public static int STRESS_INT = 300 * 16;
	public static int DAMAGE_INC = -1;
	public static int HUNGER_INC = 1;
	public static int THIRST_INC = 1;
	public static int STRESS_INC = -1;
	
	
	public String name;
	VitalStats current;
	VitalStats max;	
	
	public VitalStats interval;
	public VitalStats increment;
	
	public long time;
	public PlayerCharacter mainCharacter;
		
		
	/**
	 * Create a new PlayerCharacter with randomly rolled stats.
	 * @param name the name of the character
	 */
	public PlayerCharacter(String name){
		this.name = name;
		current = new VitalStats();
		max = new VitalStats();
		
		Random r = new Random();
		max.damage = MIN + r.nextInt(MAX_RAND) + r.nextInt(MAX_RAND);
		max.hunger = MIN + r.nextInt(MAX_RAND) + r.nextInt(MAX_RAND);
		max.thirst = MIN + r.nextInt(MAX_RAND) + r.nextInt(MAX_RAND);
		max.stress = MIN + r.nextInt(MAX_RAND) + r.nextInt(MAX_RAND);
		System.out.println(getVitalStats());
		
		interval = new VitalStats(DAMAGE_INT, HUNGER_INT, THIRST_INT, STRESS_INT);
		increment = new VitalStats(DAMAGE_INC, HUNGER_INC, THIRST_INC, STRESS_INC);
	}
	
	public String getVitalStats(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("damage: %3d / %-3d\n", current.damage, max.damage));
		sb.append(String.format("hunger: %3d / %-3d\n", current.hunger, max.hunger));
		sb.append(String.format("thirst: %3d / %-3d\n", current.thirst, max.thirst));
		sb.append(String.format("stress: %3d / %-3d\n", current.stress, max.stress));	
		return sb.toString();
	}
	
	/**
	 * Make the Timer wait a number of ticks.
	 * @param ticks
	 */
	public void wait(int ticks){
		for (int i = 0; i < ticks; i++){
			tick();
		}
	}
	
	/**
	 * Update the character's state.  They may die after a call to this method.
	 */
	public void tick(){
		time ++;
		if (time % interval.damage == 0 && current.damage > 0){
			current.damage += interval.damage;
		}
		if (time % interval.hunger == 0 && current.hunger < max.hunger){
			current.hunger += interval.hunger;
		}
		if (time % interval.thirst == 0 && current.thirst < max.thirst){
			current.thirst += interval.thirst;
		}
		if (time % interval.stress == 0 && current.stress > 0){
			current.stress += interval.stress;
		}
		
		if(current.damage >= max.damage) death(Vitals.DAMAGE);
		if(current.hunger >= max.hunger) death(Vitals.HUNGER);
		if(current.thirst >= max.thirst) death(Vitals.THIRST);
		if(current.stress >= max.stress) death(Vitals.STRESS);
	}
	
	/**
	 * Handle the death of the character.
	 */
	public void death(Vitals cause){
		String s = String.format("%s died due to severe %s.", name, cause);
		System.out.println(s);
	}
	
}
