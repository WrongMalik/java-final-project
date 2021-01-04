package server;

import game.BattleField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML public AnchorPane mainPane;

    @FXML public AnchorPane HLWPane;
    @FXML public ProgressIndicator HLWIndicator;
    @FXML public Label HLWWaitLabel;
    @FXML public Label HLWIPLabel;
    @FXML public ImageView HLWimage;

    @FXML public AnchorPane YGPane;
    @FXML public ProgressIndicator YGIndicator;
    @FXML public Label YGWaitLabel;
    @FXML public Label YGIPLabel;
    @FXML public ImageView YGimage;

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

    @FXML public Label ipLabel;

    BattleField battleField;

    protected ServerSocket serverSocket;
    protected List<ClientHandler> clientList;
    protected int clientCount;
    public int port = 8888;

    public int endSocket;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            String ip = Inet4Address.getLocalHost().getHostAddress();
            ipLabel.setText("主机ip："+ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        endSocket = 0;
        Server.serverController = this;
        clientCount = 0;
        clientList = new ArrayList<>();
        battleField = new BattleField(mapPane, 0);
        battleField.addHuLuWa(hlw1, hlw2, hlw3, hlw4, hlw5, hlw6, hlw7);
        battleField.addYaoGuai(xiezijing, shejing, xiaoyao1, xiaoyao2);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("创建Server失败！");
            System.exit(-1);
        }
    }

    public void connectClient() {
        Socket socket;
        ClientHandler clientHandler;
        try {
            while(clientCount < 2) {
                socket = serverSocket.accept();
                //一个客户端接入就启动一个handler线程去处理
                clientCount++;
                clientHandler = new ClientHandler(socket, this, clientCount, battleField);
                clientList.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
