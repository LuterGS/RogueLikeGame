package rogue_like;

import java.util.ArrayList;

public interface Skill {

    // String[] skillName = {};// checkSKill 구현시 필요해서 만들었습니다!

    int Skill1(Life player, ArrayList<Life> monsters, int target);
    int Skill2(Life player, ArrayList<Life> monsters, int target);
    int Skill3(Life player, ArrayList<Life> monsters, int target);
    int Skill4(Life player, ArrayList<Life> monsters, int target);
    int attacked(Life player, ArrayList<Life> monsters, double ratio);

    void showSkill();
    boolean getSkillTargeted(int num);
}
