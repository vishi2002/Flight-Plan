
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class Graph {

    ArrayList<String> airportIntegers;
    ArrayList<HashMap<Integer, Integer>> graph;

    public Graph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        airportIntegers = new ArrayList<String>();
        graph = new ArrayList<HashMap<Integer, Integer>>(n);
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
            graph.add(hash);
        }

    }
    
    public void setAirports(ArrayList<String> airports)
    {
        for (String s : airports)
        {
            airportIntegers.add(s);
        }
    }
    
    public int getSize() {
        return graph.size();
    }
    
    public boolean hasEdge(int u, int v) {
        if (u < 0 || u >= getSize() || v < 0 || v >= getSize()) {
            throw new IllegalArgumentException();
        }
        return graph.get(u).containsKey(v);
    }
    
    public int getWeight(int u, int v) {
        if (u < 0 || u >= getSize() || v < 0 || v >= getSize()) {
            throw new IllegalArgumentException();
        }
        if (hasEdge(u, v)) {
            return graph.get(u).get(v);
        } else {
            throw new NoSuchElementException();
        }

    }
    
    
    public boolean addEdge(String first, String second, int weight) {
        int u = airportIntegers.indexOf(first);
        int v = airportIntegers.indexOf(second);
  
        if (u < 0 || u >= getSize() || v < 0 || v >= getSize() || u == v) {

            throw new IllegalArgumentException();
        }
        if (hasEdge(u, v)) {
            return false;
        } else {
            graph.get(u).put(v, weight);
            return true;
        }
    }
    
    public Set<Integer> outNeighbors(int v) {
        if (v < 0 || v >= getSize()) {
            throw new IllegalArgumentException();
        }
        return graph.get(v).keySet();
    }


}
