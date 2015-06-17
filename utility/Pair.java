package utility;

import java.io.Serializable;

/**
 * A simple key/Value {@code Pair} represented as a data type.  Helpful for
 * encapsulating related values.
 * @author Aaron Carson
 * @version Jun 12, 2015
 * @param <K> The first value, denoted as {@code key}.
 * @param <V> The second value, denoted as {@code value}.
 */
public class Pair<K extends Serializable, V extends Serializable> implements Serializable
{	
	private static final long	serialVersionUID	= 1L;
	private K key;
	private V value;
		
	/**
	 * Create a new key/value {@link Pair}.
	 * @param key The key.
	 * @param value The value;
	 */
	public Pair(K key, V value){
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
}
