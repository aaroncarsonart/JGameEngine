package game.combat;

import utility.Direction;
import utility.Gender;
import utility.Orientation;
import utility.Pronoun;
import utility.Quad;
import utility.Rng;

/**
 * Battler handles all calculations needed for battle, attacking, including
 * resolving hits, misses, evasion and critical hits.
 * 
 * @author Aaron Carson
 * @version Aug 4, 2015
 */
public interface Battler
{
	public static final boolean	DEBUG	= false;
	
	/**
	 * Obtain a copy of this Battler, by storing the result of each getter
	 * method in a new TestBattler. Any non-primitive values are duplicated to
	 * preserve the state of the original Battler.
	 * 
	 * @return a new Battler with identical stats as this Battler.
	 */
	default Battler getCopy() {
		return new TestBattler(this);
	}
	
	/**
	 * Each Battler needs a unique identifier, given by its name.
	 * <p>
	 * Examples:
	 * <li>John
	 * <li>Goblin
	 * <li>Shar'guul The Impaler
	 * 
	 * @return The name of the Battler.
	 */
	String getName();
	
	/**
	 * Get this battler's Gender, defaults to Gender.MALE (for now). The purpose
	 * of this value is to properly identify the Battler in battle update
	 * messages with pronouns, such as saying
	 * "John was hit by the axe, and he took 9 damage."
	 * 
	 * @return The gender of this Battler.
	 */
	default Gender getGender() {
		return Gender.MALE;
	}
	
	/**
	 * Get the amount of Health this Battler has. This is equivalent to the
	 * amount of damage this Battler can sustain before death.
	 * 
	 * @return The amount of Health for this Battler.
	 */
	int getHealth();
	
	/**
	 * Get the amount of damage this Battler has sustained. This Battler is
	 * alive as long as this value is less than getHealth().
	 * 
	 * @return The amount of sustained Damage of this Battler.
	 */
	int getDamage();
	
	/**
	 * Get the number of basic physical attacks this Battler can make per turn.
	 * <p>
	 * Each Battler can make at minimum one attack. As they increase in skill or
	 * level, they gain additional attacks, each which is resolved separately.
	 * 
	 * @return The number of attacks this Battler can make.
	 */
	int getAttacks();
	
	/**
	 * Get the range of this Battler's attack. This often defaults to 1 for
	 * physical attacks, but may be greater or less than this depending on the
	 * weapon or creature type.
	 * 
	 * @return
	 */
	double getRange();
	
	/**
	 * Get the power stat of this Battler.
	 * <p>
	 * Power is used as a base for damage. The higher the power, the more damage
	 * is dealt for physical attacks.
	 * 
	 * @return The power stat.
	 */
	int getPower();
	
	/**
	 * Get the defense stat of this Battler.
	 * <p>
	 * Defense is used as a base for reducing physical damage. The higher the
	 * defense, the more damage is absorbed from physical attacks.
	 * 
	 * @return The defense stat.
	 */
	int getDefense();
	
	/**
	 * Get the accuracy stat for this Battler.
	 * <p>
	 * Accuracy is used to calculate if this Battler's attack misses. The higher
	 * the accuracy, the less likely the attack will miss.
	 * 
	 * @return The accuracy stat.
	 */
	int getAccuracy();
	
	/**
	 * Get the chance the base attack will miss, represented as a number between
	 * 0 and 100.
	 * <p>
	 * Most weapons and attacks have a base 5% chance of missing. If a weapon is
	 * particularly unwieldly, this may be increased. A character's proficiency
	 * with a weapon may modify this value as well.
	 * 
	 * @return The chance between 1 and 100 that this weapon will miss.
	 */
	int getMissChance();
	
	/**
	 * Get the evasion stat for this Battler.
	 * <p>
	 * Evasion is used to calculate if attacks made on this Battler are dodged.
	 * The higher the evasion, the more likely an attack will be dodged.
	 * 
	 * @return The evasion stat.
	 */
	int getEvasion();
	
	/**
	 * Get the critical hit rate for this Battler.
	 * <p>
	 * CritRate is used to calculate if an attack made by this Battler is a
	 * critical hit. Critical hits automatically hit and do damage multiplied by
	 * getCriticalHitMultiplier().
	 * 
	 * @return The critRate stat.
	 */
	int getCriticalHitRate();
	
