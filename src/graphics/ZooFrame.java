package graphics;
import java.awt.*;
import javax.swing.*;

/**
 * A class that activates a frame, with panels of choices- using GUI.
 *
 * @version 04/06/2022
 * @author  Shaked Asido.
 * @author Michael Klaiman.
 */
public class ZooFrame extends JFrame {

    /**
     * Constructor of ZooFrame
     */
    public ZooFrame() {

        setTitle("Zoo");
        setSize(1150, 670);
        ZooPanel zooPanel=ZooPanel.getInstance();
        add(zooPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            ZooFrame open = new ZooFrame();
            open.setVisible(true);
        });
    }
}
