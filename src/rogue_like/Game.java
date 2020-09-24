package rogue_like;

public class Game {
	private MapHandler handler;

    private Map[] maps;
    private Player player;
    private Field field;

    public Game(){
        // 게임 인트로 메뉴
        gameIntro();
        System.out.println("Game start!");

        // 맵 선택
        Map gameMap = chooseMap();

        // 플레이어 생성
        this.player = Player.makeOne();
        this.field = new Field(gameMap, this.player);

        // 게임 플레이
        playGame(this.field);
    }

    private void gameIntro(){

        System.out.println("게임에 오신 것을 환영합니다!");
        System.out.println("게임을 시작하려면 1번을, 게임을 끝내려면 2번을 눌러주세요!");
        int value = Checker.getInt(1, 2, "1이나 2 중 하나의 값을 골라주세요: ");
        if(value == 2){
            System.out.println("게임을 종료합니다.");
            System.exit(0);
        }

    }

    private Map chooseMap(){
    	//map과 maphandler를 구분함에 따라 구문을 살짝 변경했습니다.
    	
        //maps = Map.loadMaps(); //기존 구문
    	handler = new MapHandler(); //호출과 동시에 맵 로드
    	this.maps = handler.getMaps();
    	
        for(int i = 0; i < maps.length; i++){
            if(!maps[i].isValid){
                System.out.println(maps[i].mapName + " 은 열 수 없습니다 (잘못된 형식의 맵입니다)");
            }else{
                System.out.println(Integer.toString(i+1) + ". 맵 이름 : " + maps[i].mapName +
                                "  몬스터 수 : " + Integer.toString(maps[i].monsterNum) +
                                "    안식처 수 : " + Integer.toString(maps[i].safehouseNum) +
                                "   상점 수 : " + Integer.toString(maps[i].storeNum)
                );
            }
        }
        int value = Checker.getMapNum(maps, "맵 번호를 골라주세요.");

        return maps[value];
    }

    private void playGame(Field field){

        //필드 생성한거 토대로 게임함.
        //필드 안에 있는 로직을 돌아가게 할것인지 or 여기서 게임을 돌아게게 할 것인지.
    }
}