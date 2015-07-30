package game.engine.mode;


import game.graphics.GameGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Render a Graph of array data to the graphics.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class ElevationMapMode extends GameMode
{	
	private double min;
	private double max;
	private double[][] elevation;
	
	private BufferedImage map;
	
	
	/**
	 * Create a new ExploreMode.
	 * @param values the values to render.
	 */
	public ElevationMapMode(double[][] elevation) {
		super("Elevation Map Mode", new GameGraphics(elevation.length, elevation[0].length));
		System.out.printf("ElevationMapMode()\n");
		System.out.printf("width:  %5d\n", graphics.getWidth());
		System.out.printf("height: %5d\n", graphics.getHeight());
		this.elevation = elevation;
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();	
		map = gc.createCompatibleImage(graphics.getWidth(), graphics.getHeight(), Transparency.TRANSLUCENT);
		min = getMin();
		max = getMax();	

		// draw the data.
		renderData();	
	}
	
	/**
	 * 
	 * @param iterations
	 * @return
	 */
	public static final int getSize(int iterations){
		return (int) Math.pow(2, iterations) + 1;
	}
	
	/**
	 * Render the data to the background image.
	 */
	public void renderData(){
		int width  = this.getGameGraphics().getWidth();
		int height = this.getGameGraphics().getHeight();
		int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();
		
		//map.getGraphics().setColor(Color.RED);
		//map.getGraphics().fillRect(width / 4, height / 4, width / 2, height / 4);
		
		double range = max - min;
		
		int offset = (int) Math.ceil(Math.abs(Math.min(min, 0)));
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				//System.out.println("elevation: " + elevation[x][y]);
				//int rgb = Terrain.getTerrainFor(elevation[x][y], min, max).getColor().getRGB();	
				float alpha =  (float)((elevation[x][y] - Math.min(0,  min))/range);
				//System.out.printf("alpha: %f\n", alpha);
				int rgb = new Color(0f, 0f, 0f, alpha).getRGB();
				//map.setRGB(x, y, rgb);
				pixels[x + y * width] = rgb;
				//pixels[x + y * width] = (int)elevation[x][y];
				//System.out.printf("elevation[%d][%d] = %f\n", x, y, elevation[x][y]);
				//System.out.printf("(%d, %d) rgb: %d\n", x, y, rgb);
			}
		}
		
		// draw the map to the screen.
		Graphics2D fgGraphics = graphics.getForegroundImage().createGraphics();
		fgGraphics.setColor(Color.WHITE);
		fgGraphics.fillRect(0, 0, width, height);
		fgGraphics.drawImage(map, 0, 0, null);
		fgGraphics.dispose();
	}
	


	/**
	 * Get the minimum value of the dataset.
	 * @return The minimum value.
	 */
	private final double getMin(){
		double min = elevation[0][0];
		for (int x = 0; x < elevation.length; x ++){
			for(int y = 0; y < elevation[x].length; y++){
				if (elevation[x][y] < min) min = elevation[x][y];
			}
		}
		return min;
	}
	
	/**
	 * Get the maximum value of the dataset.
	 * @return The maximum value.
	 */
	private final double getMax(){
		double max = elevation[0][0];
		for (int x = 0; x < elevation.length; x ++){
			for(int y = 0; y < elevation[x].length; y++){
				if (elevation[x][y] > max) max = elevation[x][y];
			}
		}
		return max;

	}
		
	@Override
	public void update() {
		
	}
	
	@Override
	public void updateGraphics() {
	//	graphics.getForegroundGraphics().drawImage(map, 0, 0, null);
}	
}