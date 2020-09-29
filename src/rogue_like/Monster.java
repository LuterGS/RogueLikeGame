package rogue_like;

public class Monster extends Life{

    private Monster(){
    }

    public Monster(Player player){

        //플레이어의 정보를 토대로 몬스터의 능력을 조정하면서 생성하는 로직
        //private 선언자를 막음으로써 오직 player가 입력으로 들어왔을때만 생성하도록 함.
        this();
        this.name = Names.get_name(this);
        if(player.getHP() * 2 < player.getMaxHP()) this.maxHP = (player.getMaxHP() / 2) + 20;
        else this.maxHP = player.getMaxHP();
        this.HP = maxHP;
        this.ATK = player.getATK() * 0.7;
        this.DEF = player.getDEF() * 0.8;

    }
}
