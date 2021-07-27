import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Main implements Runnable {

    @Override
    public void run() {
        final JFrame frame = new JFrame("TravelGuide");
        App app = null;
        Survey survey = null;
        try {
            survey = new Survey();
            app = new App();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final Header header = new Header();
        final Footer footer = new Footer();
        //final FlightPath flightPath = new FlightPath();
        
        
        frame.add(footer, BorderLayout.SOUTH);
        frame.add(header, BorderLayout.NORTH);
        frame.add(survey, BorderLayout.EAST);
        frame.add(app, BorderLayout.WEST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

}
