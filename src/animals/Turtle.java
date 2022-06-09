package animals;
import food.EFoodType;
import graphics.Observer;
import graphics.ZooPanel;
import java.util.Vector;
import diet.*;
import mobility.*;

/**
 * A class representing a Turtle, which it is kind of animal, vegetable eater.
 * Extends class ChewAnimals.
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Turtle extends Animal{

    public final static int defAge=1;
    public final static int minAge=0;
    public final static int maxAge=500;
    private int age=1;

    /**
     * Constructor for the Turtle's name. It creates a specific Turtle, with a default location.
     *        represent the Turtle's name.
     */
    public Turtle(int size, String col, int x_dir, int y_dir, int horSpeed, int verSpeed, ZooPanel pan) {
        super("Turtle", size, size*0.5, horSpeed, verSpeed, col, pan,"trt");
        setLocation(new Point(20,100));
        this.setDiet(new Herbivore());
        this.SetWeight(size*0.5);
        this.setX_dir(x_dir);
        this.setY_dir(y_dir);
        loadImages("trt");
    }



    //setters
    public boolean setAge(int a)
    {
        boolean x=true;

        if(a<minAge||a>maxAge)
            x= false;
        else
            this.age=a;

        if(this.age==-1) // constructor call -> if an isn't valid, age must be initialized with default value
        {
            x=true;
            this.age=defAge;


        }
        return x;
    }

    //getters
    public EFoodType getFoodType() { return EFoodType.MEAT;}
    public int getAge() { return this.age;}

    //methods
    public String toString()
    {
        return  "[ Turtle]: "+this.getAnimalName();
    }

    /**
     * Returns clone of the animal
     * @return clone of animal
     */
    @Override
	public Object clone() {
    	Turtle clone = (Turtle)AnimalFactoryProducer.getFactory("Herbivore").getAnimal("Turtle",this.getX_dir(),this.getY_dir(), this.getSize(),this.getColor(),this.getHorSpeed(),this.getVerSpeed(),this.getPanel());
		clone.setLocation(this.getLocation());
		clone.setEatCount(getEatCount());
		clone.setoldHspeed(getoldHspeed());
		clone.setoldVspeed(getoldVspeed());
		clone.setAge(getAge());
		clone.SetWeight(getWeight());
		Vector<Observer> observers = getObserversList();
		for(Observer obs : observers)
			clone.registerObserver(obs);
		return clone;
	}
}