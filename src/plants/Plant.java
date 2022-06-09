package plants;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import food.EFoodType;
import food.IEdible;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.ILocatable;
import mobility.Point;

/**
 * Abstract class that defines the common features for all plants food type.
 * The class implements IEdible, ILocatable and IDrawable interfaces.
 *
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public abstract class Plant implements IEdible, ILocatable, IDrawable {

    private BufferedImage img;
    private Point location;

    /**
     * Constructor for the Plant's attributes. It initializes elements of an abstract plant, and then it will be
     * sent to a specific plant to continue the plant creations.
     */
    public Plant() {
        int size = 50;
        Random rand = new Random();
        int x = Point.max_x/2;
        int y = Point.max_y/2;
        this.location = new Point(x, y);
        double height = rand.nextInt(30);
        double weight = rand.nextInt(12);
    }
    //setters
    /**
     * (non-Javadoc)
     *
     * @see mobility.ILocatable#setLocation(mobility.Point)
     */
    @Override
    public boolean setLocation(Point newLocation) {
        boolean isSuccess = Point.checkBoundaries(newLocation);
        if (isSuccess) {
            this.location = newLocation;
        }
        return isSuccess;
    }

    //getters
    /**
     * @see EFoodType
     */
    public EFoodType getFoodType() {return EFoodType.VEGETABLE;}


    /**
     * @see mobility.ILocatable
     */
    @Override
    public Point getLocation() {
        return this.location;
    }
    public String getColor() {return "Green";}

    //methods
    @Override
    public void loadImages(String nm) {


        BufferedImage image = null;

        try {
            if (nm=="meat") { image= ImageIO.read(new File(PICTURE_PATH + nm+ ".gif"));}
            else { image = ImageIO.read(new File(PICTURE_PATH + nm+ ".png"));}}
        catch (IOException e) { System.out.println("Cannot load image");}
        img = image;
    }

    public void drawObject(Graphics g)
    {g.drawImage(img, location.GetX()+50, location.GetY(), 50, 50, null);}


    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] ";
    }

}