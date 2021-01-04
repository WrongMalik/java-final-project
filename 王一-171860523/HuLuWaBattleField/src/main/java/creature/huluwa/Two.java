package creature.huluwa;

import creature.HuLuWa;

public class Two extends HuLuWa {
    protected int addRange;
    protected int skillRound;
    protected int skillState;

    public Two(String nameImagePath, String characterImagePath) {
        super("二娃", 1300, nameImagePath, characterImagePath,
                5, 8, 300, 75);
        addRange = 4;
        skillRound = 2;
    }

    public int getAddRange() {
        return addRange;
    }

    public int getSkillRound() {
        return skillRound;
    }

    public int getAttackRange() {
        if (skillState == 0) {
            // 不在技能形态
            return attackRange;
        } else {
            // 在技能形态
            return addRange + attackRange;
        }
    }

    public int getMovableDistance() {
        if (skillState == 0) {
            // 不在技能形态
            return movableDistance;
        } else {
            // 在技能形态
            return addRange + movableDistance;
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
        return "消耗" + skillMP + "点MP，" + skillRound + "回合内（包括本回合），使移动距离和攻击距离增加" + addRange + "格";
    }
}
