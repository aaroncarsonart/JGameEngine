package game.creature.stat;

public enum Stat {

	// *******************
	// VitalStat
	// *******************
	DAMAGE ("Damage"), 
	HUNGER ("Hunger"), 
	THIRST ("Thirst"), 
	STRESS ("Stress"),
	
	// *******************
	// BaseStat
	// *******************
	STRENGTH ("Strength"), 
	STAMINA ("Stamina"), 
	AGILITY ("Agility"), 
	INTELLIGENCE ("Intelligence"), 
	WISDOM ("Wisdom"), 
	CHARISMA ("Charisma"), 
	
	;
	private String name;
	
	/**
	 * Create a new Vitals.
	 * @param name The name.
	 */
	private Stat(String name){
		this.name = name;
	}
	
	/**
	 * Convenience method to get the name of the Vitals.
	 */
	public String toString(){
		return name;
	}
}
