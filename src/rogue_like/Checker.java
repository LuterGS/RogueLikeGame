package rogue_like;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Checker {

    public static Scanner scan = new Scanner(System.in);

    public static int getInt(int min, int max, String printInfo){

        int value = 0;
        System.out.print(printInfo);

        while(true){
            try {
                value = scan.nextInt();
            }catch(InputMismatchException e){
                value = 0;
            }finally{
                scan.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }else {
                    System.out.print(printInfo);
                }
            }
        }
    }

    public static int getMapNum(Map[] maps, String printInfo){

        int value = 0;
        System.out.print(printInfo);

        while(true){
            try {
                value = scan.nextInt();
            }catch (InputMismatchException e){
                value = 0;
            }finally{
                scan.nextLine();
                if (value >= 1 && value <= maps.length && maps[value - 1].isValid) {
                    return value - 1;
                }else {
                    System.out.print(printInfo);
                }
            }
        }
    }

    //checkers for MapHandler
    public static boolean isRect(String[][] stringMap) {
    	int row_len = stringMap[0].length;
    	for(int i = 1; i < stringMap.length; i++) {
    		if(stringMap[i].length != row_len) return false;
    	}
    	return true;
    }
    
    public static boolean isSingleDigitInt(String[][] stringMap) {
    	for(int i = 0; i < stringMap.length; i++) {
    		for(int j = 0; j < stringMap[i].length; j++) {
    			int tmp;
    			try {
    				tmp = Integer.parseInt(stringMap[i][j]); 
    			}
    			catch (NumberFormatException e){
    				return false;
    			}
    			if (tmp < 0 || tmp >9) return false;
    		}
    	}
    	return true;
    }
    
    public static boolean no0sInEdge(int[][] map) {
    	int row_len = map.length;
    	int col_len = map[0].length;
    	
    	for(int i = 0; i < row_len; i++) {
    		if(map[0][i] == 0 || map[col_len-1][i] == 0)
    			return false;
    	}
    	
    	for(int i = 1; i < col_len - 1; i++) {
    		if(map[i][0] == 0 || map[i][row_len-1] == 0)
    			return false;
    	}
    	return true;
    }
    
    public static boolean isReachable(int[][] map){//BFS
    	Graph g = new Graph(map);
    	if(g.isTraversable())
    		return true;
    	else return false;
    }
}
