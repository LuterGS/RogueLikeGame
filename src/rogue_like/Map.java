package rogue_like;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;



public class Map {

    //맵에 관련된 로직을 처리하는 클래스. 맵 렌더링 후 전체값을 게임에 전달하는 부분 위주
    int [][] map;
    int monsterNum;
    int safehouseNum;
    int storeNum;
    int randomNum;
    String mapName;
    String errorMessage = "";
    boolean isValid;
    
    
    //mapName 저장시에 .txt 뺴고 저장 하도록 변경하였음
    public Map(String fileName) {	//동근: Map이 invalid하다고 가정하고 변수 초기화 (어차피 valid하면 나중에 올바른 값으로 바꾸기 땜에)
    	map = new int[0][0];        // 여기엔 맵의 2차원 정보가 들어옴
        monsterNum = -1;
        safehouseNum = -1;
        storeNum = -1;
        isValid = false;             // 이거도 valid할때만 true를 return 하도록 해야함.
        mapName = fileName.split(Numbers.DOT)[0];// 임시로 이렇게 설정해뒀지만, 여기서 mapName은 텍스트 파일의 이름을 가지도록 설정해야 함
        randomNum = -1;              // 랜덤한 이벤트가 일어날 장소의 개수
        errorMessage = "";          // 만약 맵 체크 시 오류가 발생하면 여기에 어떤 오류가 발생했는지 넣어줌 (이건 막 생각한거긴 함)
    }

    public static Map[] loadMaps(){
    	File mapRoute = new File(Numbers.MAP_FILES_ROUTE);
    	
    	//폴더가 존재 하지 않으면 만들기
    	if (!(mapRoute.isDirectory())) {
    		System.out.println(mapRoute.getName() + " 폴더가 만들어 졌습니다.");//<-----임시 확인용 구문
    		mapRoute.mkdir();
    	}
    	
    	//maps 폴더에 있는 파일들을 읽어서 files에 저장 후 이름만 가져와 map_list에 저장
    	File[] files = mapRoute.listFiles();
    	String[] map_list = new String[files.length];
    	for (int i=0; i<files.length; i++) {
    		map_list[i] = files[i].getName();
    	}
    	
        //String[] map_list = new String[]{"snow", "jungle", "castle"};   //이것도 특정 로직을 통해 특정 폴더에 있는 맵의 종류를 받아와야 함
        Map[] maps = new Map[map_list.length];

        for(int i = 0; i < maps.length; i++){
            maps[i] = Map.loadMap(map_list[i]);
        }
        return maps;
    }

