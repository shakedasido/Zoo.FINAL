package animals;
import java.util.Vector;
import diet.Herbivore;
import food.EFoodType;
import graphics.Observer;
import graphics.ZooPanel;
import mobility.Point;

/**
 * A class representing an Elephant, which it is kind of animal, vegetable eater.
 * Extends class Animal.
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Elephant extends Animal
{
    private double trunkLength= 1;


    /**
     * Constructor for the Elephant's name. It creates a specific Elephant. and defines his location.
     */
    public Elephant(int size , int x_dir, int y_dir, int horSpeed, int verSpeed, String col, ZooPanel pan)
    {
        super("Elephant", size, size*10, horSpeed, verSpeed, col, pan,"elf");
        setLocation(new Point(50,90));
        this.setDiet(new Herbivore());
        this.SetWeight(size*10);
        this.setX_dir(x_dir);
        this.setY_dir(y_dir);
        loadImages("elf");
    }
    /**
     * Setter.
     * sets the attribute furColor, and print a massage with the function logSetter, from MassageUtilities class
     */
    public void setTrunkLength(double trunkLength)
    {
        boolean isSuccess = (trunkLength >= 0.5&&trunkLength <= 3);
        if (isSuccess) {this.trunkLength = trunkLength;}
        else {this.trunkLength = 1;}
    }

    /**
     * Getter.
     * returns the attribute trunkLength, and print a massage with the function logGetter, from MassageUtilities class.
     * @return the value of the attribute trunkLength as double.
     */
    public double getTrunkLength() {return this.trunkLength;}

    @Override
    public EFoodType getFoodType() { return EFoodType.MEAT; }


    /**
     * Returns clone of the animal
     * @return clone of animal
     */
    @Override
	public Object clone() {
		Elephant clone = (Elephant)AnimalFactoryProducer.getFactory("Herbivore").getAnimal("Elephant",this.getX_dir(),this.getY_dir(), this.getSize(),this.getColor(),this.getHorSpeed(),this.getVerSpeed(),this.getPanel());
		clone.setLocation(this.getLocation());
		clone.setEatCount(getEatCount());
		clone.setoldHspeed(getoldHspeed());
		clone.setoldVspeed(getoldVspeed());
		clone.setTrunkLength(getTrunkLength());
		clone.SetWeight(getWeight());
		Vector<Observer> observers = getObserversList();
		for(Observer obs : observers)
			clone.registerObserver(obs);
		return clone;
	}
}

