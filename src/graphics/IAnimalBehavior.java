package graphics;

/**
 * An interface that includes functions that describes the behavior of drawing an animal.
 *
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public interface IAnimalBehavior {

    String getAnimalName();
    int getSize();
    void eatInc();
    int getEatCount();
    boolean getChanges();
    void setSuspended();
    void setResumed();
    void setChanges (boolean state);

}
