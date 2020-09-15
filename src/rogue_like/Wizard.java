package rogue_like;

public class Wizard implements Skill{

    private String[] skillName = {
            "화염구",
            "눈보라",
            "신비한 폭발",
            "요그사론의 수수께끼 상자"
    };

    private String[] skillInfo = {
            "설명1",
            "설명2",
            "설명3",
            "설명4"
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
