package terrain;

import java.awt.Color;
import java.util.Random;

import main.JGameEngine;
import utility.Graph;
import utility.Position;

public class TerrainGenerator
{	
	public static final double MIN_ELEVATION = -2000.0;
	public static final double MAX_ELEVATION = 10000.0;

	/** Do not instantiate. */
	private TerrainGenerator(){}
	
	/**
	 * Run the diamond-square algorithm, backed by a graph data structure.
	 * @param iterations How many iterations to subdivide over.
	 * @param min The minimum value for the array.
	 * @param max The maximum value for the array.
	 * @return a double[][] representing the elevations generated.
	 */
	public static double[][] diamondSquareAlgorithm(int iterations, double min, double max, double smoothness){
		if(min > max){
			throw new IllegalArgumentException("min must be less than max");
		}
		System.out.printf("diamondSquareAlgorithm()\n");
		System.out.printf("iterations: %d\n", iterations);
		
		
		int size = (int) Math.pow(2, iterations) + 1;
		int width = size;
		int height = size;
		double[][] elevation = new double[width][height];

		Random random = new Random();
		double range = max - min;
		
		/*
		elevation[0][0] = 
			elevation[width - 1][0] = 
			elevation[0][height - 1] = 
			elevation[width - 1][height - 1] = min + random.nextInt((int) range);
		*/
		elevation[0][0]                   = range * random.nextDouble();
		elevation[width - 1][0]           = range * random.nextDouble();
		elevation[0][height - 1]          = range * random.nextDouble();
		elevation[width - 1][height - 1]  = range * random.nextDouble();
		
		// do initial loop to provide more varied results
		int stride = (int) Math.pow(2, iterations); 
		int offset = stride / 2;
		elevation[offset][offset] = 100000 + range * random.nextDouble();
		squareStep(elevation, random, range, offset, stride);
		range *= smoothness;
		
		
		
		for(int i = 1; i < iterations; i++){
			System.out.println("Iteration: " + i);
			stride = (int)Math.pow(2, iterations - i); 
			offset = stride / 2;
			//System.out.printf("stride: %d\n", stride);
			diamondStep(elevation, random, range, offset, stride);
			squareStep(elevation, random, range, offset, stride);
			range *= smoothness;
		}
 		return elevation;
	}
	
	

	/**
	 * Execute one Diamond Step, according to the elevation.
	 * @param elevation The 2d double array containing all values.
	 * @param random The Random number generator.
	 * @param range The valid range for random numbers.
	 * @param offset The offset for x and y (equal to stride / 2)
	 * @param stride The stride for x and y (equal to 2 ^ (maxIterations - currentIteration).
	 */
	public static void diamondStep(double[][] elevation, Random random, double range, int offset, int stride){
		int width = elevation[0].length;
		int height = elevation.length;
		for (int y = offset; y < height; y += stride){
			for (int x = offset; x < width; x += stride){
				// set each midpoint to average of the four square corners,
				// plus a random offset.
				elevation[x][y] = //range * random.nextDouble() - range / 2 +
								  (elevation[x - offset][y - offset]
								 + elevation[x - offset][y + offset]
								 + elevation[x + offset][y - offset]
								 + elevation[x + offset][y + offset])
								 / 4;
			}	
		}
	}
	
	/**
	 * Execute one Diamond Step, according to the elevation.
	 * @param elevation The 2d double array containing all values.
	 * @param random The Random number generator.
	 * @param range The valid range for random numbers.
	 * @param offset The offset for x and y (equal to stride / 2)
	 * @param stride The stride for x and y (equal to 2 ^ (maxIterations - currentIteration).
	 * @param value the starting value for the center item.
	 */
	public static void diamondStep(double[][] elevation, int offset, int stride, double value){
		int width = elevation[0].length;
		int height = elevation.length;
		for (int y = offset; y < height; y += stride){
			for (int x = offset; x < width; x += stride){
				// set each midpoint to average of the four square corners,
				// plus a random offset.
				elevation[x][y] = value;
			}	
		}
	}

	public static void squareStep(double[][] elevation, Random random, double range, int offset, int stride){
		int width = elevation[0].length;
		int height = elevation.length;
		for (int y = 0; y < height; y += offset){
			for (int x = (y % 2 == 0) ? offset : 0; x < width; x += stride){
			// set each midpoint to average of the four diamond corners,
			// plus a random offset.
			double e = 0;//range * random.nextDouble() - range / 2;
			int count = 0;
			System.out.printf("(%d, %d) ", x,y);
			if (y - offset >= 0){
				System.out.print("top ");
				e += elevation[x][y - offset];
				count ++;
			}
			if (y + offset < height){
				System.out.print("bottom ");
				e += elevation[x][y + offset];
				count ++;
			}
			if (x - offset >= 0){
				System.out.print("left ");
				e += elevation[x - offset][y];
				count ++;
			}
			if (x + offset < width){
				System.out.print("right ");
				e += elevation[x + offset][y];
				count ++;
			}
			System.out.println(count);

			elevation[x][y] = e / count;
			//System.out.printf("elevation[%d][%d] = %f\n", x, y, elevation[x][y]);
			}	
			//System.out.println();
		}	
	}
		
	/**
	 * Generate a number that lies evenly distributed between -range/2 and range/2.
	 * @param range The range of the double to generate.
	 * @return a value between -x and x, where x = range / 2.
	 */
	private static final double randomDouble(Random random, double range){
		return range * random.nextDouble() - range / 2;
	}

	/**
	 * Helping recursive method that subdivides a range of values by finding
	 * a midpoint, setting the value to an average, and then subdividing a
	 * sub range to the left and the right of that midpoint.
	 * <p>
	 * Note that min and max should be initialized to some value for interesting
	 * results.
	 * 
	 * @param values THe values to subdivide.
	 * @param low The lower index
	 * @param high The higher index.
	 * @param range The allowed range to vary the values over.
	 */
	private static final void subdivide(Random random, double[] values, int low, int high, double range, double smoothness){
		int mid = (low + high) / 2;
		// if a new value
		if (low != mid && high != mid){
			values[mid] = (values[low] + values[high]) / 2 + randomDouble(random, range);
			range *=  smoothness;			
			subdivide(random, values, low, mid, range, smoothness);
			subdivide(random, values, mid, high, range, smoothness);
		}
	}
		
	/**
	 * Test the subdivide() method.
	 * @return a subdivided double array;
	 */
	public static double[] testSubdividing(){
		Random random = new Random();
		int size = 1000;
		double range = 1;
		double smoothness = 0.5;
		double[] values = new double[size];
		values[0] = randomDouble(random, range);
		values[values.length - 1] = randomDouble(random, range);
		subdivide(random, values, 0, values.length - 1, range, smoothness);
		return values;
	}
	
	/**
	 * Test the subdivision.
	 * @param args
	 */
	public static void main(String[] args){		
		JGameEngine.main(null);
	}

}
