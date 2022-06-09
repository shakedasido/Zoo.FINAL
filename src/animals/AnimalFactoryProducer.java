package animals;

/**
 * Class that represent a factory of factories (Abstract factory), expands AnimalDecorator.
 *
 * @version 29/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class AnimalFactoryProducer {

	/**
	 * Receives the type of the factory from which we want to create the animal, and
	 * creates the chosen factory by the user, and return it.
	 * @param type
	 * 		factory to create and return
	 * @return factory that the user chosen
	 */
	public static AnimalFactory getFactory(String type) {
		return switch (type) {
			case "Carnivore" -> new CarnivoreAnimalFactory();
			case "Herbivore" -> new HerbivoreAnimalFactory();
			case "Omnivore" -> new OmnivoreAnimalFactory();
			default -> null;
		};
	}
}
