import java.awt.*;
import javax.swing.*;

public class Footer extends JPanel{

    // dimensions 
    public static final int COURT_WIDTH = 1100;
    public static final int COURT_HEIGHT = 50;
    
    // colors 
    public static final Color brand = new Color(179, 149, 0);
    public static final Color brown = new Color(77, 38, 0);
    
    // fonts
    public static final Font names = new Font("Poor Richard", Font.BOLD, 20); 
    
    
    public Footer () {
        
        
    }
    
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
       
        // sets background and rect  
        g.setColor(Color.black);
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        
        g.setColor(brown);
        g.fillRect(5, 5, COURT_WIDTH - 10, COURT_HEIGHT - 10);
        
        // writes title 
        g.setFont(names);
        g.setColor(brand);
        g.drawString("Created by Vishesh Patel", 20, 30);
  
        
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    
}
