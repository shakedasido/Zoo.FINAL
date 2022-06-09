package graphics;

import animals.*;
import food.EFoodType;
import plants.Meat;
import mobility.Point;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A class that activates a panel of choices- using GUI.
 *
 * @version 02/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class ZooPanel extends JPanel{
    private BufferedImage img= null;
    private final String PICTURE_PATH = "src\\photos\\";
    private ArrayList<Animal> animalArrayList = new ArrayList<>(); //List of all the animals, max 10
    private int MAX_ANIMAL_AMOUNT = 15;
    private int totalEatingAmount = 0;
    private EFoodType foodType = EFoodType.NOTFOOD;
    private Plant plantOrMeat = null; // plant object
    private final Color color = null; // color of panel
    private ZooFrame zooFrame;

    //part 1 of the work: the definition of the threadPul
    private int corePoolSize = 10, maxPoolSize = 10, keepAliveTime = 5000;
    private ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(5));

    //for the red rectangle that mark the animal
    private static Animal rect = null;
    private boolean drawRectangle = false;

    private Controller controller = new Controller();
    private ZooPanelHistoryManager historyManager;

    //this class is singleton: private constructor and get instance
    private static volatile ZooPanel singletonZooPanel = null;

    /**
     * Constructor of ZooPanel
     */
    private ZooPanel(){
        this.setLayout(new BorderLayout());
        this.historyManager = new  ZooPanelHistoryManager(this);
        createMenuBar();

    }

    /**
     * Returns ZooPanel that is Singleton (if it's null initializes it)
     * @return ZooPanel that is Singleton
     */
    public static ZooPanel getInstance() {
    	if (singletonZooPanel == null)
    	{
    		synchronized (ZooPanel.class) {
				if(singletonZooPanel == null)
				{
					singletonZooPanel = new ZooPanel();
				}
			}
    		
    	}
    	return singletonZooPanel;
    }

    /**
     * Paint function of ZooPanel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        Graphics2D gr = (Graphics2D) g;
        if(img!=null)
        {
            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gr.drawImage(img,0,20,getWidth(),getHeight(), this);
        }
        else
            g.setColor(null);

        synchronized(animalArrayList) {
            for(Animal an : animalArrayList)
                an.drawObject(g);
        }

        if(this.plantOrMeat !=null) //drawing a plant
            plantOrMeat.drawObject(g);

        if(rect!=null && drawRectangle)
        {
        	Graphics2D g2 = (Graphics2D)g;
    		g2.setColor(Color.red);
    		if(rect.getX_dir()==1)
    			g2.drawRect(rect.getLocation().GetX() - rect.getSize()/2, rect.getLocation().GetY() - rect.getSize()/10, rect.getSize(), rect.getSize());
    		else
    			g2.drawRect(rect.getLocation().GetX(), rect.getLocation().GetY() - rect.getSize()/10, rect.getSize(), rect.getSize());
        }

    }

    /**
     * Creates menu bar
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        // menu
        JMenu fileMenu = new JMenu("File");
        JMenu backgroundMenu = new JMenu("Background");
        JMenu helpMenu = new JMenu("Help");

        //default- Savanna image will appear
        try {
            img = ImageIO.read(new File(PICTURE_PATH+"savanna.jpg"));
            repaint();
        }
        catch (IOException e) { System.out.println("Cannot load image"); }

        //Submenu: Exit
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> exitClicked());
        fileMenu.add(exitMenuItem);

        //Submenu: image
        JMenuItem image = new JMenuItem("Image");
        image.addActionListener(e -> {
            try {
                repaintToImage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        backgroundMenu.add(image);

        //Submenu: green
        JMenuItem green = new JMenuItem("Green");
        green.addActionListener(e -> repaintToGreen());
        backgroundMenu.add(green);

        //Submenu: None
        JMenuItem none = new JMenuItem("None");
        none.addActionListener(e -> repaintToNone());
        backgroundMenu.add(none);

        //Submenu: help
        JMenuItem Help = new JMenuItem("Help");
        Help.addActionListener(e -> JOptionPane.showMessageDialog(null, "Home Work 3 \nGUI"));
        helpMenu.add(Help);

        //activate the menu
        backgroundMenu.add(none);
        menuBar.add(fileMenu);
        menuBar.add(backgroundMenu);
        menuBar.add(helpMenu);
        menuBar.add(Box.createHorizontalGlue());
        this.add(menuBar, BorderLayout.NORTH);

        //panel of buttons
        JPanel downMenu = new JPanel();
        downMenu.setLayout(new GridLayout(1,9,2,0));

        //buttons
        JButton addAnimal = new JButton("Add Animal");
        JButton changeColorOfAnimal = new JButton("Change Color");
        JButton saveStatus = new JButton("Save Status");
        JButton loadStatus = new JButton("Load Status");
        JButton sleep = new JButton("Sleep");
        JButton wakeUp = new JButton("Wake Up");
        JButton clear = new JButton("Clear");
        JButton food = new JButton("Food");
        JButton info = new JButton("Info");
        JButton exit = new JButton("Exit");

        //activate the downMenu
        downMenu.add(addAnimal);

        //add animal
        addAnimal.addActionListener(e -> ConnectToAddAnimalDialog());
        //save status
        saveStatus.addActionListener(e -> saveStatus());
     	//load status
        loadStatus.addActionListener(e -> loadStatus());
        
        //change color of animal
        downMenu.add(changeColorOfAnimal);
        changeColorOfAnimal.addActionListener(this::ConnectToChangeColorOfAnimal);
        
        downMenu.add(saveStatus);
        downMenu.add(loadStatus);

        //sleep
        downMenu.add(sleep);
        sleep.addActionListener(e -> SleepAnimal());

        //Wake up
        downMenu.add(wakeUp);
        wakeUp.addActionListener(e -> wakeUpAnimal());

        //clear
        downMenu.add(clear);
        clear.addActionListener(e -> {
            threadPoolExecutor.shutdownNow();
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(5));
            animalArrayList.clear();
            foodType = EFoodType.NOTFOOD;
            plantOrMeat = null;
            totalEatingAmount = 0;
            repaint();
        });

        //food
        downMenu.add(food);
        food.addActionListener(e -> Food());

        //info
        info.addActionListener(e -> ConnectToInfoDialog());

        downMenu.add(info);

        //exit
        downMenu.add(exit);
        exit.addActionListener(e -> exitClicked());

        //put the panel of buttons on the south of the frame
        this.add(downMenu,BorderLayout.SOUTH);

    }

    /**
     * Opens add animal dialog window
     * @param evt to get who called the function
     */
    public void ConnectToChangeColorOfAnimal(java.awt.event.ActionEvent evt)
    {
    	if(animalArrayList.size()>0)
    	{
	    	drawRectangle = true;
	    	rect = animalArrayList.get(0);
	    	ChangeColorOfAnimalDialog ca = new ChangeColorOfAnimalDialog((JFrame) SwingUtilities.getWindowAncestor((JButton)evt.getSource()),animalArrayList,"Change color of animal: ", this);
	    	drawRectangle = false;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "No animals");
    	}
    }
    /**
     * Saves current status of panel
     */
    public void saveStatus()
    {
        historyManager.hitSave();
    }
    /**
     * Loads saved status of panel
     */
    public void loadStatus()
    {
        historyManager.hitUndo();
    }
    /**
     * Exits the application
     */
    public void exitClicked()
    {
    	threadPoolExecutor.shutdownNow();
    	System.exit(0);
    }

    /**
     * Open an image you choose and convert it into file
     * @throws IOException if file not found
     */
    //open an image you choose and convert it into file
    public void repaintToImage() throws IOException {

        FileDialog fileDialog = new FileDialog((Frame) null, "Choose an image: ", FileDialog.LOAD);
        fileDialog.setDirectory(PICTURE_PATH);
        fileDialog.setVisible(true);
        String fileDirectory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();
        if (filename != null)
        {
            File file = new File(fileDirectory, filename);
            try {
                img = ImageIO.read(file);
                repaint();
            } catch (IOException ex) { System.out.println("Cannot load image");}
        }
        else
            JOptionPane.showMessageDialog(null, "No image loaded");
        System.out.println("You cancelled the choice");
    }

    /**
     * Creates a green screen
     */
    //creates a green screen
    public void repaintToGreen(){
        img = null;
        this.setBackground(new Color(34,139,34));
        repaint();
    }

    /**
     * Creates a white screen
     */
    //creates a white screen
    public void repaintToNone(){
        img = null;
        this.setBackground(new Color(225,225,225));
        repaint();
    }


    /**
     *  this function gets animal from addAnimalDialog by its kind and adds it to the list of the animal.
     */
    public void ConnectToAddAnimalDialog()
    {
    	Object[] foodTypes = {"Omnivore", "Carnivore", "Herbivore"};
    	String factory = "";
        int n = JOptionPane.showOptionDialog(zooFrame, "Please choose animal type", "Choose animal type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, foodTypes, foodTypes[2]);
        switch (n) {
            case 0 -> factory = "Omnivore";
            case 1 -> factory = "Carnivore";
            case 2 -> factory = "Herbivore";
        }

        // only if I choose any of the kinds of the animals
        if(!factory.equals(""))
        {
	        if(animalArrayList.size()< MAX_ANIMAL_AMOUNT) {
	            AddAnimalDialog animalAdded = new AddAnimalDialog(this,"Add an animal to the zoo: ",factory);
	            animalAdded.setVisible(true); }
	        else {JOptionPane.showMessageDialog(
	                null, "You cannot add more than "+ MAX_ANIMAL_AMOUNT +" animals");}
        }
    }

    /**
     * Add animal that was sent from add animal dialog
     * @param animal animal type to add
     * @param size size of animal
     * @param horSpeed horizantal speed of animal
     * @param verSpeed vertical speed of animal
     * @param col color of animal
     * @param factory factory chosen
     */
    public void AddAnimal(String animal, int size, int horSpeed, int verSpeed, String col, String factory)
    {
        // getFactory- Abstract factory- returns factory of factories
        Animal newAnimal = AnimalFactoryProducer.getFactory(factory).getAnimal(animal,1,1, size, col, horSpeed, verSpeed, this);
        animalArrayList.add(newAnimal);
        newAnimal.registerObserver(controller);
        threadPoolExecutor.execute(newAnimal);
    }

    /**
     * Opens info dialog window
     */
    public void ConnectToInfoDialog()
    {
        if(animalArrayList.size()>0) {
            InfoDialog info = new InfoDialog(this, animalArrayList);
            info.setVisible(true); }
        else { JOptionPane.showMessageDialog(null, "There is no animals to Present!"); }
        repaint();
    }

    /**
     * Adds food to panel
     */
    public void Food()
    {
        if(foodType == EFoodType.NOTFOOD){
            Object[] foodTypes = {"Lettuce", "Cabbage", "Meat" };
            int n = JOptionPane.showOptionDialog(zooFrame, "Please choose food", "Food for animals",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, foodTypes, foodTypes[2]);
            switch (n) {
                case 0 -> {
                    foodType = EFoodType.VEGETABLE;  // Lettuce
                    plantOrMeat = Lettuce.getInstance();
                }
                case 1 -> {
                    foodType = EFoodType.VEGETABLE;  // Cabbage
                    plantOrMeat = Cabbage.getInstance();
                }
                case 2 -> {
                    foodType = EFoodType.MEAT;  // Meat
                    plantOrMeat = Meat.getInstance();
                }
            }
        }
        else {
            foodType = EFoodType.NOTFOOD;
            plantOrMeat = null; }

        repaint();
    }

    /**
     * Make all animals sleeping
     */
    private void SleepAnimal() {
        for(Animal animal : animalArrayList)
            animal.setSuspended();
    }
    /**
     * Wakes up all animals
     */
    public void wakeUpAnimal() {
        for (Animal animal : animalArrayList) {
            synchronized (animal){
                animal.setResumed();
                animal.notify();
            }
        }
    }


    /**
     * This method will check each time an animal moves if they can eat an object that is near them
     */
    public void manageZoo()
    {
        synchronized(animalArrayList)
        {
            for (Animal animal : animalArrayList) { // going through the entire array

                //if the current animal eats plants nd there is plant on the screen
                if (plantOrMeat !=null && animal.getDiet().canEat(plantOrMeat.getFoodType())) {
                    //The X-axis goes right: if the animal is on the right side of the plant, and it goes right, I want
                    // it to go to the opposite way, in order to eat the plant. if not, keep going on the same path.
                    if (animal.getLocation().GetX() > plantOrMeat.getLocation().GetX())
                        animal.setX_dir(-1);
                    else
                        animal.setX_dir(1);

                    //The Y-axis goes down: If the animal is under the plant, I want it to go up. Means I want
                    // it to go to the opposite way, in order to eat the plant. if not, keep going on the same path
                    if (animal.getLocation().GetY() > plantOrMeat.getLocation().GetY())
                        animal.setY_dir(-1);
                    else
                        animal.setY_dir(1);

                    //if the hor speed of the animal is too bigger then the location of the plant,
                    // then the animal can accidentally skip the plant, so:
                    if (Math.abs(animal.getLocation().GetX() - plantOrMeat.getLocation().GetX()) <= animal.getHorSpeed() && animal.getHorSpeed() != 0) {
                        //set the location to be the same location as the plant in the X-axis,
                        // and keep the same location of the animal at ht ey-axis
                        animal.setLocation(new Point(plantOrMeat.getLocation().GetX(), animal.getLocation().GetY()));

                        //I want the animal to stop move on the x-axis for this eating, in order to make sure that
                        // the animal will not skip the food, so first I keep the hor speed for later.
                        animal.setoldHspeed(animal.getHorSpeed());

                        //then I set it to 0, just until the animal will eat the food.
                        animal.setHorSpeed(0);
                    }

                    //if the ver speed of the animal is too bigger then the location of the plant,
                    // then the animal can accidentally skip the plant, so:
                    if (Math.abs(animal.getLocation().GetY() - plantOrMeat.getLocation().GetY()) <= animal.getVerSpeed() && animal.getVerSpeed() != 0) {
                        //same as the x-axis.(up)
                        animal.setLocation(new Point(animal.getLocation().GetX(), plantOrMeat.getLocation().GetY()));

                        animal.setoldVspeed(animal.getVerSpeed());

                        animal.setVerSpeed(0);
                    }

                }
                //checking if plant can be eaten
                if (plantOrMeat != null && animal.calcDistance(plantOrMeat.getLocation()) <= animal.getEatDistance() && animal.eat(plantOrMeat)) {
                    animal.eatInc();
                    totalEatingAmount += 1;
                    plantOrMeat = null;
                    foodType = EFoodType.NOTFOOD;
                }
                //checking if other close animals can be eaten
                for (Animal other : animalArrayList) {
                    if (animal != other && animal.getWeight() >= 2*other.getWeight() && animal.calcDistance(other.getLocation()) < other.getSize() && animal.eat(other)) {
                        animal.eatInc();
                        totalEatingAmount -= (other.getEatCount()-1);
                        other.interrupt();
                        animalArrayList.remove(other); //removing eaten animal from array
                        return;
                    }
                }

                //after eating the food: the food is null and because I want the animal to keep going on the same path
                //as it was before the eating. if I had to move the animal with only one of the speeds (hor/ver),
                //so it means I made the other speed (ver/ hor) to be 0 before, so now I want to update the speed
                //again to be the original speed required by the user.
                if(animal.getHorSpeed()==0 && plantOrMeat ==null)
                    animal.setHorSpeed(animal.getoldHspeed());

                if(animal.getVerSpeed()==0&& plantOrMeat ==null)
                    animal.setVerSpeed(animal.getoldVspeed());
            }
        }
        
        this.repaint(); //repainting panel
    }


    /**
	 * Receives data from ChangeColorOfAnimalDialog and sets a new rectangle to draw
	 * @param animal
	 * 		animal on which to draw rectangle
	 */
	public static void receiveResponseDrawRectangle(Animal animal)
	{
		rect = animal;
	}

    /**
     * private class that stores state of panel (list of animals)
     */
	private static class ZooPanelState implements Memento {
		private ArrayList<Animal> animalArrayList;
		public ZooPanelState(ArrayList<Animal> animalArrayList) { this.animalArrayList = animalArrayList;}
		public ArrayList<Animal> getAnimalArrayList() { return this.animalArrayList; }
	}
    /**
     * Saves state of panel and returns it
     * @return state of panel
     */
    public Memento save() {
        return new ZooPanelState(cloneAnimalList());
    }

    /**
     * Restore state of panel
     * @param state to restore
     */
	public void restore(Memento state) {
		threadPoolExecutor.shutdownNow();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(5));
		this.animalArrayList = ((ZooPanelState)state).getAnimalArrayList();
		for(Animal animal : this.animalArrayList)
		{
			threadPoolExecutor.execute(animal);
		}
		repaint();
	}

    /**
     * Shows message that history is empty
     */
    public void notifyEmptyHistory()
    {
        JOptionPane.showMessageDialog(null, "No history to restore");
    }

    /**
     * Shows message that history is full
     */
    public void notifyFullHistory()
    {
        JOptionPane.showMessageDialog(null, "Can't add more than 3 states to history");
    }

    /**
     * Clones the list of animals and returns it
     * @return cloned list of animals
     */
	private ArrayList<Animal> cloneAnimalList()
	{
		ArrayList<Animal> clonedList = new ArrayList<>();
		for (Animal animal : this.animalArrayList)
		{
			try {
				Animal clonedAnimal = (Animal)animal.clone();
				clonedAnimal.setSuspended();
				clonedList.add(clonedAnimal);
			} catch (CloneNotSupportedException ignored) {
				
			}
		}
		return clonedList;
	}
}
