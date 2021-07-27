import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Header extends JPanel{

    // dimensions 
    public static final int COURT_WIDTH = 1100;
    public static final int COURT_HEIGHT = 100;
    
    // colors 
    public static final Color brand = new Color(179, 149, 0);
    public static final Color brown = new Color(77, 38, 0);
    
    // fonts
    public static final Font brandName = new Font("Poor Richard", Font.BOLD, 70); 
    
    
    public Header () {
        
        
    }
    
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
       
        // sets background and rect  
        g.setColor(Color.black);
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        
        g.setColor(brown);
        g.fillRect(5, 5, COURT_WIDTH - 10, COURT_HEIGHT - 10);
        
        // writes title 
        g.setFont(brandName);
        g.setColor(brand);
        g.drawString("Flight Plan", 150, 80);
        
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    
}
