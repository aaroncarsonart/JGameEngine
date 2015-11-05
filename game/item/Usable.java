package game.item;

/**
 * Usable denotes an item that is a valid option for the "Use" menu of the
 * Inventory Menu. Using an item has some undefined effect. Exactly what happens
 * Depends upon the implementation, but may use a skill, cast a spell, or
 * consume an item.
 * <p>
 * A Usable Item should only have one effect. Thus, Each implementation of
 * usable should only have one result from calling {@link #use()} and thus
 * simplify the game.
 * <p>
 * Items which are Consumable should return true to {@link #isConsumable()}.
 * 
 * @author Aaron Carson
 * @version Aug 11, 2015
 */
public interface Usable<Target>
{
	/**
	 * Use this item (Called from InventoryMenu -> Use).
	 */
	public void use(Target target);
	
	/**
	 * Check if this item is consumable. If true, the item is consumed when
	 * used. Otherwise, this item has unlimited uses.
	 * 
	 * @return
	 */
	public boolean isConsumable();
}
