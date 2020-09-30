package rogue_like;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Checker {

    private static final Scanner scan = new Scanner(System.in);
    
    public static String getInput() {
    	String input = "";
    	
    	//라인 단위로 받아서 처리
    	try {
    		input = scan.nextLine();
    	} catch (Exception e) {
    		return null;
    	} finally {
    		if (input.equals("종료")) {
	    		System.out.println("게임을 종료합니다.");
	    		System.exit(0);
	    	}
    	}
    	
//    	int int_input;
//    	try {
//    		int_input = scan.nextInt();
//    		input = String.valueOf(int_input);
//    	} catch(InputMismatchException e) {
//	    	input = scan.nextLine();
//    	} finally {
//    		if (input.equals("종료")) {
//	    		System.out.println("게임을 종료합니다.");
//	    		System.exit(0);
//	    	}
//    	}
    	return input;
    }

    public static int getInt(int min, int max, String printInfo){

        int value = 0;
        System.out.print(printInfo);

        while(true){
            try {
            	value = Integer.parseInt(getInput());
                //value = scan.nextInt();
            }catch(NumberFormatException e){
            	System.out.println("숫자가 아닌 값을 입력하셨습니다. 제대로 된 값을 입력해주세요.");
                value = 0;
            }finally{
                //scan.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }else {
                	System.out.println("잘못 입력하셨습니다. " + min + " ~ " + max + " 의  정수를 입력해주세요.");
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
                value = Integer.parseInt(getInput());
            }catch (NumberFormatException e){
                value = 0;
            }finally{
                //scan.nextLine();
                if (value >= 1 && value <= maps.length) {
                	if (!maps[value - 1].getValid())
                		System.out.println(maps[value - 1].getMapName() + " 맵은 열 수 없습니다. 다른 맵을 선택해주세요.");
                	else
                		return value - 1;
                }
                else 
                	System.out.println("잘못 입력하셨습니다. 1 ~ " + maps.length + " 의 정수를 입력해주세요.");
            }
            System.out.print(printInfo);
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
    		if(map[i][0] == 0 || map[i][col_len-1] == 0)
    			return false;
    	}
    	
    	for(int i = 1; i < col_len - 1; i++) {
    		if(map[0][i] == 0 || map[row_len-1][i] == 0)
    			return false;
    	}
    	return true;
    }
    
    public static boolean hasStartEnd(int[][] map) {
    	boolean start_flag = false;
    	boolean end_flag = false;
    	for(int i = 0; i < map.length; i++) {
    		for(int j = 0; j < map[0].length; j++) {
    			if(map[i][j] == Numbers.START)
    				start_flag = true;
    			else if(map[i][j] == Numbers.END)
    				end_flag = true;
    			if(start_flag && end_flag)
    				return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isReachable(int[][] map){//BFS
    	Graph g = new Graph(map);
    	if(g.isTraversable())
    		return true;
    	else return false;
    }
    
    public static boolean isSettable(int[][] map, Map tmap) {
    	//맵 내용이 아직 할당 안된 상태이므로 tmap을 이용할 수 없음!
    	int pathNum = map.length*map[0].length;
    	int entityNum = tmap.getSafehouseNum() + tmap.getStoreNum() + tmap.getMonsterNum();
    	if (pathNum <= entityNum) {
    		return false;
    	}
    	else
    		return true;
    }
    
    /*
     * @param: 사용자가 움직이기 위해 입력한 문자열
     * 입력한 문자열이 올바르면 return {direction, length}
     * 그렇지 않으면 에러 확인 후 return null
     */
    public static int[] moveCheck(String moveInput) {
    	int[] arr = new int[2];
    	
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
        		arr[0] = directionStrToInt(st.nextToken());
        		arr[1] = Integer.parseInt(st.nextToken());
        	}
        	//앞에 먼저 숫자가 오는 경우
        	else {
        		arr[1] = Integer.parseInt(st.nextToken());
        		arr[0] = directionStrToInt(st.nextToken());
        	}
    	} catch (Exception e) {
    		e.printStackTrace(); // for test
    		System.out.println(e.getMessage()); // for test
    		return null;
    	}
    	
		return arr;
    }

    /*
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
    }*/

    public static int[] getFieldMoveInput(String inputString){

//    	if(inputString.equals("맵 출력")){
//    		return new int[]{0, 0};
//		}
    	if(inputString.equals("도움말")) {
    		return new int[]{1, 0};
    	}
    	else if(inputString.equals("종료")) {
    		return new int[]{2, 0};
    	}
    	else return moveCheck(inputString);
	}

	public static int directionStrToInt(String direction){
    	if(direction.equals(Numbers.UP)) return Numbers.UP_INT;
    	if(direction.equals(Numbers.DOWN)) return Numbers.DOWN_INT;
    	if(direction.equals(Numbers.LEFT)) return Numbers.LEFT_INT;
    	if(direction.equals(Numbers.RIGHT)) return Numbers.RIGHT_INT;
    	else return Numbers.ERROR;
	}
}
