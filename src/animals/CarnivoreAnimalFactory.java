package animals;

import graphics.ZooPanel;

/**
 * Class that represent a factory Carnivore animals (Abstract factory), expands AnimalFactory.
 *
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class CarnivoreAnimalFactory extends AnimalFactory {

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
		if (animalType.equals("Lion"))
		{
			return new Lion(size, col,x_dir,y_dir, horSpeed, verSpeed, pan);
		}
		return null;
	}
	

}