	/**
	 * Get the critical hit damage multiplier for this Battler. This is a
	 * scaling factor to determine how much damage a critical hit deals. In most
	 * cases this is 2, but for some weapons, classes, or enemies this value may
	 * increase to 2.5, 3, or greater.
	 * 
	 * @return The critical hit damage multiplier.
	 */
	double getCriticalHitMulitplier();
	
	/**
	 * Get the luck stat for this Battler.
	 * <p>
	 * Luck is used to curve the distribution of random numbers. Positive luck
	 * values will slowly increase the average number generated; negative luck
	 * will decrease it. Numbers close to zero have little to no effect.
	 * 
	 * @return The luck stat.
	 */
	int getLuck();
	
	/**
	 * This method resolves reception of damage by a battler.
	 * 
	 * @param damage
	 */
	void takeDamage(int damage);
	
	/**
	 * Get the Quad for this Battler.
	 * <p>
	 * A Quad contains the hitbox for the battler, as well as the current
	 * position. The Quad is used to calculate if one Battler is in range of
	 * another Battler's attacks.
	 * 
	 * @return
	 */
	Quad getQuad();
	
	/**
	 * Get the current Direction this Battler is facing.
	 * 
	 * @return Direction.UP, DOWN, LEFT, or RIGHT.
	 */
	Direction getFacingDirection();
	
	/**
	 * Get the current Facing this Battler can see of target Battler.
	 * 
	 * @param target The Battler to get the Facing of.
	 * @return Facing.FRONT, SIDE, or REAR, depending on which Facing of the
	 *         target this Battler is facing.
	 */
	default Facing getFacingFor(Battler target) {
		//@formatter:off
		
		Quad attacker = this.getQuad();
		Quad targetQuad = target.getQuad();
		
		// get orientation of target.
		Orientation orientation = targetQuad.getOrientationFrom(attacker);
		
		// get target's facing.
		Direction facing = target.getFacingDirection();
		switch(orientation){
		case ABOVE:
			switch (facing){
			case UP:   return Facing.REAR;
			case DOWN: return Facing.FRONT;
			case LEFT: return Facing.SIDE;
			case RIGHT:return Facing.SIDE;
			}
		case BELOW:
			switch (facing){
			case UP:   return Facing.FRONT;
			case DOWN: return Facing.REAR;
			case LEFT: return Facing.SIDE;
			case RIGHT:return Facing.SIDE;
			}
		case TO_THE_LEFT:
			switch (facing){
			case UP:   return Facing.SIDE;
			case DOWN: return Facing.SIDE;
			case LEFT: return Facing.REAR;
			case RIGHT:return Facing.FRONT;
			}
		case TO_THE_RIGHT:
			switch (facing){
			case UP:   return Facing.SIDE;
			case DOWN: return Facing.SIDE;
			case LEFT: return Facing.FRONT;
			case RIGHT:return Facing.REAR;
			}
		}
		return null;
		//@formatter:on
	}
	
	/**
	 * Check if this Battler is in range of the given target, using the current
	 * range of physical attack.
	 * 
	 * @param target The Battler in question.
	 * @return True, if this Battler's attack range is less than the distance
	 *         between it and the target's hit boxes.
	 */
	default boolean inRangeOf(Battler target) {
		double attackRange = this.getRange();
		double distance = this.getQuad().getDistanceBetween(target.getQuad());
		boolean inRange = distance <= attackRange;
		// System.out.println("\ninRangeOf(target)");
		// System.out.printf("attackRange: %s\n", attackRange);
		// System.out.printf("distance:    %s\n", distance);
		// System.out.printf("inRange: %s\n\n", inRange);
		return inRange;
	}
	
