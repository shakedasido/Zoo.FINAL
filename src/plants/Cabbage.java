package plants;

/**
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class Cabbage extends Plant {
	private static volatile Cabbage singletonCabbage = null;

    private Cabbage() {
        super();
        loadImages("cabbage");
    }
    public static Cabbage getInstance() {
    	if (singletonCabbage == null)
    	{
    		synchronized (Cabbage.class) {
				if(singletonCabbage == null)
				{
					singletonCabbage = new Cabbage();
				}
			}
    		
    	}
    	return singletonCabbage;
    }
}