package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Handles Popup Messages that appear above a character or item in the game.
 * 
 * TODO look into this http://docs.oracle.com/javase/7/docs/api/java/awt/font/TextLayout.html
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class Message
{	
	private String text;
	private int duration;
	private long time;
	private Color color;
	private int x;
	private int y;
	private byte messageType;
	
	/** Anchor the message text from the top-left corner of the screen. **/
	public static final byte ANCHOR_TO_SCREEN = 1;
	/** Anchor the message text from the top-left corner of the map. **/
	public static final byte ANCHOR_TO_MAP = 2;
	
	public static final int FONT_SIZE = 8;
	public static final String FONT_NAME = "Courier";
	
	/**
	 * Display a Message 
	 * @param text
	 */
	public Message(String text, Color color, int x, int y, int duration, byte messageType){
		this.text = text;
		this.color = color;
		this.x = x;
		this.y = y;
		this.duration = duration;
		this.messageType = messageType;
		this.time = System.currentTimeMillis();
	}
	
	/**
	 * Render the given text to the screen.
	 * @param g
	 * @param xOffset
	 * @param yOffset
	 */
	public void render(Graphics2D g, int xOffset, int yOffset){
		Font f = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
		g.setFont(f);
		int mx, my;
		
		if(messageType == ANCHOR_TO_SCREEN){
			mx = x;
			my = y;
		}
		else {
			mx = xOffset + x;
			my = yOffset + y;
		}
		
		/*
		//g.drawString(text, mx, my);		
		
		TextLayout tl = new TextLayout(text, f, g.getFontRenderContext());
		AffineTransform at = new AffineTransform();
		Shape s = tl.getOutline();
		g.setColor(color);
		g.draw(s);
		g.setColor(Color.BLACK);
		g.draw(s);
		//tl.draw(g, mx, my);
		*/
		   TextLayout tl = new TextLayout("This is a string.", f,  g.getFontRenderContext());
		  
		   Rectangle2D bounds = tl.getBounds();
		   bounds.setRect(bounds.getX() + mx - 8,
		                  bounds.getY() + my - 8,
		                  bounds.getWidth() + 16,
		                  bounds.getHeight() + 16);
		   g.setColor(Color.BLACK);
		   g.fill(bounds);
		   g.setColor(Color.WHITE);
		   tl.draw(g, mx, my);

	}
	
	/**
	 * Check if the Message is finished.
	 * @return True, if it is completed.
	 */
	public boolean finished(){
		return System.currentTimeMillis() - time > duration;
	}
}
