package game.item;

import game.graphics.SpriteSheet;
import game.graphics.SpriteSheet.Items;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code Item} is the base class for all items in the game. Items are things
 * that can be carried in a Character's Inventory. Items represent a physical,
 * existing thing in the game, such as an article of clothing, a weapon, or some
 * food. These belongings all have some common properties, which are defined
 * here.
 * <p>
 * Items have an intrinsic numerical value (the value in currency), as well as a
 * weight (which adds to a carrier's encumbrance). They also have a maximum
 * stack value, which means how many of one item take up one slot in the
 * Inventory.
 * <p>
 * Inventories which hold Items don't hold physical copies of the items.
 * Instead, inventory slots reference an Item by id (like a relational
 * database), and track some metadata, such as how many of that Item is in the
 * inventory, etc.
 * <p>
 * 
 * @author Aaron Carson
 * @version Jun 12, 2015
 */
public class Item implements Serializable, Comparable<Item>
{
	private static final long	serialVersionUID	= 1L;
	private static int			COUNTER				= 0;
	
	public final int			id;
	public final String			name;
	public final int			value;
	public final double			weight;
	public final int			stack;
	public int					tileIndex;
	public SpriteSheet			spriteSheet;
	
	/**
	 * Create a new {@Link Item item}.
	 * 
	 * @param name The name of this item. The name is used to identify unique
	 *        items, so each name must be unique.
	 * @param value How valuable the item is in currency
	 * @param weight How heavy the item is, in pounds. A weight of zero does not
	 *        add to the player's encumbrance.
	 * @param stack How many items can stack into one Inventory slot. A stack of
	 *        one means that item cannot stack. A stack of 0 means The slot it
	 *        occupies is empty and cannot be removed (normally, not used).
	 */
	public Item(String name, int value, double weight, int stack) {
		this.name = name;
		this.value = value;
		this.weight = weight;
		this.stack = stack;
		this.tileIndex = tileIndex;
		
		// add the element, or throw an exception if it already exists.
		this.id = COUNTER++;// TODO: address this Database.ITEMS.append(this);
		if (id == -1) {
			throw new IllegalArgumentException("names must be unique; '" + name
					+ "' already exists.");
		}
	}
	
	/**
	 * Create an item with no value, weight, and a stack of 1.
	 * 
	 * @param name The name of the Item.
	 */
	public Item(String name) {
		this(name, 0, 0, 1);
	}
	
	/**
	 * Get the index of this {@link Item item}.
	 * 
	 * @return The index where this item is stored in {@link Items}.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the name of this (@link Item}.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Compares two {@link Item items} based on their {@code name}. They are
	 * considered to be equal if they are both instances of {@code Item}, and
	 * their names are equal. The constructor does not allow Items with
	 * duplicate names to be created.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			return ((Item) obj).name.equals(this.name);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns a value based on the implementation of {@code Item.name}'s
	 * {@link String#hashCode()}.
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public void loadItems() {
		
	}
	
	@Override
	public int compareTo(Item item) {
		return this.name.compareTo(item.name);
	}
	
	@Override
	public String toString() {
		return String.format(
				"(%s) %-16s value: %7s  weight: %4.2f lb  stack: %2d",
				getClass().getSimpleName(), name,
				String.format("%d cp", value), weight, stack);
	}
	
	public static final String			ITEMS_FILE		= "/res/txt/items.txt";
	
	public static final int				NAME_ID			= 0;
	public static final int				VALUE_ID		= 1;
	public static final int				WEIGHT_ID		= 2;
	public static final int				STACK_ID		= 3;
	public static final int				SPRITE_ID		= 4;
	public static final int				SPRITESHEET_ID	= 5;
	
	public static final List<String>	COMMENTS		= Arrays.asList(new String[] {
			"#", "-"									});
	// public static final String COMMENT = "#";
	public static final String			DELIMITER		= "\t";
	
	/**
	 * Helper method to get a delimited string, delimited by Item.DELIMITER.
	 * Used to read item declarations in from a file.
	 * 
	 * @param string The String to delimit.
	 * @return A list of strings.
	 */
	public static ArrayList<String> getDelimitedStringList(String string) {
		ArrayList<String> list = new ArrayList<>();
		int dIndex = string.indexOf(DELIMITER);
		while (!string.isEmpty()) {
			list.add(string.substring(0, dIndex));
			string = string.substring(dIndex).trim();
			int next = string.indexOf(DELIMITER);
			dIndex = next == -1 ? string.length() : next;
		}
		return list;
	}
	
	/**
	 * Try to read an array of Items from a text file.
	 * 
	 * @return A List of Items.
	 */
	public static ArrayList<Item> readItemsFromTextFile() {
		try {
			// load the file.
			BufferedReader br = new BufferedReader(new InputStreamReader(
					Item.class.getResourceAsStream(ITEMS_FILE)));
			ArrayList<Item> items = new ArrayList<Item>();
			
			// read each line of the file.
			while (br.ready()) {
				String line = br.readLine().trim();
				
				// skip comments or blank lines.
				// if (line.startsWith(COMMENT) || line.length() == 0) continue;
				if (line.length() == 0
						|| COMMENTS.contains(line.substring(0, 1))) continue;
				
				// get a list of values.
				List<String> values = getDelimitedStringList(line);
				
				// skip invalid lines.
				if (values.size() < 5) continue;
				
				// get the item field values.
				String name = values.get(NAME_ID);
				int value = Integer.parseInt(values.get(VALUE_ID));
				double weight = Double.parseDouble(values.get(WEIGHT_ID));
				int stack = Integer.parseInt(values.get(STACK_ID));
				int sprite = Integer.parseInt(values.get(SPRITE_ID));
				int spriteSheetID = Integer
						.parseInt(values.get(SPRITESHEET_ID));
				System.out.printf("spriteSheetID: %d\n", spriteSheetID);
				SpriteSheet spriteSheet = SpriteSheet.Items.get(spriteSheetID);
				
				// create and add the Item.
				Item item = new Item(name, value, weight, stack);
				item.tileIndex = sprite;
				item.spriteSheet = spriteSheet;
				items.add(item);
			}
			
			// free resources and return the result.
			br.close();
			return items;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Test the method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Item> items = Item.readItemsFromTextFile();
		for (Item item : items) {
			System.out.println(item);
		}
	}
}
