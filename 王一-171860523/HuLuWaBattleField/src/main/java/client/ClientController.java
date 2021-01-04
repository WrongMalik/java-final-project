package client;

import creature.ControllableCreature;
import creature.Creature;
import creature.Monster;
import creature.huluwa.Four;
import creature.huluwa.Seven;
import game.BattleField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML public AnchorPane waitPane;

    @FXML public AnchorPane characterPane;
    @FXML public ImageView teamImage;
    @FXML public ImageView characterImage;
    @FXML public ImageView nameImage;
    @FXML public ProgressBar HPBar;
    @FXML public ProgressBar MPBar;
    @FXML public Label HPLabel;
    @FXML public Label MPLabel;
    @FXML public Button attackButton;
    @FXML public Button skillButton;
    @FXML public Button endButton;
    @FXML public Tooltip skillToolTip;
    @FXML public Tooltip attackToolTip;

    @FXML public GridPane mapPane;
    @FXML public ImageView hlw1;
    @FXML public ImageView hlw2;
    @FXML public ImageView hlw3;
    @FXML public ImageView hlw4;
    @FXML public ImageView hlw5;
    @FXML public ImageView hlw6;
    @FXML public ImageView hlw7;

    @FXML public ImageView xiezijing;
    @FXML public ImageView shejing;
    @FXML public ImageView xiaoyao1;
    @FXML public ImageView xiaoyao2;

    @FXML public AnchorPane endPane;
    @FXML public Label winLabel;
    @FXML public Button saveButton;
    @FXML public Button unsaveButton;

    public BattleField battleField; // 处理战场

    public int teamID; // 阵营ID   0：葫芦娃   1：妖怪
    public int operationable; // 是否处于自己回合
    public Creature currentCreature; // 当前选中的角色ID
    public int moving; // 是否处于移动状态
    public int attacking; // 是否处于攻击状态下
    public int skilling; // 判断是否处在指向性技能


    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;
    public void initialize(URL location, ResourceBundle resources) {
        waitPane.setVisible(true);
        for(Node node : characterPane.getChildren()) {
            node.setVisible(false);
        }

        battleField = new BattleField(mapPane, teamID);
        battleField.addHuLuWa(hlw1, hlw2, hlw3, hlw4, hlw5, hlw6, hlw7);
        battleField.addYaoGuai(xiezijing, shejing, xiaoyao1, xiaoyao2);



        Client.clientController = this;

        teamID = 0;
        operationable = 0;
        currentCreature = null;
        moving = 0;
        attacking = 0;
        skilling = 0;

        // 设置gridpane的点击事件
        mapPane.setOnMouseClicked(mouseEvent -> {
            if (operationable == 1) {
                int y = (int) Math.floor(mouseEvent.getX() / 37.5);
                int x = (int) Math.floor(mouseEvent.getY() / 37.5);
                System.out.println(x + " " + y);
                if (battleField.mapIndex[x][y] != null && battleField.mapIndex[x][y].getImageView().isVisible()) {
                    // 点击了一个角色，并且这个角色的视图可见（即可以选中）
                    Creature creature = battleField.mapIndex[x][y];
                    if ((currentCreature == null && teamID == creature.getTeam()) ||
                            (moving == 0 && attacking == 0 && skilling == 0 && teamID == creature.getTeam())) {
                        // 当前没有选中角色，或者在不处于任何状态时选中其他角色
                        // 所以是角色选中，同时判断是否可以操作该单位
                        currentCreature = creature;
                        System.out.println("选中" + currentCreature);
                        for(Node node : characterPane.getChildren()) {
                            node.setVisible(true);
                        }
                        Pair<Integer, Integer> HP = currentCreature.getHPmaxHP();
                        HPLabel.setText(HP.getKey() + "/" + HP.getValue());
                        HPBar.setProgress((double) HP.getKey() / HP.getValue());
                        Pair<Integer, Integer> MP = currentCreature.getMPmaxMP();
                        MPLabel.setText(MP.getKey() + "/" + MP.getValue());
                        MPBar.setProgress((double) MP.getKey() / MP.getValue());

                        nameImage.setImage(new Image(((ControllableCreature) currentCreature).getNameImagePath()));
                        characterImage.setImage(new Image(((ControllableCreature) currentCreature).getCharacterImagePath()));
                        // moving的赋值，从类里面获取
                        moving = ((ControllableCreature) currentCreature).getMoving();
                        if(moving == 1) {
                            battleField.displayMovable((ControllableCreature) currentCreature);
                        }
                    } else if (currentCreature != null) {
                        // 当前选中了角色
                        if (moving == 1) {
                            // 处于移动状态，取消移动
                            System.out.println("取消移动");
                            moving = 0;
                            battleField.hideMovable();
                        } else if (attacking == 1) {
                            // 处于攻击状态
                            if (teamID != creature.getTeam()) {
                                // 攻击的对象是敌方阵营
                                if(battleField.attackMap[x][y].isVisible()) {
                                    // 说明可以攻击
                                    System.out.println("攻击！");
                                    int currentCreatureX = currentCreature.getXY().getKey();
                                    int currentCreatureY = currentCreature.getXY().getValue();
                                    battleField.hideAttack();
                                    attacking = 0;
                                    ((ControllableCreature) currentCreature).setAttacking(0);
                                    out.println(currentCreatureX +"," + currentCreatureY + " attack " + x + "," + y);
                                    try {
                                        String inLine = in.readLine();
                                        if (inLine != null) {
                                            System.out.println(inLine);
                                            if(inLine.contains("exit")) {
                                                battleField.battle(inLine);
                                                operationable = 0;
                                                if(battleField.isEnd() == teamID)
                                                    winLabel.setText("失败");
                                                endPane.setVisible(true);
                                            } else {
                                                battleField.battle(inLine);
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                System.out.println("不能攻击友方，取消攻击");
                                attacking = 0;
                                battleField.hideAttack();
                            }
                        } else if (skilling == 1) {
                            // 处于指向性技能形态，和攻击状态处理模式相同
                            if (teamID != creature.getTeam()) {
                                // 攻击的对象是敌方阵营
                                int currentCreatureX = currentCreature.getXY().getKey();
                                int currentCreatureY = currentCreature.getXY().getValue();
                                battleField.hideAttack();
                                skilling = 0;
                                System.out.println(currentCreatureX +"," + currentCreatureY + " skill " + x + "," + y);
                                out.println(currentCreatureX +"," + currentCreatureY + " skill " + x + "," + y);
                                System.out.println("发动技能，指向性攻击");
                                try {
                                    String inLine = in.readLine();
                                    if(inLine != null) {
                                        // 处理server返回的技能解读
                                        System.out.println(inLine);

                                        if(inLine.contains("exit")) {
                                            battleField.battle(inLine);
                                            operationable = 0;
                                            if(battleField.isEnd() == teamID)
                                                winLabel.setText("失败");
                                            endPane.setVisible(true);
                                        } else {
                                            battleField.battle(inLine);
                                        }
                                        // 重新显示血量和法力值
                                        Task<Void> reDisplayTask = new Task<Void>() {
                                            @Override
                                            protected Void call() {
                                                Platform.runLater(() -> {
                                                    System.out.println("重新显示血量");
                                                    Pair<Integer, Integer> HP = currentCreature.getHPmaxHP();
                                                    HPLabel.setText(HP.getKey() + "/" + HP.getValue());
                                                    HPBar.setProgress((double) HP.getKey() / HP.getValue());
                                                    Pair<Integer, Integer> MP = currentCreature.getMPmaxMP();
                                                    MPLabel.setText(MP.getKey() + "/" + MP.getValue());
                                                    MPBar.setProgress((double) MP.getKey() / MP.getValue());
                                                });
                                                return null;
                                            }
                                        };
                                        new Thread(reDisplayTask).start();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("不能攻击友方，取消技能");
                                skilling = 0;
                                battleField.hideAttack();
                            }
                        }
                    }
                } else { // 点击了地板
                    if (moving == 0 && attacking == 0 && skilling == 0) {
                        // 既没有在攻击，也没有在移动，也没有处于指向性技能状态，说明取消选中人物
                        System.out.println("取消选中");
                        battleField.hideAttack();
                        battleField.hideMovable();
                        currentCreature = null;
                        for (Node node : characterPane.getChildren()) {
                            node.setVisible(false);
                        }
                        teamImage.setVisible(true);
                        endButton.setVisible(true);
                    } else if (moving == 1) {
                        // 处在移动状态
                        if(battleField.movableMap[x][y].isVisible()) {
                            System.out.println("移动！");
                            // 如果点击的是可移动至的区块
                            int currentCreatureX = currentCreature.getXY().getKey();
                            int currentCreatureY = currentCreature.getXY().getValue();
                            battleField.hideMovable();
                            ((ControllableCreature) currentCreature).setMoving(0);
                            moving = 0;
                            out.println(currentCreatureX +"," + currentCreatureY + " move " + x + "," + y);
                            try {
                                String inLine = in.readLine();
                                if (inLine != null) {
                                    System.out.println(inLine);
                                    battleField.battle(inLine);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            battleField.hideMovable();
                        }
                    } else if (attacking == 1) {
                        // 处在攻击状态，取消攻击
                        System.out.println("取消攻击");
                        attacking = 0;
                        battleField.hideAttack();
                    } else if (skilling == 1) {
                        // 处在指向性技能状态，取消技能
                        System.out.println("取消技能");
                        skilling = 0;
                        battleField.hideAttack();
                    }
                }
            }
        });

        attackButton.setOnAction((event) -> {
            if(operationable == 1 && currentCreature != null) {
                moving = 0;
                skilling = 0;
                battleField.hideMovable();
                // attacking赋值，从类里获取
                attacking = ((ControllableCreature) currentCreature).getAttacking();
                if (attacking == 1) {
                    battleField.displayAttack((ControllableCreature) currentCreature);
                }
            }
        });


        attackButton.setOnMouseEntered((event) -> {
            attackToolTip.setText(((ControllableCreature) currentCreature).attackMsg());
        });

        skillButton.setOnAction((event) -> {
            if(operationable == 1 && currentCreature != null &&
               currentCreature.getMP() >= ((ControllableCreature) currentCreature).getSkillMP() &&
               ((ControllableCreature) currentCreature).getSkilling() == 1) {
                // 处于可操控状态，并且当前选中了角色，并且角色的MP足够释放技能，并且角色该回合没有释放过技能
                if (currentCreature instanceof Four ||
                    currentCreature instanceof Seven ||
                    currentCreature instanceof Monster) {
                    // 这些角色的技能是指向性技能，和攻击的处理方式相同
                    moving = 0;
                    attacking = 0;
                    battleField.hideMovable();
                    battleField.hideAttack();
                    // skilling赋值，因为已经判断过MP是否可以释放技能，所以直接设定可以
                    skilling = 1;
                    // 技能和攻击的范围一致
                    battleField.displayAttack((ControllableCreature) currentCreature);
                } else {
                    // 这些角色的技能是瞬间释放的
                    // 瞬间释放技能
                    System.out.println("瞬间释放技能");
                    int currentCreatureX = currentCreature.getXY().getKey();
                    int currentCreatureY = currentCreature.getXY().getValue();
                    moving = 0;
                    attacking = 0;
                    battleField.hideMovable();
                    battleField.hideAttack();
                    ((ControllableCreature) currentCreature).setSkilling(0);
                    out.println(currentCreatureX + "," + currentCreatureY + " skill");
                    try {
                        String inLine = in.readLine();
                        if(inLine != null) {
                            // 处理server返回的技能解读
                            System.out.println(inLine);
                            if(inLine.contains("exit")) {
                                battleField.battle(inLine);
                                operationable = 0;
                                if(battleField.isEnd() == teamID)
                                    winLabel.setText("失败");
                                endPane.setVisible(true);
                            } else {
                                battleField.battle(inLine);
                            }
                            // 重新显示血量和法力值
                            Task<Void> reDisplayTask = new Task<Void>() {
                                @Override
                                protected Void call() {
                                    Platform.runLater(() -> {
                                        System.out.println("重新显示血量");
                                        Pair<Integer, Integer> HP = currentCreature.getHPmaxHP();
                                        HPLabel.setText(HP.getKey() + "/" + HP.getValue());
                                        HPBar.setProgress((double) HP.getKey() / HP.getValue());
                                        Pair<Integer, Integer> MP = currentCreature.getMPmaxMP();
                                        MPLabel.setText(MP.getKey() + "/" + MP.getValue());
                                        MPBar.setProgress((double) MP.getKey() / MP.getValue());
                                    });
                                    return null;
                                }
                            };
                            new Thread(reDisplayTask).start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        skillButton.setOnMouseEntered((event) -> {
            skillToolTip.setText(((ControllableCreature) currentCreature).skillMsg());
        });


        endButton.setOnAction((event) -> {
            Task<Void> serverEchoTask = new Task<Void>() {
                @Override
                protected Void call() {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                        if(line.contains("exit")) {
                            battleField.battle(line);
                            operationable = 0;
                            if(battleField.isEnd() == teamID)
                                winLabel.setText("失败");
                            endPane.setVisible(true);
                            return null;
                        } else {
                            battleField.battle(line);
                        }
                        if (line.contains("next")) {
                            battleField.endRound();
                            operationable = 1;
                            endButton.setVisible(true);
                            return null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
                }
            };

            battleField.setTeamMovable(teamID, 1);
            battleField.setTeamAttacking(teamID, 1);
            battleField.setTeamSkilling(teamID, 1);
            operationable = 0;
            moving = 0;
            attacking = 0;
            skilling = 0;
            battleField.hideMovable();
            battleField.hideAttack();
            currentCreature = null;
            for (Node node : characterPane.getChildren()) {
                node.setVisible(false);
            }
            teamImage.setVisible(true);
            battleField.endRound();
            out.println("end");
            new Thread(serverEchoTask).start();
        });

        unsaveButton.setOnAction((event) -> {
            System.exit(0);
        });

        saveButton.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Txt");
            File file = fileChooser.showSaveDialog(new Stage());
            System.out.println(file);

            try{
                File f = new File(file.getPath());
                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                for(String s : battleField.allOperations) {
                    bw.write(s);
                    bw.write('\n');
                }
                bw.close();
                System.exit(0);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void gameBegin() {
        try {
            String serverEcho;
            out.println("connected");
            while ((serverEcho = in.readLine()) != null) {
                if (serverEcho.contains("begin")) {
                    System.out.println(serverEcho);
                    waitPane.setVisible(false);
                    teamID = Integer.parseInt(serverEcho.substring(serverEcho.length()-2, serverEcho.length()-1));
                    battleField.setTeam(teamID);
                    operationable = Integer.parseInt(serverEcho.substring(serverEcho.length()-1));
                    teamImage.setVisible(true);
//                    System.out.println(teamID);
//                    System.out.println(operationable);
                    if (teamID == 2)
                        teamImage.setImage(new Image("/image/YG.jpg"));
                    if (operationable == 1) {
                        endButton.setVisible(true);
                        return;
                    }
                } else if (serverEcho.contains("next")) {
                    battleField.endRound();
                    operationable = 1;
                    endButton.setVisible(true);
                    return;
                } else {
                    System.out.println(serverEcho);
                    battleField.battle(serverEcho);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
