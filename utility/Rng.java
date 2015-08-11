package utility;

import java.util.Random;

/**
 * Has come convenience methods for calculating scaled random numbers.
 * 
 * @author Aaron Carson
 * @version Aug 4, 2015
 */
public class Rng
{
	private static final Random	random		= new Random();
	private static final double	LUCK_SCALE	= 16.0;
	
	/**
	 * Get the next random integer capped by the upper bound, scaled by the luck
	 * stat.
	 * 
	 * @param bound The upper bound of the random number.
	 * @param luck A scaling factor. In general, if positive then a greater
	 *        value gives a better chance of a higher number. If negative, then
	 *        the greater value increases the chance of a lower number.
	 * @return A random integer value scaled by the luck stat.
	 */
	public static int nextInt(int bound, int luck) {
		return (int) (bound * nextDouble(luck));
	}
	
	/**
	 * Get the next random double value, distributed between 0.0 and 1.0, scaled
	 * by scaled by the luck stat.
	 * 
	 * @param luck A scaling factor. In general, if positive then a greater
	 *        value gives a better chance of a higher number. If negative, then
	 *        the greater value increases the chance of a lower number.
	 * @return A random double value scaled by the luck stat.
	 */
	public static double nextDouble(int luck) {
		return scaleDouble(random.nextDouble(), luck);
	}
	
	/**
	 * Scale a double number x between 0.0 and 1.0 by the given luck value. A
	 * root value n is derived by taking the absolute value of (luck /
	 * LUCK_SCALE) + 1.
	 * <p>
	 * <li>If Luck is zero, then the resulting function is linear (as 1st root
	 * of x or x ^ 1 is always just x), and so the value is unmodified.
	 * <li>if Luck is positive, the return value is the nth root of x.
	 * (calculated by the function <code>Math.pow(x, 1/n)</code>)
	 * <li>if Luck is negative, the return value is x ^ n power. (calculated by
	 * the function <code>Math.pow(x, n)</code>)
	 * <p>
	 * For a double value between 0.0 and 1.0 (specifically for random numbers
	 * from 0.0 to 1.0) this will subtly adjust the distribution from linear to
	 * a curve up or down, which will shift how frequently each value occurs.
	 * This works as the functions x^n or the nth root of x will always pass
	 * through 0 and 1.
	 * <p>
	 * Thus, with a higher luck value, better values are generated, and with
	 * poor luck (negative), lower values are generated. How fast luck scales
	 * the Double is determined by the scaling factor LUCK_SCALE.
	 * <li>This function may exhibit unintended behavior for x values outside
	 * the 0 - 1 range, but if the inputs are valid for the
	 * <code>Math.pow()</code> function, it will work for this function.
	 * 
	 * @param x A double value from 0.0 to 1.0.
	 * @param luck Used to calculate a scaling value <code>n = 1 + Math.abs(luck
	 *        / LUCK_SCALE)</code>.
	 * @return A scaled value of x. If luck is positive, then it returns the nth
	 *         root of x. If luck is positive, it returns x to the nth power. If
	 *         luck is zero, then it returns just x.
	 */
	public static double scaleDouble(double x, int luck) {
		double n = 1 + Math.abs(luck / LUCK_SCALE);
		if (luck >= 0) return Math.pow(x, 1 / n);
		else return Math.pow(x, n);
	}
	
	/**
	 * My older formula, kept for posterity.
	 * 
	 * @param value
	 * @param luck
	 * @return
	 */
	public static double scaleDoubleOld(double value, int luck) {
		double root;
		if (luck >= 0) root = 1 + luck / LUCK_SCALE;
		else root = 1 + luck / (LUCK_SCALE * 2);
		return nthRoot(value, root);
	}
	
	/**
	 * Calculate the nth root of num.
	 * 
	 * @param num The number to find to root.
	 * @param root the root to find.
	 * @return The nth root.
	 */
	public static double nthRoot(double num, double root) {
		return Math.pow(num, 1 / root);
	}
	
	/**
	 * Runs 100,000 tests for generating random numbers scaled by luck.
	 */
	private static void testRandomWithLuckScaling() {
		int runs = 100000;
		int range = 10;
		int bound = range + 1;
		for (int l = -30; l <= 100; l++) {
			double sum = 0;
			int luck = l;
			for (int i = 0; i < runs; i++) {
				int result = nextInt(bound, luck);
				// System.out.println(d);
				// System.out.printf("bound: %d luck: %d result: %d \n", bound,
				// luck, result);
				sum += result;
			}
			double avg = sum / runs;
			System.out.printf("range: %d luck: %d avg over %d runs: %s\n",
					range, luck, runs, avg);
		}
		
	}
	
	public static void main(String[] args) {
		testRandomWithLuckScaling();
	}
	
}
