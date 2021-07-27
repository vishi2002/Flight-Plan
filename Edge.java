
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Edge {

    // letters of destinations 
    String first;
    String second;
    
    // index #s of destinations
    int i1;
    int i2;
    
    // coordinates of destinations 
    Point s;
    Point f;
    
    // cost
    int weight;
    
    // boolean characteristics 
    boolean onFlight = false; 
    boolean direct = false; 
    
    // pre-set coordinates
    ArrayList<Point> coords = new ArrayList<Point>();
    Point ATL = new Point(500, 600);
    Point BNA = new Point(472, 580);
    Point BOS = new Point(603, 480);
    Point BWI = new Point(560, 527);
    Point CLT = new Point(530, 577);
    Point DAL = new Point(380, 610);
    Point DCA = new Point(557, 532);
    Point DEN = new Point(320, 532);
    Point DFW = new Point(370, 608); 
    Point DTW = new Point(500, 500); 
    Point EWR = new Point(580, 510);
    Point FLL = new Point(555, 680);
    Point IAD = new Point(553, 525); 
    Point IAH = new Point(390, 650); 
    Point JFK = new Point(585, 502); 
    Point LAS = new Point(210, 551);
    Point LAX = new Point(180, 572);
    Point LGA = new Point(582, 502);
    Point MCO = new Point(544, 663);
    Point MDW = new Point(462, 508);
    Point MIA = new Point(554, 689); 
    Point MSP = new Point(416, 476);
    Point ORD = new Point(460, 503);
    Point PDX = new Point(188, 416);
    Point PHL = new Point(569, 516);
    Point PHX = new Point(236, 590);
    Point SAN = new Point(190, 589);
    Point SEA = new Point(205, 414);
    Point SFO = new Point(156, 512);
    Point SLC = new Point(258, 521);
    Point TPA = new Point(535, 674);
    
    
    // places by alphabetical order
    ArrayList<String> places = new ArrayList<String>();
    
    public Edge (String o, String o2, int cost, boolean onFlight, boolean direct){
        
        this.onFlight = onFlight;
        this.direct = direct;
        
        coords.add(ATL);
        coords.add(BNA);
        coords.add(BOS);
        coords.add(BWI);
        coords.add(CLT);
        coords.add(DAL);
        coords.add(DCA);
        coords.add(DEN);
        coords.add(DFW);
        coords.add(DTW);
        coords.add(EWR);
        coords.add(FLL);
        coords.add(IAD);
        coords.add(IAH);
        coords.add(JFK);
        coords.add(LAS);
        coords.add(LAX);
        coords.add(LGA);
        coords.add(MCO);
        coords.add(MDW);
        coords.add(MIA);
        coords.add(MSP);
        coords.add(ORD);
        coords.add(PDX);
        coords.add(PHL);
        coords.add(PHX);
        coords.add(SAN);
        coords.add(SEA);
        coords.add(SFO);
        coords.add(SLC);
        coords.add(TPA);
        
        places.add("ATL");
        places.add("BNA");
        places.add("BOS");
        places.add("BWI");
        places.add("CLT");
        places.add("DAL");
        places.add("DCA");
        places.add("DEN");
        places.add("DFW");
        places.add("DTW");
        places.add("EWR");
        places.add("FLL");
        places.add("IAD");
        places.add("IAH");
        places.add("JFK");
        places.add("LAS");
        places.add("LAX");
        places.add("LGA");
        places.add("MCO");
        places.add("MDW");
        places.add("MIA");
        places.add("MSP");
        places.add("ORD");
        places.add("PDX");
        places.add("PHL");
        places.add("PHX");
        places.add("SAN");
        places.add("SEA");
        places.add("SFO");
        places.add("SLC");
        places.add("TPA");
        
        first = o;
        second = o2;
        weight = cost; 
        
        i1 = places.indexOf(first);
        i2 = places.indexOf(second);  
        
    }
    
    public void setOnFlight(boolean input) {
        onFlight = input;
    }
    
    public void setDirect(boolean input) {
        direct = input;
    }
    
    public boolean getOnFlight() {
        return onFlight;
    }
    
    public boolean getDirect() {
        return direct;
    }
    
    public String getID () {
        String id = "" + first + second;
        return id; 
    }
    
    public void drawEdge(Graphics g) {
        g.drawLine((int) coords.get(i1).getX(), (int) coords.get(i1).getY(), 
                (int) coords.get(i2).getX(), (int) coords.get(i2).getY());
        
        
        
    }
    
    
}
