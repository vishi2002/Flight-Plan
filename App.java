import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class App extends JPanel {

    // dimensions 
    public static final int COURT_WIDTH = 800;
    public static final int COURT_HEIGHT = 800;

    // colors
    public static final Color background = new Color(255, 247, 205);
    public static final Color background2 = new Color(255, 212, 128);
    
    // fonts
    public static final Font font = new Font("Poor Richard", Font.BOLD, 30); 
    public static final Font text = new Font("Poor Richard", Font.BOLD, 20); 
    
    // classes
    FlightPath paths = new FlightPath();
    ArrayList<String> codes;
    ArrayList<String> oCodes = new ArrayList<String>();
    
    // info
    public static String home = "ATL";
    public static String destination = "PHL";
    
    // gui components
    JLabel homeResult;
    JLabel destinationResult;
    String answer = "";
    boolean found = false; 
    
    // images
    ImageIcon unitedStates = new ImageIcon("src/unitedStates.png");
    
    // edges
    ArrayList<String> edges; 
    ArrayList<Edge> edgeSet = new ArrayList<Edge>();
    
    // controls
    boolean showPaths = false;
    boolean showFlights = true;
    
    public App () throws IOException {
        
        edges = FlightPath.getEdges();
        createEdges();
        
        JPanel selection = new JPanel();
        
        GridLayout g = new GridLayout(4, 2);
        selection.setLayout(g);
        selection.setSize(200, 50);
        
        FlightPath.readLaborDayData();
        codes = paths.getAirportCodes();
        for (int i = 0; i < codes.size(); i++) {
            oCodes.add(codes.get(i));
        }
       // Collections.sort(codes);
        String[] options = new String[codes.size()];
        codes.toArray(options);
        
        final JLabel departure = new JLabel("Select the location for your departure:     ");
        departure.setFont(text);
        departure.setBackground(background);
        departure.setOpaque(true);
        final JLabel arrival = new JLabel("    Select the location for your arrival:");
        arrival.setFont(text);
        arrival.setBackground(background);
        arrival.setOpaque(true);
        
        final JComboBox<String> choices = new JComboBox<String>(options);
        final JComboBox<String> choices2 = new JComboBox<String>(options);
        choices.setPreferredSize(new Dimension(70, 50));
        choices.setBackground(Color.red.brighter());
        choices2.setPreferredSize(new Dimension(70, 50));
        choices2.setBackground(Color.green.brighter());
        
        choices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home = (String) ((JComboBox)e.getSource()).getSelectedItem();
            }    
        });
        
        choices2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                destination = (String) ((JComboBox)e.getSource()).getSelectedItem();
            }    
        });
        
        JButton go = new JButton("Calculate Flights");
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < edgeSet.size(); i++) {
                    Edge ed = edgeSet.get(i);
                    ed.setOnFlight(false);
                    ed.setDirect(false);
                }
                FlightPath.setFrom(oCodes.indexOf(home));
                FlightPath.setTo(oCodes.indexOf(destination));
                try {
                    FlightPath.readLaborDayData();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (home != destination) {
                    FlightPath.runDij();
                    answer = FlightPath.getAnswer();
                    found = true; 
                    assignEdges();
                    repaint();
                }
            }  
        });
        go.setBackground(background2);
        
        JLabel info = new JLabel("     We'll find you the cheapest path!");
        info.setFont(text);
        info.setBackground(background);
        info.setOpaque(true);
        
        JButton showPathsButton = new JButton("Show All Paths");
        showPathsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPaths) {showPaths = false; } else {showPaths = true;}
                repaint();
            }
        });
        
        JButton showFlightsButton = new JButton("Show Flight Paths");
        showFlightsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showFlights) {showFlights = false; } else {showFlights = true; }
                repaint();
            }
        });
        
        showPathsButton.setBackground(background2);
        showFlightsButton.setBackground(background2);
        
        selection.add(departure);
        selection.add(arrival);
        selection.add(choices);
        selection.add(choices2);
        selection.add(go);
        selection.add(info);
        selection.add(showPathsButton);
        selection.add(showFlightsButton);
        
        add(selection);
        
    }
    
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        
        setFont(font);
        
        // sets background and rect 
        g.setColor(Color.black);
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        
        g.setColor(background);
        g.fillRect(5, 5, COURT_WIDTH - 10, COURT_HEIGHT - 10);
        
        // print answer 
        if (found) {
            g.setColor(Color.black);
            g.fillRect(90, 220, 620, 120);
            g.setColor(background2);
            g.fillRect(100, 230, 600, 100);
            g.setColor(Color.black);
            g.setFont(text);
            g.drawString(answer.substring(0, 52), 150, 280);
            if (answer.length() > 51) {
                g.drawString(answer.substring(52), 150, 310);
            }
            
            if (showFlights) {
                g.setColor(Color.green);
                g.fillOval(320, 360, 10, 10);
                
                g.setColor(Color.red);
                g.fillOval(520, 360, 10, 10);
                
                g.setColor(Color.black);
                g.drawString("Direct Flight", 340, 360);
                g.drawString("Cheapest Flight", 540, 360);
            }
            
        }
        
        unitedStates.paintIcon(this, g, 150, 400);
        
        g.setColor(Color.red);
        //ATL
        g.fillOval(500, 600, 5, 5);
        //BNA
        g.fillOval(472, 580, 5, 5);
        //BOS
        g.fillOval(603, 480, 5, 5);
        //BWI
        g.fillOval(563, 524, 5, 5);
        //CLT
        g.fillOval(530, 577, 5, 5);
        //DAL
        g.fillOval(380, 610, 5, 5);
        //DCA
        g.fillOval(557, 532, 5, 5);
        //DEN
        g.fillOval(320, 532, 5, 5);
        //DFW
        g.fillOval(370, 608, 5, 5);
        //DTW
        g.fillOval(500, 500, 5, 5);
        //EWR
        g.fillOval(580, 510, 5, 5);
        //FLL
        g.fillOval(555, 680, 5, 5);
        //IAD
        g.fillOval(553, 525, 5, 5);
        //IAH 
        g.fillOval(390, 650, 5, 5);
        //JFK
        g.fillOval(585, 502, 5, 5);
        //LAS
        g.fillOval(210, 551, 5, 5);
        //LAX
        g.fillOval(180, 572, 5, 5);
        //LGA
        g.fillOval(582, 502, 5, 5);
        //MCO
        g.fillOval(544, 663, 5, 5);
        //MDW
        g.fillOval(462, 508, 5, 5);
        //MIA
        g.fillOval(554, 689, 5,  5);
        //MSP
        g.fillOval(416, 476, 5, 5);
        //ORD
        g.fillOval(460, 503, 5, 5);
        //PDX
        g.fillOval(188, 416, 5, 5);
        //PHL
        g.fillOval(569, 516, 5, 5);
        //PHX
        g.fillOval(236, 590, 4, 5);
        //SAN
        g.fillOval(190, 589, 5, 5);
        //SEA
        g.fillOval(205, 414, 5, 5);
        //SFO
        g.fillOval(156, 512, 5, 5);
        //SLC
        g.fillOval(258, 521, 5, 5);
        //TPA
        g.fillOval(535, 674, 5, 5);
        
        if (showPaths) {
            for (int i = 0; i < edgeSet.size(); i++) {
                g.setColor(Color.blue);
                edgeSet.get(i).drawEdge(g);
             }   
        }
        
        if (showFlights) {
            for (int i = 0; i < edgeSet.size(); i++) {
                Edge e = edgeSet.get(i);
                if (e.getOnFlight()) {
                    g.setColor(Color.red);
                    e.drawEdge(g);
                } else if (e.getDirect()) {
                    g.setColor(Color.green);
                    e.drawEdge(g);
                }

             }  
        }
            
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    // creates edges from FlightPath into a set of class Edge
    public void createEdges () {
        for (int i = 0; i < edges.size(); i++) {
            
            String first = edges.get(i).substring(0, 3); 
            String second = edges.get(i).substring(4, 7);
            int weight = Integer.parseInt(edges.get(i).substring(8));
            
            edgeSet.add(new Edge(first, second, weight, false, false));
        }
    }
    
    // assigns certain edges to be true for being onFlight or direct
    public void assignEdges () {
        ArrayList<String> locations = new ArrayList<String>();
        for (int i = 42; i < answer.length(); i+=7) {
            locations.add((String) answer.subSequence(i, i + 3));
        }
        
        for (int i = 0; i < locations.size() - 1; i++) {
            String code = "" + locations.get(i) + locations.get(i + 1);
            String directCode = "" + locations.get(0) + locations.get(locations.size() - 1);
            for (int c = 0; c < edgeSet.size(); c++) {
                Edge e = edgeSet.get(c);
                if (e.getID().equals(code)) {
                    e.setOnFlight(true);
                }
                if (e.getID().equals(directCode)) {
                    e.setDirect(true);
                }
            }
        }
        
        
    }
    
}
