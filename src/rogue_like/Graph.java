package rogue_like;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList; 
  

class Graph {   
	private int[][] map;
	private ArrayList<Coor> coors;
    private int v_count;
    private LinkedList<Coor> adj[];
    
    
    class Coor {//map에서 좌표 하나를 의미
    	int index, x, y; //index: linked list에서 index용
    	public Coor(int index, int x, int y) {
    		this.index = index;
    		this.x = x;
    		this.y = y;
    	}
    }
    
    Graph(int[][] m) {	
    	coors = new ArrayList<Coor>();
    	this.map = m;
    	setCoors();
        v_count = coors.size();
        adj = new LinkedList[v_count]; 
        for (int i=0; i<v_count; ++i) 
            adj[i] = new LinkedList<Coor>();
        seekEdges();
    }
    
    private void setCoors() {//map에서 이동 가능한 좌표 추출
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
    
    private void seekEdges() {//인접한 좌표끼리 edge 생성
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
  
    private void addEdge(Coor v, Coor w) { 	
    	int i = v.index;
        adj[i].add(w); 
        int j = w.index;
        adj[j].add(v);
    } 
  
    public boolean isTraversable() {//BFS 알고리즘	
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
    /*public static void main(String[] args) {
    	String[][] dummy_input = {{"1", "9", "1", "1", "1"}, // 0:길, 1:벽, 8:시작, 9:끝
				  				  {"1", "0", "1", "0", "0"},
				  				  {"1", "0", "0", "0", "0"},
				  				  {"1", "1", "1", "1", "0"},
				  				  {"1", "1", "1", "1", "8"}};
    	Map map = new Map("test_dummy");
    	map.validate(dummy_input);
    	Graph g = new Graph(map);
    	System.out.println(g.isTraversable());
    }*/
}