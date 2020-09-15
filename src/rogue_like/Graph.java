package rogue_like;

import java.io.*; 
import java.util.*; 
  

class Graph 
{   
	private int[][] map;
	private ArrayList<Coor> coors;
    private int v_count;
    private LinkedList<Coor> adj[];
    
    
    class Coor{
    	int index, x, y;
    	public Coor(int index, int x, int y) {
    		this.index = index;
    		this.x = x;
    		this.y = y;
    	}
    }
    
    Graph(Map m) 
    {	
    	coors = new ArrayList<Coor>();
    	this.map = m.map;
    	setCoors();
        v_count = coors.size();
        adj = new LinkedList[v_count]; 
        for (int i=0; i<v_count; ++i) 
            adj[i] = new LinkedList<Coor>();
        seekEdges();
    }
    
    void setCoors() {
    	int count = 0;
    	for(int i = 0; i < map.length; i++) {
    		for(int j = 0; j < map[i].length; j++) {
    			int value = map[i][j];
    			if(value != 1) {
    				Coor tmp = new Coor(count++, j, i);
    				coors.add(tmp);
    			}
    		}
    	}
    }
    
    void seekEdges() {
    	for(int i = 0; i < coors.size()-1; i++) {
    		int cur_x = coors.get(i).x;
    		int cur_y = coors.get(i).y;
    		for(int j = i + 1; j < coors.size(); j++) {
    			if(cur_x + 1 == coors.get(j).x) {
    				if(cur_y == coors.get(j).y)
    					addEdge(coors.get(i), coors.get(j));
    			}
    			else if(cur_y + 1 == coors.get(j).y) {
    				if(cur_x == coors.get(j).x)
    					addEdge(coors.get(i), coors.get(j));
    			}
    		}
    	}
    }
  
    void addEdge(Coor v, Coor w) 
    { 	
    	int i = v.index;
        adj[i].add(w); 
        int j = w.index;
        adj[j].add(v);
    } 
  
    boolean isTraversable() 
    {	
    	Coor v = coors.get(0);
    	int s = v.index;
        boolean visited[] = new boolean[v_count]; 
        LinkedList<Coor> queue = new LinkedList<Coor>(); 
        visited[s]=true; 
        queue.add(v); 
  
        while (queue.size() != 0) 
        { 
            v = queue.poll(); 
            Iterator<Coor> i = adj[v.index].listIterator(); 
            while (i.hasNext()) 
            { 
                Coor n = i.next(); 
                if (!visited[n.index]) 
                { 
                    visited[n.index] = true; 
                    queue.add(n); 
                } 
            } 
        }
        for(int i = 0; i < visited.length; i++) {
        	System.out.println(visited[i]);
        }
        for(int i = 0; i < visited.length; i++) {
        	if(!visited[i])
        		return false;
        }
        return true;
    }
    public static void main(String[] args) {
    	String[][] dummy_input = {{"1", "9", "1", "1", "1"}, // 0:길, 1:벽, 8:시작, 9:끝
				  				  {"1", "0", "1", "0", "0"},
				  				  {"1", "0", "0", "0", "0"},
				  				  {"1", "1", "1", "1", "0"},
				  				  {"1", "1", "1", "1", "8"}};
    	Map map = new Map("test_dummy");
    	map.validate(dummy_input);
    	Graph g = new Graph(map);
    	System.out.println(g.isTraversable());
    }
}