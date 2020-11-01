package rogue_like;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Field {
	Random rand = new Random();
	//Scanner sc = new Scanner(System.in);
    private Map map;// 
    private Player player;// 0:길, 1:벽, 2:몬스터, 3:휴식처, 4:상점, 8:시작, 9:끝

    public Field(Map map, Player player){
        //맵에 대한 입력이 들어오면 맵을 만듬.
        this.map = map;
        assignField(map.getMonsterNum(), Numbers.MONSTER);
        assignField(map.getSafehouseNum(), Numbers.SAFEHOUSE);
        assignField(map.getStoreNum(), Numbers.STORE);
        //추가적으로 여기서 맵 세팅이 끝나야만 함.
        //(여기서 assignXXX 이 일어나야 함)

        this.player = player;
        map.setPlayerPos(map.getStartPoint());

    }

    public boolean isPlayable(){
    	if(player.getHP() <= 0.0){
    		System.out.println("플레이어 " + player.getName() + "이/가 사망했습니다!");
    		return false;
		}
		return true;
	}

    //Edited by 김우열, 2020.09.25 04:00
    public boolean move(int[] inputResult){
    	int moveLen = inputResult[1];
    	int moveDirection = inputResult[0];
    	int possibleLen = 0, checker = 0;
    	int[] dx = new int[]{-1, 0, 1, 0};
    	int[] dy = new int[]{0, 1, 0, -1};
    	//0, 1, 2, 3이 각각 상, 우, 하, 좌임

    	//플레이어 위치 가져옴
    	int[] playerPos = map.getPlayerPos();
    	int[] tempPos = new int[]{playerPos[0], playerPos[1]};

		//System.out.println("방향 : " + moveDirection);
		for(int i = 0; i < moveLen; i++){
			map.setSpecificLocation(playerPos[0], playerPos[1] , Numbers.PASSED);// 지나간길 표시
			playerPos[0] += dx[moveDirection - Numbers.DIRECTION_INT_DIFF];
			playerPos[1] += dy[moveDirection - Numbers.DIRECTION_INT_DIFF];
			//System.out.println("테스트 위치 : " + Arrays.toString(playerPos));
			checker = map.checkLocation(playerPos);
			if(checker == Numbers.PATH || checker == Numbers.START || checker == Numbers.PASSED){ //checker == END를 맨 밑에 else if로 이동했음
				possibleLen += 1;
				map.setPlayerPos(playerPos);//플레이어의 이동이 끝나서 맵에 플레이어 위치 추가
			}else if(checker == Numbers.WALL || checker == Numbers.OUT_OF_MAP){
				System.out.println("벽이 있어 " + moveLen + "칸 만큼 이동할 수 없습니다.");
				playerPos[0] -= dx[moveDirection - Numbers.DIRECTION_INT_DIFF];
				playerPos[1] -= dy[moveDirection - Numbers.DIRECTION_INT_DIFF];
				map.setPlayerPos(playerPos);
				break;
			}else if(checker == Numbers.MONSTER || checker == Numbers.SAFEHOUSE || checker == Numbers.STORE || checker == Numbers.END){
				System.out.println("이동 중 이벤트를 만났습니다.");
				possibleLen += 1;
				map.setPlayerPos(playerPos);
				break;
			}
		}
		System.out.println(possibleLen + "칸만큼 이동했습니다.");

    	System.out.print("플레이어가 " + Arrays.toString(tempPos) + "에서 " + Arrays.toString(playerPos) + "로 이동했습니다.\n");
		//System.out.println("test, 마지막 위치 : " + Arrays.toString(playerPos) + checker);

		map.setPlayerPos(playerPos);
		if(checker == Numbers.MONSTER) this.meetMonster();
		if(checker == Numbers.SAFEHOUSE) this.meetSafeHouse();
		if(checker == Numbers.STORE) this.meetStore();
		if(checker == Numbers.END) {return this.finalBoss();}

		//일종의 행동이 끝나고 나면, 해당 칸을 그냥 PATH로 바꾼다.
		//map.setSpecificLocation(playerPos[0], playerPos[1], 0);
		return false;
	}
 

    // 기존에 주석으로 정의했던 부분을 Numbers 클래스에 상수로 선언
	// 기존에 나누어져있던 3개의 메소드를 하나로 합침
    //targetNum이 1일 경우 아예 할당하는 반복문이 실행되지 않기 때문에 수정했습니다.
    private void assignField(int targetNum, int target){
    	for(int i = 0; i < targetNum;) {			//이관석 -> 아주아주 좋은 반복문이라 생각합니다!
    		int randNum1 = rand.nextInt(map.getMapRow());
    		int randNum2 = rand.nextInt(map.getMapCol());
    		if(map.getSpecificLocation(randNum1, randNum2) == Numbers.PATH) {
    			map.setSpecificLocation(randNum1, randNum2, target);
    			i++;
    		}
    	}
	}

    public void showField(){
        //필드의 정보를 보여주는 메소드
    	System.out.println();
    	for(int i = 0; i < map.getMapRow(); i++) {
    		for(int j = 0; j < map.getMapCol(); j++) {
    			System.out.print(changeMapToSymbol(map.getSpecificLocation(i, j)));
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }

    public void meetMonster(){
        //몬스터를 만났을 때 전투를 하는 메소드

    	ArrayList<Life> monsters = new ArrayList<>();
    	int monsterNum;

    	//몬스터 객체 새로 생성함
		//두 객체 이상의 몬스터가 나올수도 있기 때문에, 그 부분을 구현함
		double ratio = Math.random();
		if(ratio < 0.7) monsterNum = 1;
		else if(ratio < 0.9) monsterNum = 2;
		else monsterNum = 3;

		//몬스터를 생성함과 동시에 동시 출력
		System.out.printf("\n%s 이/가 ", player.getName());
		for(int i = 0; i < monsterNum; i++){
			monsters.add(new Monster(player));
			System.out.printf("%s	", monsters.get(i).getName());
		}
		System.out.print("를 만났다!\n");


		//게임 전투문
		while(true){
			//플레이어가 공격할 몬스터와, 이용할 스킬을 선택하는 부분 (구현해야함)
			//Checker 등등을 이용해 수준높게 구현해야할 필요성이 있음
			//다른 메소드들을 만들어 불러오는것도 방법
			int select = Checker.getInt(1, 4, "현재 몬스터 정보는 1, 스킬 정보를 보려면 2, 플레이어 정보를 보려면 3, 몬스터를 공격하려면 4를 입력해주세요 : ");

			if(select == 1) printLiveMonster(monsters);
			else if(select == 2) player.printSkill();
			else if(select == 3) player.printPlayerInfo();
			else if(select == 4) {
				player.attack(monsters);
				monsters.removeIf(monster -> (monster.getHP() <= 0.0));
			}
							
			if(player.getHP() <= 0.0){
				//게임 오버
				System.out.println("GAMEOVER");
				break;
			}
			if(/*몬스터들이 모두 죽었을 때*/monsters.isEmpty()){
				System.out.printf("플레이어 %s가 모든 몬스터를 처리했습니다.\n", player.getName());
				//뱅가드일때 쉴드 회복하는거 넣어야함.
				if(player.getMaxShield() == 20){
					System.out.println("방어막이 모두 회복되었습니다.");
					player.setShield(player.getMaxShield());
				}
				break;
			}
		}
    }

    public void meetSafeHouse(){
        //안식처를 만났을 때 휴식을 하는 메소드
    	int num;
    	System.out.println(player.name + "이 휴식처에 도착했다.");
    	System.out.println();
    	num = Checker.getInt(1, 2, "안식처에 온 것을 환영합니다!\n1.휴식을 취한다(5초)\n2.이동한다\n할 일을 선택하십시오 : ");
    	if(num == 1) {
			System.out.println("5초간 휴식을 취합니다.");
			try{
				Thread.sleep(5000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("HP가 20만큼 회복되었습니다.");
			// player.HP += 20;  -> Player의 정보를 직접적으로 수정하는 코드는 지양해야 함.
			// getter, setter를 생성해서 수정하도록 변경함.
			player.heal(20.0);
			System.out.println(player.getName() + " 의 HP가 20 회복되었습니다.");
			System.out.printf("%s 의 체력 : %.2f\n", player.getName(), player.getHP());
    	}else{
    		System.out.println(player.getName() + "이/가 휴식을 끝내고 이동을 시작합니다.");
		}
    	//루프를 빠져나오면 다시 move메소드를 실행하도록 함
    	//System.out.println("이동하고 싶은 방향과 길이를 입력하십시오.");
    	//String way = sc.next();
    	//move(way);
    }

    public void meetStore(){
        //상점을 만났을 때 구매하는 메소드
		int num;
    	System.out.println(player.name + "이 상점에 도착했다.");
    	System.out.println();
    	System.out.println("1. 공격력 강화");
    	System.out.println("2. 방어력 강화");
    	if(player.getSkill() instanceof Vanguard){
    		System.out.println("3. 쉴드 강화");
    		System.out.println("4. 나가기");
    		num = Checker.getInt(1, 4, "하고 싶은 일을 선택해 주세요 : ");
		}else {
			System.out.println("3. 나가기");
			num = Checker.getInt(1, 3, "하고 싶은 일을 선택해 주세요 : ");
			if(num == 3) num = 4;
		}

    	
    	switch(num) {
    		case 1:
    			System.out.println(player.name + "의 공격력을 강화합니다.");    		
    			player.setATK(player.getATK() + 5.0);
    			break;
    			
    		case 2:
    			System.out.println(player.name + "의 방어력을 강화합니다.");
    			player.setDEF(player.getDEF() + 5.0);
    			break;

    		case 3:
    			System.out.println(player.name + "의 쉴드를 강화합니다.");
    			player.setMaxShield(player.getMaxShield() + 10.0);
    			player.setShield(player.getMaxShield());
    			break;

			case 4:
    			System.out.println("상점을 나갑니다.");
    			break;
    	}
    }

    
    public void printLiveMonster(ArrayList<Life> monsters) {
    	for(int i =0 ; i < monsters.size(); i++) {
    		if(monsters.get(i).getHP() > 0) {
    			System.out.printf("%d번 %s, 체력 : %.2f / %.2f, 공격력 %.1f, 방어력 %.1f\n", i+1, monsters.get(i).getName(), monsters.get(i).getHP(), monsters.get(i).getMaxHP(), monsters.get(i).getATK(), monsters.get(i).getDEF());
    		}else{
    			System.out.println((i+1) + "번 " + monsters.get(i).getName() + ", 사망");
			}
    	}
    }
    // 번호를 기호로 만드는 메소드 입니다. 폰크크기 오류있어서 수정하고 다시 사용하겠습니다. Edited by 원재: 20.09.27  4:50
    public static char changeMapToSymbol(int mapNum){

    	//0:길, 1:벽, 2:몬스터, 3:휴식처, 4:상점, 8:시작, 9:끝
		/*
		 * 시작지점: □ 벽 : ■ 길 : ○ 목표: ☆ 현재위치: ◇ 몬스터: ◈ 상점: ♥ 휴식처: ♨
		 */
    	switch(mapNum) {
    		case Numbers.PATH:
    		return '▒';

			case Numbers.PASSED:
			return '□';

    		case Numbers.WALL:
    		return '■';

    		case Numbers.MONSTER:
    		return '※';
    		//return '◈';
    		
			case Numbers.SAFEHOUSE:
    		return '♨';
    		
			case Numbers.STORE:
    		return '♥';

			case Numbers.PLAYER_POS:
			return '♠';

			case Numbers.START:
    		return '۞';
    		
    		case Numbers.END:
    		return '☆';
    		
    	}

    	return (char)mapNum;
    	}
    
    public boolean finalBoss(){
    	FinalBoss finalBoss = FinalBoss.endOfGame(player);
    	ArrayList<Life> finalBossList = new ArrayList<>();
    	ArrayList<Life> playerList = new ArrayList<>();
    	finalBossList.add(finalBoss);
    	playerList.add(player);

    	System.out.println("최후의 관문에 도착하셨습니다. 마지막 보스를 상대하고 승리하세요!");
    	if(player.getSkill() instanceof Vanguard){
    		System.out.println("마지막 보스를 위해, 체력과 방어막이 모두 회복됩니다.");
		}
    	else System.out.println("마지막 보스를 위해, 체력이 모두 회복됩니다.");

		//게임 오버
		do {
			int select = Checker.getInt(1, 4, "???의 정보는 1, 스킬 정보를 보려면 2, 플레이어 정보를 보려면 3, 몬스터를 공격하려면 4를 입력해주세요 : ");

			if (select == 1) {
				System.out.printf("??? 의 체력 : %.2f / %.2f, ", finalBoss.getHP(), finalBoss.getMaxHP());
				if(player.getSkill() instanceof Vanguard) System.out.printf(", 방어막 : %.2f / %.2f, ", finalBoss.getShield(), finalBoss.getMaxShield());
				System.out.printf("공격력 %.2f, 방어력 %.2f\n", finalBoss.getATK(), finalBoss.getDEF());
			}
			else if (select == 2) player.printSkill();
			else if (select == 3) player.printPlayerInfo();
			else if (select == 4) {
				player.attack(finalBossList);
				if (finalBoss.getHP() <= 0.0) return true;
				finalBoss.finalAttack(playerList);
				if (finalBoss.getHP() <= 0.0) return true;
			}

		} while (!(player.getHP() <= 0.0));

		return false;

        //출구에 도착했을 때 마지막 보스 만남
        // -> 설계상으론 자기 자신을 똑같이 만나면 괜찮을 것이라고 생각함
        // -> player 객체를 복사해서 만들면 될 듯


        // -> 이거 깨면 엔딩 나오면서 게임 종료
    }
}
