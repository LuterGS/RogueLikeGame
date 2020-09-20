package rogue_like;

import java.util.Random;

public class Wizard implements Skill{

    private Random rand = new Random();

    private String[] skillName = {
            "화염구",
            "눈보라",
            "신비한 폭발",
            "요그사론의 수수께끼 상자"
    };

    private String[] skillInfo = {
            "적에게 내 공격력의 200%의 데미지를 입힙니다.",
            "이번 턴의 적의 공격을 모두 막지만, 최대 체력의 10%에 해당하는 데미지를 입습니다.",
            "적에게 70%의 데미지를 주지만, 만약 적의 현재 체력이 50% 이하일 경우, 50%의 확률로 적을 즉사시킵니다.",
            "5번의 공격 (각각 공격력의 30~170% 사이 랜덤한 값)을 무작위 대상에게 (본인 포함) 난사합니다."
    };


    @Override
    public int Skill1(Player player,  Monster[] monsters, int target) {
        monsters[target].attacked(player.getATK()* 2);
        attacked(player, monsters, 1);


        return 0;
    }

    @Override
    public int Skill2(Player player,  Monster[] monsters, int target) {
        player.setHP(player.getMaxHP() * 0.1);
        return 0;
    }

    @Override
    public int Skill3(Player player,  Monster[] monsters, int target) {

        if(monsters[target].getHP() <= monsters[target].getMaxHP() * 0.5){
            if(Math.random() <= 0.5){
                monsters[target].setHP(0.0);
            }else{
                monsters[target].attacked(player.getATK() * 0.7);
            }
        }else{
            monsters[target].attacked(player.getATK() * 0.7);
        }

        return 0;
    }

    @Override
    public int Skill4(Player player,  Monster[] monsters, int target) {

        int selector;
        double damage;

        for(int i = 0; i < 5; i++){
            selector = rand.nextInt(monsters.length + 1);
            damage = player.getATK() * ((Math.random() * 140) + 30.0);
            if(selector < monsters.length){
                monsters[selector].attacked(damage);
            }else{
                player.setHP(player.getHP() - damage);
            }
        }
        return 0;
    }

    @Override
    public int attacked(Player player, Monster[] monsters, double ratio){
        for(Monster monster: monsters){
            singleAttacked(player, monster, ratio);
        }
        return 0;
    }

    public int singleAttacked(Player player, Monster monster, double ratio){
        player.setHP(player.getHP() - (monster.attack() * ratio));
        return 0;
    }

    @Override
    public void showSkill() {
        for(int i = 0; i < 4; i++){
            System.out.println(skillName[i] + "         " + skillInfo[i]);
        }
    }
}
