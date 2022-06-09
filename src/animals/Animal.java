package animals;
import graphics.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import diet.*;
import food.*;
import mobility.*;


/**
 * Abstract class that defines the common features for all animals The class expands Mobile and implements IEdible,
 * IDrawable, IAnimalBehavior.
 *
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman
 */
public abstract class Animal extends Mobile implements Cloneable,animals.Observable,Runnable, IAnimalColor,
        IEdible, IDrawable, IAnimalBehavior{

    private String name;
    private double weight;
    private IDiet diet;
    private final int EAT_DISTANCE = 10;
    private int size;
    private String col;
    private int horSpeed;
    private int verSpeed;
    private boolean cordChanged;
    private int x_dir=1;
    private int y_dir=1;
    private int eatCount;
    private ZooPanel pan;
    private BufferedImage img1, img2;
    protected boolean threadSuspended = false;
    private int new_x, new_y;
    private int oldHspeed;
    private int oldVspeed;
    private boolean interrupted = false;
    private final String nm;
    private Vector<Observer> list = new Vector<>();

    /**
     * Constructor for the Animal's attributes. It initializes elements of an abstract animal, and then it will be
     * sent to a specific animal to continue the animal creations
     */
    public Animal(String kind, int size, double weight, int horSpeed, int verSpeed, String col, ZooPanel pan, String nm)
    {
        super(new Point(0,0));
        this.name = kind;
        this.size=size;
        this.weight = weight;
        this.horSpeed = horSpeed;
        this.verSpeed = verSpeed;
        this.col = col;
        this.pan = pan;
        this.pan.repaint();
        this.nm = nm;
        new_x =0;
        new_y =0;
    }



    //setters
    public void setX_dir(int x) { x_dir = x;}
    public void setY_dir(int y) { y_dir = y;}
    public void SetWeight(double weight)
    {
        boolean isSuccess = (weight >= 0);
        if (isSuccess) { this.weight = weight;}
        else
        {
            this.weight = 0;
        }
    }
    public boolean setDiet(IDiet diet)
    {
        this.diet=diet;
        return true;
    }
    @Override
    public void setChanges (boolean state) {this.cordChanged =state;}
    public void setHorSpeed(int horSpeed) {this.horSpeed = horSpeed;}
    public void setVerSpeed(int verSpeed) { this.verSpeed = verSpeed;}
    public void setoldHspeed(int ohspeed) {this.oldHspeed=ohspeed;}
    public void setoldVspeed(int ovspeed) {this.oldVspeed=ovspeed;}
    public void setEatCount(int eatCount) {this.eatCount = eatCount;}
    @Override
    public void setSuspended() { this.threadSuspended = true;}
    public void setResumed() { this.threadSuspended = false;}

    //getters
    public String getAnimalName() {return this.name;}
    public Vector<Observer> getObserversList() {return this.list;}
    public int getX_dir() {return this.x_dir;}
    public int getY_dir() {return this.y_dir;}
    public abstract EFoodType getFoodType();
    public int getEatDistance() {return this.EAT_DISTANCE;}
    public String getColor() {return col;}
    public int getEatCount() {return eatCount;}
    public int getSize() {return this.size;}
    public ZooPanel getPanel() {return this.pan; }
    public double getWeight() { return this.weight;}
    public IDiet getDiet() {return this.diet;}
    public boolean getChanges() {return this.cordChanged;}
    public int getHorSpeed() {return this.horSpeed;}
    public int getVerSpeed() {return this.verSpeed;}
    public int getoldHspeed() {return this.oldHspeed;}
    public int getoldVspeed() {return this.oldVspeed;}


    /**
     * Returns  true if food eaten, false otherwise
     * @param other food to eat
     * @return true if food eaten, false otherwise
     */
    public boolean eat(IEdible other)
    {
        boolean x=true;
        double wad=diet.eat(this, other);

        if(wad==0)
            x = false;
        else
        {
            this.SetWeight(this.weight+wad);
        }


        return x;
    }

    @Override
    public void eatInc() {eatCount++;}

    public double move(Point p)
    {

	    double distance =super.move(p);
	
	    double weight =this.getWeight();
	
	    this.SetWeight(weight-(distance*weight*0.00025));
	
	    boolean x= (distance != 0);
	
	    return distance;

    }


    /**
     * Loads images for animal
     * @param nm string that represents how to load images
     */
    @Override
    public void loadImages(String nm) {
        String color = this.col;
        switch(color) {
            case "Red":
                try {
                    img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_1.png"));
                    img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_2.png"));
                }
                catch (IOException e) { System.out.println("Cannot load image"); }
                break;
            case "Blue":
                try {
                    img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_1.png"));
                    img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_2.png"));
                }
                catch (IOException e) { System.out.println("Cannot load image"); }
                break;
            case "Natural":
                try {
                    img1 = ImageIO.read(new File(PICTURE_PATH + nm+ "_n_1.png"));
                    img2 = ImageIO.read(new File(PICTURE_PATH + nm+ "_n_2.png"));
                }
                catch (IOException e) { System.out.println("Cannot load image"); }
                break;
            default:
                System.out.println("Cannot load image");
        }
    }

    /**
     * Draws animal on graphics g
     * @param g graphics where to draw animal
     */
    public void drawObject (Graphics g)
    {

        if(x_dir==1) // animal goes to the right side
            g.drawImage(img1, this.getLocation().GetX()-size/2, this.getLocation().GetY() -size/10, size, size, pan);
        else // animal goes to the left side
            g.drawImage(img2, this.getLocation().GetX(), this.getLocation().GetY() -size/10, size, size, pan);
    }

    /**
     * Allows stopping animal when it's killed
     */
    public void interrupt() { this.interrupted = true; }


    /**
     * run method of animal
     */
    @Override
    public void run() {

        while(!interrupted) // when you make interrupted to thread then the animal doesn't
            // go out of the threadPul
        {
            if(threadSuspended)
            {
                synchronized (this)
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        return;
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {return;}

            ///(800, 600) is a point which is the limit boundaries of a point(determinate in Point)
            if (new_x <=800 && new_y <=600 && new_x >=0 && new_y >=0)
            {
                new_x = this.getLocation().GetX()+this.x_dir*this.horSpeed;
                new_y = this.getLocation().GetY()+this.y_dir*this.verSpeed;
                this.move(new Point(new_x, new_y));
            }
            else
            {
                if (new_x >800) { this.setX_dir((-x_dir));
                    new_x = this.getLocation().GetX()+this.x_dir*this.horSpeed;}
                if (new_y >600) { this.setY_dir((-y_dir));
                    new_y = this.getLocation().GetY()+this.y_dir*this.verSpeed;}
                if (new_x <0) { this.setX_dir((-x_dir));
                    new_x = this.getLocation().GetX()+this.x_dir*this.horSpeed;}
                if (new_y <0) { this.setY_dir((-y_dir));
                    new_y = this.getLocation().GetY()+this.y_dir*this.verSpeed;}
            }
            
            this.notifyObservers("UPDATED_COORDINATES");
        }

    }

    /**
     * reloads images for animal according to nm
     */
    public void reloadImages()
    {
    	this.loadImages(this.nm);
    }

    /**
     * Set color of animal
     * @param col color of animal to be set
     */
    public void setColor(String col)
    {
    	this.col = col;
    }

    /**
     * Registers observer for an animal
     * @param l observer to be registered
     */
	public void registerObserver(Observer l) {
		list.add(l);
	}

    /**
     * Unregisters observer for an animal
     * @param l observer to be unregistered
     */
	public synchronized void unregisterObserver(Observer l) {
		int index = list.indexOf(l);
		list.set(index, list.lastElement());
		list.remove(list.size()-1);
	}

    /**
     * Notifies all observers of an animal
     * @param msg message to send to observers
     */
	private void notifyObservers(String msg) {
		for (Observer l : list) {
	        l.notify(msg);
		}
	}

    /**
     * Clone method of an animal
     */
	public abstract Object clone() throws CloneNotSupportedException;

}
