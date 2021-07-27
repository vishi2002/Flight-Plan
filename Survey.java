import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class Survey extends JPanel{

    // dimensions
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 800;
    
    //colors
    public static final Color background = new Color(255, 212, 128);
    
    // fonts
    public static final Font font = new Font("Poor Richard", Font.BOLD, 30);
    public static final Color brown = new Color(77, 38, 0);
    
    // classes
    FlightPath paths = new FlightPath();
    ArrayList<String> codes;
    
    public Survey () throws IOException {
        FlightPath.readLaborDayData();
        codes = paths.getAirportCodes();
        
    }
    
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        
        // sets background and rect
        g.setColor(Color.black);
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        
        g.setColor(background);
        g.fillRect(5, 5, COURT_WIDTH - 10, COURT_HEIGHT - 10);
        
        g.setColor(brown);
        g.setFont(font);
        g.drawString("Locations:", 20, 50);
        
        int mid = codes.size() / 2 + 1; 
        for (int i = 0; i < mid; i++) {
            g.drawString(codes.get(i), 20, 50 + 45 * (i + 1));
        }
        
        for (int i = mid; i < codes.size(); i++) {
            g.drawString(codes.get(i), 120, 50 + 45 * (i - mid + 1));
        }
        
        
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    
}
