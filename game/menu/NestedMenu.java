package game.menu;

import java.awt.Graphics2D;

public class NestedMenu extends Menu
{
	private Menu	nestedMenu;
	private Menu	focusedMenu;
	
	/**
	 * Create a new NestedMenu, comprised of a primary menu wrapped around a
	 * secondary menu. Both are displayed at the same time.
	 * 
	 * @param title The title of this Menu.
	 * @param menuItems The menu items of the outer menu.
	 * @param nestedItems The menu items of the inner menu.
	 * @param outerAlignment The alignment of the outer menu.
	 * @param nestedAlignment The alignment of the inner menu.
	 * @param outerResponder The SelectionResponder for the outer menu.
	 * @param nestedResponder The SelectionResponder for the nested menu.
	 * @param width The combined total width of this NestedMenu.
	 * @param width The combined total height of this NestedMenu.
	 */
	public NestedMenu(String title, String[] menuItems, String[] nestedItems,
			byte outerAlignment, byte nestedAlignment,
			SelectionResponder outerResponder,
			SelectionResponder nestedResponder, int width, int height) {
		super();
		
		// create the outer menu.
		int outerWidth, outerHeight, nestedWidth, nestedHeight;
		if (outerAlignment == Menu.HORIZONTAL) {
			outerWidth = width;
			outerHeight = 0;
			initialize(title, menuItems, outerAlignment, outerResponder,
					outerWidth, outerHeight);
			nestedWidth = width;
			nestedHeight = height - super.getHeight();
			nestedMenu = new Menu(null, nestedItems, nestedAlignment,
					nestedResponder, nestedWidth, nestedHeight);
		}
		else {
			outerWidth = 0;
			outerHeight = height;
			initialize(title, menuItems, outerAlignment, outerResponder,
					outerWidth, outerHeight);
			nestedWidth = width - super.getWidth();
			nestedHeight = height;
			nestedMenu = new Menu(null, nestedItems, nestedAlignment,
					nestedResponder, nestedWidth, nestedHeight);
		}
		nestedMenu.setRenderCursor(false);
		
		System.out.println("outerWidth:  " + outerWidth);
		System.out.println("outerHeight: " + outerHeight);
		
		// create the nested menu.
		System.out.println("nestedWidth:  " + nestedWidth);
		System.out.println("nestedHeight: " + nestedHeight);
		
		focusedMenu = this;
		System.out.println("Width:  " + getWidth());
		System.out.println("Height: " + getHeight());
	}
	
	@Override
	public int getWidth() {
		return this.getAlignment() == Menu.HORIZONTAL ? super.getWidth()
				: super.getWidth() + nestedMenu.getWidth();
	}
	
	@Override
	public int getHeight() {
		return this.getAlignment() == Menu.VERTICAL ? super.getHeight() : super
				.getHeight() + nestedMenu.getHeight();
	}
	
	/**
	 * Get the nested Menu.
	 * 
	 * @return the nested Menu.
	 */
	public Menu getNestedMenu() {
		return nestedMenu;
	}
	
	@Override
	public void render(Graphics2D g, int x, int y, float alpha) {
		if (this.getAlignment() == Menu.HORIZONTAL) {			
			// render outer menu.
			int ox = x;
			int oy = y - nestedMenu.getHeight() / 2;
			super.render(g, ox, oy, alpha);
			
			// render nested menu.
			int ix = x;
			int iy = y + super.getHeight() / 2;
			nestedMenu.render(g, ix, iy, alpha);
		}
		else{
			// render outer menu.
			int ox = x - nestedMenu.getWidth() / 2;
			int oy = y;
			super.render(g, ox, oy, alpha);
			
			// render nested menu.
			int ix = x + super.getWidth() / 2;
			int iy = y;
			nestedMenu.render(g, ix, iy, alpha);			
		}
	}
	
	public void listen(){
		if (focusedMenu == this) super.listen();
		else nestedMenu.listen();
	}
}