	/**
	 * Resolve one Battler attacking another.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param target The Battler receiving the attack.
	 */
	static void attack(Battler attacker, Battler defender) {
		
		// 0. check range.
		boolean inRange = attacker.inRangeOf(defender);
		if (!inRange) {
			System.out.println(attacker.getOutOfRangeMessage(defender));
			return;
		}
		
		CombatStatistics attackerStatistics = attacker.getCombatStatistics();
		CombatStatistics defenderStatistics = defender.getCombatStatistics();
		
		// 1. calculate number of attacks
		int attacks = attacker.getAttacks();
		System.out.println(attacker.getAttackMessage(defender));
		attackerStatistics.attacks += attacks;
		defenderStatistics.attacksRecieved += attacks;
		
		int misses = 0;
		int dodges = 0;
		int hits = 0;
		int criticalHits = 0;
		
		// 2. resolve each attack.
		for (int i = 0; i < attacks; i++) {
			//@formatter:off
			Attack result = rollToHit(attacker, defender);
			switch(result){
			case HIT:      hits++;         break;
			case MISS:     misses++;       break;
			case DODGE:    dodges++;       break;
			case CRITICAL: criticalHits++; break;
			}
			//@formatter:on
		}
		attackerStatistics.hits += hits;
		attackerStatistics.misses += misses;
		attackerStatistics.dodges += dodges;
		attackerStatistics.criticalHits += criticalHits;
		defenderStatistics.attacksDodged += dodges;
		
		// 3. Resolve the results of the attack.
		if (misses > 0) {
			boolean all = attacks == misses;
			System.out.println(defender.getMissedAttacksMessage(misses, all));
		}
		if (dodges > 0) {
			boolean all = attacks == dodges;
			System.out.println(defender.getDodgedAttacksMessage(dodges, all));
		}
		if (hits > 0) {
			int damage = 0;
			for (int i = 0; i < hits; i++) {
				damage += resolveBaseAttackDamage(attacker, defender);
			}
			attackerStatistics.hitDamage += damage;
			defender.takeDamage(damage);
			System.out.println(attacker.getSuccessfulAttacksMessage(defender,
					hits, damage));
		}
		if (criticalHits > 0) {
			for (int i = 0; i < criticalHits; i++) {
				int damage = resolveCriticalHitDamage(attacker, defender);
				attackerStatistics.criticalHitDamage += damage;
				defender.takeDamage(damage);
				System.out.println(attacker.getCriticalHitMessage(defender,
						damage));
			}
		}
		
		// check if opponent is dead.
		if (defender.hasLethalDamage()) {
			System.out.println(defender.getLethalDamageMessage());
		}
		
	}
	
	/**
	 * This should return true if the Battler represents a generic or non-named
	 * mob. This is used to format messages about the Battler. For instance,
	 * generic enemies will be referred to as "the Goblin" instead of
	 * "Snar'gul the Impaler". For named Characters this should always return
	 * false.
	 * 
	 * @return True if this is a generic Battler.
	 */
	boolean isGenericBattler();
	
	/**
	 * Get an attack notification message for attacking the target Battler.
	 * <p>
	 * An example message would be:
	 * "John swings at the goblin with his long sword!"
	 * 
	 * @param target The Battler being attacked.
	 * @return An attack notification message.
	 */
	default String getAttackMessage(Battler target) {
		String attacker = getCapitalizedString(getNameFor(this));
		String defender = getNameFor(target);
		String format = "%s attacks %s.";
		return String.format(format, attacker, defender);
	}
	
	/**
	 * Get a message notifying the player that the attack is out of range. For
	 * Example: "John tried to attack, but the goblin was too far way."
	 * 
	 * @param target The Battler being attacked.
	 * @return A String describing that the attack was out of range.
	 */
	default String getOutOfRangeMessage(Battler target) {
		String attacker = getCapitalizedString(getNameFor(this));
		String defender = getNameFor(target);
		String format = "%s tried to attack %s, but was out of range.";
		return String.format(format, attacker, defender);
	}
	
	/**
	 * Get a message describing that the given number of attacks missed this
	 * battler.
	 * <p>
	 * For instance: "2 attacks missed the goblin!"
	 * 
	 * @param attacks The number of attacks that missed.
	 * @param all If true, then all attacks missed.
	 * @return A message describing the missed attacks.
	 */
	default String getMissedAttacksMessage(int attacks, boolean all) {
		String target = getNameFor(this);
		String format;
		boolean singleAttack = attacks == 1;
		if (all && singleAttack) format = "The attack missed %s.";
		else if (singleAttack) format = "1 attack missed %s.";
		else if (all) format = "Every attack missed %s!";
		else format = attacks + " attacks missed %s.";
		return String.format(format, target);
	}
	
	/**
	 * Get a message describing that this Battler has dodged the given number of
	 * attacks.
	 * <p>
	 * For instance: "John dodged every attack!"
	 * 
	 * @param attacks The number of attacks that were dodged.
	 * @param all If true, then all attacks were dodged.
	 * @return A message describing the dodged attacks.
	 */
	default String getDodgedAttacksMessage(int attacks, boolean all) {
		String target = getCapitalizedString(getNameFor(this));
		String format;
		boolean singleAttack = attacks == 1;
		if (all && singleAttack) format = "%s dodged the attack.";
		else if (singleAttack) format = "%s dodged 1 attack.";
		else if (all) format = "%s dodged every attack!";
		else format = "%s dodged %d attacks.";
		return String.format(format, target, attacks);
	}
	
