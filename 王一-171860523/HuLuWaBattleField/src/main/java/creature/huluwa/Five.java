package creature.huluwa;

import creature.HuLuWa;

public class Five extends HuLuWa {
    protected double addHPRate;

    public Five(String nameImagePath, String characterImagePath) {
        super("五娃", 2000, nameImagePath, characterImagePath,
                5, 5, 200, 100);
        addHPRate = 0.2;
    }

    public double getAddHPRate() {
        return addHPRate;
    }

    @Override
    public String attackMsg() {
        return "对一个敌人造成" + attackDamage + "点伤害";
    }

    @Override
    public String skillMsg() {
        return "消耗" + skillMP + "点MP，" + "使场上存活的友方队友恢复" + addHPRate*100 + "%的血量";
    }
}
