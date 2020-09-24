package rogue_like;

import java.io.File;

public class Numbers {

    //상수 선언
    //ex) public static int monsterNum = 5;
	
	public static final String DOT_TXT = ".txt";
	public static final String MAP_FILES_ROUTE = "maps" + File.separator; //맵 저장하는 maps라는 폴더 위치 경로
	public static final String MAP_CONTENTS_DELIM = "/"; //맵 파일을 읽을 시에 구분할 구분자
	public static final String FIRST_LINE_REGEX = "^([\\d]+)/([\\d]+)/([\\d]+)/([\\d]+)$"; //파일 첫번 째 줄이 맞는 형식인지 확인해주는 정규식
	public static final String MAP_LINE_REGEX = "^[" + Numbers.PATH + "," + Numbers.WALL 
			+ "," + Numbers.START + "," + Numbers.END + "]+$"; //맵 한줄 정규식
	public static final String MOVE_REGEX_1 = "^([\\s]*)(["+Numbers.UP+","+Numbers.DOWN+","+
			Numbers.LEFT+","+Numbers.RIGHT+"])([\\s]+)([\\d]+)([\\s]*)$";
	
	public static final String MOVE_REGEX_2 = "^([\\s]*)([\\d]+)([\\s]+)(["+Numbers.UP+","+
	Numbers.DOWN+","+Numbers.LEFT+","+Numbers.RIGHT+"])([\\s]*)$";
	
	public static final String EMPTY_SPACE = " ";
	
	//김우열 - 한글에 문제있을 경우나 방향에 대한 스트링 변경시에는 이 상수만 변경하면 됨
	public static final String LEFT = "좌"; //left
	public static final String RIGHT = "우"; //or right
	public static final String UP = "상"; //or up
	public static final String DOWN = "하";  //or down
	
	//맵 구성 요소 Constant
	public static final int START = 8;
	public static final int END = 9;
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int MONSTER = 2;
	public static final int SAFEHOUSE = 3;
	public static final int STORE = 4;
	
	//이 비율은 게임 벨런스 및 컨턴츠 내용 조정에서 확인하고 수정해야될 사항
	//임시로 지정한 것이므로 마음대로 바꿔서 확인...
	public static final double MAX_MONSTER_TO_PATH = 0.2;
	public static final double MAX_SAFEHOUSE_TO_PATH = 0.2;
	public static final double MAX_STORE_TO_PATH = 0.1;
	public static final double MIN_MONSTER_TO_PATH = 0.1;
	public static final double MIN_SAFEHOUSE_TO_PATH = 0.1;
	public static final double MIN_STORE_TO_PATH = 0.05;

    public static final int Way = 0;
    public static final int Wall = 1;
    public static final int Monster = 2;
    public static final int SafeHouse = 3;
    public static final int Store = 4;
    public static final int StartPoint = 8;
    public static final int EndPoint = 9;

}
