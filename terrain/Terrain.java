package terrain;

import java.awt.Color;

	/**
	 * Defines a range of Terrain types that are specified with a maximum
	 * elevation value.
	 * @author Aaron Carson
	 * @version Jul 6, 2015
	 */
	public enum Terrain{
		DEEP_OCEAN (new Color(0x0033cc), 3),
		OCEAN      (new Color(0x0066ff), 1),
		BEACH      (new Color(0xffcc66), 2),
		GRASS      (new Color(0x33cc33), 5),
		FOREST     (new Color(0x009933), 7),
		MOUNTAIN   (new Color(0x996633), 2),
		GLACIER    (new Color(0xccffff), 1);
		
		public static int RANGE = getMaxElevation();
		private final int weight;
		private final Color color;
		
		/**
		 * Create a new Terrain.
		 * @param weight The maximum allowed Elevation.
		 */
		Terrain(Color color, int weight){
			this.weight = weight;
			this.color = color;
		}
		
		/**
		 * Get the maximum Elevation.
		 * @return The max Elevation.
		 */
		public static int getMaxElevation(){
			int maxElevation = 0;
			for(Terrain t : Terrain.values()){
				maxElevation += t.weight;
			}
			System.out.println("maxElevation: " + maxElevation);
			return maxElevation;
		}
		
		/**
		 * Get the color of this Terrain generator.
		 * @return
		 */
		public Color getColor(){
			return color;
		}
		
		/**
		 * Get an Elevation 
		 * @param elevation
		 * @return
		 */
		public static Terrain getTerrainFor(double elevation, double min, double max){
			Terrain[] ts = Terrain.values();
			double tRange = RANGE;
			double eRange = max - min;
			double scaledValue = (elevation - Math.min(min, 0)) * tRange / eRange;
			System.out.println("ScaledValue: " + scaledValue);
			//System.out.printf("scaledValue: %f\n", scaledValue);
			int maxElevation = 0;
			for(Terrain t : ts){
				maxElevation += t.weight;
				if (scaledValue <= maxElevation) return t;
			}
			return ts[ts.length - 1];
		}
	}
