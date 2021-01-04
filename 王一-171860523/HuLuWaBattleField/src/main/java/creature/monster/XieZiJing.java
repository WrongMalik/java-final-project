package creature.monster;

import creature.Monster;

public class XieZiJing extends Monster {
    protected int skillDamage;
    public XieZiJing(String nameImagePath, String characterImagePath) {
        super("蝎子精", 15000, nameImagePath, characterImagePath,
                5, 2, 800, 80);
        skillDamage = 1800;
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
        return "消耗" + skillMP + "点MP，" + "对单个敌人造成" + skillDamage + "点高额伤害";
    }
}
