package game.map;

import game.graphics.GameGraphics;
import game.graphics.sprite.Sprite;
import game.menu.Message;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import utility.Quad;

/**
 * MiniMap is a special map that draws and displays a transparent mini-map
 * on the screen. 
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class MiniMap
{	
	private static final int HUD_WIDTH	= 64;
	private int mapWidth;
	private int mapHeight;
	private int tileSize;
	private BufferedImage miniMap;
	private Map map;
	
	/**
	 * Create a new MiniMap for the given map.
	 * @param map
	 */
	public MiniMap(Map map){
		this.map = map;
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();	
		tileSize = map.getTileSize();
		
		// create the backing image.
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		double hWidth = HUD_WIDTH;
		double hHeight = (hWidth * mapHeight / (double) mapWidth);
		miniMap = gc.createCompatibleImage((int) hWidth, (int) hHeight, Transparency.OPAQUE);
		
		// check evenly partitioned rectangles of the map.
		double xChunk = mapWidth / hWidth;
		double yChunk = mapHeight / hHeight;
		for (int hx = 0; hx < (int) hWidth; hx++) {
			for (int hy = 0; hy < (int) hHeight; hy++) {
				
				// count the paths and walls contained in the current chunk.
				int p = 0, w = 0;
				for (int x = (int) (hx * xChunk); x < hx * xChunk + xChunk
						&& x < mapWidth; x++) {
					for (int y = (int) (hy * yChunk); y < hy * yChunk + yChunk
							&& y < mapHeight; y++) {
						if (map.isPassable(x, y)) p++;
						else w++;
					}
				}
				
				//draw the MiniMap's current pixel based on the ratio
				double ratio = p / (double) (p + w);
				int v = (int) (255 * ratio);
				Color c = new Color(v, v, v);
				miniMap.setRGB(hx, hy, c.getRGB());
			}
		}
		
		// Darken HUD		
		Graphics2D g = miniMap.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.50f));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, miniMap.getWidth(), miniMap.getWidth());
	}
	
	/**
	 * Draw the MiniMap and the player to the corresponding Graphics2D context.
	 * @param g The graphics to draw to.
	 * @param alpha The alpha value to render the image at.
	 * @param player the Quad representing the player.
	 */
	public void render(Graphics2D g, float alpha, Quad player){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		int hPad = Message.FONT_SIZE / 2;
		g.drawImage(miniMap, hPad, hPad, null);
		g.setColor(Color.GREEN);
		double xConversion = (double)(miniMap.getWidth()) / tileSize / mapWidth;
		double yConversion = (double)(miniMap.getHeight()) / tileSize / mapHeight;
		
		//int hpx = (int) (player.x /*/ player.getWidth()*/ / tileSize / mapWidth * miniMap.getWidth());
		//int hpy = (int) (player.y /*/ player.getHeight()*/ / tileSize / mapHeight * miniMap.getHeight());
		//g.fillRect(hPad + hpx, hPad + hpy, 1, 1);
		g.fillRect(hPad + (int)(player.x * xConversion), hPad + (int)(player.y * yConversion), 1, 1);
		
		g.setColor(Color.YELLOW);
		for(Sprite s : map.getSprites()){
			g.fillRect(hPad + (int)(s.hitbox.x * xConversion), hPad + (int)(s.hitbox.y * yConversion), 1, 1);
			}
	}
	
}
