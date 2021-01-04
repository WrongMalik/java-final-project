package creature.huluwa;

import creature.HuLuWa;

public class Six extends HuLuWa {
    protected int skillRound;
    protected int skillState;

    public Six(String nameImagePath, String characterImagePath) {
        super("六娃", 1300, nameImagePath, characterImagePath,
                8, 1, 1000, 80);
        skillRound = 2;
        skillState = 0;
    }

    public int getSkillRound() {
        return skillRound;
    }

    public void setSkillState(int skillState) {
        this.skillState = skillState;
        if (this.skillState < 0) {
            this.skillState = 0;
        }
    }

    public int getSkillState() {
        return skillState;
    }

    @Override
    public String attackMsg() {
        return "对一个敌人造成" + attackDamage + "点伤害";
    }

    @Override
    public String skillMsg() {
        return "消耗" + skillMP + "点MP，" + skillRound +
                "回合内（包括本回合），发动隐身";
    }
}
