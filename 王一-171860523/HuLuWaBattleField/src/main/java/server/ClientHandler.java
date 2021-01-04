package server;

import game.BattleField;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ClientHandler another;
    private ServerController serverController;
    private int index;
    private String clientIP;
    protected PrintWriter out;
    protected BufferedReader in;
    protected int team;
    protected BattleField battleField;
    public ClientHandler(Socket socket, ServerController serverController, int index, BattleField battleField) {
        this.socket = socket;
        another = null;
        clientIP = socket.getInetAddress().toString();
        this.serverController = serverController;
        this.index = index;
        this.battleField = battleField;

        Platform.runLater(() -> {
            if(index == 1) {
                serverController.HLWIndicator.setVisible(false);
                serverController.HLWWaitLabel.setVisible(false);
                serverController.HLWIPLabel.setText(clientIP);
                serverController.HLWIPLabel.setVisible(true);
                serverController.HLWimage.setVisible(true);
            } else {
                serverController.YGIndicator.setVisible(false);
                serverController.YGWaitLabel.setVisible(false);
                serverController.YGIPLabel.setText(clientIP);
                serverController.YGIPLabel.setVisible(true);
                serverController.YGimage.setVisible(true);
            }
        });
    }

    public Socket getSocket() {
        return socket;
    }

    public void run() {
        try {
            System.out.println("server's client run!");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                if(another == null && serverController.clientList.size() == 2) {
                    if(serverController.clientList.get(0).equals(this))
                        another = serverController.clientList.get(1);
                    else
                        another = serverController.clientList.get(0);
                }
                System.out.println("from client " + clientIP + ": " + line);
                if(line.equals("connected") && serverController.clientList.size()==2) {
                    Random r = new Random();
                    int id = r.nextInt(100);
                    int first = r.nextInt(100);
                    // id表示随机分配的team，1：葫芦娃，2：妖怪
                    // first表示随机分配的先后手，0：先手，1：后手
                    id = id % 2 + 1;
                    first = first % 2;
                    System.out.println(id + " " + first);
                    serverController.clientList.get(0).out.println("begin" + id + first);
                    serverController.clientList.get(0).team = id;
                    id = id % 2 + 1;
                    first = (first + 1) % 2;
                    System.out.println(id + " " + first);
                    serverController.clientList.get(1).out.println("begin" + id + first);
                    serverController.clientList.get(1).team = id;
                } else if (line.equals("end")) {
                    // 一方结束回合
                    battleField.endRound();
                    another.out.println("next");
                } else if (line.contains("move")) {
                    // 一方移动
                    out.println(line);
                    another.out.println(line);
                    battleField.battle(line);
                } else if (line.contains("attack")) {
                    // 一方普通攻击
                    // TODO 拼接指令
                    List<String> ops = battleField.translate(line);
                    String allOp = "";
                    for (String op : ops) {
                        allOp = allOp + op + "=";
                    }
                    battleField.battle(allOp);
                    System.out.println("isEnd = " + battleField.isEnd());
                    if(battleField.isEnd() == 0) {
                        out.println(allOp);
                        another.out.println(allOp);
                    } else {
                        out.println(allOp + "exit");
                        another.out.println(allOp + "exit");
                    }
                } else if (line.contains("skill")) {
                    // 一方技能
                    // TODO 拼接指令
                    List<String> ops = battleField.translate(line);
                    String allOp = "";
                    for (String op : ops) {
                        allOp = allOp + op + "=";
                    }
                    battleField.battle(allOp);
                    if(battleField.isEnd() == 0) {
                        out.println(allOp);
                        another.out.println(allOp);
                    } else {
                        out.println(allOp + "exit");
                        another.out.println(allOp + "exit");
                    }
                }
            }
            System.out.println("server's client exit!");
        } catch (IOException e) {
            serverController.endSocket += 1;
            if(serverController.endSocket == 2)
                System.exit(0);
        }
    }
}
