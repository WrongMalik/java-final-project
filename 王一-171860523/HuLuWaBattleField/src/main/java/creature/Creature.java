package creature;

import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.awt.*;

public class Creature {
    protected String name;

    protected int x, y;
    protected ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair<Integer, Integer> getXY() {
        return new Pair<>(x, y);
    }

    protected int maxHP;
    protected int maxMP;
    protected int HP;
    protected int MP;

    protected int team; // 现在的阵营

    public void setPreTeam(int preTeam) {
        this.preTeam = preTeam;
    }

    public int getPreTeam() {
        return preTeam;
    }

    protected int preTeam; // 之前的阵营

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public int getHP() {
        return HP;
    }

    public int getMP() {
        return MP;
    }

    public int getTeam() {
        return team;
    }

    public int addHP(int hp) {
        HP += hp;
        if(HP > maxHP)
            HP = maxHP;
        return HP;
    }
    public int reduceHP(int hp) {
        HP -= hp;
        if(HP < 0)
            HP = 0;
        return HP;
    }

    public int addMP(int mp) {
        MP += mp;
        if(MP > maxMP)
            MP = maxMP;
        return MP;
    }
    public int reduceMP(int mp) {
        MP -= mp;
        if(MP < 0)
            MP = 0;
        return MP;
    }

    public Pair<Integer, Integer> getHPmaxHP() {
        return new Pair<>(HP, maxHP);
    }

    public Pair<Integer, Integer> getMPmaxMP() {
        return new Pair<>(MP, maxMP);
    }

}
