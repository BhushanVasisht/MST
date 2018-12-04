import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MST {
	public static Map<String, Integer> edges;
	public static int NUM_VERTICES = 0;
	public static ArrayList<String> cities;
	public static BinaryHeap<Integer> weights;
	public static Map<String, Integer> MST;
	
	public static void readCSV(String filename)
	{
		//New BufferedReader object to skim through the csv file
		BufferedReader br = null;
		
		cities = new ArrayList<String>();
		
		//Initialize the data placeholder to empty string
		String line = "";
		try
		{
			//Initialize the BufferedReader object as a new BufferedReader
			br = new BufferedReader(new FileReader(filename));
			
			while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(",");
                
                if(!cities.contains(data[0])) { cities.add(data[0]);}
				
				for(int i=1; i< data.length; i+=2)
				{
					//Construct a key as the vertex pair
					String key = data[0] + "," + data[i];
					
					
					if(!cities.contains(data[i])) { cities.add(data[i]);}
					
					//Put the generated key and the weight of the edge as a key-value pair into the hashMap edges
					edges.put(key,  Integer.parseInt(data[i+1]));
					
				}
				
			}
			
			NUM_VERTICES = cities.size();
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
		catch (IOException e) {
            e.printStackTrace();
        } 
		finally {
            if (br != null) {
                try {
                    br.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	public static void findMST()
	{
		weights = new BinaryHeap<Integer>();
		MST = new HashMap<String, Integer>();
		
		for(String key : edges.keySet())
		{
			weights.insert(edges.get(key));
		}
		
		
		/*
		 * Kruskal's algorithm start
		 */
		
		int edgesAccepted = 0;
		DisjSets edge_sets = new DisjSets(NUM_VERTICES);
		
		while(edgesAccepted < NUM_VERTICES - 1)
		{
			int minWeight = weights.deleteMin();
			String vertexName = findEdge(minWeight);
			
			String u = vertexName.split(",")[0];
			String v = vertexName.split(",")[1];
			
			int u_set = edge_sets.find(cities.indexOf(u));
			int v_set = edge_sets.find(cities.indexOf(v));
			
			if( u_set != v_set)
			{
				edgesAccepted++;
				edge_sets.union(u_set, v_set);
				MST.put(vertexName, minWeight);
				
				System.out.println(vertexName + " : " + minWeight);
			}
		}
		
		/*
		 * Kruskal's algorithm end
		 */
	}
	public static String findEdge(int we)
	{
		for(String key: edges.keySet())
		{
			if(edges.get(key) == we) { return key;}
		}
		
		return null;
		
	}
	
	public static int findMSTCost()
	{
		int cost = 0;
		
		for(String key: MST.keySet())
		{
			cost += MST.get(key);
		}
		
		return cost;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		edges = new HashMap<String, Integer>();
		
		readCSV(args[0]);
		
		System.out.println("The Minimum Spanning Tree Edges and their weights are as follows:\n");
		
		findMST();
		
		int MSTCost = findMSTCost();
		
		System.out.println("\n\n" + "The total cost/weight of the Minimum Spanning Tree found is equal to: " + MSTCost + "\n");
		
	}
	
}
