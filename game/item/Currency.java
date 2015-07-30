package game.item;

import game.graphics.SpriteSheet;


/**
 * How to represent money?  Money can always be stored via the lowest possible
 * currency (and be converted upwards as necessary), but will be displayed as 
 * portions of what currency types are available.
 * <p>
 * Rather than be boring and use base 10 (or 100), I will use base 12, or the 
 * dozenal, system for fun.  This means that:
 * <ul>
 *  <li>12 Coppers = 1 Silver</li>
 * </ul>
 * <p>
 * This abstraction is to make storage easier.  Money can also be passed as a
 * length 4 int array, where the indices associate with each value.
 * <p>
 * Conversely, Currency can be used to represent money in a physical sense, 
 * so it can be carried in one's inventory.  This limits how much money a
 * character an carry, and also weighs them down.
 * @author Aaron Carson
 * @version Jun 12, 2015
 */
public class Currency extends Item
{
	// calculated weights of a single coin with volume of 0.033 cubic inches
	// using weights in lbs of materials from:
	// http://www.coyotesteel.com/assets/img/PDFs/weightspercubicfoot.pdf
	private static final double COPPER_WEIGHT   = 0.01069387700304;
	private static final double SILVER_WEIGHT   = 0.01250696820912;
	private static final double GOLD_WEIGHT     = 0.02304711249456;
	private static final double PLATINUM_WEIGHT = 0.0255750130944;
	
	// constants
	public static final Currency COPPER   = new Currency("Copper",   pow12(0), COPPER_WEIGHT);
	public static final Currency SILVER   = new Currency("Silver",   pow12(1), SILVER_WEIGHT);
	public static final Currency GOLD     = new Currency("Gold",     pow12(2), GOLD_WEIGHT);
	public static final Currency PLATINUM = new Currency("Platinum", pow12(3), PLATINUM_WEIGHT);
	
	/**
	 * Create a new Currency value.
	 * @param name The name of the currency.
	 * @param value The value, in copper.
	 * @param weight The weight, in pounds.
	 */
	private Currency(String name, int value, double weight){
		super(name, value, weight, 99 /*(int)(1 / weight)*pow12(2) - 1*/);
	}
	
	/**
	 * Get the closest integer value of raising 12 to the given power.
	 * @param power The power to raise 12 to.
	 * @return The given power of 12.
	 */
	private static int pow12(int power){
		return (int) Math.pow(12, power);
	}
	
	@Override
	public String toString(){
		return String.format("%9s value: %4d weight: %5.3f stack: %2d max value per Stack: %d" , name, value, weight, stack, stack * value);
	}
	
	/**
	 * Test the Currencies.
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(COPPER);
		System.out.println(SILVER);
		System.out.println(GOLD);
		System.out.println(PLATINUM);
	}
}
