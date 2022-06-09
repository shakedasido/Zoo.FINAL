package animals;

/**
 * Class that represent a factory of factories (Abstract factory), expands AnimalDecorator.
 *
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class ColoredDecorator extends AnimalDecorator {
    public ColoredDecorator(IAnimalColor changeableColorAnimal) { super(changeableColorAnimal); }
    /**
     * Sets color to animal
     * @param col color to be set
     */
    public void setColor(String col) {
        super.setColor(col);
    }
    /**
     * Reloads images of animal
     */
    public void reloadImages() {
        super.reloadImages();
    }
}