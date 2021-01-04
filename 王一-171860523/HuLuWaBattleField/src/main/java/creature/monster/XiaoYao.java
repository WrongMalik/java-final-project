package creature.monster;

import creature.Monster;

public class XiaoYao extends Monster {
    protected int skillDamage;
    public XiaoYao(String nameImagePath, String characterImagePath) {
        super("小妖", 3000, nameImagePath, characterImagePath,
                5, 1, 300, 50);
        skillDamage = 600;
    }

    public int getSkillDamage() {
        return skillDamage;
    }

    @Override
    public String attackMsg() {
        return "对一个敌人造成" + attackDamage + "点伤害";
    }

    @Override
    public String skillMsg() {
        return "消耗" + skillMP + "点MP，" + "对单个敌人造成" + skillDamage + "点伤害";
    }
}
