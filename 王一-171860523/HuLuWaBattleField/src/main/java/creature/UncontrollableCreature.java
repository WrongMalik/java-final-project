package creature;

public class UncontrollableCreature extends Creature {
    protected String characterImagePath;

    public void setCharacterImagePath(String characterImagePath) {
        this.characterImagePath = characterImagePath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCharacterImagePath() {
        return characterImagePath;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int x, y;
}
