package rogue_like;

public class Assassin implements Skill{

    private String[] skillName = {
            "숨고르기",
            "단검 부채",
            "일발필살",
            "분신술"
    };
    
    private String[] skillInfo = {
            "모든 적에게 공격당할 확률이 50%가 되며, 공격당했을 시 피해량을 적에게 그대로 되돌립니다.",
            "모든 적에게 공격력만큼의 데미지를 주며, 10%의 확률로 데미지가 두 배가 됩니다.",
            "적에게 200%의 데미지를 받지만, 적의 최대 체력의 30%에 해당하는 데미지를 줍니다.",
            "적 공격력 혹은 자신의 최대 체력 15% 중 낮은 만큼의 데미지를 입습니다."
    };
    
    
    @Override
    public int Skill1(Player player, Monster[] monsters, int target) {

        for(Monster monster: monsters) {
            if (Math.random() < 0.5) {
                singleAttacked(player, monster, 1);
                monster.attacked(monster.attack());
                System.out.println("회피 실패! " + Double.toString(monster.attack()) + "만큼의 데미지를 입었습니다.");
            } else {
                System.out.println("회피 성공! 데미지를 입지 않습니다.");
            }
        }

        return 0;
    }

    public int Skill2(Player player, Monster[] monsters, int target) {

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
    public int Skill3(Player player, Monster[] monsters, int target) {

        monsters[target].attacked(monsters[target].getMaxHP() * 0.3);
        attacked(player, monsters, 2);

        return 0;
    }
    
    @Override
    public int Skill4(Player player, Monster[] monsters, int target) {

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
