package animals;
import food.EFoodType;
import graphics.Observer;
import graphics.ZooPanel;

import java.util.Vector;

import diet.*;
import mobility.*;

/**
 * A class representing a Giraffe, which it is kind of animal, vegetable eater.
 * Extends class Animal.
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Giraffe extends Animal{

    private double neckLength;
    public final static double minNeckLength=1.0;
    public final static double maxNeckLength=2.5;
    public final static double defNeckLength=1.5;


    public Giraffe(int size, String col,int x_dir, int y_dir, int horSpeed, int verSpeed, ZooPanel pan)
    {
        super("Giraffe", size, size*2.2, horSpeed, verSpeed, col, pan,"grf");
        //here we create a new point, so we use new, in order to create a new default value.
        setLocation(new Point(50,0));
        this.setDiet(new Herbivore());
        this.SetWeight(size*2.2);
        loadImages("grf");
        neckLength = 1.5;
    }




    //setters
    public boolean setNeckLength(double t)
    {
        boolean x=true;

        if(t<minNeckLength||t>maxNeckLength)
            x = false;
        else
            this.neckLength=t;


        if(this.neckLength==0 && !x) // constructor call -> must be initialized with default value
        {
            this.neckLength=defNeckLength;
            x=true;

        }
        return x;
    }

    //getters
    public EFoodType getFoodType() { return EFoodType.MEAT; }
    public double getNeckLength() {return this.neckLength;}

    //methods
    public String toString()
    {
        return  "[ Giraffe]: "+this.getAnimalName();
    }


    /**
     * Returns clone of the animal
     * @return clone of animal
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
    	Giraffe clone = (Giraffe)AnimalFactoryProducer.getFactory("Herbivore").getAnimal("Giraffe",this.getX_dir(),this.getY_dir(), this.getSize(),this.getColor(),this.getHorSpeed(),this.getVerSpeed(),this.getPanel());
		clone.setLocation(this.getLocation());
		clone.setEatCount(getEatCount());
		clone.setoldHspeed(getoldHspeed());
		clone.setoldVspeed(getoldVspeed());
		clone.setX_dir(getX_dir());
		clone.setY_dir(getY_dir());
		clone.setNeckLength(getNeckLength());
		clone.SetWeight(getWeight());
		Vector<Observer> observers = getObserversList();
		for(Observer obs : observers)
			clone.registerObserver(obs);
		return clone;
	}

}