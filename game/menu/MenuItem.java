package game.menu;

import java.util.EnumSet;
import java.util.Stack;

import utility.Direction;

/**
 * Designes to be implemented by Enums representing MenuOptions, to provide a
 * convenient way to declare and use MenuItems.
 * 
 * @author Aaron Carson
 * @version Jul 29, 2015
 */
public interface MenuItem
{
	/**
	 * Get the Enum value from the given class with the ordinal value of i, or
	 * null if it doesn't exist.
	 * 
	 * @param clazz The Enum class.
	 * @param i The index to get.
	 * @return The Enum value, or null if not found.
	 */
	public static <E extends Enum<E>> E get(Class<E> clazz, int i) {
		for (E e : EnumSet.allOf(clazz)) {
			if (e.ordinal() == i) return e;
		}
		return null;
	}
	
	/**
	 * Get an array of Capitalized Strings from the given Enum class.
	 * 
	 * @param clazz The class
	 * @return An array of capitalized strings.
	 */
	public static <E extends Enum<E>> String[] getStringValues(Class<E> clazz) {
		int i = 0;
		EnumSet<E> set = EnumSet.allOf(clazz);
		// System.out.println(set.size());
		String[] names = new String[set.size()];
		for (E e : set) {
			// System.out.println(e);
			names[i] = getCapitalizedString(e.toString());
			i++;
		}
		return names;
	}
	
	/**
	 * A helper method to get a capitalized String, where the 1st letter is
	 * upper case and the rest are lower case.
	 * 
	 * @param s The String to capitalize.
	 * @return A Capitalized String.
	 */
	public static String getCapitalizedString(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
	/**
	 * Get a capitalized string representation of the name of this MenuItem.
	 * 
	 * @return a capitalized form of this MenuItem's toString() return value.
	 */
	default String getFormattedName() {
		return getCapitalizedString(this.toString());
	}
	
	/* *
	 * Get an array of names from the given array of MenuItems.
	 * 
	 * @param menuItems The MenuItems to iterate over.
	 * 
	 * @return a String array of capitalized names of the MenuItems.
	 * 
	 * @SuppressWarnings("rawtypes") public static String[]
	 * getStringValues(MenuItem[] menuItems) { String[] names = new
	 * String[menuItems.length]; for (int i = 0; i < menuItems.length; i++) {
	 * names[i] = menuItems[i].getFormattedName(); } return names; }
	 */
	
	/**
	 * Performs some desired behavior when the given MenuItem is selected.
	 */
	// void select();
	
	public static void main(String[] args) {
		testAbs();
	}
	
	public static void testStack(){
		Stack<Direction> directions = new Stack<Direction>();
		
		// contains first.
		int tests = 5;
		int iterations = 1000000;
		for (int t = 0; t < tests; t++) {
			Long time = System.nanoTime();
			for (int i = 0; i < iterations; i++) {
				if (directions.contains(Direction.UP)) {
					directions.remove(Direction.UP);
				}
			}
			time = System.nanoTime() - time;
			System.out.printf("%d - %d contains: %d\n", t, iterations, time);
			
			time = System.nanoTime();
			for (int i = 0; i < iterations; i++) {
				directions.remove(Direction.UP);
			}
			time = System.nanoTime() - time;
			System.out.printf("%d - %d removes:  %d\n", t, iterations, time);
		}

	}

	
	public static void testAbs(){
		
		// contains first.
		int tests = 5;
		long iterations = 1000000000l;
		int v1, v2;		
		v1 = v2 = -1;
		int tmp = 0;
		for (int t = 0; t < tests; t++) {
			
			// test negative sign.
			Long time = System.nanoTime();
			for (long i = 0; i < iterations; i++) {
				tmp = -v1;
				v1 --;
			}
			time = System.nanoTime() - time;
			System.out.printf("%d - %d test neg: %9d\n", t, iterations, time);
			
			// test Math.abs()
			time = System.nanoTime();
			for (int i = 0; i < iterations; i++) {
				tmp = Math.abs(v2);				
				v2 --;
			}
			time = System.nanoTime() - time;
			System.out.printf("%d - %d test abs: %9d\n", t, iterations, time);
		}

	}

}
