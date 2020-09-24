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
}
