package game.item;

import game.creature.Consumer;

public class Drink extends Item implements Usable<Consumer>
{	
	public Drink(String name, int nutrition){
		super(name);
	}

	@Override
	public void use(Consumer target) {
		
	}

	@Override
	public boolean isConsumable() {
		return true;
	}
}
