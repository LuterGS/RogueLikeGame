package rogue_like;

import java.io.File;

public class Numbers {

    //상수 선언
    //ex) public static int monsterNum = 5;
	
	public static String DOT = ".";
	public static String MAP_FILES_ROUTE = "maps" + File.separator; //맵 저장하는 maps라는 폴더 위치 경로
	public static String MAP_CONTENTS_DELIM = "/"; //맵 파일을 읽을 시에 구분할 구분자
	public static String FIRST_LINE_REGEX = "^([\\d]+)/([\\d]+)/([\\d]+)/([\\d]+)$"; //파일 첫번 째 줄이 맞는 형식인지 확인해주는 정규식
	
	//맵 구성 요소 Constant
	public static int START = 8;
	public static int END = 9;
	public static int PATH = 0;
	public static int WALL = 1;
	public static int MONSTER = 2;
	public static int SAFEHOUSE = 3;
	public static int STORE = 4;
	
	//이 비율은 게임 벨런스 및 컨턴츠 내용 조정에서 확인하고 수정해야될 사항
	//임시로 지정한 것이므로 마음대로 바꿔서 확인...
	public static double MAX_MONSTER_TO_PATH = 0.2;
	public static double MAX_SAFEHOUSE_TO_PATH = 0.2;
	public static double MAX_STORE_TO_PATH = 0.1;
	public static double MIN_MONSTER_TO_PATH = 0.1;
	public static double MIN_SAFEHOUSE_TO_PATH = 0.1;
	public static double MIN_STORE_TO_PATH = 0.05;
}
