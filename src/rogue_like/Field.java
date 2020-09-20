package rogue_like;

import java.util.Random;
import java.util.Scanner;

public class Field {
	Random rand = new Random();
	Scanner sc = new Scanner(System.in);
    private Map map;// 
    private Player player = null;// 0:길, 1:벽, 2:몬스터, 3:휴식처, 4:상점, 8:시작, 9:끝

    public Field(Map map, Player player){
        //맵에 대한 입력이 들어오면 맵을 만듬.
        this.map = map;
        assignMonster();
        assignSafeHouse();
        assignStore();
        //추가적으로 여기서 맵 세팅이 끝나야만 함.
        //(여기서 assignXXX 이 일어나야 함)

        this.player = player;

    }

    public void move(String inputString){
        //필드에서 움직이는 메소드.
        //여기서 meetXXX이 일어나야 함.
    	
    	//첫글자와 두번째글자를 읽어서 글자인식
    	char way = inputString.charAt(0);
    	int len = Character.getNumericValue(inputString.charAt(1));
    	
    	//문자 인식후 플레이어 위치 변경
    	
    	switch(way) {
    		case '상':
    			map.map_y -= len;
    			break;
    		case '하':
    			map.map_y += len;
    			break;
    		case '좌':
    			map.map_x -= len;
    			break;
    		case '우':
    			map.map_x += len;
    			break;    		
    	}
    	player.move(Character.toString(way), len);
    	
    	// 숫자 인식후 메소드 호출
		/*
		 * if(map.map[map.map_x][map.map_y] == 0) {
		 * 
		 * } // 길
		 * 
		 * if(map.map[map.map_x][map.map_y] == 1) {
		 * 
		 * } //벽
		 */
    	if(map.map[map.map_x][map.map_y] == 2) {
    		meetSafeHouse();
    	}
    	//휴식처
    	
    	if(map.map[map.map_x][map.map_y] == 3) {
    		meetMonster();
    	}
    	//몬스터
    	
    	if(map.map[map.map_x][map.map_y] == 4) {
    		meetStore();
    	}   	
    	//상점
    	
		/*
		 * if(map.map[map.map_x][map.map_y] == 8) {
		 * 
		 * } //시작
		 */    	
    	if(map.map[map.map_x][map.map_y] == 3) {
    		
    	}
    	//끝

    }
    // 0:길, 1:벽, 2:몬스터, 3:휴식처, 4:상점,  8:시작, 9:끝
    private void assignMonster(){
        //몬스터를 맵에 배치하는 메소드
    	// 길중에 랜덤으로 찾아서 몬스터를 배치 나중에 수에 상수 넣는거 가능
    	int monsterNum = rand.nextInt(5);
    	for(int i = 0; i < monsterNum;) {
    		int randNum1 = rand.nextInt(map.map.length);
    		int randNum2 = rand.nextInt(map.map[0].length);
    		if(map.map[randNum1][randNum2] == 0) {
    			map.map[randNum1][randNum2] = 2;
    			++i;
    		} else {
    			continue;
    		}
    	}    	
    }

    private void assignSafeHouse(){
        //휴식처를 맵에 배치하는 메소드
    	// 길중에 랜덤으로 찾아서 휴식처 배치
    	int monsterNum = rand.nextInt(3);
    	for(int i = 0; i < monsterNum;) {
    		int randNum1 = rand.nextInt(map.map.length);
    		int randNum2 = rand.nextInt(map.map[0].length);
    		if(map.map[randNum1][randNum2] == 0) {
    			map.map[randNum1][randNum2] = 3;
    			++i;
    		} else {
    			continue;
    		}
    	} 
    }

    private void assignStore(){
        //상점을 맵에 배치하는 메소드
    	// 길중에 랜덤으로 찾아서 상점 배치
    	int monsterNum = rand.nextInt(3);
    	for(int i = 0; i < monsterNum;) {
    		int randNum1 = rand.nextInt(map.map.length);
    		int randNum2 = rand.nextInt(map.map[0].length);
    		if(map.map[randNum1][randNum2] == 0) {
    			map.map[randNum1][randNum2] = 4;
    			++i;
    		} else {
    			continue;
    		}
    	} 
    }

