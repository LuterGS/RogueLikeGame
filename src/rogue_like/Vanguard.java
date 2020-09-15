package rogue_like;

import java.util.Random;

public class Vanguard implements Skill {

    private Random rand = new Random();

    private String[] skillName = {
            "방어막 돌격",
            "혜성 분출",
            "특이점 공격",
            "곡예사"
    };

    private String[] skillInfo = {
            "적에게 내 공격력의 200% 만큼의 데미지를 주고, 방어막을 모두 완충합니다. 단, 적에게 20%의 확률로 세 배의 데미지를 입습니다.",
            "방어막을 모두 소모해 데미지를 줍니다.",
            "적에게 확률적으로 내 공격력의 120~150%만큼의 데미지를 줍니다.",
            "일정 확률로 적의 공격을 피하고, 내 공격력의 170%만큼의 데미지를 줍니다. 실패시 모든 방어막이 채워지고, 두 배의 데미지를 입습니다."
    };

    @Override
    public int Skill1(Player player, Life life) {
        return 0;
    }

    @Override
    public int Skill2(Player player, Life life) {
        return 0;
    }

    @Override
    public int Skill3(Player player, Life life) {

        int success_rate = rand.nextInt(31) + 120;
        double damage = player.ATK * 0.01 * success_rate;
        System.out.println("특이점 공격!" + Double.toString(damage) + "만큼의 데미지를 주었다.");
        life.attacked(damage);

        return 0;
    }

    @Override
    public int Skill4(Player player, Life life) {
        return 0;
    }

    @Override
    public void showSkill() {

    }
}
