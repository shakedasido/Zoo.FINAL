package graphics;
import java.awt.Graphics;

/**
 * An interface that includes functions that describes the functionality of drawing a picture on the screen.
 *
 * @version 03/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public interface IDrawable {
    String PICTURE_PATH = "src\\photos\\";
    void loadImages(String nm);
    void drawObject (Graphics g);
    String getColor();

}