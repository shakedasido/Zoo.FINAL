package animals;
import graphics.ZooPanel;

/**
 * Animal factory
 *
 * @version 28/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public abstract class AnimalFactory {
	/**
	 * Returns animal
	 * @param animalType type of animal
	 * @param x_dir x directory of animal
	 * @param y_dir y directory of animal
	 * @param size size of animal
	 * @param col color of animal
	 * @param horSpeed horizontal speed of animal
	 * @param verSpeed vertical speed of animal
	 * @param pan panel of animal
	 * @return animal
	 */
	public abstract Animal getAnimal(String animalType,int x_dir,int y_dir,int size, String col, int horSpeed, int verSpeed, ZooPanel pan);
}
