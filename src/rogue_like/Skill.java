package rogue_like;

public interface Skill {

    String[] skillName = {};// checkSKill 구현시 필요해서 만들었습니다!
    int Skill1(Player player, Monster[] monsters, int target);
    int Skill2(Player player, Monster[] monsters, int target);
    int Skill3(Player player, Monster[] monsters, int target);
    int Skill4(Player player, Monster[] monsters, int target);
    int attacked(Player player, Monster[] monsters, double ratio);

    void showSkill();
}
