package party;

public enum Vitals {
	DAMAGE ("Damage"), 
	HUNGER ("Hunger"), 
	THIRST ("Thirst"), 
	STRESS ("Stress");
	
	private String name;
	
	/**
	 * Create a new Vitals.
	 * @param name The name.
	 */
	private Vitals(String name){
		this.name = name;
	}
	
	/**
	 * Convenience method to get the name of the Vitals.
	 */
	public String toString(){
		return name;
	}
}
