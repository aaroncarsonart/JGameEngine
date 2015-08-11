package game.menu;

import game.engine.Command;
import game.graphics.GameGraphics;
import game.graphics.WindowBorderRenderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

import utility.Direction;

/**
 * Menus are a list of MenuItems that can be traversed by the player. Each Menu
 * has a sub-menu.
 * <p>
 * The default Menu behavior is designed for a Linear Menu.
 * 
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class Menu implements Message
{
	public static final byte	HORIZONTAL		= 1;
	public static final byte	VERTICAL		= 12;
	public static final Color	SELECTION_COLOR	= new Color(100, 100, 255, 120);
	
	private String[]			menuItems;
	private SelectionResponder	responder;
	
	/** Used by horizontal layouts only. **/
	private int[]				menuItemWidths;
	private String				menuTitle;
	private int					selection;
	private int					savedSelection;
	private byte				alignment;
	private BufferedImage		backgroundImage;
	private int					textHeight;
	private int					width;
	private int					height;
	private int					fontAscent;
	private int					xTitleInset;
	private int					yTitleInset;
	private int					xMenuInset;
	private int					yMenuInset;
	private int					lineSpacing;
	/** Used by horizontal layouts only. **/
	private int					selectionWidth;
	private boolean				renderTitle;
	private boolean				renderCursor;
	
	/**
	 * Create a new Menu.
	 * 
	 * @param title The title of this menu. If the title is null (or of size
	 *        zero), then The title is not displayed.
	 * @param menuItems An array of Strings representing the menu items of this
	 *        menu.
	 * @param alignment The alignment of this menu, either Menu.HORIZONTAL or
	 *        Menu.VERTICAL.
	 * @param responder The Menu.SelectionResponder object that defines what
	 *        behavior to take when a menu item is selected.
	 */
	public Menu(String title, String[] menuItems, byte alignment,
			SelectionResponder responder) {
		this(title, menuItems, alignment, responder, 0, 0);
	}
	
	/**
	 * Get the width of this Menu.
	 * 
	 * @return The width in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of this Menu.
	 * 
	 * @return The height in pixels.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Create a new Menu.
	 * 
	 * @param title The title of this menu. If the title is null (or of size
	 *        zero), then The title is not displayed.
	 * @param menuItems An array of Strings representing the menu items of this
	 *        menu.
	 * @param alignment The alignment of this menu, either Menu.HORIZONTAL or
	 *        Menu.VERTICAL.
	 * @param responder The Menu.SelectionResponder object that defines what
	 *        behavior to take when a menu item is selected.
	 * @param width Optional parameter, if greater than zero then it limits the
	 *        width of this menu.
	 * @param height Optional parameter, if greater than zero then it limits the
	 *        height of this menu.
	 */
	public Menu(String title, String[] menuItems, byte alignment,
			SelectionResponder responder, int width, int height) {
		initialize(title, menuItems, alignment, responder, width, height);
		
	}
	
	/**
	 * Hidden default constructor for custom subclass behavior. Call
	 * initialize() to set needed values after this constructor call.
	 */
	protected Menu() {}
	
	/**
	 * Initialize the menu with the given parameters.
	 * 
	 * @param title The title of this menu. If the title is null (or of size
	 *        zero), then The title is not displayed.
	 * @param menuItems An array of Strings representing the menu items of this
	 *        menu.
	 * @param alignment The alignment of this menu, either Menu.HORIZONTAL or
	 *        Menu.VERTICAL.
	 * @param responder The Menu.SelectionResponder object that defines what
	 *        behavior to take when a menu item is selected.
	 * @param width Optional parameter, if greater than zero then it limits the
	 *        width of this menu.
	 * @param height Optional parameter, if greater than zero then it limits the
	 *        height of this menu.
	 */
	protected void initialize(String title, String[] menuItems, byte alignment,
			SelectionResponder responder, int width, int height) {
		this.menuTitle = title;
		this.menuItems = menuItems;
		this.alignment = alignment;
		this.responder = responder;
		this.width = width < 0 ? 0 : width;
		this.height = height < 0 ? 0 : height;
		this.selection = 0;
		this.savedSelection = -1;
		this.renderCursor = true;
		
		// title will only be rendered if the title is not null or empty.
		this.renderTitle = !(menuTitle == null || menuTitle.isEmpty());
		
		// begin selection at first menu item.
		// initialize based on alignment.
		switch (alignment) {
		case HORIZONTAL:
			initializeForHorizontalAlignment();
			break;
		case VERTICAL:
		default:
			initializeForVerticalAlignment();
			break;
		}
		
	}
	
	/**
	 * initialize the Menu state for displaying with a horizontal alignment.
	 */
	private void initializeForHorizontalAlignment() {
		// **********************************
		// 1. get font rendering metadata
		// **********************************
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage tmp = gc.createCompatibleImage(1, 1,
				Transparency.TRANSLUCENT);
		Graphics2D g = tmp.createGraphics();
		g.setFont(FONT);
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		
		// calculate height of a single line of text.
		FontMetrics metrics = g.getFontMetrics();
		textHeight = metrics.getHeight();
		fontAscent = metrics.getAscent();
		
		FontRenderContext context = g.getFontRenderContext();
		int innerTitleWidth;
		if (renderTitle) {
			innerTitleWidth = Message.getRenderedWidth(context, menuTitle);
		}
		else {
			innerTitleWidth = 0;
		}
		// int xPadding = 8;
		// int yPadding = 8;
		this.lineSpacing = X_PADDING / 2;
		selectionWidth = Message.getMaximumRenderedWidth(metrics, menuItems);
		menuItemWidths = new int[menuItems.length];
		for (int i = 0; i < menuItems.length; i++) {
			menuItemWidths[i] = Message.getRenderedWidth(metrics, menuItems[i]);
		}
		// the width of the menuItem selection area
		int innerMenuWidth = (selectionWidth + lineSpacing * 2)
				* menuItems.length;
		g.dispose();
		
		// *****************************************
		// Create the background image for the menu.
		// *****************************************
		WindowBorderRenderer borderRenderer = new WindowBorderRenderer();
		
		int tileWidth = borderRenderer.getTileWidth();
		int tileHeight = borderRenderer.getTileHeight();
		// the menu contains a bordered label to the left, and a menu selection
		// out to the right.
		if (height == 0) {
			this.height = textHeight + 2 * (Y_PADDING + tileHeight) + Y_FIX;
		}
		int titleWidth = innerTitleWidth + 2 * (X_PADDING + tileWidth);
		int menuWidth = innerMenuWidth + 2 * (X_PADDING + tileWidth);
		if (width == 0) {
			if (renderTitle) this.width = titleWidth + menuWidth;
			else this.width = menuWidth;
		}
		
		BufferedImage menuImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		g = menuImage.createGraphics();
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		
		// draw dark backgrounds.
		g.setColor(Color.BLACK);
		int rx, ry, rw, rh;
		int txOff = tileWidth / 2;
		int tyOff = tileHeight / 2;
		rx = txOff;
		ry = tyOff;
		rw = width - txOff / 2 * 2;
		rh = height - tyOff * 2;
		g.fillRect(rx, ry, rw, rh);
		
		// render the menu borders
		if (renderTitle) {
			borderRenderer.render(g, 0, 0, titleWidth, height);
			borderRenderer.render(g, titleWidth, 0, width - titleWidth, height);
		}
		else {
			borderRenderer.render(g, 0, 0, width, height);
		}
		
		// calculate menu title and menu option positioning values.
		if (renderTitle) {
			this.xTitleInset = X_PADDING + tileWidth;
			this.yTitleInset = Y_PADDING + tileHeight + fontAscent;
			this.xMenuInset = titleWidth + xTitleInset;
			this.yMenuInset = yTitleInset;
			renderTitle(g, 0, 0);
		}
		else {
			this.xMenuInset = X_PADDING + tileWidth;
			this.yMenuInset = Y_PADDING + tileHeight + fontAscent;
		}
		g.dispose();
		backgroundImage = menuImage;
		
	}
	
	/**
	 * initialize the Menu state for displaying with a vertical alignment.
	 */
	private void initializeForVerticalAlignment() {
		// **********************************
		// get font rendering metadata
		// **********************************
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage tmp = gc.createCompatibleImage(1, 1,
				Transparency.TRANSLUCENT);
		Graphics2D g = tmp.createGraphics();
		g.setFont(FONT);
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		
		// calculate height of a single line of text.
		FontMetrics metrics = g.getFontMetrics();
		textHeight = metrics.getHeight();
		fontAscent = metrics.getAscent();
		
		// calculate inner width and height of menu.
		FontRenderContext context = g.getFontRenderContext();
		int innerWidth = Math.max(
				renderTitle ? Message.getRenderedWidth(metrics, menuTitle) : 0,
				Message.getMaximumRenderedWidth(metrics, menuItems));
		
		// int xPadding = 8;
		// int yPadding = 8;
		this.lineSpacing = Y_PADDING / 2;
		int innerHeight = (textHeight + lineSpacing) * menuItems.length
				- lineSpacing;
		
		g.dispose();
		
		// *****************************************
		// Create the background image for the menu.
		// *****************************************
		WindowBorderRenderer borderRenderer = new WindowBorderRenderer();
		
		int tileWidth = borderRenderer.getTileWidth();
		int tileHeight = borderRenderer.getTileHeight();
		// the menu contains a bordered label on top, and a menu selection
		// below.
		int topHeight;
		if (renderTitle) topHeight = textHeight + 2 * (Y_PADDING + tileHeight);
		else topHeight = 0;
		int bottomHeight = innerHeight + 2 * (Y_PADDING + tileWidth);
		if (height == 0) {
			this.height = topHeight + bottomHeight;
		}
		if (width == 0) {
			this.width = innerWidth + 2 * tileWidth + 2 * X_PADDING;
		}
		BufferedImage menuImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		g = menuImage.createGraphics();
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		
		// draw dark backgrounds.
		g.setColor(Color.BLACK);
		int rx, ry, rw, rh;
		int txOff = tileWidth / 2;
		int tyOff = tileHeight / 2;
		rx = txOff;
		ry = tyOff;
		rw = width - txOff * 2;
		rh = height - tyOff * 2;
		g.fillRect(rx, ry, rw, rh);
		
		// render the menu borders
		if (renderTitle) {
			borderRenderer.render(g, 0, 0, width, topHeight);
			borderRenderer.render(g, 0, topHeight, width, height - topHeight);
		}
		else {
			borderRenderer.render(g, 0, 0, width, height);
		}
		
		// calculate menu title and menu option positioning values.
		if (renderTitle) {
			this.xTitleInset = X_PADDING + tileWidth;
			this.yTitleInset = Y_PADDING + tileHeight + fontAscent;
			this.xMenuInset = xTitleInset;
			this.yMenuInset = yTitleInset + topHeight;
			// draw the title.
			renderTitle(g, 0, 0);
		}
		else {
			this.xMenuInset = X_PADDING + tileWidth;
			this.yMenuInset = Y_PADDING + tileHeight + fontAscent;
			
		}
		
		g.dispose();
		backgroundImage = menuImage;
		
	}
	
	/**
	 * Get the alignment used by this Menu.
	 * 
	 * @return The alignment.
	 */
	public byte getAlignment() {
		return alignment;
	}
	
	/**
	 * Set the alignment used by this Menu.
	 * 
	 * @param alignment The new alignment.
	 */
	public void setAlignment(byte alignment) {
		this.alignment = alignment;
	}
	
	/**
	 * Get the Menu name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return menuTitle;
	}
	
	/**
	 * Set the Menu's name.
	 * 
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.menuTitle = name;
	}
	
	/**
	 * Set the new MenuItem at the given index.
	 * 
	 * @param index
	 * @param menuItem
	 */
	public void setMenuItem(int index, String menuItem) {
		menuItems[index] = menuItem;
	}
	
	/**
	 * Get the MenuItem at the specified index.
	 * 
	 * @param index The index to get at.
	 * @return The MenuItem at the index.
	 */
	public String getMenuItem(int index) {
		return menuItems[index];
	}
	
	/**
	 * Select a new menu item, based in the input Direction.
	 * 
	 * @param direction The Direction input, either UP, DOWN, LEFT, or RIGHT.
	 */
	public void moveSelectionCursor(Direction direction) {
		switch (direction) {
		
		// go back.
		case UP:
		case LEFT:
			selection = selection == 0 ? getCapacity() - 1 : selection - 1;
			break;
		
		// go forward.
		case DOWN:
		case RIGHT:
		default:
			selection = (selection + 1) % getCapacity();
			break;
		}
		// System.out.printf("direction: %s index: %d\n", direction, selection);
	}
	
	/**
	 * Manually set the selection cursor position.
	 * 
	 * @param position The new position.
	 */
	public void setSelectionCursor(int position) {
		if (position < 0) selection = 0;
		else if (position >= menuItems.length) selection = menuItems.length - 1;
		else selection = position;
	}
	
	/**
	 * Listen to the given Command from user input, and move in the related
	 * direction if the Command is ready to be consumed.
	 * 
	 * @param command The command to listen to.
	 */
	private void listenToMoveSelectionCursor(Command command) {
		if (!command.isConsumed()) {
			command.consume();
			moveSelectionCursor(Command.getDirectionFor(command));
		}
	}
	
	/**
	 * Listen to move the selection cursor based on inputs from the user.
	 */
	public void listenToMoveSelectionCursor() {
		listenToMoveSelectionCursor(Command.UP);
		listenToMoveSelectionCursor(Command.DOWN);
		listenToMoveSelectionCursor(Command.LEFT);
		listenToMoveSelectionCursor(Command.RIGHT);
	}
	
	/**
	 * Listen to Confirm the menu selection input by the user.
	 */
	public void listenToConfirmSelection() {
		if (!Command.OK.isConsumed()) {
			Command.OK.consume();
			responder.selectMenuItem(selection);
		}
	}
	
	/**
	 * Cancels the menu selection if CANCEL is pressed.
	 */
	public void listenToCancelSelection() {
		if (!Command.CANCEL.isConsumed()) {
			Command.CANCEL.consume();
			cancel();
		}
	}
	
	/**
	 * Listen to user input for the menu.
	 */
	public void listen() {
		listenToMoveSelectionCursor();
		listenToConfirmSelection();
		listenToCancelSelection();
	}
	
	/**
	 * Get the max number of MenuItems this menu can hold.
	 * 
	 * @return
	 */
	public int getCapacity() {
		return menuItems.length;
	}
	
	/**
	 * Get the current selection of this Menu.,
	 * 
	 * @return The index of the current selection.
	 */
	public int getSelection() {
		return selection;
	}
	
	/**
	 * Get the boolean value representing if the selection cursor is rendered.
	 * 
	 * @return True or false
	 */
	public boolean getRenderCursor() {
		return renderCursor;
	}
	
	/**
	 * Set if the selection cursor will be rendered.
	 * 
	 * @param renderCursor The new value.
	 */
	public void setRenderCursor(boolean renderCursor) {
		this.renderCursor = renderCursor;
	}
	
	@Override
	public void render(Graphics2D g, int x, int y, float alpha) {
		render(g, x, y, alpha, renderCursor);
	}
	
	/**
	 * Save the current selection.
	 */
	public void saveSelection() {
		savedSelection = selection;
	}
	
	/**
	 * Clear the savedSelection.
	 */
	public void clearSavedSelection() {
		savedSelection = -1;
	}
	
	/**
	 * Get the current saved selection, which may be different from the current
	 * selection. This is -1 if a selection is not saved.
	 * 
	 * @return The savedSelection, or -1.
	 */
	public int getSavedSelection() {
		return savedSelection;
	}
	
	/**
	 * Check if this menu has a saved selection.
	 * 
	 * @return
	 */
	public boolean hasSavedSelection() {
		return savedSelection != -1;
	}
	
	/**
	 * Render the Menu.
	 * 
	 * @param g The Graphics context.
	 * @param x The x position.
	 * @param y The y position.
	 * @param alpha The alpha value.
	 * @param cursor if true render the cursor position.
	 */
	public void render(Graphics2D g, int x, int y, float alpha, boolean cursor) {
		// int ix = x - width / 2;
		// int iy = y - height / 2;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g.drawImage(backgroundImage, x, y, null);
		
		g.setFont(FONT);
		g.setColor(Color.WHITE);
		
		// renderTitle(g, ix, iy);
		if (alignment == VERTICAL) renderMenuVertical(g, x, y, cursor);
		else renderMenuHorizontal(g, x, y, cursor);
		
	}
	
	/**
	 * Render the Menu Title text.
	 * 
	 * @param g The graphics context to render to.
	 * @param xOff The xOffset for the Title.
	 * @param yOff the yOffset for the title.
	 */
	private void renderTitle(Graphics2D g, int xOff, int yOff) {
		g.setFont(FONT);
		g.setColor(Color.WHITE);
		g.drawString(menuTitle, xTitleInset + xOff, yTitleInset + yOff);
	}
	
	/**
	 * Render the menu and current menu selection for a vertical layout.
	 * 
	 * @param g The Graphics2D context to render to.
	 * @param xOff The x offset to shift the menu text by.
	 * @param yOff The y offset to shift the menu text by.
	 */
	private void renderMenuVertical(Graphics2D g, int xOff, int yOff,
			boolean cursor) {
		// draw the menu items.
		int x = xMenuInset + xOff;
		for (int i = 0; i < menuItems.length; i++) {
			
			// get the current y text position.
			int y = yMenuInset + (textHeight + lineSpacing) * i + yOff;
			
			// draw the selection rectangle.
			if ((i == selection || i == savedSelection) && cursor) {
				Color prevColor = g.getColor();
				// Composite prevComposite = g.getComposite();
				g.setColor(SELECTION_COLOR);
				// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				// 0.15f));
				int w = width - xMenuInset * 2;
				int h = textHeight;
				g.fillRect(x, y - fontAscent, w, h);
				g.setColor(prevColor);
				// g.setComposite(prevComposite);
			}
			
			// draw the menu item.
			g.drawString(menuItems[i], x, y);
		}
	}
	
	private boolean	centerMenus	= true;
	
	/**
	 * Render the menu and current menu selection for a vertical layout.
	 * 
	 * @param g The Graphics2D context to render to.
	 * @param xOff The x offset to shift the menu text by.
	 * @param yOff The y offset to shift the menu text by.
	 */
	private void renderMenuHorizontal(Graphics2D g, int xOff, int yOff,
			boolean cursor) {
		// draw the menu items.
		int y = yMenuInset + yOff;
		int selectSpan = selectionWidth + lineSpacing * 2;
		for (int i = 0; i < menuItems.length; i++) {
			int x = xOff + xMenuInset + selectSpan * i;
			int xCenter;
			if (centerMenus) xCenter = (selectionWidth - menuItemWidths[i]) / 2;
			else xCenter = 0;
			// get the current y text position.
			
			// draw the selection rectangle.
			if (i == selection && cursor) {
				Color prevColor = g.getColor();
				// Composite prevComposite = g.getComposite();
				g.setColor(SELECTION_COLOR);
				// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				// 0.15f));
				int w = selectSpan;
				int h = textHeight;
				g.fillRect(x - lineSpacing, y - fontAscent, w, h);
				g.setColor(prevColor);
				// g.setComposite(prevComposite);
			}
			
			// draw the menu item.
			g.drawString(menuItems[i], x + xCenter, y);
		}
	}
	
	/**
	 * Handle a cancel or back command for this menu.
	 */
	public void cancel() {
		responder.cancel();
	}
	
	/**
	 * A MenuSelectionResponder must be implemented for each Menu, and passed to
	 * the constructor. This object is used to respond to menu presses, accessed
	 * by the menuItem index. These indexes correspond to the array indices of
	 * the menuItem array in Menu.
	 * 
	 * @author Aaron Carson
	 * @version Jul 28, 2015
	 */
	public interface SelectionResponder
	{
		
		/**
		 * Called when a menu item with the given index is selected.
		 * 
		 * @param index The index of the selected menu item.
		 */
		public void selectMenuItem(int index);
		
		/**
		 * Defines what behavior should be taken when this menu is cancelled.
		 */
		public void cancel();
		
	}
}
