package game.map;

import java.awt.image.BufferedImage;

/**
 * MapRenderer encapsulates all logic needed to render a Map.  This mainly
 * includes the background and foreground tiles, as well as a static lighting
 * mask layer.
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class MapRenderer
{	
	
	private Map map;
	public MapRenderer(Map map){
		this.map = map;
	}
	
	public BufferedImage renderFullBgImage(){
		return null;
	}
}
