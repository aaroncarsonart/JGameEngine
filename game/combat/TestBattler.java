package game.combat;

import utility.Direction;
import utility.Gender;
import utility.Orientation;
import utility.Quad;

public class TestBattler implements Battler
{
	private String	name;
	private Gender	gender;
	private boolean	generic;
	private int		health;
	private int		damage;
	private int		attacks;
	private double	range;
	private int		power;
	private int		defense;
	private int		accuracy;
	private int		evasion;
	private int		missChance;
	private int		critRate;
	private double	critMultiplier;
	private int		luck;
	private Quad	quad;
	private CombatStatistics combatStatistics;
	
	/**
	 * Create a generic TestBattler with default stats, for testing.
	 */
	public TestBattler() {
		this.name = "Default Battler";
		this.gender = Gender.MALE;
		this.generic = false;
		this.health = 5;
		this.damage = 0;
		this.attacks = 1;
		this.range = 1;
		this.power = 1;
		this.defense = 1;
		this.accuracy = 1;
		this.evasion = 1;
		this.missChance = 5;
		this.critRate = 5;
		this.critMultiplier = 2;
		this.luck = 1;
		this.quad = getDefaultQuad(1, 1);
		this.combatStatistics = new CombatStatistics(this);
	}
	
	/**
	 * Copy the given Battler into a new TestBattler, but with a new set of
	 * CombatStatistics.
	 * 
	 * @param battler The Battler to copy.
	 */
	public TestBattler(Battler battler) {
		this.name = battler.getName();
		this.gender = battler.getGender();
		this.generic = battler.isGenericBattler();
		this.health = battler.getHealth();
		this.damage = battler.getDamage();
		this.attacks = battler.getAttacks();
		this.range = battler.getRange();
		this.power = battler.getPower();
		this.defense = battler.getDefense();
		this.accuracy = battler.getAccuracy();
		this.evasion = battler.getEvasion();
		this.missChance = battler.getMissChance();
		this.critRate = battler.getCriticalHitRate();
		this.critMultiplier = battler.getCriticalHitMulitplier();
		this.luck = battler.getLuck();
		this.quad = new Quad(battler.getQuad());
		this.combatStatistics = new CombatStatistics(this);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Set the gender
	 * 
	 * @param gender The Gender to set.
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public int getDamage() {
		return damage;
	}
	
	@Override
	public int getAttacks() {
		return attacks;
	}
	
	@Override
	public double getRange() {
		return range;
	}
	
	@Override
	public int getPower() {
		return power;
	}
	
	@Override
	public int getDefense() {
		return defense;
	}
	
	@Override
	public int getAccuracy() {
		return accuracy;
	}
	
	@Override
	public int getEvasion() {
		return evasion;
	}
	
	@Override
	public int getMissChance() {
		return missChance;
	}
	
	@Override
	public int getCriticalHitRate() {
		return critRate;
	}
	
	@Override
	public double getCriticalHitMulitplier() {
		return critMultiplier;
	}
	
	@Override
	public int getLuck() {
		return luck;
	}
	
	@Override
	public void takeDamage(int damage) {
		this.damage += damage;
	}
	
	@Override
	public Quad getQuad() {
		return quad;
	}
	
	@Override
	public Direction getFacingDirection() {
		return null;
	}
	
	@Override
	public boolean isGenericBattler() {
		return generic;
	}
	
	/**
	 * Return a Default size Quad (16 x 16) at the given position.
	 * 
	 * @param x The x Position.
	 * @param y The y Position.
	 * @return A default Quad with the top left corner at the x and y position.
	 */
	public static Quad getDefaultQuad(double x, double y) {
		return new Quad(x + 8, y + 8, 8, 8);
	}
	
	/**
	 * Get a Quad that is immediately adjacent to this quad, based on the given
	 * Orientation value.
	 * 
	 * @param quad The Quad to align to.
	 * @param orientation The orientation from the original Quad: either
	 *        Orientation.ABOVE, BELOW, TO_THE_LEFT, or TO_THE_RIGHT.
	 * @return A new Quad.
	 */
	public static Quad getAdjacentQuad(Quad quad, Orientation orientation) {
		double x = quad.x;
		double y = quad.y;
		
		//@formatter:off
		switch(orientation){
		case ABOVE: 		y -= quad.getHeight();	break;
		case BELOW: 		y += quad.getHeight();	break;
		case TO_THE_LEFT: 	x -= quad.getWidth();	break;
		case TO_THE_RIGHT:  x += quad.getWidth();	break;
		}
		//@formatter:on
		
		return new Quad(x, y, quad.halfWidth, quad.halfHeight);
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param generic the generic to set
	 */
	public void setGeneric(boolean generic) {
		this.generic = generic;
	}
	
	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * @param attacks the attacks to set
	 */
	public void setAttacks(int attacks) {
		this.attacks = attacks;
	}
	
	/**
	 * @param range the range to set
	 */
	public void setRange(double range) {
		this.range = range;
	}
	
	/**
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	
	/**
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	/**
	 * @param evasion the evasion to set
	 */
	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}
	
	/**
	 * @param missChance the missChance to set
	 */
	public void setMissChance(int missChance) {
		this.missChance = missChance;
	}
	
	/**
	 * @param critRate the critRate to set
	 */
	public void setCritRate(int critRate) {
		this.critRate = critRate;
	}
	
	/**
	 * @param critMultiplier the critMultiplier to set
	 */
	public void setCritMultiplier(double critMultiplier) {
		this.critMultiplier = critMultiplier;
	}
	
	/**
	 * @param luck the luck to set
	 */
	public void setLuck(int luck) {
		this.luck = luck;
	}
	
	/**
	 * @param quad the quad to set
	 */
	public void setQuad(Quad quad) {
		this.quad = quad;
	}

	@Override
	public CombatStatistics getCombatStatistics() {
		return combatStatistics;
	}
}
