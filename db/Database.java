package db;

import game.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Database} is a collection of {@Link DbList DbLists} that is used to 
 * store and reference game data.  Access the static members of this 
 * class to access the game data.
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class Database
{	
	// ***************************************************************
	// static fields
	// ***************************************************************
	/**
	 * The set of available items (immutable list);
	 */
	public static final DbListSet<Item> ITEMS = new DbListSet<>();
	
	static{
		initialize();
	}
		
	/**
	 * Used to initialize all data stored in the {@link Database}.
	 */
	private static void initialize(){
		
		// ***************************************************
		// 1.) ITEMS
		// ***************************************************
		/*
		Item[] items = new Item[] {
				new Item("None",       0, 0.0, 0),
				new Item("Potion",     1, 0.0, 5),
				new Item("Iron Sword", 100, 3.2, 1),
				new Item("Flower",     20, 0.0, 20),
				
		};
		*/
	}
}
