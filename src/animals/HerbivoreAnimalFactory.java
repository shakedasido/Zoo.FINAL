package animals;

import graphics.ZooPanel;
/**
 * @version 18/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class HerbivoreAnimalFactory extends AnimalFactory{
	/**
	 * Receives the type of the factory from which we want to create the animal, and
	 * creates the chosen factory by the user, and return it.
	 * @param animalType
	 * 		the kind of the animal
	 * @param x_dir
	 * 	    the x dir of the animal
	 *@param y_dir
	 *      the y dir of the animal
	 *@param size
	 * 		the size of the animal
	 @param col
	  *      the color of the animal
	 *@param horSpeed
	 * 		the horizontal Speed of the animal
	 *@param verSpeed
	 * 		the vertical Speed of the animal
	 *@param pan
	 * 		the panel of the frame ( ZooPanel )
	 *
	 *@return factory that the user chosen
	 */
	public Animal getAnimal(String animalType,int x_dir,int y_dir,int size, String col, int horSpeed, int verSpeed, ZooPanel pan) {
		return switch (animalType) {
			case "Giraffe" -> new Giraffe(size, col, x_dir, y_dir, horSpeed, verSpeed, pan);
			case "Turtle" -> new Turtle(size, col, x_dir, y_dir, horSpeed, verSpeed, pan);
			case "Elephant" -> new Elephant(size, x_dir, y_dir, horSpeed, verSpeed, col, pan);
			default -> null;
		};
	}

}
