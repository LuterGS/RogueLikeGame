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


    public static boolean isReachable(int[][] map){
        //astar나 dfs, bfs 알고리즘 이용해서 searching

        return false;
    }
}