	/**
	 * Get a message describing that this battler successfully hit the target
	 * Battler with the given number of attacks.
	 * <p>
	 * For instance:
	 * <li>"John hit the goblin for 5 damage!"
	 * <li>"The goblin stabbed John 3 times and caused 35 points of damage."
	 * <li>"John hit the goblin, but failed to injure it.
	 * 
	 * @param target The Battler being attacked.
	 * @param attacks The number of attacks that hit.
	 * @param damage The amount of damage inflicted by the attacks.
	 * @return A description of the result of the attacks that hit the target.
	 */
	default String getSuccessfulAttacksMessage(Battler target, int attacks,
			int damage) {
		
		// clear references to attacker and defender.
		Battler attacker = this;
		Battler defender = target;
		
		// their names
		String atkName = getCapitalizedString(getNameFor(attacker));
		String defName = getNameFor(defender);
		
		// the attack verb
		String verb = "hit";
		
		boolean singleAttack = attacks == 1;
		boolean twoAttacks = attacks == 2;
		
		// **********************************
		// build the suffix of the message.
		// i.e. "John hit the goblin"
		// **********************************
		String attackerHitDefender;
		if (singleAttack) {
			attackerHitDefender = String.format("%s %s %s",          // single
					atkName,	// John
					verb,		// hit
					defName		// the goblin
					);
		}
		else if (twoAttacks) {
			attackerHitDefender = String.format("%s %s %s twice",    // double
					atkName,	// John
					verb, 		// hit
					defName		// the goblin
					);          // twice
		}
		else {
			attackerHitDefender = String.format("%s %s %s %d times", // default
					atkName,	// John
					verb,		// hit
					defName,	// the goblin
					attacks		// 5 times
					);
			
		}
		
		// **********************************
		// determine the rest of the message.
		// **********************************
		String subjectPronoun = Pronoun.SUBJECT.forGender(attacker.getGender());
		if (subjectPronoun.equals("it")) subjectPronoun = "";
		else subjectPronoun += " ";
		String comma = singleAttack ? "" : ",";
		
		boolean noDamage = damage == 0;
		String message;
		if (noDamage) {
			message = String.format("%s, but %sinflicted no damage.",
					attackerHitDefender,	// John hit the goblin
					subjectPronoun			// "he ", "she ", or ""
					);                      // inflicted no damage.
		}
		else {
			message = String.format("%s%s and %sinflicted %d damage.",
					attackerHitDefender,	// John hit the goblin
					comma,                  // "," or ""
					subjectPronoun,			// "he ", "she ", or ""
					damage);				// 5 damage.
		}
		
		return message;
	}
	
	/**
	 * Get a message describing the result of this Battler scoring a critical
	 * hit on the given target.
	 * <p>
	 * For instance: "John critically hits the goblin, dealing 62 damage."
	 * "The goblin critically hits John for 24 damage, killing him."
	 * 
	 * @param target The Battler who was critically hit.
	 * @param damage The amount of damage inflicted by the attack.
	 * @return A description of critical hit scored on the target.
	 */
	default String getCriticalHitMessage(Battler target, int damage) {
		
		// clear references to attacker and defender.
		Battler attacker = this;
		Battler defender = target;
		
		// their names
		String atkName = getCapitalizedString(getNameFor(attacker));
		String defName = getNameFor(defender);
		
		// **********************************
		// determine the the message.
		// **********************************
		String subjectPronoun = Pronoun.SUBJECT.forGender(attacker.getGender());
		if (subjectPronoun.equals("it")) subjectPronoun = "";
		else subjectPronoun += " ";
		
		String message = String.format(
				"%s scored a critical hit, and %sinflicted %d damage on %s!",
				atkName,		// John
				subjectPronoun,	// "he ", "she ", or ""
				damage,			// 5 damage.
				defName			// the goblin
				);
		
		return message;
		
	}
	
	/**
	 * Check if this Battler is ready to attack.
	 * 
	 * @return True, if this Battler can attack.
	 */
	default boolean isReadyToAttack() {
		return !hasLethalDamage();
	}
	
