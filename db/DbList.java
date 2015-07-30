package db;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for Relational-Database styled, static classes that are 
 * containers for a given type.  
 * @author Aaron Carson
 * @version Jun 12, 2015
 * @param <E>
 */
public class DbList<E> extends ArrayList<E>
{
	private static final long	serialVersionUID	= 1L;

	
	/**
	 * Create a new {@link DbList}.
	 */
	public DbList(){
		super();
	}

	/**
	 * Create a new {@link DbList}, initialized from the given {@link List} of
	 * elements.
	 * @param elements The {@code List} of elements used to initialize this
	 *                 {@code DbList}.
	 */
	public DbList(ArrayList<E> elements){
		setElements(elements);
	}
	
	/**
	 * Create a new {@link DbList}, initialized from the given array of
	 * elements.
	 * @param elements The array of elements used to initialize this
	 *                 {@code DbList}.
	 */
	public DbList(E[] elements){
		setElements(elements);
	}

	
	/**
	 * Try to retrieve The element at the given index.
	 * @param id The id of the element to retrieve.
	 * @return The element, if it exists, or null.
	 */
	public int append(E element) {
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
	
	/**
	 * Try to retrieve The element at the given index.
	 * @param id The id of the element to retrieve.
	 * @return The element, if it exists, or null.
	 */
	public E get(int id){
		if (id < 0 || id >= size()) return null;	
		else return super.get(id);
	}
		
	/**
	 * Replace the values of this {@link DbList} with the values contained 
	 * in the given array of elements.
	 * @param elements The new set of elements, represented as an array.
	 *                 The index position of each element is retained.
	 */
	public void setElements(E[] elements){
		this.clear();
		int size = elements.length;
		this.ensureCapacity(size);
		for(int i = 0; i < size; i++){
			this.set(i, elements[i]);
		}		
	}
	
	/**
	 * Replace the values of this {@link DbList} with the values contained 
	 * in the given list of elements.
	 * @param elements The new set of elements, represented as a List.
	 *                 The index position of each element is retained.
	 */
	public void setElements(List<E> elements){
		this.clear();
		int size = elements.size();
		this.ensureCapacity(size);
		this.addAll(elements);
	}
}
