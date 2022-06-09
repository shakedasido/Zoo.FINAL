package mobility;

/**
 * An interface that includes functions that describes location.
 *
 * @version 03/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public interface ILocatable {
    Point getLocation();
    boolean setLocation(Point p);
}
