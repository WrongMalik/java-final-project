package game;

import creature.ControllableCreature;
import creature.Creature;
import creature.HuLuWa;
import creature.Monster;
import creature.huluwa.*;
import creature.monster.SheJing;
import creature.monster.XiaoYao;
import creature.monster.XieZiJing;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.util.Pair;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BattleField {
    public GridPane mapPane; // 地图
    public Creature[][] mapIndex; // 角色在地图的坐标
    public List<ImageView> hlw; // 葫芦娃图像块
    public List<ImageView> yg; // 妖怪图像块

    public List<HuLuWa> hlwList; // 葫芦娃队列
    public int hlwNum;
    public List<Monster> ygList; // 妖怪队列
    public int ygNum;

    public AnchorPane[][] movableMap; // 处理移动范围
    public AnchorPane[][] attackMap; // 处理攻击范围

    public void setTeam(int team) {
        this.team = team;
    }

    public int team;
    public List<String> allOperations;

    public BattleField(GridPane mapPane, int team) {
        allOperations = new ArrayList<>();
        this.mapPane = mapPane;
        mapIndex = new Creature[20][20];
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                mapIndex[i][j] = null;
            }
        }
        hlw = new ArrayList<>();
        yg = new ArrayList<>();
        hlwList = new ArrayList<>();
        ygList = new ArrayList<>();
        this.team = team;

        AnchorPane anchor;
        movableMap = new AnchorPane[20][20];
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                anchor = new AnchorPane();
                anchor.setStyle("-fx-opacity: 0.4; -fx-background-color: white;");
                anchor.setVisible(false);
                movableMap[i][j] = anchor;
                mapPane.add(anchor, j, i);
            }
        }

        attackMap = new AnchorPane[20][20];
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                anchor = new AnchorPane();
                anchor.setStyle("-fx-opacity: 0.4; -fx-background-color: red;");
                anchor.setVisible(false);
                attackMap[i][j] = anchor;
                mapPane.add(anchor, j, i);
            }
        }
    }

    /**
     * 加入葫芦娃
     */
    public void addHuLuWa(ImageView hlw1, ImageView hlw2, ImageView hlw3,
                     ImageView hlw4, ImageView hlw5, ImageView hlw6, ImageView hlw7) {
        int x, y;
        // hlw1
        x = mapPane.getRowIndex(hlw1);
        y = mapPane.getColumnIndex(hlw1);
        One one = new One("/image/name1.png", "/image/hlw1.png");
        one.setXY(x, y);
        one.setImageView(hlw1);
        mapIndex[x][y] = one;
        hlwList.add(one);

        // hlw2
        x = mapPane.getRowIndex(hlw2);
        y = mapPane.getColumnIndex(hlw2);
        Two two = new Two("/image/name2.png", "/image/hlw2.png");
        two.setImageView(hlw2);
        two.setXY(x, y);

        mapIndex[x][y] = two;
        hlwList.add(two);

        // hlw3
        x = mapPane.getRowIndex(hlw3);
        y = mapPane.getColumnIndex(hlw3);
        Three three = new Three("/image/name3.png", "/image/hlw3.png");
        three.setImageView(hlw3);
        three.setXY(x, y);
        mapIndex[x][y] = three;
        hlwList.add(three);

        // hlw4
        x = mapPane.getRowIndex(hlw4);
        y = mapPane.getColumnIndex(hlw4);
        Four four = new Four("/image/name4.png", "/image/hlw4.png");
        four.setImageView(hlw4);
        four.setXY(x, y);
        mapIndex[x][y] = four;
        hlwList.add(four);

        // hlw5
        x = mapPane.getRowIndex(hlw5);
        y = mapPane.getColumnIndex(hlw5);
        Five five = new Five("/image/name5.png", "/image/hlw5.png");
        five.setXY(x, y);
        five.setImageView(hlw5);
        mapIndex[x][y] = five;
        hlwList.add(five);

        // hlw6
        x = mapPane.getRowIndex(hlw6);
        y = mapPane.getColumnIndex(hlw6);
        Six six = new Six("/image/name6.png", "/image/hlw6.png");
        six.setXY(x, y);
        six.setImageView(hlw6);
        mapIndex[x][y] = six;
        hlwList.add(six);

        // hlw7
        x = mapPane.getRowIndex(hlw7);
        y = mapPane.getColumnIndex(hlw7);
        Seven seven = new Seven("/image/name7.png", "/image/hlw7.png");
        seven.setXY(x, y);
        seven.setImageView(hlw7);
        mapIndex[x][y] = seven;
        hlwList.add(seven);

        hlwNum = hlwList.size();
    }

    /**
     * 加入妖怪
     */
    public void addYaoGuai(ImageView xiezijing, ImageView shejing, ImageView xiaoyao1,
                          ImageView xiaoyao2) {
        int x, y;
        // xiezijing
        x = mapPane.getRowIndex(xiezijing);
        y = mapPane.getColumnIndex(xiezijing);
        XieZiJing xieZiJing = new XieZiJing("/image/name11.png", "/image/xiezijing.png");
        xieZiJing.setXY(x, y);
        xieZiJing.setImageView(xiezijing);
        mapIndex[x][y] = xieZiJing;
        ygList.add(xieZiJing);

        // shejing
        x = mapPane.getRowIndex(shejing);
        y = mapPane.getColumnIndex(shejing);
        SheJing sheJing = new SheJing("/image/name12.png", "/image/shejing.png");
        sheJing.setXY(x, y);
        sheJing.setImageView(shejing);
        mapIndex[x][y] = sheJing;
        ygList.add(sheJing);

        // xiaoyao1
        x = mapPane.getRowIndex(xiaoyao1);
        y = mapPane.getColumnIndex(xiaoyao1);
        XiaoYao xiaoYao1 = new XiaoYao("/image/name13.png", "/image/xiaoyao.png");
        xiaoYao1.setXY(x, y);
        xiaoYao1.setImageView(xiaoyao1);
        mapIndex[x][y] = xiaoYao1;
        ygList.add(xiaoYao1);

        // xiaoyao2
        x = mapPane.getRowIndex(xiaoyao2);
        y = mapPane.getColumnIndex(xiaoyao2);
        XiaoYao xiaoYao2 = new XiaoYao("/image/name13.png", "/image/xiaoyao.png");
        xiaoYao2.setXY(x, y);
        xiaoYao2.setImageView(xiaoyao2);
        mapIndex[x][y] = xiaoYao2;
        ygList.add(xiaoYao2);

        ygNum = ygList.size();
    }

    /**
     * 将一个阵营的moving进行设置
     * @param team 指定的队列
     * @param moving 设置的是否可以
     */
    public void setTeamMovable(int team, int moving) {
        if(team == 1) {
            for (HuLuWa h : hlwList) {
                h.setMoving(moving);
            }
        } else if (team == 2) {
            for (Monster m : ygList) {
                m.setMoving(moving);
            }
        }
    }

    /**
     * 将一个阵营的attacking进行设置
     * @param team 指定的队列
     * @param attacking 设置的是否可以
     */
    public void setTeamAttacking(int team, int attacking) {
        if(team == 1) {
            for (HuLuWa h : hlwList) {
                h.setAttacking(attacking);
            }
        } else if (team == 2) {
            for (Monster m : ygList) {
                m.setAttacking(attacking);
            }
        }
    }

    /**
     * 将一个阵营的skilling进行设置
     * @param team 指定的队列
     * @param skilling 设置的是否可以
     */
    public void setTeamSkilling(int team, int skilling) {
        if(team == 1) {
            for (HuLuWa h : hlwList) {
                h.setSkilling(skilling);
            }
        } else if (team == 2) {
            for (Monster m : ygList) {
                m.setSkilling(skilling);
            }
        }
    }

    /**
     * 可以移动时显示可移动范围
     * @param creature 将要移动的角色
     */
    public void displayMovable(ControllableCreature creature) {
        Pair<Integer, Integer> p = creature.getXY();
        int x = p.getKey();
        int y = p.getValue();
        int temp = 0;
        int movableDistance = creature.getMovableDistance();
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                temp = Math.abs(x - i) + Math.abs(y - j);
                if(temp <= movableDistance && mapIndex[i][j] == null)
                    movableMap[i][j].setVisible(true);
            }
        }
    }

    /**
     * 隐藏所有可移动范围地砖
     */
    public void hideMovable() {
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                movableMap[i][j].setVisible(false);
            }
        }
    }

    /**
     * 显示将要攻击角色的可攻击范围
     * @param creature 将要进行攻击的角色
     */
    public void displayAttack(ControllableCreature creature) {
        Pair<Integer, Integer> p = creature.getXY();
        int x = p.getKey();
        int y = p.getValue();
        int temp = 0;
        int attackRange = creature.getAttackRange();
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                temp = Math.abs(x - i) + Math.abs(y - j);
                if(temp <= attackRange && (mapIndex[i][j] == null || mapIndex[i][j].getTeam() != creature.getTeam()))
                    attackMap[i][j].setVisible(true);
            }
        }
    }

    /**
     * 隐藏所有攻击范围地砖
     */
    public void hideAttack() {
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                attackMap[i][j].setVisible(false);
            }
        }
    }

    /**
     * 将一些内部计算比较复杂的指令进行翻译，输出一系列可以直接使用battle()函数呈现结果的指令
     * 需要注意的是本函数不对各个角色的血量、位置、攻击等信息进行修改，仅是翻译
     * @param operation 输入的指令，其内部计算比较复杂
     * @return 输出的一系列简单指令
     */
    public List<String> translate(String operation) {
        List<String> result = new ArrayList<>();
        String[] ops;
        if (operation.contains("attack")) {
            // 攻击指令，格式：x1,y1 attack x2,y2
            // 其中x1,y1是发动攻击方的坐标（grid），x2,y2是攻击目标的坐标
            ops = operation.split(" ");
            int srcX = Integer.parseInt(ops[0].split(",")[0]);
            int srcY = Integer.parseInt(ops[0].split(",")[1]);
            int desX = Integer.parseInt(ops[2].split(",")[0]);
            int desY = Integer.parseInt(ops[2].split(",")[1]);
            ControllableCreature srcCreature = (ControllableCreature) mapIndex[srcX][srcY];
            ControllableCreature desCreature = (ControllableCreature) mapIndex[desX][desY];
            if(desCreature instanceof Three && ((Three) desCreature).getSkillState() > 0) {
                // 攻击的是三娃，并且三娃处在技能形态下，不会受到普通攻击的伤害
                String op = operation + " damage " + "0";
                result.add(op);
            } else {
                // 攻击的是正常单位
                int srcAttackDamage = srcCreature.getAttackDamage();
                String op = operation + " damage " + srcAttackDamage;
                result.add(op);
                if(srcAttackDamage >= desCreature.getHP()) {
                    // 此次攻击会导致目标死亡
                    op = desX + "," + desY + " dead";
                    if(desCreature instanceof HuLuWa) {
                        hlwNum -= 1;
                        System.out.println("葫芦娃死一个，还剩" + hlwNum);
                    } else {
                        ygNum -= 1;
                        System.out.println("妖怪死一个，还剩" + ygNum);
                    }
                    result.add(op);
                }
            }
        } else if (operation.contains("skill")) {
            // 处理技能
            ops = operation.split(" ");
            // 释放技能，扣除MP
            String op = ops[0] + " " + ops[1];
            result.add(op);
            if (ops.length == 3) {
                // 处理的是指向性技能
                int srcX = Integer.parseInt(ops[0].split(",")[0]);
                int srcY = Integer.parseInt(ops[0].split(",")[1]);
                int desX = Integer.parseInt(ops[2].split(",")[0]);
                int desY = Integer.parseInt(ops[2].split(",")[1]);
                ControllableCreature srcCreature = (ControllableCreature) mapIndex[srcX][srcY];
                ControllableCreature desCreature = (ControllableCreature) mapIndex[desX][desY];
                // 三娃技能无法免疫技能伤害，所以不需要区别对待
                // 指向性技能也有不同，需要区别对待
                if(srcCreature instanceof Four) {
                    // 四娃
                    int srcSkillDamage = ((Four) srcCreature).getSkillDamage();
                    op = operation.replace("skill", "attack") + " damage " + srcSkillDamage;
                    result.add(op);
                    if(srcSkillDamage >= desCreature.getHP()) {
                        // 技能会导致目标死亡
                        op = desX + "," + desY + " dead";
                        if(desCreature instanceof HuLuWa) {
                            hlwNum -= 1;
                            System.out.println("葫芦娃死一个，还剩" + hlwNum);
                        } else {
                            ygNum -= 1;
                            System.out.println("妖怪死一个，还剩" + ygNum);
                        }
                        result.add(op);
                    }
                } else if (srcCreature instanceof Seven) {
                    // 七娃，沉默敌人
                    op = desX + "," + desY + " silence";
                    result.add(op);
                } else if (srcCreature instanceof SheJing) {
                    // 蛇精，精神控制
                    op = srcX + "," + srcY + " control " + desX + "," + desY;
                    result.add(op);
                } else if (srcCreature instanceof XieZiJing) {
                    // 蝎子精，伤害
                    int srcSkillDamage = ((XieZiJing) srcCreature).getSkillDamage();
                    op = operation.replace("skill", "attack") + " damage " + srcSkillDamage;
                    result.add(op);
                    if(srcSkillDamage >= desCreature.getHP()) {
                        // 技能会导致目标死亡
                        op = desX + "," + desY + " dead";
                        if(desCreature instanceof HuLuWa) {
                            hlwNum -= 1;
                            System.out.println("葫芦娃死一个，还剩" + hlwNum);
                        } else {
                            ygNum -= 1;
                            System.out.println("妖怪死一个，还剩" + ygNum);
                        }
                        result.add(op);
                    }
                } else if (srcCreature instanceof XiaoYao) {
                    // 小妖，伤害
                    int srcSkillDamage = ((XiaoYao) srcCreature).getSkillDamage();
                    op = operation.replace("skill", "attack") + " damage " + srcSkillDamage;
                    result.add(op);
                    if(srcSkillDamage >= desCreature.getHP()) {
                        // 技能会导致目标死亡
                        op = desX + "," + desY + " dead";
                        if(desCreature instanceof HuLuWa) {
                            hlwNum -= 1;
                            System.out.println("葫芦娃死一个，还剩" + hlwNum);
                        } else {
                            ygNum -= 1;
                            System.out.println("妖怪死一个，还剩" + ygNum);
                        }
                        result.add(op);
                    }
                }
            } else {
                // 处理的是瞬发技能
                int srcX = Integer.parseInt(ops[0].split(",")[0]);
                int srcY = Integer.parseInt(ops[0].split(",")[1]);
                ControllableCreature srcCreature = (ControllableCreature) mapIndex[srcX][srcY];
                // 大娃，二娃，三娃, 六娃不需要有其他指令，只需要有一个 x,y skill 发动技能的指令
                if(srcCreature instanceof Five) {
                    // 五娃，场上存活队友加血
                    op = srcX + "," + srcY + " addHP " + (int) (srcCreature.getMaxHP() * ((Five) srcCreature).getAddHPRate());
                    result.add(op);
                    if (srcCreature.getTeam() == 1) {
                        // 此时五娃是葫芦娃阵营
                        for (HuLuWa h : hlwList) {
                            if(h.getHP() > 0 && !h.equals(srcCreature)) {
                                // 此时还存活，可以加血
                                op = h.getXY().getKey() + "," + h.getXY().getValue() + " addHP " +
                                        (int) (h.getMaxHP() * ((Five) srcCreature).getAddHPRate());
                                result.add(op);
                            }
                        }
                    } else {
                        // 妖怪阵营
                        for (Monster m : ygList) {
                            if(m.getHP() > 0) {
                                // 此时还存活，可以加血
                                op = m.getXY().getKey() + "," + m.getXY().getValue() + " addHP " +
                                        (int) (m.getMaxHP() * ((Five) srcCreature).getAddHPRate());
                                result.add(op);
                            }
                        }
                    }
                }
            }
        }
//        result.add("skill complete");
        return result;
    }

    public void battle(String operation) {
        allOperations.add(operation);
        Platform.runLater(() -> {
            String[] ops;
            if (operation.contains("move")) {
                // 移动指令，格式：x1,y1 move x2,y2
                // 其中x1,y1是原坐标（grid），x2,y2是目标坐标
                ops = operation.split(" ");
                int srcX = Integer.parseInt(ops[0].split(",")[0]);
                int srcY = Integer.parseInt(ops[0].split(",")[1]);
                int desX = Integer.parseInt(ops[2].split(",")[0]);
                int desY = Integer.parseInt(ops[2].split(",")[1]);
                Creature creature = mapIndex[srcX][srcY];
                ImageView imageView = creature.getImageView();
                // 转移图片，变换mapIndex，修改对象内的XY
                mapPane.getChildren().remove(imageView);
                mapPane.add(imageView, desY, desX);
                mapIndex[srcX][srcY] = null;
                mapIndex[desX][desY] = creature;
                creature.setXY(desX, desY);
            } else {
                // 处理的是一系列指令
                String[] operations = operation.split("=");
                for(String op : operations) {
                    if(op.contains("attack")) {
                        // 攻击伤害指令
                        ops = op.split(" ");
                        int desX = Integer.parseInt(ops[2].split(",")[0]);
                        int desY = Integer.parseInt(ops[2].split(",")[1]);
                        int damage = Integer.parseInt(ops[4]);
                        Creature desCreature = mapIndex[desX][desY];
                        desCreature.reduceHP(damage);

                        // 特效
                        ImageView attackImageView = new ImageView(new Image("/image/attack.png"));
                        attackImageView.setFitWidth(37.5);
                        attackImageView.setFitHeight(37.5);
                        mapPane.add(attackImageView, desY, desX);
                        KeyValue kv = new KeyValue(attackImageView.imageProperty(), null);
                        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(kf);
                        timeline.play();
                    } else if (op.contains("dead")) {
                        // 角色死亡
//                        System.out.println("dead了啊");
                        ops = op.split(" ");
                        int x = Integer.parseInt(ops[0].split(",")[0]);
                        int y = Integer.parseInt(ops[0].split(",")[1]);
                        Creature deadCreature = mapIndex[x][y];
                        ImageView imageView = deadCreature.getImageView();
                        mapPane.getChildren().remove(imageView);
                        mapIndex[x][y] = null;

                        // 特效
                        ImageView deadImageView = new ImageView(new Image("/image/dead.png"));
                        deadImageView.setFitWidth(37.5);
                        deadImageView.setFitHeight(37.5);
                        mapPane.add(deadImageView, y, x);
                        KeyValue kv = new KeyValue(deadImageView.imageProperty(), null);
                        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(kf);
                        timeline.play();
                    } else if (op.contains("skill")) {
                        // 释放技能，扣除MP
                        ops = op.split(" ");
                        int x = Integer.parseInt(ops[0].split(",")[0]);
                        int y = Integer.parseInt(ops[0].split(",")[1]);
                        Creature skillCreature = mapIndex[x][y];
                        skillCreature.reduceMP(((ControllableCreature) skillCreature).getSkillMP());

                        // 状态技能，需要设置状态
                        if(skillCreature instanceof One) {
                            One one = (One) skillCreature;
                            one.setSkillState(one.getSkillRound() * 2);
                        } else if (skillCreature instanceof Two) {
                            Two two = (Two) skillCreature;
                            two.setSkillState(two.getSkillRound() * 2);
                        } else if (skillCreature instanceof Three) {
                            Three three = (Three) skillCreature;
                            three.setSkillState(three.getSkillRound() * 2);
                        } else if (skillCreature instanceof Six) {
                            Six six = (Six) skillCreature;
                            six.setSkillState(six.getSkillRound() * 2);
                        }

                        // TODO 对六娃进行处理
                        if(skillCreature instanceof Six) {
                            if (team != 0) {
                                //不是server端
                                if(team != skillCreature.getTeam()) {
                                    // 对于敌方不可见
                                    skillCreature.getImageView().setVisible(false);
                                }
                            } else {
                                // server端，半透明
                                skillCreature.getImageView().setStyle("-fx-opacity: 0.4;");
                            }
                        }
                        // 特效
                        ImageView skillImageView = new ImageView(new Image("/image/skill.png"));
                        skillImageView.setFitWidth(37.5);
                        skillImageView.setFitHeight(37.5);
                        mapPane.add(skillImageView, y, x);
                        KeyValue kv = new KeyValue(skillImageView.imageProperty(), null);
                        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(kf);
                        timeline.play();
                    } else if (op.contains("silence")) {
                        // 沉默
                        ops = op.split(" ");
                        int x = Integer.parseInt(ops[0].split(",")[0]);
                        int y = Integer.parseInt(ops[0].split(",")[1]);
                        ControllableCreature slienceCreature = (ControllableCreature) mapIndex[x][y];
                        slienceCreature.setMoving(0);
                        slienceCreature.setSkilling(0);
                        slienceCreature.setAttacking(0);
                    } else if (op.contains("addHP")) {
                        // 回血
                        ops = op.split(" ");
                        int x = Integer.parseInt(ops[0].split(",")[0]);
                        int y = Integer.parseInt(ops[0].split(",")[1]);
                        Creature recoverCreature = mapIndex[x][y];
                        int addHP = Integer.parseInt(ops[2]);
                        recoverCreature.addHP(addHP);

                        // 特效
                        ImageView addImageView = new ImageView(new Image("/image/recover.png"));
                        addImageView.setFitWidth(37.5);
                        addImageView.setFitHeight(37.5);
                        mapPane.add(addImageView, y, x);
                        KeyValue kv = new KeyValue(addImageView.imageProperty(), null);
                        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(kf);
                        timeline.play();
                    } else if (op.contains("control")) {
                        // 精神控制
                        ops = op.split(" ");
                        int srcX = Integer.parseInt(ops[0].split(",")[0]);
                        int srcY = Integer.parseInt(ops[0].split(",")[1]);
                        int desX = Integer.parseInt(ops[2].split(",")[0]);
                        int desY = Integer.parseInt(ops[2].split(",")[1]);
                        SheJing shejing = (SheJing) mapIndex[srcX][srcY];
                        shejing.setControlCreature((ControllableCreature) mapIndex[desX][desY]);
                        ControllableCreature c = shejing.getControlCreature();
                        c.setPreTeam(c.getTeam());
                        c.setTeam(shejing.getTeam());
                    }
                }
            }
        });
    }

    /**
     * 用于处理回合结束时的：
     * 增加MP，减少持续性技能的回合数，精控结束
     */
    public void endRound() {
        // 查看蛇精是否进行了精控
        for(Monster m : ygList) {
            if(m instanceof SheJing) {
                SheJing shejing = (SheJing) m;
                if (((SheJing) m).getControlCreature() != null) {
                    ControllableCreature c = shejing.getControlCreature();
                    c.setTeam(c.getPreTeam());
                    shejing.setControlCreature(null);
                    if (c instanceof Six) {
                        // 六娃需要立刻结束技能形态
                        ((Six) c).setSkillState(0);
                    }
                }
            }
        }
        // 减少技能状态的回合数
        for(HuLuWa h : hlwList) {
            if (h instanceof One) {
                One one = (One) h;
                one.setSkillState(one.getSkillState() - 1);
            } else if (h instanceof Two) {
                Two two = (Two) h;
                two.setSkillState(two.getSkillState() - 1);
            } else if (h instanceof Three) {
                Three three = (Three) h;
                three.setSkillState(three.getSkillState() - 1);
            } else if (h instanceof Six) {
                Six six = (Six) h;
                six.setSkillState(six.getSkillState() - 1);
                if(((Six) h).getSkillState() == 0) {
                    h.getImageView().setVisible(true);
                    h.getImageView().setStyle("");
                }
            }
        }

        // 增加MP
        for(HuLuWa h : hlwList)
            h.addMP(10);
        for(Monster m : ygList)
            m.addMP(10);
    }

    public int isEnd() {
        System.out.println("shuliang: " + hlwNum + " " + ygNum);
        if(hlwNum == 0)
            return 2;
        else if (ygNum == 0)
            return 1;
        else
            return 0;
    }
}
