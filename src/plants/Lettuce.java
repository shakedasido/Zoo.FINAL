package plants;

/**
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Lettuce extends Plant {
	private static volatile Lettuce singletonLettuce = null;

    private Lettuce() {
        super();
        loadImages("lettuce");
    }
    public static Lettuce getInstance() {
    	if (singletonLettuce == null)
    	{
    		synchronized (Lettuce.class) {
				if(singletonLettuce == null)
				{
					singletonLettuce = new Lettuce();
				}
			}
    		
    	}
    	return singletonLettuce;
    }

}
