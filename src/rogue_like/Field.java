package rogue_like;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Field {
	Checker checker = new Checker();
	Random rand = new Random();
	Scanner sc = new Scanner(System.in);
    private Map map;// 
    private Player player;// 0:길, 1:벽, 2:몬스터, 3:휴식처, 4:상점, 8:시작, 9:끝

    public Field(Map map, Player player){
        //맵에 대한 입력이 들어오면 맵을 만듬.
        this.map = map;
        assignField(map.monsterNum, Numbers.Monster);
        assignField(map.safehouseNum, Numbers.SafeHouse);
        assignField(map.storeNum, Numbers.Store);
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
    	int[] player_loc;
    	
    	//문자 인식후 플레이어 위치 변경
		//Edited by 이관석, 2020.09.20 17:02
		//-> switch문으로 플레이어 위치조정하는부분 변경 (간단히 플레이어의 정보값만 바꾸도록 함)
		//-> 여기에 벽에 부딪혔을 때, 이벤트에 조우했을 때에 대한 예외처리가 이루어져야 함
		
    	// 필드의 상황을 알기위해서 플레이어의 위치를 temp를 만들어서 임시로 받아서 map의 값과 비교하여 이벤트 확인 
    	int[] playerTemp_loc = player.getLocation();
	    	for(int i = 0 ; i < len; i++) {
	    		switch (way) {
	            case '상':
	            	playerTemp_loc[0] += len;
	                break;
	            case '하':
	            	playerTemp_loc[0] -= len;
	                break;
	            case '좌':
	            	playerTemp_loc[1] -= len;
	                break;
	            case '우':
	            	playerTemp_loc[1] += len;
	    		}
	    		// 플레이어가 이벤트를 만나면 for문을 빠져나온후 아래 메소드 실행
	    		if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] != 0) {
	    			meetRandom(playerTemp_loc, len, i);//맵에 길이 무엇이 있는지 확인하고 출력해주는 메소드
	    			break;
	    		}	    		
	        }	    		    	
    	
		//1. 입력받은 값이 갈 수 있는지 조회
		
		//2. 갈 수 있으면, 그대로 이동
		//   갈 수 없으면, 특정 이벤트의 위치까지 이동하도록 len을 재조정
		//   이후 왜 갈 수 없는지 출력 후
		
		//3. player.move를 실행하면 됨
    	player.move(Character.toString(way), len);
    	player_loc = player.getLocation();
    	
    	//4. 만약 갈 수 없는 이유가 이벤트를 만나서라면 그 이벤트를 여기서 실행
		if(map.map[player_loc[0]][player_loc[1]] == Numbers.SafeHouse){
			meetSafeHouse();
		}else if(map.map[player_loc[0]][player_loc[1]] == Numbers.Monster){
			meetMonster();
		}else if(map.map[player_loc[0]][player_loc[1]] == Numbers.Store){
			meetStore();
		}else if(map.map[player_loc[0]][player_loc[1]] == Numbers.EndPoint){
			finalBoss();
		}
    }
 

    // 기존에 주석으로 정의했던 부분을 Numbers 클래스에 상수로 선언
	// 기존에 나누어져있던 3개의 메소드를 하나로 합침
    private void assignField(int targetNum, int target){
    	for(int i = 0; i < targetNum;) {			//이관석 -> 아주아주 좋은 반복문이라 생각합니다!
    		int randNum1 = rand.nextInt(map.map.length);
    		int randNum2 = rand.nextInt(map.map[0].length);
    		if(map.map[randNum1][randNum2] == 0) {
    			map.map[randNum1][randNum2] = target;
    			++i;
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

    	Monster[] monster;
    	int monsterNum, monstersHP = 0;

    	//몬스터 객체 새로 생성함
		//두 객체 이상의 몬스터가 나올수도 있기 때문에, 그 부분을 구현함
		double ratio = Math.random();
		if(ratio < 0.7) monsterNum = 1;
		else if(ratio < 0.9) monsterNum = 2;
		else monsterNum = 3;

		//몬스터를 생성함과 동시에 동시 출력
		monster = new Monster[monsterNum];
		System.out.printf("%s 이/가 ", player.getName());
		for(int i = 0; i < monsterNum; i++){
			monster[i] = new Monster(player);
			System.out.printf("%s	", monster[i].getName());
		}
		System.out.print("를 만났다!\n");

		//게임 전투문
		while(true){
			//플레이어가 공격할 몬스터와, 이용할 스킬을 선택하는 부분 (구현해야함)
			//Checker 등등을 이용해 수준높게 구현해야할 필요성이 있음
			//다른 메소드들을 만들어 불러오는것도 방법
			
			int attackMonster = 0;// 몇번째 몬스터를 공격할지 선택변수
			
			liveMonster(monster);//살아있는 몬스터의 번호와 이름 출력
			System.out.println("몇번째 몬스터를 공격할지 선택하십시오.");
			try {
				attackMonster = sc.nextInt();
				//몬스터를 선택했을때 죽었는지 확인하는 메소드
				checkAliveMonster(monster, attackMonster);
				
				player.attack(monster, attackMonster);
			} catch (InputMismatchException e) {
				attackMonster = 0;
				String printInfo = "숫자로 " + monster.length +  "이하로 입력해주세요.\n"; 
				attackMonster = checker.getInt(1, monster.length, printInfo);
				checkAliveMonster(monster, attackMonster);
				
				player.attack(monster, attackMonster);
				//System.out.printf("숫자로 %d 이하로 입력해주세요.\n", monster.length);
				//attackMonster = sc.nextInt();
			} 			
			monstersAttack(monster, player);//살아있는 몬스터가 순서대로 공격을 함
			
							
			if(player.getHP() <= 0.0){
				//게임 오버
				System.out.printf("플레이어 %d가 사망했습니다.\n", player.name);
				break;
			}

			if(/*몬스터들의 모든 HP가 0일 때*/aliveMonsters(monster) <= 0.0){
				System.out.printf("플레이어 %d가 모든 몬스터를 처리했습니다.\n", player.name);
				break;
			}
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
        		// player.HP += 20;  -> Player의 정보를 직접적으로 수정하는 코드는 지양해야 함.
				// getter, setter를 생성해서 수정하도록 변경함.
				player.heal(20.0);
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
    
    public void meetRandom(int[] playerTemp_loc, int len, int i){
        //랜덤한 장소를 만났을 때 상황을 출력하는 메소드, 이동거리값도 재조정
    	
    	if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] == Numbers.Wall){
    		System.out.println("벽에 막혀 더이상 이동할 수 없습니다.");
    		len = i;
   		}else if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] == Numbers.Monster){
   			System.out.println("몬스터를 만나 이동할 수 없습니다.");
   			len = i;
   		}else if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] == Numbers.SafeHouse){
   			System.out.println("휴식처를 발견했다! ");
   			len = i;
		}else if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] == Numbers.Store){
			System.out.println("상점을  발견했다! ");
			len = i;
    	}else if(map.map[playerTemp_loc[0]][playerTemp_loc[1]] == Numbers.EndPoint){
   			System.out.println("엔드 포인트에 도착했습니다. 축하합니다!");
    	}
    }
    
    public void liveMonster(Monster[] monsters) {
    	for(int i =0 ; i < monsters.length; i++) {
    		if(monsters[i].getHP() > 0) {
    			System.out.printf("%d번 %s 몬스터가 있습니다.\n", i, monsters[i].getName());
    		}
    	}
    }
    //몬스터가 살아있는 것이 있으면 1 없으면 0을 리턴
    public int aliveMonsters(Monster[] monsters) {
    	int aliveMonsters = 0;
    	for(int i = 0; i < monsters.length; i++) {
    		if(monsters[i].getHP() <= 0 ) {
    			aliveMonsters += 1;
    		}
    	}
    	if(aliveMonsters == 0) {
    		return 0;
    	} else {
    		return 1;
    	}
    }
    // 여러 몬스터가 공격을 할 때 살아있는 몬스터만 공격을 하도록 구현
    public void monstersAttack(Monster[] monsters, Player player) {
    	System.out.println("현재 존재하는 몬스터");
    	for(int i = 0; i <monsters.length ;i++) {
    		if(monsters[i].getHP() > 0) {
    			monsters[i].attack(player);
    		}
    	}
    }
    // 선택한 몬스터가 살아있는지 확인
    public void checkAliveMonster(Monster[] monster,int attackMonster) {
    	while(monster[attackMonster].getHP() <= 0){
			String info = "몬스터번호를" + monster.length + "이하 중 하나를 입력하세요.";
			System.out.println("현재 선택한 몬스터는 처리되었습니다.");
			System.out.println("다른 몬스터를 선택하여주세요.");
			attackMonster = checker.getInt(1, monster.length, info);
			
		}
	}
    
    public void finalBoss(){
        //출구에 도착했을 때 마지막 보스 만남
        // -> 설계상으론 자기 자신을 똑같이 만나면 괜찮을 것이라고 생각함
        // -> player 객체를 복사해서 만들면 될 듯
        
        // -> 이거 깨면 엔딩 나오면서 게임 종료
    }
}
