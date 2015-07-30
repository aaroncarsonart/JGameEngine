package db;

import game.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Set extension class for Relational-Database styled, static classes that are 
 * containers for a given type. This is a List which does not allow 
 * duplicates (as defined by the element's {@code equals()} method).
 * @author Aaron Carson
 * @version Jun 15, 2015
 * @param <E> The type of elements stored in this DbListSet.
 */
public class DbListSet<E> extends DbList<E>
{	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Create a new {@link DbListSet}.
	 */
	public DbListSet(){
		super();
	}

	/**
	 * Create a new {@link DbListSet}, initialized from the given {@link List} of
	 * elements.
	 * @param elements The {@code List} of elements used to initialize this
	 *                 {@code DbListSet}.
	 */
	public DbListSet(ArrayList<E> elements){
		super(elements);
	}
	
	/**
	 * Create a new {@link DbListSet}, initialized from the given array of
	 * elements.
	 * @param elements The array of elements used to initialize this
	 *                 {@code DbListSet}.
	 */
	public DbListSet(E[] elements){
		super(elements);
	}

	
	/**
	 * Add the {@link Item}, then return the index which can be used to
	 * retrieve the {@code Item}.
	 * @param item The {@code Item} to add.
	 * @return The index where the {@code Item} was added, or -1 if it
	 *         could not be added (for instance, if it is a duplicate value)
	 */
	public int append(E element){
		// return error index if item already exists
		if(this.contains(element)){
			return -1;
		}
		
		// otherwise, insert the element at the end, then return the index.
		else{
			int id = this.size();
			this.add(element);
			return id;
		}
	}
}
