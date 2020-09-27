package rogue_like;

import java.util.Random;

public class SkillInfo {

    protected Random rand = new Random();

    protected String[] skillName = new String[4];
    protected String[] skillInfo = new String[4];
    protected boolean[] selectSpecificMonster = new boolean[4];

    public boolean getSkillTargeted(int num){
        return selectSpecificMonster[num];
    }

    public void showSkill() {
        for(int i = 0; i < 4; i++){
            System.out.println((i+1) + ". " + skillName[i] + "         " + skillInfo[i]);
        }
    }
}