	/**
	 * Check if this battler has sustained lethal damage, i.e. if getDamage() is
	 * greater than getHealth().
	 * 
	 * @return True, if this Battler has sustained lethal damage.
	 */
	default boolean hasLethalDamage() {
		return getDamage() >= getHealth();
	}
	
	/**
	 * Get a message reporting that this Battler has sustained lethal damage.
	 * 
	 * @return A String.
	 */
	default String getLethalDamageMessage() {
		return String.format("%s has sustained lethal damage and is dead.",
				getCapitalizedString(getNameFor(this)));
	}
	
	/**
	 * Get the CombatStatistics for this Battler.
	 * 
	 * @return This Battler's CombatStatistics.
	 */
	CombatStatistics getCombatStatistics();
	
	/**
	 * Resolve the result of a single attack made by one Battler to another.
	 * <p>
	 * Notes:
	 * <li>1% chance of always missing.
	 * <li>Critical hits cannot miss.
	 * <li>% to dodge equal to defender's Evasion minus attacker's Accuracy.
	 * <li>base to hit is %90 minus the Battler's getMissChance()
	 * <li>Accuracy is added to roll to hit after checking for critical hits.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param defender The Battler receiving the attack.
	 * @return Attack.HIT, MISS, DODGE, or CRITICAL.
	 */
	static Attack rollToHit(Battler attacker, Battler defender) {
		
		int toCritical = 100 - attacker.getCriticalHitRate();
		int toHit = 100 - getChanceToHit(attacker);
		int toEvade = getChanceToDodge(attacker, defender);
		
		// get a number between 0 and 100
		int luck = combineLuck(attacker, defender);
		int roll = 1 + Rng.nextInt(100, luck);
		
		Attack result;
		//@formatter:off
		if (roll >= toCritical)	result = Attack.CRITICAL;
		if (roll == 1)			result = Attack.MISS;
		if (roll <= toEvade)	result = Attack.DODGE;
		if (roll < toHit)		result = Attack.MISS;
		else					result = Attack.HIT;
		//@formatter:on
		
		if (DEBUG) {
			System.out.printf("\n%s.rollToHit(%s) {\n",  //
					attacker.getName(),  // John
					defender.getName()); // the goblin
			System.out.printf("    toCritical: %d\n", toCritical);
			System.out.printf("    toHit:      %d\n", toHit);
			System.out.printf("    toEvade:    %d\n", toEvade);
			System.out.printf("    roll:   %d\n", roll);
			System.out.printf("    result: %s\n", result);
			System.out.println("}");
		}
		return result;
	}
	
	/**
	 * Calculate the chance of this Battler to hit the target.
	 * 
	 * @param attacker The Battler making the attack.
	 * @return The chance to hit as an integer value.
	 */
	static int getChanceToHit(Battler attacker) {
		int accuracy = attacker.getAccuracy();
		int chanceToHit = 90 - attacker.getMissChance();
		return chanceToHit + accuracy;
	}
	
	/**
	 * Get the chance that the defender will dodge an attack from the attacker.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param defender The Battler trying to dodge the attack.
	 * @return The chance to dodge, as an integer value.
	 */
	static int getChanceToDodge(Battler attacker, Battler defender) {
		return Math.max(0, defender.getEvasion() - attacker.getAccuracy());
	}
	
	/**
	 * Get the chance to dodge when scaled for the combined luck of the
	 * participants. This is only used for estimation purposes, not for the
	 * actual in game calculations.
	 * 
	 * @param attacker The attacker T
	 * @param defender The defender.
	 * @return The scaled chance to dodge, as an integer value.
	 */
	static int getScaledChanceToDodge(Battler attacker, Battler defender) {
		int chance = getChanceToDodge(attacker, defender);
		
		// the defender is the benefactor here.
		int luck = combineLuck(defender, attacker);
		return (int) (Rng.scaleDouble(chance / 100.0, luck) * 100);
	}
	
	/**
	 * This method is used to resolve a single basic attack, denoted by a result
	 * of {@link Battler.Attack#HIT} from {@link #rollToHit(Battler, Battler)}.
	 * <p>
	 * This is calculated by reducing the result of
	 * {@link #getBaseDamage(Battler, Battler)} by the defender's
	 * {@link #getDefense()} to a minimum of zero if the defense is high enough.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param defender The Battler receiving the attack.
	 * @return The amount of damage caused by the attack.
	 */
	static int resolveBaseAttackDamage(Battler attacker, Battler defender) {
		
		// Base damage is a value from Power to Power * 2
		int baseDamage = getBaseDamage(attacker, defender);
		
		// the damage can be reduced down to zero by the defender's defense.
		return getBaseAttackDamage(attacker, defender, baseDamage);
	}
	
