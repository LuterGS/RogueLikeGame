package rogue_like;

import java.io.File;

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


    public static Map[] loadMaps(){

        String[] map_list = new String[]{"snow", "jungle", "castle"};   //이것도 특정 로직을 통해 특정 폴더에 있는 맵의 종류를 받아와야 함
        Map[] maps = new Map[map_list.length];

        for(int i = 0; i < maps.length; i++){
            maps[i] = Map.loadMap(map_list[i]);
        }
        return maps;
    }

    public static Map loadMap(String fileName){

        Map map = new Map();
        File mapFile = new File(fileName);      //맵을 읽어옴

        /*
        여기서 맵에 대한 로직을 처리해 맵의 정보를 가져옴. 만약 맵이 잘못된 경우 isValid = false;
        -> 나중에 게임 화면에서 해당 맵도 load는 하되 선택할 수 없게 하고, 이유를 출력하도록 해야 함
        ---> 어떻게 잘못된 파일인걸 판단하냐고? 여기서 false로 출력되면 바로 얄짤없이 버리는거로 함.

        난이도는 나중에 게임에서 설정 가능하도록
         */

        map.map = new int[3][3];        // 여기엔 맵의 2차원 정보가 들어옴
        map.monsterNum = 3;
        map.safehouseNum = 3;
        map.storeNum = 2;
        map.isValid = true;             // 이거도 valid할때만 true를 return 하도록 해야함.
        map.mapName = fileName;         // 임시로 이렇게 설정해뒀지만, 여기서 mapName은 텍스트 파일의 이름을 가지도록 설정해야 함
        map.randomNum = 3;              // 랜덤한 이벤트가 일어날 장소의 개수
        map.errorMessage = "";          // 만약 맵 체크 시 오류가 발생하면 여기에 어떤 오류가 발생했는지 넣어줌 (이건 막 생각한거긴 함)

        return map;
    }

    public boolean isReachable(int[][] intMap){

        //astar나 bfs등의 알고리즘을 이용해 맵이 종착지까지 갈 수 있는지 탐색 후, 갈 수 있으면 true, 아니면 false return
        return true;
    }
}
