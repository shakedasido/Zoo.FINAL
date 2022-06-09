package plants;
import food.EFoodType;

/**
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Meat extends Plant {
	private static volatile Meat singletonMeat = null;

    private Meat() {
        super();
        loadImages("meat");
    }
    public static Meat getInstance() {
    	if (singletonMeat == null)
    	{
    		synchronized (Meat.class) {
				if(singletonMeat == null)
				{
					singletonMeat = new Meat();
				}
			}
    		
    	}
    	return singletonMeat;
    }

    @Override
    public EFoodType getFoodType() {
        return EFoodType.MEAT;}

}