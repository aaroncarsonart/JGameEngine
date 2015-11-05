package game.creature;

import game.creature.stat.VitalStat;

/**
 * A Consumer is any game entity that can eat or drink.  
 * @author Aaron Carson
 * @version Aug 11, 2015
 */
public interface Consumer
{
	VitalStat getCurrentVitalStats();
	VitalStat getMaximumVitalStats();

	default int getCurrentDamage(){
		return getCurrentVitalStats().damage;
	}
		
	default int getCurrentHunger(){
		return getCurrentVitalStats().hunger;
	}

	default int getCurrentThirst(){
		return getCurrentVitalStats().thirst;
	}

	default int getCurrentStress(){
		return getCurrentVitalStats().stress;
	}


	default int getMaximumDamage(){
		return getMaximumVitalStats().damage;
	}
		
	default int getMaximumHunger(){
		return getMaximumVitalStats().hunger;
	}

	default int getMaximumThirst(){
		return getMaximumVitalStats().thirst;
	}

	default int getMaximumStress(){
		return getMaximumVitalStats().stress;
	}
	
	default void setCurrentDamage(int newValue){
		 getCurrentVitalStats().damage = newValue;
	}
		
	default void setCurrentHunger(int newValue){
		 getCurrentVitalStats().hunger = newValue;
	}

	default void setCurrentThirst(int newValue){
		 getCurrentVitalStats().thirst = newValue;
	}

	default void setCurrentStress(int newValue){
		 getCurrentVitalStats().stress = newValue;
	}


	default void setMaximumDamage(int newValue){
		 getMaximumVitalStats().damage = newValue;
	}
		
	default void setMaximumHunger(int newValue){
		 getMaximumVitalStats().hunger = newValue;
	}

	default void setMaximumThirst(int newValue){
		 getMaximumVitalStats().thirst = newValue;
	}

	default void setMaximumStress(int newValue){
		 getMaximumVitalStats().stress = newValue;
	}

	
}
