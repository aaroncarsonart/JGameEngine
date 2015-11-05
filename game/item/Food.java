package game.item;

import game.creature.Creature;

public class Food extends Item implements Usable<Creature>
{
	private int	nutrition;
	
	/**
	 * Create a new Food Item.
	 * 
	 * @param name The name
	 * @param nutrition The amount of hunger this food replenishes.
	 */
	public Food(String name, int nutrition) {
		super(name);
		this.nutrition = nutrition;
	}
	
	@Override
	public void use(Creature target) {
		
	}
	
	@Override
	public boolean isConsumable() {
		return true;
	}
	
}
