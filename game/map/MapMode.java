package game.map;

import game.creature.Character;
import game.engine.Command;
import game.engine.mode.GameMode;
import game.graphics.Images;
import game.graphics.SpriteSheet;
import game.graphics.sprite.ItemSprite;
import game.graphics.sprite.Sprite;
import game.graphics.sprite.WalkingSprite;
import game.menu.Menu;
import game.menu.MenuItem;
import game.menu.Message;
import game.menu.MultiLineMessage;
import game.menu.NestedMenu;
import game.menu.SingleLineMessage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import utility.Position;

/**
 * MapMode is a general-purpose ExploreMode that uses a Map as a backer to
 * facility exploration by the player.
 * 
 * @author Aaron Carson
 * @version Jul 20, 2015
 */
public class MapMode extends GameMode
{
	Message						testMessage				= new MultiLineMessage(
																"Hello, world\n test\nFour\nha ha!!!");
	
	public static final float	SOFT_LIGHT_ALPHA		= 0.85f;
	public static final boolean	DISPLAY_SOFT_LIGHT		= false;
	
	private static final String	NAME					= "Map Mode";
	private static final int	WIDTH					= 100;
	private static final int	HEIGHT					= 70;
	
	private Map					map;
	private MiniMap				miniMap;
	private BufferedImage		bgImage;
	private BufferedImage		fgImage;
	private BufferedImage		lightMaskImage;
	
	private Random				random;
	
	private int					width;
	private int					height;
	
	private int					widthInPixels;
	private int					heightInPixels;
	private int					tileSize;
	
	private WalkingSprite		player;
	private Character			hero;
	
	private boolean				displayMap				= false;
	private boolean				displayHelp				= false;
	private boolean				displayTimer			= false;
	private boolean				displayVitals			= false;
	// private boolean hideDisplays = false;
	private boolean				displaySoftLight		= DISPLAY_SOFT_LIGHT;
	
	private Message				notificationMessage;
	private int					notificationMessageTime	= -1;
	private Stack<Menu>			menus;
	private Menu				primaryMenu;
	private Menu				secondaryMenu;
	private boolean				displayMenu				= false;
	
	/**
	 * Create a new MapMode with default settings.
	 */
	public MapMode() {
		super(NAME);
		Map map = new CavernMap("Cavern Test Map", WIDTH, HEIGHT);
		this.initialize(map, map.getEmptyAbsolutePosition());
	}
	
	/**
	 * Create a new MapMode, with a random starting position for the given Map.
	 * 
	 * @param map The Map to use.
	 */
	public MapMode(Map map) {
		this(map, map.getEmptyAbsolutePosition());
	}
	
	/**
	 * Create a new MapMode for the given map.
	 * 
	 * @param map The map to use.
	 * @param start The starting position for the player.
	 */
	public MapMode(Map map, Position start) {
		super(NAME);
		this.initialize(map, start);
	}
	
	/**
	 * Initialize the fields.
	 * 
	 * @param map The Map for this MapMode.
	 * @param start The starting position for the player.
	 */
	private void initialize(Map map, Position start) {
		this.map = map;
		this.miniMap = new MiniMap(map);
		this.width = map.getWidth();
		this.height = map.getHeight();
		this.tileSize = map.getTileSize();
		this.widthInPixels = width * tileSize;
		this.heightInPixels = height * tileSize;
		this.random = new Random();
		
		// create the static images to render this map scene.
		this.bgImage = map.createBackgroundImage();
		this.fgImage = map.createForegroundImage();
		this.lightMaskImage = map.createLightMaskImage();
		
		// createRegularHitboxPlayerSprite(WalkingSprite.MALE_WARRIOR, start);
		createCustomHitboxPlayerSprite(WalkingSprite.MALE_WARRIOR, start);
		hero = new Character("Michael");
		menus = new Stack<Menu>();
	}
	
	/**
	 * Create a player sprite with a hitbox matching the sprite size.
	 * 
	 * @param path The path to the source bitmap for the SpriteSheet.
	 * @param start The starting position.
	 */
	private void createRegularHitboxPlayerSprite(String path, Position start) {
		this.player = new WalkingSprite(WalkingSprite.MALE_WARRIOR, start);
	}
	
