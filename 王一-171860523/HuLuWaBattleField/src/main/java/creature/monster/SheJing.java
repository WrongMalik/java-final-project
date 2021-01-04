package creature.monster;

import creature.ControllableCreature;
import creature.Monster;

public class SheJing extends Monster {
    protected ControllableCreature controlCreature;

    public SheJing(String nameImagePath, String characterImagePath) {
        super("蛇精", 12000, nameImagePath, characterImagePath,
                7, 3, 700, 100);
    }

    public void setControlCreature(ControllableCreature controlCreature) {
        this.controlCreature = controlCreature;
    }

    public ControllableCreature getControlCreature() {
        return controlCreature;
    }

    @Override
    public String attackMsg() {
        return "对一个敌人造成" + attackDamage + "点伤害";
    }

    @Override
    public String skillMsg() {
        return "消耗" + skillMP + "点MP，" + "将一个敌人转变为可操控己方，回合结束后敌人恢复";
    }
}
