package game.engine.mode;


import game.graphics.GameGraphics;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Render a Graph of array data to the graphics.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class LineGraphMode extends GameMode
{	
	private int minX;
	private int maxX;
	private double minY;
	private double maxY;
	private double[] values;
	private int plotType;
	
	public static final int LINES = 1;
	public static final int DOTS = 2;
	public static final int LINES_AND_DOTS = 3;
	
	/**
	 * Create a new ExploreMode.
	 * @param values the values to render.
	 */
	public LineGraphMode(double[] values) {
		super("Graph Mode", new GameGraphics(1000, 600));
		this.values = values;
		
		minX = 0;
		maxX = values.length - 1;
		
		minY = getMin();
		maxY = getMax();
		
		// draw the data.
		plotType = LINES;
		renderData(plotType);	
	}
	
	/**
	 * Render the data to the background image.
	 */
	public void renderData(int plotType){
		Graphics2D g = this.getGameGraphics().getBackgroundImage().createGraphics();
		
		g.setColor(Color.WHITE);
		int domain = maxX - minX;
		double range = maxY - minY;
		String sMin    = String.format("  min: %10.5f", minY);
		String sMax    = String.format("  max: %10.5f", maxY);
		String sRange  = String.format("range: %10.5f", range);
		
		int width  = this.getGameGraphics().getWidth();
		int height = this.getGameGraphics().getHeight();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		double scaleX = width / (double) domain;
		double scaleY = height / (double)range;
		
		double yOffset = height - range;
		
		// draw the data points.	
		if (plotType != LINES){
			g.setColor(new Color(0,0,255,80));
			int radius = 1;
			int diameter = radius;
			for(int i = 0; i < values.length; i++){
				int x = getX(i, scaleX) - 0;
				int y = getY(i, scaleY, yOffset) - 0;
				g.fillOval(x, y, diameter, diameter);
			}			
		}
		
		// draw lines between the data points.
		if(plotType != DOTS){
			g.setColor(new Color(0,0,255,120));
			int prevX = getX(0, scaleX);
			int prevY = getY(0, scaleY, yOffset);	
			for (int i = 1; i < values.length; i++){
				int x = getX(i, scaleX);
				int y = getY(i, scaleY, yOffset);		
				g.drawLine(prevX, prevY, x, y);
				prevX = x;
				prevY = y;
			}	
		}		
		
		// draw the text.
		g.setColor(new Color(0,0,255,100));
		g.drawString(sMin,   20, 20);
		g.drawString(sMax,   20, 40);
		g.drawString(sRange, 20, 60);
		g.dispose();
	}
	
	/**
	 * Get the x value to display a given data value at the specified index.
	 * @param i The index of the value.
	 * @param xScale The x scaling factor.
	 * @return A scaled x value, for drawing to the screen.
	 */
	private final int getX(int i, double xScale){
		return (int) (xScale * i);
	}

	/**
	 * Get the y value to display a given data value at the specified index.
	 * @param i The index of the value.
	 * @param yScale The y scaling factor.
	 * @param yOffset The offset to translate the y value to fit on the screen.
	 * @return A scaled and offset y value, for drawing to the screen.
	 */
	private final int getY(int i, double yScale, double yOffset){
		return (int) (yScale *( values[i] - minY));
	}

	
	/**
	 * Draws the edges of the graph.
	 * @param g The Graphics2D context to draw to.
	 * /
	public void drawEdges(Graphics2D g) {
		g.setColor(edgeColor);
		for (Position v : graph.getVertices()) {
			for (Position e : graph.getEdges(v)) {
				g.drawLine(v.x() * scale, v.y() * scale, e.x() * scale, e.y()
				        * scale);
			}
		}
	}
	*/

	/**
	 * Get the minimum value of the dataset.
	 * @return The minimum value.
	 */
	private final double getMin(){
		double min = values[0];
		for(int i = 0; i < values.length; i++){
			if (values[i] < min) min = values[i];
		}
		return min;
	}
	
	/**
	 * Get the maximum value of the dataset.
	 * @return The maximum value.
	 */
	private final double getMax(){
		double max = values[0];
		for(int i = 0; i < values.length; i++){
			if (values[i] > max) max = values[i];
		}
		return max;
	}
		
	@Override
	public void update() {
		
	}
	
	@Override
	public void updateGraphics() {
		renderData(plotType);
	}
	
	/**
	 * For testing graphMode.
	 * @param x
	 * @return
	 */
	public static final double getY(int x){
		return -8 * x*x + 100 * x + 3;
	}

	
}