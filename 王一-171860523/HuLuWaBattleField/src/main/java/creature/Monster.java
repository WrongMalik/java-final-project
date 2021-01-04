package creature;

public class Monster extends ControllableCreature{

    public Monster(String name, int maxHP, String nameImagePath, String characterImagePath,
           int movableDistance, int attackRange, int attackDamage, int skillMP) {
        team = 2;
        preTeam = 2;
        this.name = name;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = 100;
        this.MP = 100;
        this.nameImagePath = nameImagePath;
        this.characterImagePath = characterImagePath;
        this.movableDistance = movableDistance;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.skillMP = skillMP;
    }

    @Override
    public String attackMsg() {
        return null;
    }

    @Override
    public String skillMsg() {
        return null;
    }
}
