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

    public void attacked(double damage){

        damage -= DEF;
        if(damage < 0) {
            System.out.println("방어력이 너무 높아 데미지를 입지 않았다!");
        }else{
            double beforeAttacked = this.HP;
            this.HP -= damage;

            System.out.printf("%s 이/가 공격받음! 체력이 %.2f에서 %.2f로 감소\n", this.name, beforeAttacked, this.HP);

            if(this.HP <= 0) {
        	    System.out.println(this.name + "이가 공격을 받고 사망하였다!");
            }
        }
    }

    public void heal(double HP){
        this.HP += HP;
    }

    public String getName(){
        return this.name;
    }

    public void setShield(double shield){
        this.shield = shield;
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
}
