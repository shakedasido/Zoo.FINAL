package graphics;
import java.util.Objects;

/**
 * Observer of animals
 * @version 30/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Controller implements Observer {

	/**
	 * Observable objects can send their message to observer by calling this method
	 */
	public void notify(String msg) {
		if(Objects.equals(msg, "UPDATED_COORDINATES")) {
			ZooPanel.getInstance().manageZoo();
		}
	}
}
