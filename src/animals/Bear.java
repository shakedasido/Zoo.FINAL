package animals;
import java.util.Vector;
import diet.Omnivore;
import food.EFoodType;
import graphics.Observer;
import graphics.ZooPanel;
import mobility.Point;


/**
 * A class representing a bear, all eater, which is kind of animal.
 * Extends class Animal.
 * @version 28/05/2022
 * @author Shaked Asido.
 * @author Michael Klaiman.
 */
public class Bear extends Animal {

    /**
     * Constructor for the bear's attributes. It creates a specific bear. and defines his attributes.
     */
    public Bear(int size , int x_dir, int y_dir, int horSpeed, int verSpeed, String col, ZooPanel pan)
    {
        super("Bear", size, size*1.5, horSpeed, verSpeed, col, pan,"bea");
        setLocation(new Point(100,5));
        this.setDiet(new Omnivore());
        this.SetWeight(size*1.5);
        this.setX_dir(x_dir);
        this.setY_dir(y_dir);
        loadImages("bea");
    }

    //getters
    /**
     * Getter.
     * @return the kind of the food that Bear eats .
     */
    public EFoodType getFoodType() {return EFoodType.MEAT;}



    /**
     * Returns clone of the animal
     * @return clone of animal
     */
    @Override
	public Object clone() {
		//Bear clone = new Bear(this.getSize(),this.getX_dir(),this.getY_dir(),this.getHorSpeed(),this.getVerSpeed(),this.getColor(),this.getPanel());
		Bear clone = (Bear)AnimalFactoryProducer.getFactory("Omnivore").getAnimal("Bear",this.getX_dir(),this.getY_dir(), this.getSize(),this.getColor(),this.getHorSpeed(),this.getVerSpeed(),this.getPanel());
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
