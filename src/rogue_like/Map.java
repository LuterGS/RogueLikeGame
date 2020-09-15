package rogue_like;

import java.io.File;


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
    
    public Map(String fileName) {	//동근: Map이 invalid하다고 가정하고 변수 초기화 (어차피 valid하면 나중에 올바른 값으로 바꾸기 땜에)
    	map = new int[0][0];        // 여기엔 맵의 2차원 정보가 들어옴
        monsterNum = -1;
        safehouseNum = -1;
        storeNum = -1;
        isValid = false;             // 이거도 valid할때만 true를 return 하도록 해야함.
        mapName = fileName;         // 임시로 이렇게 설정해뒀지만, 여기서 mapName은 텍스트 파일의 이름을 가지도록 설정해야 함
        randomNum = -1;              // 랜덤한 이벤트가 일어날 장소의 개수
        errorMessage = "";          // 만약 맵 체크 시 오류가 발생하면 여기에 어떤 오류가 발생했는지 넣어줌 (이건 막 생각한거긴 함)
    }

    public static Map[] loadMaps(){

        String[] map_list = new String[]{"snow", "jungle", "castle"};   //이것도 특정 로직을 통해 특정 폴더에 있는 맵의 종류를 받아와야 함
        Map[] maps = new Map[map_list.length];

        for(int i = 0; i < maps.length; i++){
            maps[i] = Map.loadMap(map_list[i]);
        }
        return maps;
    }

    public static Map loadMap(String fileName){

        File mapFile = new File(fileName);      //맵을 읽어옴

        /*
        여기서 맵에 대한 로직을 처리해 맵의 정보를 가져옴. 만약 맵이 잘못된 경우 isValid = false;
        -> 나중에 게임 화면에서 해당 맵도 load는 하되 선택할 수 없게 하고, 이유를 출력하도록 해야 함
        ---> 어떻게 잘못된 파일인걸 판단하냐고? 여기서 false로 출력되면 바로 얄짤없이 버리는거로 함.

        난이도는 나중에 게임에서 설정 가능하도록
         */
        String[][] input = {{}}; //우열님께서 데이터를 넣어주시면 됩니다
        Map map = new Map(fileName);
        map.validate(input);
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
        else 
        	map = intCast(stringMap);
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
