package rogue_like;

import java.util.ArrayList;

public class Wizard extends SkillInfo implements Skill {


    public Wizard(){
        skillName[0] = "화염구";
        skillName[1] = "눈보라";
        skillName[2] = "신비한 폭발";
        skillName[3] = "요그사론의 수수께끼 상자";

        skillInfo[0] = "적에게 내 공격력의 200%의 데미지를 입힙니다.";
        skillInfo[1] = "이번 턴의 적의 공격을 모두 막지만, 최대 체력의 10%에 해당하는 데미지를 입습니다.";
        skillInfo[2] = "적에게 70%의 데미지를 주지만, 만약 적의 현재 체력이 50% 이하일 경우, 50%의 확률로 적을 즉사시킵니다.";
        skillInfo[3] = "5번의 공격 (각각 공격력의 30~170% 사이 랜덤한 값)을 무작위 대상에게 (본인 포함) 난사합니다.";

        selectSpecificMonster = new boolean[]{true, false, true, false};
    }

    @Override
    public int Skill1(Player player, ArrayList<Monster> monsters, int target) {
        monsters.get(target).attacked(player.getATK() * 2);
        attacked(player, monsters, 1);
        return 0;
    }

    @Override
    public int Skill2(Player player,  ArrayList<Monster> monsters, int target) {
        player.setHP(player.getMaxHP() * 0.1);
        return 0;
    }

    @Override
    public int Skill3(Player player,  ArrayList<Monster> monsters, int target) {

        if(monsters.get(target).getHP() <= monsters.get(target).getMaxHP() * 0.5){
            if(Math.random() <= 0.5){
                monsters.get(target).setHP(0.0);
            }else{
                monsters.get(target).attacked(player.getATK() * 0.7);
            }
        }else{
            monsters.get(target).attacked(player.getATK() * 0.7);
        }

        return 0;
    }

    @Override
    public int Skill4(Player player,  ArrayList<Monster> monsters, int target) {

        int selector;
        double damage;

        for(int i = 0; i < 5; i++){
            selector = rand.nextInt(monsters.size() + 1);
            damage = player.getATK() * ((Math.random() * 140) + 30.0);
            if(selector < monsters.size()){
                monsters.get(selector).attacked(damage);
            }else{
                System.out.println("플레이어에게 스스로 공격! 데미지를 받았다.");
                player.setHP(player.getHP() - damage);
            }
        }
        return 0;
    }

    @Override
    public int attacked(Player player, ArrayList<Monster> monsters, double ratio){
        for(Monster monster: monsters){
            singleAttacked(player, monster, ratio);
        }
        return 0;
    }

    public void singleAttacked(Player player, Monster monster, double ratio){
        if(monster.getHP() > 0.0) {
            player.setHP(player.getHP() - (monster.attack() * ratio));
        }
    }
}