	/**
	 * Create a player sprite with a hitbox that is smaller than the sprite.
	 * 
	 * @param path The path to the source bitmap for the SpriteSheet.
	 * @param start The starting position.
	 */
	private void createCustomHitboxPlayerSprite(String path, Position start) {
		SpriteSheet src = new SpriteSheet(
				Images.loadImageFrom(WalkingSprite.MALE_WARRIOR), 16);
		
		double hw = 10;
		double hh = 10;
		Quad hitbox = new Quad(start.x(), start.y(), hw / 2, hh / 2);
		
		System.out.printf("hitbox x: %f y: %f hw: %f hh: %f\n", hitbox.x,
				hitbox.y, hitbox.halfHeight, hitbox.halfHeight);
		int xOff = (int) ((src.getTileWidth() - hitbox.getWidth()) / 2);
		int yOff = 0;
		System.out.printf("xOff: %d yOff: %d\n", xOff, yOff);
		int frameRate = WalkingSprite.DEFAULT_FRAMERATE;
		this.player = new WalkingSprite(src, hitbox, xOff, yOff, frameRate);
	}
	
	// ***********************************************************************
	// 1. UPDATE
	// **********************************************************************
	@Override
	public void update() {
		for (Sprite sprite : map.getSprites()) {
			sprite.update();
		}
		updatePlayer();
		updateGraphics();
	}
	
	/**
	 * Update the player's position.
	 */
	private void updatePlayer() {
		
		// TODO: add INVENTORY shortcut logic
		
		if (!Command.INVENTORY.isConsumed()) {
			Command.INVENTORY.consume();
			handleInventoryCommand();
		}
		
		/**
		 * Show pause menu
		 */
		if (!Command.MENU.isConsumed()) {
			Command.MENU.consume();
			handleMenuCommand();
		}
		if (displayMenu) {
			primaryMenu.listen();
			return;
		}
		
		// toggle map display on input
		if (!Command.MAP.isConsumed()) {
			displayMap = !displayMap;
			Command.MAP.consume();
		}
		
		// toggle display of vitals
		if (!Command.VITALS.isConsumed()) {
			displayVitals = !displayVitals;
			Command.VITALS.consume();
		}
		
		// toggle display of timer
		if (!Command.TIME.isConsumed()) {
			displayTimer = !displayTimer;
			Command.TIME.consume();
		}
		
		// toggle overlay display on input
		if (!Command.HELP.isConsumed()) {
			displayHelp = !displayHelp;
			Command.HELP.consume();
			displayTimer = false;
			displayVitals = false;
			displayMap = false;
		}
		
		player.updateDirections();
		boolean moving = !player.getDirections().isEmpty();
		// boolean moving = Command.moveSelected();
		
		if (moving && !Command.REST.isPressed()) {
			/*
			 * Stack<Direction> directions = new Stack<Direction>();
			 * 
			 * if (Command.CANCEL.isPressed()) player.setRunning(true); else
			 * player.setRunning(false);
			 * 
			 * if (Command.UP.isPressed()) directions.push(Direction.UP); if
			 * (Command.DOWN.isPressed()) directions.push(Direction.DOWN); if
			 * (Command.LEFT.isPressed()) directions.push(Direction.LEFT); if
			 * (Command.RIGHT.isPressed()) directions.push(Direction.RIGHT); if
			 * (directions.isEmpty()) directions.push(Direction.DOWN);
			 * 
			 * player.setDirectionStack(directions);
			 */
			if (Command.CANCEL.isPressed()) player.setRunning(true);
			else player.setRunning(false);
			
			player.update();
			// System.out.printf("px: %f py: %f\n", player.x /
			// player.getWidth(), player.y / player.getHeight());
		}
		else {
			// System.out.println("stop");
			player.stop();
		}
		
		// update the player's clock if moving, or if resting.
		if (moving || Command.REST.isPressed()) {
			hero.tick();
		}
		
		// ****************************
		// adjust for any collisions.
		// ****************************
		map.adjustForMapBoundaries(player.hitbox);
		map.adjustForCollisions(player.hitbox);
		// adjustForMapBoundaries(player.hitbox);
		// adjustForCollisions(player.hitbox);
		
		/**
		 * Check front space for items.
		 */
		if (!Command.OK.isConsumed()) {
			if (notificationMessage != null) {
				notificationMessage = null;
			}
			System.out.println("search for items ...");
			checkForItems();
			Command.OK.consume();
		}
	}
	