    public void showField(){
        //필드의 정보를 보여주는 메소드
    	for(int i = 0; i < map.map.length; i++) {
    		for(int j = 0; j < map.map[i].length; j++) {
    			System.out.print(map.map[i][j]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    	
    }

    public void meetMonster(){
        //몬스터를 만났을 때 전투를 하는 메소드
    	
    	//몬스터 객체 새로 생성함
    	Monster monster = new Monster(player);
    	System.out.println(player.name + "이/가 " + monster.name + "을 만났다!");
    	while(!(player.HP == 0 || monster.HP == 0)) {
        	player.attack(player);//Life에 일단은 player를 넣었음
        	monster.attack(monster);
    	}

    }

    public void meetSafeHouse(){
        //안식처를 만났을 때 휴식을 하는 메소드
    	int num = 0;
    	System.out.println(player.name + "이 휴식처에 도착했다.");
    	System.out.println();
    	while(!(num == 2)) {
    		System.out.println("안식처에서 할 일을 선택하십시오.");
        	System.out.println("1. 휴식을 취한다(20초)");
        	System.out.println("2. 이동한다 ");
        	num = sc.nextInt();
        	
        	if(num == 1) {
        		System.out.println("20초간 휴식을 취합니다.");
        		try{
        			Thread.sleep(20000);
        		} catch(InterruptedException e) {
        			e.printStackTrace();
        		}
        		
        		System.out.println("HP가 20만큼 회복되었습니다.");
        		player.HP += 20;
        		System.out.println("현재" + player.name + "의" + "HP:" + player.getHP());
        	}

        	if(num == 2) {
        		System.out.println(player.name + "이/가 휴식을 끝내고 이동을 시작합니다.");        		
        	}
    	}
    	//루프를 빠져나오면 다시 move메소드를 실행하도록 함
    	System.out.println("이동하고 싶은 방향과 길이를 입력하십시오.");
    	String way = sc.next();
    	move(way);
    }

    public void meetStore(){
        //상점을 만났을 때 구매하는 메소드
    	System.out.println(player.name + "이 상점에 도착했다.");
    	System.out.println();
    	System.out.println("1. 체력포션(50)");
    	System.out.println("2. 공격력 강화");
    	System.out.println("3. 방어력 강화");
    	System.out.println("4. 쉴드 강화");
    	System.out.println("5. 나가기");
    	System.out.println("하고 싶은 일을 선택하십시오. ");
    	System.out.println("선택 :");
    	int num = sc.nextInt();
    	
    	//일단 숫자는 그냥 넣어둠 후에 수정
    	while(num != 5) {
    		
    	}
    	switch(num) {
    		case 1:    			
    			if(player.HP < player.maxHP - 50) {
    				System.out.println("체력포션을 구매해 체력을 50만큼 회복합니다.");
        			player.HP += 50;
        			System.out.println("현재 체력은 " + player.HP + " 입니다.");

    			} else {
    				System.out.println("체력을 " + (player.maxHP - player.HP) + "만큼 회복합니다.");
    				player.HP = player.maxHP;
    				System.out.println("현재 체력은 " + player.HP + " 입니다.");
    			}
    			break;
    			
    		case 2:
    			System.out.println(player.name + "의 공격력을 강화합니다.");    		
    			player.ATK += 2;
    			break;
    			
    		case 3:
    			System.out.println(player.name + "의 방어력을 강화합니다.");
    			player.DEF += 2;
    			break;
    			
    		case 4:
    			if(player.shield < player.maxShield) {
    				System.out.println(player.name + "의 쉴드를 강화합니다.");
        			player.shield += 1;        			
    			} else {
    				System.out.println("더이상 쉴드를 업그레이드 할 수 없습니다.");
    			}
    			break;
    			
    		case 5:
    			System.out.println("상점을 나갑니다.");
    			break;
    	}
    	
    			
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
