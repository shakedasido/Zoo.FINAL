package graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import animals.Animal;
import animals.ColoredDecorator;
import animals.IAnimalColor;

/**
 * JDialog that allows to change color of an animal
 * @version 30/05/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class ChangeColorOfAnimalDialog extends JDialog{
	private ArrayList<Animal> listOfAnimals;
	private ZooPanel pan;
	
	private final JComboBox<String> combo1; //animals
	private final JComboBox<String> combo2 = new JComboBox<>(); //colors

	/**
	 * Constructor of change animal color dialog
	 * @param window frame
	 * @param listOfAnimals list of animals
	 * @param title title of window
	 * @param pan panel
	 */
	public ChangeColorOfAnimalDialog(JFrame window,ArrayList<Animal> listOfAnimals,String title, ZooPanel pan)
	{
		super(window, title, true);
		this.pan = pan;
		this.listOfAnimals = listOfAnimals;
		this.setPreferredSize(new Dimension(500, 200));
		this.setResizable(false);
		GridLayout myGridLayout = new GridLayout(4, 2);
		myGridLayout.setHgap(20);
		this.setLayout(myGridLayout);
		this.add(new JLabel("Choose animal:"));
		this.add(combo1 = new JComboBox<>());
		this.add(new JLabel("Choose color:"));
		this.add(combo2);
		updateCombos();
		combo1.addActionListener(e -> {
			if(combo1.getSelectedIndex()>=0){
				ZooPanel.receiveResponseDrawRectangle(listOfAnimals.get(combo1.getSelectedIndex()));
				pan.repaint();}

		});
		combo2.addActionListener(e -> changeColor());
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}

	/**
	 * Changes color of animal
	 */
	private void changeColor()
	{
		if(combo2.getSelectedIndex()>=0)
		{
			IAnimalColor animal = new ColoredDecorator(listOfAnimals.get(combo1.getSelectedIndex()));
			animal.setColor(Objects.requireNonNull(combo2.getSelectedItem()).toString());
			animal.reloadImages();
			pan.repaint();
		}
	}
	/**
	 * Updates values in combobox combo1 and combo2
	 */
	private void updateCombos()
	{
		combo1.removeAllItems();
		int counter = 1;
		for (Animal ignored : listOfAnimals)
		{
			combo1.addItem("Animal " + counter);
			counter++;
		}
		combo2.addItem("Red");
		combo2.addItem("Blue");
		combo2.addItem("Natural");
	}

}
