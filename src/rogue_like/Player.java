package rogue_like;

import java.util.Scanner;

public class Player extends Life {

    private static Player player = null;
    private Scanner scan = new Scanner(System.in);
    private Skill skill = null;
    /*
     * 현재 location에 할당되는 값이 없습니다. 
     * location 할당 함수를 만들어서 field가 생성될 때 호출을 하던가
     * (플레이어 위치도 결국은 Field에만 가지고 있는 값이니까 )
     * Player 위치 자체를 Field의 멤버함수로 바꿔서 플레이어의 위치를 Field에서 관리하는 방법도
     * 괜찮을거 같습니다.
     */
    private int[] location = new int[2]; 

    private Player(){

        int skillSelect;

        System.out.println("플레이어의 이름을 입력해주세요 : ");
        this.name = scan.nextLine();
        System.out.println("플레이어의 직업을 선택"); //추후에 Prints에 구현해서 선택하도록 함
        skillSelect = scan.nextInt(2);

        //플레이어 스탯 조정 부분. 난이도를 추가한다면 여기를 좀 더 디테일하게 다듬어야함
        this.ATK = 10;
        this.DEF = 2;
        this.maxHP = 80;
        this.HP = this.maxHP;

        /*
        이 부분은 캐릭터에 장비나 패시브 버프같은걸 구현하고싶어서 넣어놨는데, 구현하는건 두고봅시다.

        this.passive = new Passive[3];
        this.passive[0] = new Passive(new ATKPassive(0));
        this.passive[1] = new Passive(new DEFPassive(0));
        this.passive[2] = new Passive(new SKPassive(0));

         */

        this.changeSkill(skillSelect);

    }

    public static Player makeOne(){

        if(player == null) {
            player = new Player();
        }
        return player;
    }

    //상 과 하가 반대로 되있어서 수정했습니다. - 김우열
    public void move(String moveDirection, int moveRange){
        //매개변수는 나중에 수정 가능
        switch (moveDirection) {
            case Numbers.UP:
                player.location[0] -= moveRange;
                break;
            case Numbers.DOWN:
                player.location[0] += moveRange;
                break;
            case Numbers.LEFT:
                player.location[1] -= moveRange;
                break;
            case Numbers.RIGHT:
                player.location[1] += moveRange;
        }

        System.out.println(this.name + " 이/가 " + moveDirection + "으로 " + Integer.toString(moveRange) + "칸 이동했다!");
    }


    public double attack(Monster[] monsters, int target){

        System.out.print("사용할 스킬을 골라주세요");
        int select = scan.nextInt(3);

        if(select == 1){
            this.skill.Skill1(this, monsters, target);
        }else if(select == 2){
            this.skill.Skill2(this, monsters, target);
        }else if(select == 3){
            this.skill.Skill3(this, monsters, target);
        }

        //몬스터가 죽었는지에 대한 여부를 return하게끔
        return 0;
    }


    public void changeSkill(int select){

        //Vanguard만 Shield라는 특수한 자원을 사용
        
        if(select == 1){
            this.skill = new Vanguard();
            this.maxShield = 10;
            this.shield = this.maxShield;
        }else if(select == 2){
            this.skill = new Assassin();
        }else if(select == 3){
            this.skill = new Wizard();
        }
    }

    public int[] getLocation() {
        return location;
    }
}