	/**
	 * Helper method to handle Command.MENU press.
	 */
	private void handleMenuCommand() {
		// toggle display of the menu, and create/tear it down.
		if (!displayMenu) {
			displayMenu = true;
			displayMainMenu();
		}
		else {
			toPreviousMenu();
		}
	}
	
	/**
	 * Helper method to handle Command.INVENTORY press
	 */
	private void handleInventoryCommand() {
		
		// go straight to the inventory menu if no menu selected.
		if (!displayMenu) {
			displayMenu = true;
			displayInventoryMenu();
		}
		else {
			if (primaryMenu != null) {
				
				// if in inventory menu
				if (primaryMenu.getName().equals("Inventory")) {
					toPreviousMenu();
				}
				
				// else, show inventory menu
				else {
					displayInventoryMenu();
				}
			}
		}
	}
	
	/**
	 * Check the Quad in front of the player for items, and pick it up if found.
	 */
	public void checkForItems() {
		List<Sprite> sprites = map.getSprites();
		
		double x = player.hitbox.x;
		double y = player.hitbox.y;
		switch (player.getLastDirection()) {
		case LEFT:
			x -= player.hitbox.getWidth();
			break;
		case RIGHT:
			x += player.hitbox.getWidth();
			break;
		case UP:
			y -= player.hitbox.getHeight();
			break;
		case DOWN:
		default:
			y += player.hitbox.getHeight();
			break;
		}
		
		Quad searchQuad = new Quad(x, y, player.hitbox.halfWidth,
				player.hitbox.halfHeight);
		
		// collide with available sprites.
		for (int i = 0; i < sprites.size(); i++) {
			Sprite s = sprites.get(i);
			
			// if collides
			if (searchQuad.collides(s.hitbox)) {
				System.out.println("collides!");
				
				// if item sprite
				if (s instanceof ItemSprite) {
					ItemSprite is = (ItemSprite) s;
					
					// player gains the item.
					
					if (hero.inventory.isFull()) {
						// itemMessage = new Message("Inventory is full",
						// false);
						setNotificationMessage("Inventory is full");
					}
					else {
						hero.inventory.add(is.item);
						// itemMessage = new Message("found \"" + is.item.name+
						// "\"", false);
						showMessage("found \"" + is.item.name + "\"", 120);
						
						// remove from sprite list.
						sprites.remove(i);
						i--;
					}
					// itemMessageTime = 120;
				}
			}
		}
	}
	
	// ***********************************************************************
	// 2. UPDATE GRAPHICS
	// **********************************************************************
	
