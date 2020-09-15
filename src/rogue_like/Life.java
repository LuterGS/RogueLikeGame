package rogue_like;

public class Life {

    protected String name;
    protected double HP, maxHP, ATK, DEF, shield, maxShield;

    //getter, setter

    public double getATK() {
        return ATK;
    }

    public double getDEF() {
        return DEF;
    }

    public double getHP() {
        return HP;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public double getShield() {
        return shield;
    }

    public double getMaxShield() {
        return maxShield;
    }

    public double attack(Life life){

        //공격 추상 메소드

        return 0;
    }

    public int attacked(double damage){

        //공격당함 추상 메소드
        
        //데미지가 방어력에 따라 경감되는 부분. 퍼센트로 변경같이 디테일하게 변경 가능
        damage -= DEF;
        double beforeAttacked = this.HP;
        this.HP -= damage;
        System.out.println(this.name + " 이/가 공격받음! 체력이 " + Double.toString(beforeAttacked) + "에서 " + Double.toString(this.HP) + "로 감소");
        
        return 0;
    }
}
