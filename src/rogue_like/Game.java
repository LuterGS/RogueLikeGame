package rogue_like;

import java.util.Scanner;

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
            if(!maps[i].getValid()){
                System.out.println(maps[i].getMapName() + " 은 열 수 없습니다 (잘못된 형식의 맵입니다)");
            }else{
                System.out.println(Integer.toString(i+1) + ". 맵 이름 : " + maps[i].getMapName() +
                                "  몬스터 수 : " + Integer.toString(maps[i].getMonsterNum()) +
                                "    안식처 수 : " + Integer.toString(maps[i].getSafehouseNum()) +
                                "   상점 수 : " + Integer.toString(maps[i].getStoreNum())
                );
            }
        }
        int value = Checker.getMapNum(maps, "맵 번호를 골라주세요.");

        return maps[value];
    }

    private void playGame(Field field){

        Scanner scan = new Scanner(System.in);
        int[] inputResult;
        while(true){
            System.out.print("\"맵 출력\"을 입력하면 맵을 출력하고, 방향+칸수 혹은 칸수+방향을 입력하면 이동합니다. 도움말을 보려면 \"도움말\"을 입력하세요. 값을 입력해 주세요 : ");
            inputResult = Checker.getFieldMoveInput(scan.nextLine());
            if(inputResult == null) {
                System.out.println("잘못된 입력입니다! 도움말을 보려면 \"도움말\"을 입력하세요");
            }else if(inputResult[0] == 0){
                field.showField();
            }else if(inputResult[0] == 1) {
            	Help.mapIcons();
            	Help.move();
            }
            else {
                field.showField();
                field.move(inputResult);
            }

            if(!field.isPlayable()){
                System.out.println("게임에서 패배했습니다! 게임을 종료합니다");
                break;
            }
        }
        //필드 생성한거 토대로 게임함.
        //필드 안에 있는 로직을 돌아가게 할것인지 or 여기서 게임을 돌아게게 할 것인지.
    }
}