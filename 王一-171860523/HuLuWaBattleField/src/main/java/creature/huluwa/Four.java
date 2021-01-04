package creature.huluwa;

import creature.HuLuWa;

public class Four extends HuLuWa {
    protected int skillDamage;

    public Four(String nameImagePath, String characterImagePath) {
        super("四娃", 1500, nameImagePath, characterImagePath,
                5, 5, 600, 90);
        skillDamage = 1500;
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
        return "消耗" + skillMP + "点MP，" +
                "对一个敌人造成" + skillDamage + "点高额伤害";
    }
}
