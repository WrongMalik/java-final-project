package creature.huluwa;

import creature.HuLuWa;

public class One extends HuLuWa {
    protected int addDamage;
    protected int skillRound;
    protected int skillState;

    public One(String nameImagePath, String characterImagePath) {
        super("大娃", 2500, nameImagePath, characterImagePath,
                5, 1, 500, 75);
        addDamage = 500;
        skillRound = 2;
        skillState = 0;
    }

    public int getAddDamage() {
        return addDamage;
    }

    public int getSkillRound() {
        return skillRound;
    }

    public int getAttackDamage() {
        if(skillState == 0) {
            //没有处在技能形态下
            return attackDamage;
        } else {
            // 处在技能形态下，攻击加成
            return addDamage + attackDamage;
        }
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
        return "消耗" + skillMP + "点MP，" + skillRound + "回合内（包括本回合），使普通攻击增加" + addDamage + "点伤害";
    }
}
