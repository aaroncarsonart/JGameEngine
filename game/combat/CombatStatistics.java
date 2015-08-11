package game.combat;

/**
 * This class encapsulates combat statistics for a given Battler. For instance,
 * it stores how many times a HIT, MISS, or DODGE occurred from your attacks,
 * how much total damage has been incurred by HIT and CRITICAL attacks, and
 * various statistical averages.
 * 
 * @author Aaron Carson
 * @version Aug 6, 2015
 */
public class CombatStatistics
{
	/**
	 * Create a new CombatStatistics for the given Battler.
	 * 
	 * @param battler The Battler this CombatStatistics represents.
	 */
	public CombatStatistics(Battler battler) {
		this.battler = battler;
	}
	
	public Battler	battler;
	// total number of attacks made.
	public int		attacks;
	
	// accumulation of Attack results when rollingToHit
	public int		hits;
	public int		misses;
	public int		dodges;
	public int		criticalHits;
	
	// attacks made against this.
	public int		attacksRecieved;
	public int		attacksDodged;
	
	// damage caused by attacks
	public int		hitDamage;
	public int		criticalHitDamage;
	
	public String getStatReadout() {
		StringBuilder sb = new StringBuilder();
		
		// create an underlined name.
		String lines = "-----------------------------------------------------";
		String name = Battler.getNameFor(battler);
		int length = Math.min(name.length(), lines.length());
		sb.append(String.format("%s\n%s\n", name, lines.substring(0, length)));
		
		// get attack frequency statistics.
		sb.append(String.format("total # of attacks: %2d - %-3s%% hit\n",	//
				attacks,						// total attacks
				formatDouble(getTotalHitRate())	// % attacks that hit
				));
		sb.append(String.format("turns attacking:    %2d\n",			//
				getTurnsSpentAttacking()));
		sb.append('\n');
		
		sb.append(String.format("Attack.HIT:         %2d - %-3s%%\n",	//
				hits, 							// number of HITS
				formatDouble(getHitRate())		// % of total
				));
		sb.append(String.format("Attack.MISS:        %2d - %-3s%%\n",	//
				misses,							// number of MISSES
				formatDouble(getMissRate())		// % of total
				));
		sb.append(String.format("Attack.DODGE:       %2d - %-3s%%\n",	//
				dodges,							// number of DODGES
				formatDouble(getDodgeRate())	// % of total
				));
		sb.append(String.format("Attack.CRITICAL:    %2d - %-3s%%\n",	//
				criticalHits,					// number of CRITICALS
				formatDouble(getCriticalRate())	// % of total
				));
		
		sb.append('\n');
		sb.append(String.format("dodged attacks:     %2d - %-3s%%\n",			//
				attacksDodged,							// number of dodged attacks.
				formatDouble(getDodgedAttacksRate())	// % of received attacks
				));
		sb.append('\n');
		
		// get damage statistics.
		int totalDamage = getTotalInflictedDamage();
		sb.append(String.format("Total Damage:      %3d\n", totalDamage));
		sb.append(String.format("From regular hits: %3d\n", hitDamage));
		sb.append(String.format("From critical hits:%3d\n", criticalHitDamage));
		sb.append('\n');
		sb.append(String.format("Damage Per Attack: %-3s\n",
				formatDouble(getAverageDamagePerAttack())));
		sb.append(String.format("Damage Per Hit:    %-3s\n",
				formatDouble(getAverageDamagePerHit())));
		sb.append(String.format("Damage Per Attack.HIT:      %-3s\n",
				formatDouble(getAverageDamagePerRegularHit())));
		sb.append(String.format("Damage Per Attack.CRITICAL: %-3s\n",
				formatDouble(getAverageDamagePerCritical())));
		sb.append(String.format("Damage Per Attack: %-3s\n",
				formatDouble(getAverageDamagePerAttack())));
		sb.append(String.format("Damage Per Turn:   %-3s\n",
				formatDouble(getAverageDamagePerTurn())));
		return sb.toString();
	}
	
	/**
	 * Get the total amount of damage inflicted so far.
	 * 
	 * @return The sum of hitDamage and criticalHitDamage.
	 */
	public int getTotalInflictedDamage() {
		return hitDamage + criticalHitDamage;
	}
	
	/**
	 * Get the total count of successful hits scores so far.
	 * 
	 * @return The sum of hits and criticalHits.
	 */
	public int getTotalSuccessfulHits() {
		return hits + criticalHits;
	}
	
	/**
	 * Get the total average damage per each attack made.
	 * 
	 * @return The average damage per attack.
	 */
	public double getAverageDamagePerAttack() {
		return getTotalInflictedDamage() / (double) attacks;
	}
	
	/**
	 * Get the total average damage per each successful hit.
	 * 
	 * @return The average damage per hit.
	 */
	public double getAverageDamagePerHit() {
		return getTotalInflictedDamage() / (double) getTotalSuccessfulHits();
	}
	
	/**
	 * Get the number of attacks the Battler can make in one turn.
	 * 
	 * @return Attacks per turn, from {@link Battler#getAttacks()}.
	 */
	public int getAttacksPerTurn() {
		return battler.getAttacks();
	}
	
	/**
	 * Get the number of turns of combat this Battler has attacked.
	 * 
	 * @return the number of turns this has attacked.
	 */
	public int getTurnsSpentAttacking() {
		return attacks / getAttacksPerTurn();
	}
	
	/**
	 * Calculate the average damage inflicted per turn.
	 * 
	 * @return The average damage per turn.
	 */
	public double getAverageDamagePerTurn() {
		return getAverageDamagePerAttack() * getAttacksPerTurn();
	}
	
	/**
	 * Get the average inflicted damage per successful Attack.HIT.
	 * 
	 * @return The average damage per HIT.
	 */
	public double getAverageDamagePerRegularHit() {
		if (hits == 0) return 0;
		else return hitDamage / (double) hits;
	}
	
	/**
	 * Get the average inflicted damage per successful Attack.CRITICAL.
	 * 
	 * @return The average damage per CRITICAL.
	 */
	public double getAverageDamagePerCritical() {
		if (criticalHits == 0) return 0;
		else return criticalHitDamage / (double) criticalHits;
	}
	
	public double getTotalHitRate() {
		if (attacks == 0) return 0;
		else return 100 * getTotalSuccessfulHits() / (double) attacks;
	}
	
	public double getHitRate() {
		if (attacks == 0) return 0;
		else return 100 * hits / (double) attacks;
	}
	
	public double getCriticalRate() {
		if (attacks == 0) return 0;
		else return 100 * criticalHits / (double) attacks;
	}
	
	public double getMissRate() {
		if (attacks == 0) return 0;
		else return 100 * misses / (double) attacks;
	}
	
	public double getDodgeRate() {
		if (attacks == 0) return 0;
		else return 100 * dodges / (double) attacks;
	}
	
	public double getDodgedAttacksRate() {
		if (attacksRecieved == 0) return 0;
		else return 100 * attacksDodged / (double) attacksRecieved;
	}
	
	private static String formatDouble(double n) {
		if (n % 1 == 0) return String.format("%3d", (int) n);
		else return String.format("%3.1f", n);
	}
}