	/**
	 * Convenience method to ensure consistent calculations of base attack
	 * damage. This is calculated by taking the baseDamage and reducing to a
	 * minimum value of zero by the defender's {@link #getDefense()} stat.
	 * <p>
	 * This function is deterministic, and will always return the same result
	 * given identical inputs.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param defender The Battler receiving the attack.
	 * @param baseDamage The base damage for this given attack.
	 * @return The damage caused by an Attack.HIT by the attacker against the
	 *         defender, with the given baseDamage value.
	 */
	static int getBaseAttackDamage(Battler attacker, Battler defender,
			int baseDamage) {
		int defense = defender.getDefense();
		int result = Math.max(0, baseDamage - defense);
		return result;
	}
	
	/**
	 * Returns a random value ranging from the attacker's {@link #getPower()} to
	 * {@link #getPower()} * 2, as scaled by both Battlers'
	 * {@link #combineLuck(Battler, Battler)} value passed into the function
	 * {@link utility.Rng#nextInt(int, int) nextInt(int, int)}.
	 * <p>
	 * This value is NOT yet reduced by the defender's {@link #getDefense()}.
	 * This function is used to ensure consistent generation of a base damage
	 * value between {@link #resolveBaseAttackDamage(Battler, Battler)} and
	 * {@link #resolveCriticalHitDamage(Battler, Battler)}.
	 * 
	 * @param attacker The Battler who is attacking.
	 * @param attacker The Battler who is defending.
	 * @return T randomly calculated base damage of an attack by the attacker on
	 *         the defender. The random value is scaled by the combined luck of
	 *         the participants.
	 */
	static int getBaseDamage(Battler attacker, Battler defender) {
		int power = attacker.getPower();
		int luck = combineLuck(attacker, defender);
		return power + Rng.nextInt(power + 1, luck);
	}
	
	/**
	 * Get the minimum base damage the given Battler can cause.
	 * <p>
	 * This function is deterministic. The result is not yet reduced by the
	 * defender's {@link #getDefense()}.
	 * 
	 * @param attacker The Battler who is attacking.
	 * @return The minimum base damage.
	 */
	static int getBaseDamageMinimum(Battler attacker) {
		return attacker.getPower();
	}
	
	/**
	 * Get the maximum base damage this battler can cause.
	 * <p>
	 * This function is deterministic. The result is not yet reduced by the
	 * defender's {@link #getDefense()}.
	 * 
	 * @param attacker The Battler who is attacking.
	 * @return The maximum base damage.
	 */
	static int getBaseDamageMaximum(Battler attacker) {
		int power = attacker.getPower();
		return power * 2;
	}
	
	/**
	 * Get the average base damage for the given attacker and defender.
	 * <p>
	 * This function is deterministic as it always scales the average value,
	 * 0.5. The result is not yet reduced by the defender's
	 * {@link #getDefense()}.
	 *
	 * @param attacker The Battler who is attacking.
	 * @param attacker The Battler who is defending.
	 * @return The average based damage that occurs when the attacker attacks
	 *         the defender.
	 */
	static int getBaseDamageAverage(Battler attacker, Battler defender) {
		int power = attacker.getPower();
		int luck = combineLuck(attacker, defender);
		return power + (int) (Rng.scaleDouble(0.5, luck) * power);
	}
	
	/**
	 * This is used to resolve a single critical hit, denoted by a result of
	 * {@link Battler.Attack#CRITICAL} from {@link #rollToHit(Battler, Battler)}
	 * .
	 * <p>
	 * This is calculated by passing the result of
	 * {@link #getBaseDamage(Battler, Battler)} to
	 * {@link #getCriticalDamage(Battler, Battler, int)}, which scales the
	 * damage based on the attacker's {@link #getCriticalHitMulitplier()}. This
	 * will allow damage to be absorbed up to the value of the base damage, but
	 * any additional damage caused by the critical hit cannot be absorbed.
	 * <p>
	 * This function is not deterministic as it calculates a random value with
	 * {@link #getBaseDamage(Battler, Battler)}.
	 * 
	 * @param attacker The Battler making the attack.
	 * @param defender The Battler receiving the attack.
	 * @return The a randomly generated amount of damage caused by the critical
	 *         hit, scaled by the hit multiplier and reduced up to the value of
	 *         the base damage by the defender's {@link #getDefense()}. Some
	 *         portion of this damage is always unblockable.
	 */
	static int resolveCriticalHitDamage(Battler attacker, Battler defender) {
		// Base damage is a value from Power to Power * 2
		int baseDamage = getBaseDamage(attacker, defender);
		
		// critical damage is scaled up by the damage multiplier.
		int criticalDamage = getCriticalDamage(attacker, defender, baseDamage);
		
		return criticalDamage;
	}
	
