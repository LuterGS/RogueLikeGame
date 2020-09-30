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
        System.out.println("게임을 종료하려면 언제든지 \"종료\"를 입력하세요!");
        
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
    	//선택 할 수 있는 맵이 있는지 확인
    	boolean validFlag = false;
    	//map과 maphandler를 구분함에 따라 구문을 살짝 변경했습니다.
    	
        //maps = Map.loadMaps(); //기존 구문
    	handler = new MapHandler(); //호출과 동시에 맵 로드
    	this.maps = handler.getMaps();
    	System.out.println("\n-----------------------------------------------------------------------------------------------");
    	System.out.println("맵 목록");
        for(int i = 0; i < maps.length; i++){
            if(!maps[i].getValid()){
                System.out.println((i+1) + ". "+ maps[i].getMapName() + " 은 열 수 없습니다. " + maps[i].getErrorMessage());
            }else{
                System.out.println((i+1) + ". 맵 이름 : " + maps[i].getMapName() +
                                "  몬스터 수 : " + (maps[i].getMonsterNum()) +
                                "    안식처 수 : " + (maps[i].getSafehouseNum()) +
                                "   상점 수 : " + (maps[i].getStoreNum())
                );
                validFlag = true;
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------\n");
        //사용할 수 있는 맵이 없을 경우 종료
    	if (!validFlag) {
    		System.out.println("현재 저장된 맵이 없거나, 정상적으로 로드할 수 있는 맵 파일이 없습니다."); 
    		System.out.println("실행 파일의 위치에 있는 'maps' 폴더의 맵 파일을 확인해주세요.");
    		System.out.println("게임을 종료합니다...");
        	System.exit(0);
    	}
    	
        int value = Checker.getMapNum(maps, "맵 번호를 골라주세요: ");

        return maps[value];
    }

    private void playGame(Field field){

        int[] inputResult;
        field.showField();
        while(true){
            System.out.print("방향+칸수 혹은 칸수+방향을 입력하면 이동합니다. 도움말을 보려면 \"도움말\"을 입력하세요. 값을 입력해 주세요 : ");
            inputResult = Checker.getFieldMoveInput(Checker.getInput());
            if(inputResult == null) {
                System.out.println("잘못된 입력입니다! 도움말을 보려면 \"도움말\"을 입력하세요");
            }
            //else if(inputResult[0] == 0){
            //    field.showField();
            //}
        	else if(inputResult[0] == 1) {
            	Help.general();
            }else {
                if(field.move(inputResult)){
					System.out.println("마지막 보스는 사라졌습니다. 플레이해주셔서 감사합니다.");
					System.out.println("건국대학교 2020 전공기초프로젝트2 Text-Based Rogue-Like game 제작팀");
					System.out.println("고원재, 김우열, 윤동근, 이관석");
                    break;
                }
                field.showField();
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