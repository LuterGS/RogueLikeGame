package rogue_like;

import java.util.ArrayList;

public class Player extends Life {

    private static Player player = null;
    private Skill skill = null;
    /*
     * 현재 location에 할당되는 값이 없습니다. 
     * location 할당 함수를 만들어서 field가 생성될 때 호출을 하던가
     * (플레이어 위치도 결국은 Field에만 가지고 있는 값이니까 )
     * Player 위치 자체를 Field의 멤버함수로 바꿔서 플레이어의 위치를 Field에서 관리하는 방법도
     * 괜찮을거 같습니다. -> map에서 저장하도록 바꿨습니다~
     */

    private Player(){

        int skillSelect;

        System.out.print("플레이어의 이름을 입력해주세요 : ");
        this.name = Checker.getInput();
        skillSelect = Checker.getInt(1, 3, "플레이어의 직업을 선택해주세요. 1번은 Vanguard, 2번은 Assassin, 3번은 Wizard 입니다 : ");

        //플레이어 스탯 조정 부분. 난이도를 추가한다면 여기를 좀 더 디테일하게 다듬어야함
        this.ATK = 20;
        this.DEF = 10;
        this.maxHP = 120;// 원래 80이었는데 포션 먹을때  max.hp와 hp가와 상이한 부분이있어 -되는 오류 있어서 120으로 수정합니다. by 원재 
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

    public void attack(ArrayList<Life> monsters){

        int select = Checker.getInt(1, 4, "사용할 스킬을 골라주세요 : ");
        int target = 0;
        if(skill.getSkillTargeted(select-1) && monsters.size() > 1) target = (Checker.getInt(1, monsters.size(), "사용할 몬스터를 선택해주세요 : ") - 1);

        if(select == 1){
            this.skill.Skill1(this, monsters, target);
        }else if(select == 2){
            this.skill.Skill2(this, monsters, target);
        }else if(select == 3){
            this.skill.Skill3(this, monsters, target);
        }else if(select == 4){
            this.skill.Skill4(this, monsters, target);
        }
    }


    public void changeSkill(int select){

        //Vanguard만 Shield라는 특수한 자원을 사용
        
        if(select == 1){
            this.skill = new Vanguard();
            this.maxShield = 20;
            this.shield = this.maxShield;
        }else if(select == 2){
            this.skill = new Assassin();
        }else if(select == 3){
            this.skill = new Wizard();
        }
    }

    public void printSkill(){
        skill.showSkill();
    }

    public Skill getSkill(){
        return this.skill;
    }

    public void printPlayerInfo(){
        System.out.println("플레이어 이름 : " + this.name);
        System.out.printf("체력 : %.2f / %.2f\n", this.HP, this.maxHP);
        if(this.skill instanceof Vanguard) System.out.printf("방어막 : %.2f / %.2f\n", this.shield, this.maxShield);
        System.out.println("공격력 : " + this.ATK);
        System.out.println("방어력 : " + this.DEF);
    }

}
