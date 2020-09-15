package rogue_like;

public class Monster extends Life{

    private Monster(){
    }

    public Monster(Player player){

        //플레이어의 정보를 토대로 몬스터의 능력을 조정하면서 생성하는 로직
        //private 선언자를 막음으로써 오직 player가 입력으로 들어왔을때만 생성하도록 함.
        this();
        this.name = Names.get_name(this);
        this.maxHP = 100;
        this.HP = maxHP;
        this.ATK = 30;
        this.DEF = 20;
    }
}
