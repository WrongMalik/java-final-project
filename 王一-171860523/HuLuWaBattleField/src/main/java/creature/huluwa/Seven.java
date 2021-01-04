package creature.huluwa;

import creature.HuLuWa;

public class Seven extends HuLuWa {

    public Seven(String nameImagePath, String characterImagePath) {
        super("三娃", 1800, nameImagePath, characterImagePath,
                5, 3, 450, 100);
    }

    @Override
    public String attackMsg() {
        return "对一个敌人造成" + attackDamage + "点伤害";
    }

    @Override
    public String skillMsg() {
        return "消耗" + skillMP + "点MP，" +
                "使一个敌人下回合无法移动，无法攻击，无法发动主动技能";
    }
}
