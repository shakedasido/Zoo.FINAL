package animals;
/**
* @version 28/05/2022
* @author  Shaked Asido.
* @author Michael Klaiman
*/
public abstract class AnimalDecorator implements IAnimalColor {
	protected IAnimalColor iAnimal;

	/**
	 * Constructor of AnimalDecorator
	 * @param iAnimal interface that Animal implements to be decorated
	 */
	public AnimalDecorator(IAnimalColor iAnimal)
	{
		this.iAnimal = iAnimal;
	}

	/**
	 * Sets color to animal
	 * @param col color to be set
	 */
	public void setColor(String col)
	{
		iAnimal.setColor(col);
	}

	/**
	 * Reloads images of animal
	 */
	public void reloadImages()
	{
		iAnimal.reloadImages();
	}
}