	// static int calculateCriticalhitDamage(Battler attacker, Battler defender,
	// int baseDamage)
	
	/**
	 * Convenience method to ensure calculations of critical damage are
	 * consistent based on the given attacker, defender, and base damage value.
	 * <p>
	 * Critical hit damage is calculated as to the baseDamage * attacker's
	 * {@link #getCriticalHitMulitplier()}. This damage is reduced by the
	 * defender's {@link #getDefense()} up to the amount of baseDamage, but any
	 * additional damage is unblockable.
	 * <p>
	 * This function is deterministic, and will return the same result given
	 * identical inputs.
	 * 
	 * @param attacker The Battler who scored the critical hit.
	 * @param defender The unfortunate Battler who was critically hit
	 * @param baseDamage The base damage of the attack.
	 * @return The damage caused by an Attack.CRITICAL by the attacker against
	 *         the defender, with the given baseDamage value.
	 */
	static int getCriticalDamage(Battler attacker, Battler defender,
			int baseDamage) {
		int defense = defender.getDefense();
		double multiplier = attacker.getCriticalHitMulitplier();
		
		// critical damage is scaled up by the damage multiplier.
		int criticalDamage = (int) (baseDamage * multiplier);
		
		// the additional damage is unblockable.
		int unblockableDamage = criticalDamage - baseDamage;
		
		// the result is the greater of unblockable and defended full damage.
		int result = Math.max(unblockableDamage, criticalDamage - defense);
		
		return result;
		
	}
	
	/**
	 * Taking all factors into account, this function calculates the average
	 * damage output of the attacker on the defender if using basic attacks
	 * every turn, considering the average number of hits, misses, and critical
	 * hits.
	 * 
	 * @param attacker The Battler to calculate the average damage for.
	 * @param defender The Battler whom the damage is calculated against.
	 * @return The average damage per turn, as calculated over 100 turns.
	 */
	static double averageDamagePerTurn(Battler attacker, Battler defender) {
		int criticals = attacker.getCriticalHitRate();
		int evades = getChanceToDodge(attacker, defender);
		int hits = getChanceToHit(attacker) - criticals;
		if (evades > attacker.getMissChance()) {
			hits -= evades - attacker.getMissChance();
		}
		
		// System.out.println("crits  per 100 turns: " + criticals);
		// System.out.println("evades per 100 turns: " + evades);
		// System.out.println("hits   per 100 turns: " + hits);
		
		int baseDamage = getBaseDamageAverage(attacker, defender);
		int hitDamage = getBaseAttackDamage(attacker, defender, baseDamage);
		int criticalDamage = getCriticalDamage(attacker, defender, baseDamage);
		
		int totalDamage = hitDamage * hits + criticalDamage * criticals;
		double turns = 100.0;
		double averageDamage = totalDamage / (double) turns;
		return averageDamage;
	}
	
	/**
	 * Combine the value of {@link #getLuck()} of the two Battlers. The
	 * benefactor's Luck adds to the final score, while the adversary's Luck
	 * reduces the score.
	 * 
	 * @param benefactor The Battler who benefits from a higher value from this
	 *        result. The benefactor wants good luck, and their Luck stat
	 *        increases the combined value.
	 * @param adversary The Battler who benefits from a lower value from this
	 *        result. The adversary wants bad luck for the benefactor, and their
	 *        Luck stat reduces the combined value.
	 * @return The combined Luck of the two Battlers.
	 */
	static int combineLuck(Battler benefactor, Battler adversary) {
		return benefactor.getLuck() - adversary.getLuck();
	}
	