	@Override
	public void updateGraphics() {
		
		// ***********************************************************
		// Calculate Camera Offsets
		// ***********************************************************
		
		int gWidth = graphics.getWidth();
		int gHeight = graphics.getHeight();
		int hgw = gWidth / 2;
		int hgh = gHeight / 2;
		
		Quad center = this.player.hitbox;
		
		// draw bgImage to bgGraphics.
		// calculate the center of the camera.
		int xCamera, yCamera, xOffset, yOffset;
		if (widthInPixels < gWidth) {
			xCamera = widthInPixels / 2;
			xOffset = xCamera;
		}
		else if (center.x < hgw) {
			xCamera = hgw;
			xOffset = 0;
		}
		else if (center.x > widthInPixels - hgw) {
			xCamera = widthInPixels - hgw;
			xOffset = -widthInPixels + gWidth;
		}
		else {
			xCamera = (int) center.x;
			xOffset = (int) -center.x + hgw;
		}
		yOffset = 0;
		
		if (heightInPixels < gHeight) {
			yCamera = heightInPixels / 2;
			yOffset = yCamera;
		}
		else if (center.y < hgh) {
			yCamera = hgh;
			yOffset = 0;
		}
		else if (center.y > heightInPixels - hgh) {
			yCamera = heightInPixels - hgh;
			yOffset = -heightInPixels + gHeight;
		}
		else {
			yCamera = (int) center.y;
			yOffset = (int) -center.y + hgh;
		}
		
		int sx1 = xCamera - hgw;
		int sx2 = xCamera + hgw;
		int sy1 = yCamera - hgh;
		int sy2 = yCamera + hgh;
		
		// ***********************************************************
		// Background
		// ***********************************************************
		
		// Graphics2D bgGraphics = graphics.getBackgroundGraphics();
		Graphics2D bgGraphics = graphics.getBackgroundImage().createGraphics();
		bgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bgGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		bgGraphics.drawImage(bgImage, 0, 0, gWidth, gHeight, sx1, sy1, sx2,
				sy2, null);
		bgGraphics.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.60f));
		bgGraphics.drawImage(Images.LARGE_SHADOW, 0, 0, null);
		
		bgGraphics.dispose();
		// ***********************************************************
		// Sprites
		// ***********************************************************
		// Graphics2D sGraphics = bgGraphics;
		Graphics2D sGraphics = graphics.getSpriteImage().createGraphics();
		sGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		sGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		// clear image
		AlphaComposite composite = AlphaComposite
				.getInstance(AlphaComposite.CLEAR);
		sGraphics.setComposite(composite);
		sGraphics.setColor(Color.BLACK);
		sGraphics.fillRect(0, 0, gWidth, gHeight);
		
		sGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		
		// render map sprites.
		for (Sprite sprite : map.getSprites()) {
			sprite.render(sGraphics, xOffset, yOffset);
		}
		
		// finally, render the player.
		player.render(sGraphics, xOffset, yOffset);
		sGraphics.dispose();
		
		// ******************************************
		// Draw on the foreground.
		// ******************************************
		
		// ******************************************
		// draw lighting.
		// ******************************************
		Graphics2D lighting = graphics.getLightingImage().createGraphics();
		lighting.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		lighting.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		// clear previous render.
		lighting.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		lighting.setColor(Color.BLACK);
		lighting.fillRect(0, 0, gWidth, gHeight);
		
		// draw list mask.
		lighting.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		lighting.drawImage(lightMaskImage, 0, 0, gWidth, gHeight, sx1, sy1,
				sx2, sy2, null);
		
		// draw lantern light mask around player.
		int off = Images.SHADOW_PLAYER_MASK.getWidth() / 2;
		lighting.drawImage(Images.SHADOW_PLAYER_MASK, xOffset
				+ (int) player.hitbox.x - off, yOffset + (int) player.hitbox.y
				- off, null);
		
		// draw shadows at 75% if displaySoftLight, else darken to full value.
		if (displaySoftLight) lighting.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OUT, SOFT_LIGHT_ALPHA));
		else lighting.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OUT));
		lighting.fillRect(0, 0, gWidth, gHeight);
		
		// ******************************************
		// Draw overlays.
		// ******************************************
		
		Graphics2D overlay = graphics.getOverlayImage().createGraphics();
		overlay.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		overlay.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		// *******************************************
		// Map
		// *******************************************
		if (displayMap) {
			miniMap.render(lighting, 0.25f, player.hitbox);
			// miniMap.render(overlay, 0.50f, player.hitbox);
			// drawHudMap(lighting);
		}
		
		// *******************************************
		// Timer
		// *******************************************
		if (displayTimer || (Command.REST.isPressed() && !displayMenu)) {
			drawTimer(lighting);
		}
		
		// *******************************************
		// Vitals
		// *******************************************
		if (displayVitals) {
			drawVitals(lighting);
		}
		
		// *******************************************
		// Help
		// *******************************************
		if (displayHelp) {
			// drawHelpMenu(fgGraphics);
		}
		
		// *******************************************
		// Item Message
		// *******************************************
		if (notificationMessage != null) {
			drawNotificationMessage(lighting, hgw, gHeight - 64);
		}
		
		// *******************************************
		// Menu Message
		// *******************************************
		if (displayMenu) {
			int mx = hgw;
			int my = hgh;
			primaryMenu.render(lighting, mx, my, 0.75f);
			// menuMessage.render(lighting, mx, my, 0.75f);
		}
		
		lighting.dispose();
		overlay.dispose();
	}
	
	// ***********************************************************************
	// MAIN MENU
	// ***********************************************************************
	
	/**
	 * Display the Main Menu.
	 */
	private void displayMainMenu() {
		primaryMenu = createVerticalMainMenu();
		menus.push(primaryMenu);
	}
	
	/**
	 * Pop the current menu off the stack, and go to the previous menu, if one
	 * exists.
	 */
	private void toPreviousMenu() {
		synchronized (primaryMenu) {
			menus.pop();
			if (menus.isEmpty()) {
				displayMenu = false;
				primaryMenu = null;
			}
			else {
				primaryMenu = menus.peek();
			}
		}
	}
	
	/**
	 * Pop the current menu off the stack, and go to the previous menu, if one
	 * exists.
	 */
	private void clearAllMenus() {
		synchronized (primaryMenu) {
			while (!menus.isEmpty()) {
				menus.pop();
			}
			displayMenu = false;
			primaryMenu = null;
		}
	}
	
	/**
	 * Create a vertically aligned Main Menu.
	 * 
	 * @return A Menu.
	 */
	private Menu createVerticalMainMenu() {
		String title = "Main Menu";
		String[] menuItems = MainMenuItem.getStringValues();
		byte vAlign = Menu.VERTICAL;
		MainMenuResponder responder = new MainMenuResponder();
		Menu mainMenu = new Menu(title, menuItems, vAlign, responder);
		return mainMenu;
	}
	
	/**
	 * Respond to a menu selection of the main menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public class MainMenuResponder implements Menu.SelectionResponder
	{
		@Override
		public void selectMenuItem(int index) {
			
			// get the menuItem selection.
			MainMenuItem m = MainMenuItem.get(index);
			if (m == null) return;
			
			switch (m) {
			case STATUS:
				showMessage("selected \"" + m.getFormattedName() + "\"", 120);
				toPreviousMenu();
				break;
			case INVENTORY:
				displayInventoryMenu();
				break;
			case SKILLS:
				showMessage("selected \"" + m.getFormattedName() + "\"", 120);
				toPreviousMenu();
				break;
			case SETTINGS:
				displaySettingsMenu();
				break;
			case SAVE:
				showMessage("selected \"" + m.getFormattedName() + "\"", 120);
				toPreviousMenu();
				break;
			case QUIT:
				System.out.println("Quit");
				System.exit(0);
				break;
			}
		}
		
		@Override
		public void cancel() {
			handleMenuCommand();
		}
		
	}
	
	/**
	 * Contains all MenuItems used by the Main Menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public enum MainMenuItem implements MenuItem {
		
		STATUS, INVENTORY, SKILLS, SETTINGS, SAVE, QUIT;
		
		/**
		 * Get the MenuItem at the given index.
		 * 
		 * @param i The index to get.
		 * @return A MenuItem, or null if the index was invalid.
		 */
		public static MainMenuItem get(int i) {
			return MenuItem.get(MainMenuItem.class, i);
		}
		
		/**
		 * Get the String Values for the MainMenu.
		 * 
		 * @return An array of Strings representing MainMenu.values().
		 */
		public static String[] getStringValues() {
			return MenuItem.getStringValues(MainMenuItem.class);
		}
	}
	
	// ***********************************************************************
	// INVENTORY MENU
	// ***********************************************************************
	
	/**
	 * Display the Inventory Menu.
	 */
	private void displayInventoryMenu() {
		primaryMenu = createNestedInventoryMenu();
		// primaryMenu = createHorizontalInventoryMenu();
		// primaryMenu = createInventoryDetailsMenu();
		menus.push(primaryMenu);
	}
		
	/**
	 * Create a Nested Inventory Menu that displays a list of item functions in
	 * the outer menu, and a list of items in the inner menu.
	 * 
	 * @return a new Nested Inventory Menu.
	 */
	private Menu createNestedInventoryMenu() {
		String title = "Inventory";
		String[] outerItems = InventoryMenuItem.getStringValues();
		String[] innerItems = hero.inventory.getSlotDescriptions();
		byte outerAlign = Menu.HORIZONTAL;
		byte innerAlign = Menu.VERTICAL;
		InventoryMenuResponder outerResponder = new InventoryMenuResponder();
		int width = graphics.getWidth();
		int height = graphics.getHeight();
		Menu inventoryMenu = new NestedMenu(title, outerItems, innerItems,
				outerAlign, innerAlign, outerResponder, null, width, height);
		return inventoryMenu;
		
	}
	
	/**
	 * Create a horizontal test Menu.
	 * 
	 * @return A horizontal test Menu.
	 */
	private Menu createHorizontalTestMenu() {
		String hTitle = "Inventory";
		String[] hMenuItems = InventoryMenuItem.getStringValues();
		byte hAlign = Menu.HORIZONTAL;
		InventoryMenuResponder responder = new InventoryMenuResponder();
		int width = graphics.getWidth();
		System.out.println("widthInPixels: ");
		Menu hMenu = new Menu(hTitle, hMenuItems, hAlign, responder, width, 0);
		return hMenu;
		
	}
	
	/**
	 * Respond to a menu selection of the inventory menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public class InventoryMenuResponder implements Menu.SelectionResponder
	{
		@Override
		public void selectMenuItem(int index) {
			InventoryMenuItem menuItem = InventoryMenuItem.get(index);
			if (menuItem == null) return;
			switch (menuItem) {
			case USE:
				System.out.println("use");
				break;
			case SWITCH:
				System.out.println("switch");
				break;
			case SORT:
				System.out.println("sort");
				break;
			case TRASH:
				System.out.println("trash");
				break;
			}
			// toPreviousMenu();
			clearAllMenus();
			showMessage("selected \"" + menuItem.getFormattedName() + "\"", 120);
		}
		
		@Override
		public void cancel() {
			handleInventoryCommand();
		}
	}
	
	/**
	 * Contains all MenuItems used by the Inventory Menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public enum InventoryMenuItem implements MenuItem {
		USE, SWITCH, SORT, TRASH;
		
		/**
		 * Get the MenuItem at the given index.
		 * 
		 * @param i The index to get.
		 * @return A MenuItem, or null if the index was invalid.
		 */
		public static InventoryMenuItem get(int i) {
			return MenuItem.get(InventoryMenuItem.class, i);
		}
		
		/**
		 * Get the String Values for the MainMenu.
		 * 
		 * @return An array of Strings representing MainMenu.values().
		 */
		public static String[] getStringValues() {
			return MenuItem.getStringValues(InventoryMenuItem.class);
		}
	}
	
	// ***********************************************************************
	// SETTINGS MENU
	// ***********************************************************************
	
	/**
	 * Display the Settings Menu.
	 */
	private void displaySettingsMenu(){
		primaryMenu = createNestedSettingsMenu();
		menus.push(primaryMenu);
	}

	/**
	 * Create a Nested Inventory Menu that displays a list of item functions in
	 * the outer menu, and a list of items in the inner menu.
	 * 
	 * @return a new Nested Inventory Menu.
	 */
	private Menu createNestedSettingsMenu() {
		String title = "Settings";
		String[] outerItems = SettingsMenuItem.getStringValues();
		String[] innerItems = {""};
		byte outerAlign = Menu.VERTICAL;
		byte innerAlign = Menu.VERTICAL;
		SettingsMenuResponder outerResponder = new SettingsMenuResponder();
		int width = graphics.getWidth();
		int height = graphics.getHeight();
		Menu settingsMenu = new NestedMenu(title, outerItems, innerItems,
				outerAlign, innerAlign, outerResponder, null, width, height);
		return settingsMenu;
		
	}
	
	/**
	 * Respond to a menu selection of the inventory menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public class SettingsMenuResponder implements Menu.SelectionResponder
	{
		@Override
		public void selectMenuItem(int index) {
			SettingsMenuItem menuItem = SettingsMenuItem.get(index);
			if (menuItem == null) return;
			switch (menuItem) {
			default:
			}
			// toPreviousMenu();
			clearAllMenus();
			showMessage("selected \"" + menuItem.getFormattedName() + "\"", 120);
		}
		
		@Override
		public void cancel() {}
	}
	
	/**
	 * Contains all MenuItems used by the Inventory Menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public enum SettingsMenuItem implements MenuItem {
		FULLSCREEN, MUSIC, SOUND;
		
		/**
		 * Get the MenuItem at the given index.
		 * 
		 * @param i The index to get.
		 * @return A MenuItem, or null if the index was invalid.
		 */
		public static SettingsMenuItem get(int i) {
			return MenuItem.get(SettingsMenuItem.class, i);
		}
		
		/**
		 * Get the String Values for the MainMenu.
		 * 
		 * @return An array of Strings representing MainMenu.values().
		 */
		public static String[] getStringValues() {
			return MenuItem.getStringValues(SettingsMenuItem.class);
		}
	}
	
	/**
	 * Draw the vitals.
	 * 
	 * @param g
	 */
	public void drawVitals(Graphics2D g) {
		// System.out.println("vitals!");
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
		// int fontSize = 8;
		// g.setFont(new Font("Courier", Font.PLAIN, fontSize));
		g.setFont(Message.FONT);
		int windowWidth = (int) (Message.FONT_SIZE * 0.8 * 14);
		int windowHeight = Message.FONT_SIZE * (4 + 1);
		
		g.setColor(Color.BLACK);
		int windowY = Message.FONT_SIZE / 2;
		int windowX = graphics.getWidth() - windowWidth - Message.FONT_SIZE / 2;
		g.fillRect(windowX, windowY, windowWidth, windowHeight);
		
		g.setColor(Color.WHITE);
		int yStep = 1;
		windowX += tileSize / 2;
		windowY += tileSize / (Message.FONT_SIZE / 2);
		g.drawString(hero.getDamageVitals(), windowX, windowY
				+ Message.FONT_SIZE * yStep++);
		g.drawString(hero.getHungerVitals(), windowX, windowY
				+ Message.FONT_SIZE * yStep++);
		g.drawString(hero.getThirstVitals(), windowX, windowY
				+ Message.FONT_SIZE * yStep++);
		g.drawString(hero.getStressVitals(), windowX, windowY
				+ Message.FONT_SIZE * yStep++);
		
	}
	
	// ***********************************************************************
	// Inventory Details
	// ***********************************************************************
	
	private Menu createInventoryDetailsMenu() {
		String[] menuItems = hero.inventory.getSlotDescriptions();
		byte alignment = Menu.VERTICAL;
		InventoryMenuResponder responder = new InventoryMenuResponder();
		Menu hMenu = new Menu(null, menuItems, alignment, responder);
		return hMenu;
	}
	
	/**
	 * Set the notification message.
	 * 
	 * @param text The text to display. If null, it clears the display.
	 * @param framesToDisplay The number of frames to hold the display. If less
	 *        than 1, it is set to -1, which means it will be held indefinitely.
	 *        (until player presses "OK").
	 */
	public void showMessage(String text, int framesToDisplay) {
		if (text == null) {
			notificationMessage = null;
			notificationMessageTime = -1;
		}
		else {
			notificationMessage = new SingleLineMessage(text, false);
			notificationMessageTime = framesToDisplay > 0 ? framesToDisplay
					: -1;
		}
	}
	
	/**
	 * Set the notification message.
	 * 
	 * @param text The text to display. If null, it clears the display. THis
	 *        message will be displayed indefinitely (until player presses
	 *        "OK").
	 */
	public void setNotificationMessage(String text) {
		showMessage(text, -1);
	}
	
	/**
	 * Draw an item message.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawNotificationMessage(Graphics2D g, int x, int y) {
		
		// draw the message.
		notificationMessage.render(g, x, y);
		
		// Use itemMessageTimer, if specified.
		if (notificationMessageTime > -1) {
			notificationMessageTime--;
			if (notificationMessageTime < 1) {
				setNotificationMessage(null);
				// itemMessage = null;
				// itemMessageTime = -1;
			}
		}
	}
	
	/**
	 * Draw the vitals.
	 * 
	 * @param g
	 */
	public void drawTimer(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
		// int fontSize = 8;
		g.setFont(Message.FONT);
		
		g.setColor(Color.BLACK);
		int w = (int) (Message.FONT_SIZE * 0.8 * 8);
		int h = Message.FONT_SIZE + Message.FONT_SIZE / 2;
		
		int x = graphics.getWidth() - w - Message.FONT_SIZE / 2;
		int y = graphics.getHeight() - h - Message.FONT_SIZE / 2;
		g.fillRoundRect(x, y, w, h, 8, 8);
		g.setColor(Color.WHITE);
		
		x += Message.FONT_SIZE / 2;
		y += Message.FONT_SIZE;
		// g.drawString(String.format("Time: %6s", hero.time / Game.FRAME_RATE),
		// x, y);
		g.drawString(formatIntegerAsTime((int) hero.time), x, y);
	}
	
	// ***********************************************************************
	// TIME CONSTANTS
	// ***********************************************************************
	
	public static final int		SECONDS_PER_MINUTE	= 60;
	public static final int		MINUTES_PER_HOUR	= 60;
	public static final int		SECONDS_PER_HOUR	= 60 * 60;
	public static final int		HOURS_PER_DAY		= 24;
	public static final int		NOON				= 12;
	
	/** Start the game at a random time. **/
	public static final int		START_TIME;
	static {
		START_TIME = new Random().nextInt(SECONDS_PER_HOUR * HOURS_PER_DAY);
	}
	
	public static final String	AM					= "am";
	public static final String	PM					= "pm";
	
	/**
	 * Take an integer and represent it as seconds, minutes, hrs, etc.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatIntegerAsTime(int time) {
		time += START_TIME;
		int minutes = (time / MINUTES_PER_HOUR) % MINUTES_PER_HOUR;
		int hours = (time / SECONDS_PER_HOUR) % HOURS_PER_DAY;
		String period = hours >= NOON ? PM : AM;
		hours %= NOON;
		if (hours == 0) hours = NOON;
		String format = String.format("%2d:%02d %s ", hours, minutes, period);
		return format;
	}
	
}

/*
 * 4. Player, Characters and Parties ---------------------------------- Another
 * confusing decision to mull over is how to handle the player and party data.
 * The player is the player of the game. Do they need recognition?
 * 
 * The Party represents the roster of Characters controlled by the player. Each
 * Character has stats, skills, and inventory. These Characters are controlled
 * on the MapMode via keyboard controls (moving about the Map) and taking
 * actions, and on the MenuMode to manage things.
 * 
 * I need to decide on a way for The MapMode to interface with the Characters.
 * Where are things stored such as Player sprites and Entities? Are these
 * dynamic, and need simple values stored in the Character data? Where is the
 * Party data stored? (Probably in the game, and passed to the map mode).
 * 
 * I may need a global constants class, considering my Database class idea, just
 * to have a centralized way to reference the important data (hopefully no
 * synchronization issues this time).
 * 
 * 5. Items -------- I have a pretty good handle on Items so far. I can define
 * and load my current Item system in a text file, which is a convenient way to
 * create instances. I want to duplicate and simplify my system to be used for
 * Races, Classes, Skills, etc so that development is quicker.
 * 
 * Inventory is decent as well, I have everything in place to be more robustly
 * implemented. For now I only have a basic ListInventory, but I have the
 * interface in place so I can write a general stackable Inventory as well as
 * dedicated inventories for specific items, like coin purses/wallets, or
 * special item bags.
 * 
 * As mentioned earlier, I need a way to represent Items on a Map. I need an
 * interfacing class to combine an Item with a Sprite and a Quad. No need for
 * Item updates, though, just to be able to remove them if collected (or add
 * them from some event).
 * 
 * 6. Menus --------- The Menu system I want to develop is pretty basic, and
 * runs on ideas based on SWING menus. Basically, have a list of menu items
 * displaying custom text which can be selected with arrow keys. The current
 * selection is highlighted. Pressing "OK" on a selected menu navigates to a sub
 * menu, or fires some action, and either displays new information on the Menu,
 * backs up, or any number of things.
 * 
 * I need a simple and robust way to render the text, handle user input, and
 * easily fire events. Some way to pass around things that cause actions to
 * happen, I probably to need to write an interface that the menu calls.
 * 
 * I also need a nice way to render the menus and organize them. A layout system
 * that takes menus and lays them out for me. Basically, implement the bare
 * bones of Swing's layout system.
 */

