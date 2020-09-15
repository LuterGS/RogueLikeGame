package rogue_like;

public class Assassin implements Skill{

    private String[] skillName = {
            "숨고르기",
            "단검 부채",
            "일발필살",
            "분신술"
    };
    
    private String[] skillInfo = {
            "적에게 공격당할 확률이 50%가 되며, 공격당했을 시 피해량을 적에게 그대로 되돌립니다.",
            "모든 적에게 공격력만큼의 데미지를 주며, 10%의 확률로 데미지가 두 배가 됩니다.",
            "적에게 200%의 데미지를 받지만, 적의 최대 체력의 30%에 해당하는 데미지를 줍니다.",
            "적 공격력 혹은 자신의 최대 체력 15% 중 낮은 만큼의 데미지를 입습니다."
    };
    
    
    @Override
    public int Skill1(Player player, Life life) {
        //각 스킬에 대한 구성
        return 0;
    }

    @Override
    public int Skill2(Player player, Life life) {
        return 0;
    }

    @Override
    public int Skill3(Player player, Life life) {
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
