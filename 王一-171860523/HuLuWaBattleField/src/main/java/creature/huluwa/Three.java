package creature.huluwa;

import creature.HuLuWa;

public class Three extends HuLuWa {
    protected int addDamage;
    protected int skillRound;
    protected int skillState;

    public Three(String nameImagePath, String characterImagePath) {
        super("三娃", 3500, nameImagePath, characterImagePath,
                5, 1, 400, 80);
        addDamage = 300;
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
        return "消耗" + skillMP + "点MP，" + skillRound +
                "回合内（包括本回合），使普通攻击增加" + addDamage + "点伤害，" +
                "并免疫敌人的普通攻击";
    }
}
