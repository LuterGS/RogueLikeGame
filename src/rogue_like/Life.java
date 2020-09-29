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

    public void attacked(double damage){

        if(damage == -1){
            //최종 보스의 공격임을 암시합니다.
            return;
        }

        damage -= DEF;
        if(damage <= 0) {
            System.out.println("방어력이 너무 높아 데미지를 입지 않았다!");
        }else{
            double beforeAttacked = this.HP;
            this.HP -= damage;

            System.out.printf("%s 이/가 공격받음! 체력이 %.2f에서 %.2f로 감소\n", this.name, beforeAttacked, this.HP);

            if(this.HP <= 0) {
        	    System.out.println(this.name + " 이/가 공격을 받고 사망하였다!");
            }
        }
    }

    public void heal(double HP){
        this.HP += HP;
        if(this.HP > this.maxHP){
            this.HP = this.maxHP;
        }
    }

    public String getName(){
        return this.name;
    }

    public void setShield(double shield){
        this.shield = shield;
    }

    public void setMaxShield(double maxShield) {
        this.maxShield = maxShield;
    }

    public void setHP(double HP){
        this.HP = HP;
    }

    public void setATK(double ATK) {
        this.ATK = ATK;
    }

    public void setDEF(double DEF) {
        this.DEF = DEF;
    }


    public double attack(){
        //몬스터가 공격하는 메소드. 좀 더 디테일하게 가다듬을 수 있을듯, 어지간하면 print문은 안넣는걸로

        return this.ATK;
    }
}
