
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class FlightPath {

      
    static Graph flightGraph;
    
    static ArrayList<String> airportCodes;
    static ArrayList<String> airportCodesList;
    static ArrayList<String> edges = new ArrayList<String>(); 
    
    static int from;
    static int to; 
    
    static String answer = null;
    
    public static void main (String args[]) throws IOException, InterruptedException
    {
//          loadData("2021-05-06");
//          writeLaborDayData();
          from = 20;
          to = 20; 
          readLaborDayData();
        
    }
    
    public static int getFrom () {
        return from;
    }
    
    public static int getTo () {
        return to;
    }
    
    public static void setFrom (int input) {
        from = input;
    }
    
    public static void setTo (int input) {
        to = input;
    }
    
    public ArrayList<String> getAirportCodes (){
        return airportCodesList; 
    }
    
    public static String getAnswer () {
        return answer; 
    }
    
    public static ArrayList<String> getEdges (){
        return edges; 
    }
    
    public static void writeLaborDayData() throws IOException, InterruptedException
    {
        URLGetter url = new URLGetter("https://www.world-airport-codes.com/us-top-40-airports.html");
        String total = new String();
        ArrayList<String> page = url.getContents();
        for (String section : page) {
            total += section;
        }
        ArrayList<String> airportCodes = new ArrayList<String>();
        String template = "<td>(\\w{3})";
        Pattern p = Pattern.compile(template);
        Matcher m = p.matcher(total);
        while (m.find()) {

            if (!airportCodes.contains(m.group(1))) {
                airportCodes.add(m.group(1));
            }
        }
        flightGraph = new Graph(airportCodes.size());
        flightGraph.setAirports(airportCodes);
        File file = Paths.get(""
                + "files/laborDay.txt").toFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        for (int i = 0; i < airportCodes.size(); i++)
        {
            for (int j = 0; j < airportCodes.size(); j++)
            {
                if (i != j)
                {
                Thread.sleep(2000);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/"+airportCodes.get(i)+"/"+airportCodes.get(j)+"/2021-09-06"))
                        .header("x-rapidapi-key", "4e52897858msh5ea5bf995d7b24ep14a4dajsn2d7e8374e45e")
                        .header("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                // System.out.println(response.body());
                bw.write(response.body());
                bw.newLine();
                String minPrice = response.body().substring(response.body().lastIndexOf("MinPrice") + 12, response.body().lastIndexOf("MinPrice") + 15);
                if (minPrice.charAt(0) >= '1' && minPrice.charAt(0) <= '9')
                {
                    int min = 0;
                    if (minPrice.charAt(2) == ',')
                    {
                        min = Integer.parseInt(minPrice.substring(0, 2));        
                    }
                    else
                    {
                        min = Integer.parseInt(minPrice.substring(0, 3));
                    }
                    bw.write("Edge Added: " +airportCodes.get(i)+ " "+airportCodes.get(j)+" "+min);
                    bw.newLine();
                    // System.out.println("Edge Added: " +airportCodes.get(i)+ " "+airportCodes.get(j)+" "+min);
                }
                }
            }
        }
        bw.close();
    }
    
    public static void readLaborDayData() throws IOException
    {
        URLGetter url = new URLGetter("https://www.world-airport-codes.com/us-top-40-airports.html");
        String total = new String();
        ArrayList<String> page = url.getContents();
        for (String section : page) {
            total += section;
        }
        airportCodes = new ArrayList<String>();
        String template = "<td>(\\w{3})";
        Pattern p = Pattern.compile(template);
        Matcher m = p.matcher(total);
        while (m.find()) {

            if (!airportCodes.contains(m.group(1))) {
                airportCodes.add(m.group(1));
            }
        }
        
        flightGraph = new Graph(airportCodes.size());
        flightGraph.setAirports(airportCodes);
        // System.out.println(airportCodes);
        airportCodesList = airportCodes;
        BufferedReader br = new BufferedReader(new FileReader("files/laborDay.txt"));
        String temp = br.readLine();
        while (temp != null) {
            if (temp.charAt(0) == 'E')
            {            
                flightGraph.addEdge(temp.substring(12, 15), temp.substring(16, 19), Integer.parseInt(temp.substring(20)));
                edges.add("" + temp.substring(12, 15) + " "+temp.substring(16, 19)+" "+Integer.parseInt(temp.substring(20)));
                // System.out.println("Edge Added: " +temp.substring(12, 15)+ " "+temp.substring(16, 19)+" "+Integer.parseInt(temp.substring(20)));
            }
            
            temp = br.readLine();
        }
        br.close();
        
        
    }
    
    public static void runDij () { 
     // last two parameters can be changed for different airports 
        List<Integer> airportInts = Dijkstra.getShortestPath(flightGraph, from, to);
        answer = "The Cheapest Path from "+airportCodes.get(airportInts.get(0))+" to " + airportCodes.get(airportInts.get(airportInts.size() - 1)) +" is from ";
        
        for (int i : airportInts)
        {
            if (i != airportInts.get(airportInts.size() - 1))
            {
                answer = answer + airportCodes.get(i) + " to ";
            }
            else
            {
                answer = answer + airportCodes.get(i); 
            }
        }
    }
    
    
    public static void loadData(String date) throws IOException, InterruptedException
    {
        
        URLGetter url = new URLGetter("https://www.world-airport-codes.com/us-top-40-airports.html");
        String total = new String();
        ArrayList<String> page = url.getContents();
        for (String section : page) {
            total += section;
        }
        ArrayList<String> airportCodes = new ArrayList<String>();
        String template = "<td>(\\w{3})";
        Pattern p = Pattern.compile(template);
        Matcher m = p.matcher(total);
        while (m.find()) {

            if (!airportCodes.contains(m.group(1))) {
                airportCodes.add(m.group(1));
            }
        }
        
        flightGraph = new Graph(airportCodes.size());
        flightGraph.setAirports(airportCodes);
        // System.out.println(airportCodes);
        for (int i = 0; i < airportCodes.size(); i++)
        {
            for (int j = 0; j < airportCodes.size(); j++)
            {
                if (i != j)
                {
                Thread.sleep(2000);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/"+airportCodes.get(i)+"/"+airportCodes.get(j)+"/"+date))
                        .header("x-rapidapi-key", "4e52897858msh5ea5bf995d7b24ep14a4dajsn2d7e8374e45e")
                        .header("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String minPrice = response.body().substring(response.body().lastIndexOf("MinPrice") + 12, response.body().lastIndexOf("MinPrice") + 15);
                if (minPrice.charAt(1) == 'Y')
                {
                    break;
                }

                if (minPrice.charAt(0) >= '1' && minPrice.charAt(0) <= '9')
                {
                    int min = 0;
                    if (minPrice.charAt(2) == ',')
                    {
                        min = Integer.parseInt(minPrice.substring(0, 2));        
                    }
                    else
                    {
                        min = Integer.parseInt(minPrice.substring(0, 3));
                    }
                    // System.out.println("Edge Added: " +airportCodes.get(i)+ " "+airportCodes.get(j)+" "+min);
                    flightGraph.addEdge(airportCodes.get(i), airportCodes.get(j), min);
                    // System.out.println(Dijkstra.getShortestPath(flightGraph, 20, 10));
                    
                }
               
                }
               
              
                
            }
        }
       
        
    }
}
