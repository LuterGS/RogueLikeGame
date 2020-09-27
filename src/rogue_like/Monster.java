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
        this.ATK = 10;
        this.DEF = 5;

    }

    public int attacked(double damage){

        //공격당함 추상 메소드

        //데미지가 방어력에 따라 경감되는 부분. 퍼센트로 변경같이 디테일하게 변경 가능
        damage -= DEF;
        if(damage < 0) damage = 0;
        double beforeAttacked = this.HP;
        this.HP -= damage;
        // edited by 원재, 전투 로직 짤때 필요할거 같아서 추가했슴다.
        if(this.HP <= 0) {
        	System.out.println(this.name + "이가 공격을 받고 처리되었다!" );
        	return 0;
        }
        
        System.out.println(this.name + " 이/가 공격받음! 체력이 " + Double.toString(beforeAttacked) + "에서 " + Double.toString(this.HP) + "로 감소");

        return 0;
    }

    public double attack(){
        //몬스터가 공격하는 메소드. 좀 더 디테일하게 가다듬을 수 있을듯, 어지간하면 print문은 안넣는걸로

        return 10.0;
    }
}
