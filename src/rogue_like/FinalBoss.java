package rogue_like;

import java.util.ArrayList;
import java.util.Random;

public class FinalBoss extends Life{

    private static FinalBoss finalBoss = null;
    private Skill skill;
    private Random random = new Random();

    private FinalBoss(Player player){

        this.name = "???";
        this.maxHP = player.getMaxHP();
        this.HP = this.maxHP;
        this.ATK = player.getATK();
        this.DEF = player.getDEF();
        this.maxShield = player.getMaxShield();
        this.shield = this.maxShield;

        if(player.getSkill() instanceof Vanguard) skill = new Vanguard();
        else if(player.getSkill() instanceof Wizard) skill = new Wizard();
        else skill = new Assassin();

        //플레이어의 체력, 방어막 회복
        if(player.getSkill() instanceof Vanguard){
            player.setShield(player.getMaxShield());
        }
        player.setHP(player.getMaxHP());

    }

    public static FinalBoss endOfGame(Player player){
        if(finalBoss == null){
            finalBoss = new FinalBoss(player);
        }
        return finalBoss;
    }

    public void finalAttack(ArrayList<Life> monsters){

        int select = random.nextInt(4) + 1;
        int target = 0;

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
}