	/**
	 * Get the name for a given Battler, based on the value of
	 * {@link #isGenericBattler()}.
	 * <p>
	 * This convenience method formats the name for better readability in text
	 * like combat logs and Battler descriptions.
	 * 
	 * @param battler The battler in question.
	 * @return The name, formatted with "the " at the beginning if it is
	 *         generic.
	 */
	static String getNameFor(Battler battler) {
		if (battler.isGenericBattler()) return "the " + battler.getName();
		else return battler.getName();
	}
	
	/**
	 * A helper method to get a capitalized String, where the 1st letter is
	 * upper case and the rest are the original case.
	 * 
	 * @param s The String to capitalize.
	 * @return A Capitalized String.
	 */
	public static String getCapitalizedString(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	/**
	 * Print some battle statistics for the attacker when facing off against the
	 * defender.
	 * 
	 * @param attacker The attacking Battler.
	 * @param defender The defending Battler.
	 */
	public static void printStatistics(Battler attacker, Battler defender) {
		
		String lines = "-----------------------------------------------------";
		// name card.
		String name = getNameFor(attacker);
		int length = Math.min(name.length(), lines.length());
		System.out.printf("%s\n%s\n", getCapitalizedString(name),
				lines.substring(0, length));
		
		// report attack information.
		System.out.printf("Health:  %3d HP\n", attacker.getHealth());
		//System.out.printf("Range:    %s\n", attacker.getRange());
		System.out.printf("Attacks: %3d\n", attacker.getAttacks());
		System.out.println();
		System.out.printf("Power:    %s\n", attacker.getPower());
		System.out.printf("Defense:  %s\n", attacker.getDefense());
		System.out.printf("Accuracy: %s\n", attacker.getAccuracy());
		System.out.printf("Evasion:  %s\n", attacker.getEvasion());
		System.out.printf("Luck:     %s\n", attacker.getLuck());
		System.out.println();
		
		// Report hit chances.
		System.out.printf("%s has %d attack%s with a %d%% chance to hit %s.\n",
				getCapitalizedString(name),				// John
				attacker.getAttacks(),					// 2 attacks
				attacker.getAttacks() == 1 ? "" : "s",	// plural for attacks?
				getChanceToHit(attacker),			 	// 5 % chance
				getNameFor(defender)                	// the goblin
				);
		
		System.out.printf("%s has a %d%% chance to dodge %s attacks.\n",
				getCapitalizedString(getNameFor(defender)),	// the goblin
				getScaledChanceToDodge(attacker, defender), 		// 5% chance
				Pronoun.POSSESSIVE.forGender(attacker.getGender()) // his, her
				);
		
		// report attack damage statistics.
		System.out
				.printf("A normal hit inflicts %d - %d damage, with an average of %d.\n",
						getBaseAttackDamage(attacker, defender,
								getBaseDamageMinimum(attacker)),
						getBaseAttackDamage(attacker, defender,
								getBaseDamageMaximum(attacker)),
						getBaseAttackDamage(attacker, defender,
								getBaseDamageAverage(attacker, defender)));
		System.out.printf(
				"Critical hits inflict %d - %d damage,"
						+ " with an average of %d.\n",
				getCriticalDamage(attacker, defender,
						getBaseDamageMinimum(attacker)),
				getCriticalDamage(attacker, defender,
						getBaseDamageMaximum(attacker)),
				getCriticalDamage(attacker, defender,
						getBaseDamageAverage(attacker, defender)));
		
		double dps = averageDamagePerTurn(attacker, defender);
		System.out.printf(
				"%s inflicts an average of %s damage per turn,\n"
				+"  and can kill %s in around %d turns.\n",
				getCapitalizedString(name), dps, getNameFor(defender),
				(int)Math.ceil(defender.getHealth() / dps));
	}
	
	/**
	 * The possible resolutions of resolving an Attack. HIT does 1x damage,
	 * CRITICAL does 2x damage, and the others do 0x damage.
	 * 
	 * @author Aaron Carson
	 * @version Aug 4, 2015
	 */
	public enum Attack {
		HIT, MISS, DODGE, CRITICAL;
	}
	
	/**
	 * The facing of a Battler. This is used to get one Battler's position in
	 * relation to another. Say A is facing LEFT and B is facing DOWN, and A is
	 * to the left of B. this means that A is facing B's SIDE.
	 * <p>
	 * The Facing has an effect on if a Battler can Dodge and also on Defense.
	 * 
	 * @author Aaron Carson
	 * @version Aug 4, 2015
	 */
	public enum Facing {
		FRONT, SIDE, REAR;
	}
	
}