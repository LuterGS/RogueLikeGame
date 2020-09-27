package rogue_like;

import java.util.ArrayList;

public class Assassin extends SkillInfo implements Skill{

    public Assassin(){
        skillName[0] = "숨고르기";
        skillName[1] = "단검 부채";
        skillName[2] = "일발필살";
        skillName[3] = "분신술";

        skillInfo[0] = "모든 적에게 공격당할 확률이 50%가 되며, 공격당했을 시 피해량을 적에게 그대로 되돌립니다.";
        skillInfo[1] = "모든 적에게 공격력만큼의 데미지를 주며, 10%의 확률로 데미지가 두 배가 됩니다.";
        skillInfo[2] = "적에게 200%의 데미지를 받지만, 적의 최대 체력의 30%에 해당하는 데미지를 줍니다.";
        skillInfo[3] = "적 공격력 혹은 자신의 최대 체력 15% 중 낮은 만큼의 데미지를 입습니다.";

        selectSpecificMonster = new boolean[]{false, false, true, false};
    }
    
    
    @Override
    public int Skill1(Player player, ArrayList<Monster> monsters, int target) {

        for(Monster monster: monsters) {
            if (Math.random() < 0.5) {
                System.out.printf("회피 실패! %.2f 만큼의 데미지를 입습니다.\n", monster.attack());
                singleAttacked(player, monster, 1);
                monster.attacked(monster.attack());
            } else {
                System.out.println("회피 성공! 데미지를 입지 않습니다.");
            }
        }

        return 0;
    }

    public int Skill2(Player player, ArrayList<Monster> monsters, int target) {

        double damage;
        if(Math.random() < 0.1) damage = player.getATK() * 2;
        else damage = player.getATK();

        for(Monster monster: monsters){
            monster.attacked(damage);
        }
        attacked(player, monsters, 1);
        return 0;
    }

    @Override
    public int Skill3(Player player, ArrayList<Monster> monsters, int target) {

        monsters.get(target).attacked(monsters.get(target).getMaxHP() * 0.3);
        attacked(player, monsters, 2);

        return 0;
    }
    
    @Override
    public int Skill4(Player player, ArrayList<Monster> monsters, int target) {

        for(Monster monster: monsters){
            if(monster.attack() < player.getMaxHP() * 0.15){
                singleAttacked(player, monster, 1);
            }else{
                player.setHP(player.getMaxHP() * 0.15);
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
        player.attacked((monster.attack() * ratio));
    }
}
