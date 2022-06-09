package animals;
import java.util.Vector;

import diet.*;
import food.EFoodType;
import graphics.Observer;
import graphics.ZooPanel;
import mobility.*;

/**
 * A class representing a Lion, which it is kind of animal, meat eater.
 * Extends class Animal.
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Lion extends Animal{

    /**
     * Constructor for the Lion's attributes. It creates a specific Lion. and defines his attributes.
     */
    public Lion(int size, String col, int x_dir, int y_dir, int horSpeed, int verSpeed, ZooPanel pan)
    {
        super("Lion", size, size*0.8, horSpeed, verSpeed, col, pan,"lio");
        setLocation(new Point(20,0));
        this.setDiet(new Carnivore());
        this.SetWeight(size*0.8);
        this.setX_dir(x_dir);
        this.setY_dir(y_dir);
        loadImages("lio");
    }

    @Override
    public EFoodType getFoodType() {return EFoodType.NOTFOOD;}
    public String toString()
    {
        return "[ Lion]: "+this.getAnimalName();
    }


    /**
     * Returns clone of the animal
     * @return clone of animal
     */
    @Override
	public Object clone() {
		Lion clone = (Lion)AnimalFactoryProducer.getFactory("Carnivore").getAnimal("Lion",this.getX_dir(),this.getY_dir(), this.getSize(),this.getColor(),this.getHorSpeed(),this.getVerSpeed(),this.getPanel());
		clone.setLocation(this.getLocation());
		clone.setEatCount(getEatCount());
		clone.setoldHspeed(getoldHspeed());
		clone.setoldVspeed(getoldVspeed());
		clone.SetWeight(getWeight());
		Vector<Observer> observers = getObserversList();
		for(Observer obs : observers)
			clone.registerObserver(obs);
		return clone;
	}
}