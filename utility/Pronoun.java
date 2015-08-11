package utility;

/**
 * A convenience enum to get the proper Pronoun based on the kind required
 * (subject, possessive, or object) and gender (male, female, or neutral).
 * <p>
 * <a href=
 * "http://www.grammarly.com/answers/questions/7733-use-of-he-and-she-versus-him-and-her/"
 * >Source for grammar rules</a>
 * 
 * @author Aaron Carson
 * @version Aug 5, 2015
 */
public enum Pronoun {
	//@formatter:off	
	
	/** He, she, or it.  */	SUBJECT   ("he",  "she", "it"), 
	/** His, her, or its.*/	POSSESSIVE("his", "her", "its"), 
	/** Him, her, or it. */	OBJECT    ("him", "her", "it");	
	
	public final String	MALE;
	public final String	FEMALE;
	public final String	NEUTRAL;

	// @formatter:on
	
	/**
	 * Create a new Singular Pronoun set from the given values.  All NEUTRAL
	 * values are some form of "it".
	 * 
	 * @param male The male singular pronoun, i.e. he, his, him
	 * @param female The female singular pronoun, i.e. she, her
	 * @param female The neutral singular pronoun, i.e. it, its
	 */
	Pronoun(String male, String female, String neutral) {
		this.MALE = male;
		this.FEMALE = female;
		this.NEUTRAL = neutral;
	}
	
	/**
	 * Get the proper form of this Pronoun for the given gender.
	 * @param gender The gender form to get of this Pronoun.
	 * @return The proper gender form of this Pronoun as a string.
	 */
	public String forGender(Gender gender) {
		switch (gender) {
		//@formatter:off	
		case MALE:   return this.MALE;
		case FEMALE: return this.FEMALE;
		default:     return this.NEUTRAL;
		// @formatter:on
		}
	}
}
