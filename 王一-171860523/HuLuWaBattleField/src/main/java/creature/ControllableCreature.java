package creature;

public abstract class ControllableCreature extends Creature {
    protected String nameImagePath;
    protected String characterImagePath;

    public void setAttacking(int attacking) {
        this.attacking = attacking;
    }

    public int getAttacking() {
        return attacking;
    }

    public ControllableCreature() {
        moving = 1;
        attacking = 1;
        skilling = 1;
    }

    public int getSkilling() {
        return skilling;
    }

    public void setSkilling(int skilling) {
        this.skilling = skilling;
    }

    protected int moving;
    protected int attacking;
    protected int skilling;

    public void setMoving(int moving) {
        this.moving = moving;
    }

    public int getMoving() {
        return moving;
    }

    protected int movableDistance;
    protected int attackRange;
    protected int attackDamage;

    protected int skillMP;

    public void setNameImagePath(String nameImagePath) {
        this.nameImagePath = nameImagePath;
    }

    public void setCharacterImagePath(String characterImagePath) {
        this.characterImagePath = characterImagePath;
    }

    public void setMovableDistance(int movableDistance) {
        this.movableDistance = movableDistance;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setSkillMP(int skillMP) {
        this.skillMP = skillMP;
    }

    public String getNameImagePath() {
        return nameImagePath;
    }

    public String getCharacterImagePath() {
        return characterImagePath;
    }

    public int getMovableDistance() {
        return movableDistance;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getSkillMP() {
        return skillMP;
    }

    public abstract String attackMsg();
    public abstract String skillMsg();
}
