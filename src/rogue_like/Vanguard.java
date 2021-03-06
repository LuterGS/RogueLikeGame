package rogue_like;

import java.util.ArrayList;

public class Vanguard extends SkillInfo implements Skill {

    public Vanguard() {
        skillName[0] = "방어막 돌격";
        skillName[1] ="혜성 분출";
        skillName[2] = "특이점 공격";
        skillName[3] = "곡예사";

        skillInfo[0] = "적에게 내 공격력의 200% 만큼의 데미지를 주고, 방어막을 모두 완충합니다. 단, 적들에게 20%의 확률로 세 배의 데미지를 입습니다.";
        skillInfo[1] = "방어막을 모두 소모해 그만큼의 데미지를 주며, 모든 공격을 방어합니다.";
        skillInfo[2] = "모든 적에게 확률적으로 내 공격력의 120~150%만큼의 데미지를 줍니다.";
        skillInfo[3] = "일정 확률로 적의 공격을 피하고, 내 공격력의 170%만큼의 데미지를 줍니다. 실패시 모든 방어막이 채워지고, 두 배의 데미지를 입습니다.";

        selectSpecificMonster = new boolean[]{true, true, false, true};
    }

    @Override
    public int Skill1(Player player, ArrayList<Monster> monsters, int target) {

        monsters.get(target).attacked(player.getATK() * 2);
        player.setShield(player.getMaxShield());
        System.out.println("방어막 돌격!" + monsters.get(target).getName() + " 에게" + Double.toString(player.getATK() * 2) + "만큼의 데미지를 주었다.");
        double random = Math.random();
        if(random <0.2) attacked(player, monsters, 3);
        else attacked(player, monsters, 1);

        return 0;
    }

    @Override
    public int Skill2(Player player, ArrayList<Monster> monsters, int target) {

        monsters.get(target).attacked(player.getShield());
        player.setShield(0.0);
        System.out.println("방어막 돌격!" + Double.toString(player.getShield()) + "만큼의 데미지를 주었다.");
        return 0;
    }

    @Override
    public int Skill3(Player player, ArrayList<Monster> monsters, int target) {

        int success_rate = rand.nextInt(31) + 120;
        double damage = player.ATK * 0.01 * success_rate;
        for (Monster monster : monsters) {
            monster.attacked(damage);
        }
        attacked(player, monsters, 1);
        return 0;
    }

    @Override
    public int Skill4(Player player, ArrayList<Monster> monsters, int target) {

        double success_rate = Math.random();
        System.out.printf("%s %%의 확률로 곡예사 시전, ", Double.toString(success_rate * 100));
        if (Math.random() < success_rate){
            monsters.get(target).attacked(player.getATK() * 1.7);
            System.out.print("성공!" + Double.toString(player.getATK() * 1.7) + "만큼의 데미지를 주고 공격을 회피했습니다.\n");
        }else{
            player.setShield(player.getMaxShield());
            attacked(player, monsters, 2);
            System.out.print("실패! 방어막이 모두 채워지고 두 배의 데미지를 입습니다.\n");
        }

        return 0;
    }

    @Override
    public int attacked(Player player, ArrayList<Monster> monsters, double ratio){

        for (Monster monster : monsters) {
            if(monster.getHP() > 0.0){
                if (player.getShield() > monster.getATK() * ratio) {
                    player.setShield(player.getShield() - monster.getATK() * ratio);
                    System.out.println(player.getName() + "가 " + monster.getName() + "에게 공격받음! 방어막이 " + player.getShield() + "로 감소");
                } else {
                    double damage = (monster.getATK() * ratio) - player.getShield();
                    player.setShield(0.0);
                    player.setHP(player.getHP() - damage);
                    System.out.println(player.getName() + "가 " + monster.getName() + "에게 공격받음! 방어막이 모두 소모되고 체력이 " + player.getHP() + "로 감소");
                }
            }
        }

        return 0;
    }
}
