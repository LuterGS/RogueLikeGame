package rogue_like;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
    
    /*
     * @param: 사용자가 움직이기 위해 입력한 문자열
     * 입력한 문자열이 올바르면 return {direction, length}
     * 그렇지 않으면 에러 확인 후 return null
     */
    public static String[] moveCheck(String moveInput) {
    	String[] arr = new String[2];
    	String dir = null;
    	int len = 0;
    	
    	int way = 0;
    	
    	try {
    		//정규식 확인
        	if (moveInput.matches(Numbers.MOVE_REGEX_1)) {
        		way = 1;
        	}
        	else {
        		if (moveInput.matches(Numbers.MOVE_REGEX_2)) {
            		way = 2;
            	}
        		else {
        			//정규식 오류
        		return null;
        		}
        	}
        	
        	//앞 뒤 공백 자르기
        	moveInput = moveInput.trim();
        	
        	StringTokenizer st = new StringTokenizer(moveInput, Numbers.EMPTY_SPACE);
        	//앞에 먼저 방향이 오는 경우
        	if (way == 1) {
        		arr[0] = st.nextToken();
        		arr[1] = st.nextToken();
        	}
        	//앞에 먼저 숫자가 오는 경우
        	else {
        		arr[1] = st.nextToken();
        		arr[0] = st.nextToken();
        	}
    	} catch (Exception e) {
    		e.printStackTrace(); // for test
    		System.out.println(e.getMessage()); // for test
    		return null;
    	}
    	
		return arr;
    }
	
    public int skillChecker(String input, Player player) {
    	String tempInput = input;
    	Skill tempSkill = player.getSkill();//getSkill 메소드를 만들어서 tempSkill에 player에 스킬을 넣음
    	//for문을 통해 받은 문장에서 skill이 있는지 확인
    	for(int i = 0; i < tempSkill.skillName.length; i++) {
    		if(tempInput.indexOf(tempSkill.skillName[i]) >= 0) {
    			return i;// 여기서 문장에 스킬이 포함되어 있으면 인터페이스를 상속받는 직업의 skillName의 index번호를 return
    		}
    	}   	
    	System.out.println("스킬이 제대로 입력되지 않았습니다!");
    	return -1;//제대로 스킬이 입력되지 않았을때는 -1출력하게 했습니다.

    }
}
