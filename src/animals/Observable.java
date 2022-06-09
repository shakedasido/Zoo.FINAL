package animals;

import graphics.Observer;
/**
 * @version 27/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public interface Observable {
	void registerObserver(Observer l);
}
