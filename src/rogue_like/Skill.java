package rogue_like;

import java.util.ArrayList;

public interface Skill {

    // String[] skillName = {};// checkSKill 구현시 필요해서 만들었습니다!

    int Skill1(Player player, ArrayList<Monster> monsters, int target);
    int Skill2(Player player, ArrayList<Monster> monsters, int target);
    int Skill3(Player player, ArrayList<Monster> monsters, int target);
    int Skill4(Player player, ArrayList<Monster> monsters, int target);
    int attacked(Player player, ArrayList<Monster> monsters, double ratio);

    void showSkill();
    boolean getSkillTargeted(int num);
}
