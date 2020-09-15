package rogue_like;

public class Field {

    private Map map;
    private Player player = null;

    public Field(Map map, Player player){
        //맵에 대한 입력이 들어오면 맵을 만듬.
        this.map = map;
        //추가적으로 여기서 맵 세팅이 끝나야만 함.
        //(여기서 assignXXX 이 일어나야 함)

        this.player = player;

    }

    public void move(String inputString){
        //필드에서 움직이는 메소드.
        //여기서 meetXXX이 일어나야 함.
    	char way = inputString.charAt(0);
    	int len = Character.getNumericValue(inputString.charAt(1));
    	
    	if(way == '상') {
    		map.map_y -= len;
    	}
    	if(way == '하') {
    		map.map_y += len;
    	}
    	if(way == '좌') {
    		map.map_x -= len;
    	}
    	if(way == '우') {
    		map.map_x += len;
    	}
    	
    	
    }

    private void assignMonster(){
        //몬스터를 맵에 배치하는 메소드
    }

    private void assignSafeHouse(){
        //휴식처를 맵에 배치하는 메소드
    }

    private void assignStore(){
        //상점을 맵에 배치하는 메소드
    }

    public void showField(){
        //필드의 정보를 보여주는 메소드
    }

    public void meetMonster(){
        //몬스터를 만났을 때 전투를 하는 메소드
    }

    public void meetSafeHouse(){
        //안식처를 만났을 때 휴식을 하는 메소드
    }

    public void meetStore(){
        //상점을 만났을 때 구매하는 메소드
    }
    
    public void meetRandom(){
        //랜덤한 장소를 만났을 때 행동하는 메소드
    }
    
    public void finalBoss(){
        //출구에 도착했을 때 마지막 보스 만남
        // -> 설계상으론 자기 자신을 똑같이 만나면 괜찮을 것이라고 생각함
        // -> player 객체를 복사해서 만들면 될 듯
        
        // -> 이거 깨면 엔딩 나오면서 게임 종료
    }
}