    /*
     * @param fileName: 파일 이름 (오직 파일 이름만 있음. 루트 따로 지정 필요)
     * 인자로 받은 fileName에 대해서 파일을 읽은 후 유효성을 검사 후에 map 반환
     * 유효하지 않으면 isValid에 대해 default value인 false 값을 가지고 있는 map 바로 반환
     */
    public static Map loadMap(String fileName){
    	Map map = new Map(fileName); //임시로 객체 생성 내용 변경 후 반환

        File mapFile = new File(Numbers.MAP_FILES_ROUTE + fileName);      //맵을 읽어옴 *fileName에 결로를 추가하였음

        //이 구문이 여기서 필요한지는 모르지만 일단 한번 더 파일 있는지 확인해주는 구문
        if (!mapFile.exists()) {
        	System.out.println(fileName + "이 존재하지 않습니다. 건너뜁니다...");
        	//존재하지 않을 시에 isValid에 대해 default value인 false 값을 가지고 있는 map 바로 반환
        	return map;
        }
        
        BufferedReader br = null;
        String[][] input = null; //맵 내용 저장
        //유효성 검사를 위한 임시 변수
        try {
        	//UTF-8로 읽는 형식 통일 
        	br = new BufferedReader(new InputStreamReader(
					new FileInputStream(mapFile), Charset.forName("UTF8")));
        	
        	String line = ""; //한 줄 읽어서 저장할 변수
        	StringTokenizer st = null; //첫줄을 읽고 분해 위한 객체
        	
        	/*
             * 첫 줄 : (monsterNum)/(safehouseNum)/(storeNum)/(randomNum)
             * 첫 줄을 읽고 나서 StringTokenizer를 이용해 각 변수에 저장
             * */
        	
        	line = br.readLine().trim();
        	//첫번 째 줄 형식 맞는지 확인
        	if (!line.matches(Numbers.FIRST_LINE_REGEX)) {
        		System.out.println("파일 내용 오류 - 1st line : " + line);
        		return map;
        	}
        	st = new StringTokenizer(line, Numbers.MAP_CONTENTS_DELIM);
        	
        	//토큰 순서대로 대입
        	map.monsterNum = Integer.parseInt(st.nextToken().trim());
        	map.safehouseNum = Integer.parseInt(st.nextToken().trim());
        	map.storeNum = Integer.parseInt(st.nextToken().trim());
        	map.randomNum = Integer.parseInt(st.nextToken().trim());
        	
        	/*
        	 * 두번째 줄 ~ : 맵의 내용을 읽기
        	 * 다음 라인이 없을 때 까지 계속 읽음
        	 * */
        	ArrayList<String> temp_map = new ArrayList<>();
        	while ((line = br.readLine()) != null) {
        		temp_map.add(line);
        		
        	}
        	
        	//첫줄의 글자 수 만큼의 column을 가진 배열 생성
        	input = new String[temp_map.size()][temp_map.get(0).length()];
        	
        	/*
        	 * 파일에 있는 맵이 직사각형이 아닐 때
        	 * 이 부분에서  ArrayIndexOutOfBoundsException 발생 할 수 있음
        	 * (물론 반대로 길이가 부족해서 0으로 채워 질 수도 있음)
        	 * 이 부분은 후에 isRect 함수와 동일하게 적용 될 수 있음...
        	 * 2번 확인하는 것이 안된다면 파일을 다르게 읽는 방법을 찾아야됨
        	 */
        	//input에 대입
        	for (int i=0; i<input.length; i++) {
        		for (int j=0; j<input[i].length; j++) {
        			input[i][j] = Character.toString(temp_map.get(i).charAt(j));
        		}
        	}
        }
        catch (Exception e) {
        	//오류 발생 시에 오류 출력 후 null값 반환 
        	System.out.println(e.getMessage()); //<-------- 오류 확인 위한 임시 구문
        	return map;
        } finally {
        	if (br != null) {
        		try {
        			br.close();
        		}
        		catch (IOException e) {
        		}
        	}
        }
        
        /*
        여기서 맵에 대한 로직을 처리해 맵의 정보를 가져옴. 만약 맵이 잘못된 경우 isValid = false;
        -> 나중에 게임 화면에서 해당 맵도 load는 하되 선택할 수 없게 하고, 이유를 출력하도록 해야 함
        ---> 어떻게 잘못된 파일인걸 판단하냐고? 여기서 false로 출력되면 바로 얄짤없이 버리는거로 함.
        난이도는 나중에 게임에서 설정 가능하도록
         */
        
        //유효성 검사
        map.validate(input);
        
        //검사 통과 후 맵에 대입
        map.map = intCast(input);
        
        /*
         * 맵이 완전히 유효 (시작과 끝이 있는 맵인지, entity들이 지정된 비율 내에 있는지...등등)한지
         * 확인 후에 맵의 PATH에 monster, store, safehouse를 배치
         */
        //setEntity(map);
        
        
        return map;
    }

	public void validate(String[][] stringMap) {
    	if (!isRect(stringMap)) {
        	errorMessage = "맵이 사각형꼴이 아닙니다";
        	System.out.println(errorMessage);
    	}
        else if(!isSingleDigitInt(stringMap)) {
        	errorMessage = "맵에는 0~9까지 숫자만 올 수 있습니다";
        	System.out.println(errorMessage);
        }
        //else 
        	//위에 map.map = intCast(input)구문을 저기다가 넣는게 좋을 거 같아서 주석처리 했습니다.
        	//map = intCast(stringMap);
    }

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
    
    public static int[][] intCast(String[][] stringMap) {
    	int[][] intMap = new int[stringMap.length][stringMap[0].length];
    	for(int i = 0; i < stringMap.length; i++) {
    		for(int j = 0; j < stringMap[i].length; j++) {
    			intMap[i][j] = Integer.parseInt(stringMap[i][j]); 
    		}
    	}
    	return intMap;
    }
    
    public boolean isReachable(int[][] intMap){

        //astar나 bfs등의 알고리즘을 이용해 맵이 종착지까지 갈 수 있는지 탐색 후, 갈 수 있으면 true, 아니면 false return
        return true;
    }
    
    /*
    public static void main(String[] args) {
    	String[][] dummy_input = {{"1", "9", "1", "1", "1"}, // 0:길, 1:벽, 8:시작, 9:끝
    							  {"1", "0", "1", "1", "1"},
    							  {"1", "0", "0", "1", "1"},
    							  {"1", "1", "0", "0", "1"},
    							  {"1", "1", "1", "8", "1"}};
    	Map map = new Map("test_dummy");
    	map = validate(map, dummy_input);
    
    	for(int i = 0; i < map.map.length; i++) {
    		for(int j = 0; j < map.map[i].length; j++) {
    			System.out.print(map.map[i][j]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    }*/
 
}
