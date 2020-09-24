package rogue_like;

public class Map {

    //맵에 관련된 로직을 처리하는 클래스. 맵 렌더링 후 전체값을 게임에 전달하는 부분 위주
    int [][] map;
    //Edited by 이관석, 2020.09.20 17:00
    //-> 플레이어 위치를 나타내는 map_x, map_y를 Player 클래스 내에 넣음
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
        mapName = fileName.split(Numbers.DOT_TXT)[0];// 임시로 이렇게 설정해뒀지만, 여기서 mapName은 텍스트 파일의 이름을 가지도록 설정해야 함
        randomNum = -1;              // 랜덤한 이벤트가 일어날 장소의 개수
        errorMessage = "";          // 만약 맵 체크 시 오류가 발생하면 여기에 어떤 오류가 발생했는지 넣어줌 (이건 막 생각한거긴 함)
    }
    
    /*
    public static void main(String[] args) {
    	String[][] dummy_input = {{"1", "9", "1", "1", "1"}, // 0:길, 1:벽, 8:시작, 9:끝
    							  {"1", "0", "1", "1", "1"},
    							  {"1", "0", "0", "1", "1"},
    							  {"1", "1", "0", "0", "1"},
    							  {"1", "1", "1", "8", "1"}};
    	Map map = new Map("1234");
    	map.validate(dummy_input);
    
    	for(int i = 0; i < map.map.length; i++) {
    		for(int j = 0; j < map.map[i].length; j++) {
    			System.out.print(map.map[i][j]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    }
 */
}