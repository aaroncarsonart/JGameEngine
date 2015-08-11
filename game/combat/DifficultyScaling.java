package game.combat;

/**
 * For making calculations and determining a difficulty level.
 * @author Aaron Carson
 * @version Aug 4, 2015
 */
public class DifficultyScaling
{	
	public static final int MIN_LEVEL = 25;
	public static final int MAX_LEVEL = 50;
	public static final double OFFSET = 10;
	public static final double CONSTANT = 10;
	public static final double SCALE = 1.5;
	
	/**
	 * Generate a scale.
	 * @return
	 */
	public static double[] getScale(){
		int levels = MAX_LEVEL - MIN_LEVEL + 1;
		double[] scale = new double[levels]; 
		for(int i = 0; i < levels; i++){
			int level = i + MIN_LEVEL;
			scale[i] = OFFSET + level * CONSTANT * SCALE ;
		}		
		return scale;
	}
	
	public static double[] getScale(double offset, double scale, double growth){
		int levels = MAX_LEVEL - MIN_LEVEL + 1;
		double[] values = new double[levels]; 
		for(int i = 0; i < levels; i++){
			int level = i + MIN_LEVEL;
			values[i] = offset + (level + growth)  * scale ;
		}		
		return values;
	}
	
	/**
	 * Print a scale.
	 * @param scale
	 */
	public static void printScale(double[] scale){
		for(int i = 0; i < scale.length; i++){
			System.out.printf("scale[%2d] = %-3.1f\n", i + MIN_LEVEL, scale[i]);
		}
	}
	
	public static void main(String[] args){
		printScale(getScale(20, 2, 10));
	}
}
