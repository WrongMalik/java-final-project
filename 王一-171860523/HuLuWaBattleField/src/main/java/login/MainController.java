package login;

import client.Client;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loadfile.LoadFile;
import server.Server;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private AnchorPane mainRoot;
    // 选择模式
    @FXML private Button serverButton;
    @FXML private Button clientButton;
    @FXML private Button loadButton;
    @FXML private Label label;
    // client连接server
    @FXML private Button connectButton;
    @FXML private TextField IPText;
    @FXML private Label connectWrongLabel;
    @FXML private Label IPWrongLabel;

    private Stage mainStage;
    public Client client;
    public Server server;

    public void initialize(URL location, ResourceBundle resources) {
        Main.mainController = this;
        // client连接server，最开始不显示
        connectButton.setVisible(false);
        IPText.setVisible(false);
        connectWrongLabel.setVisible(false);
        IPWrongLabel.setVisible(false);

        // Server Button
        serverButton.setOnAction((event) -> {
            Task<Void> serverControllerTask = new Task<Void>() {
                @Override
                protected Void call() {
                    server.serverController.connectClient();
                    return null;
                }
            };
            processServerButton();
            new Thread(serverControllerTask).start();
        });
        // Client Button
        clientButton.setOnAction((event) -> processClientButton());
        // Load File Button
        loadButton.setOnAction((event) -> {
            try {
                LoadFile loadFile = new LoadFile();
                loadFile.start(new Stage());
                // ！！！注意：getScene需要在initialize之后做，否则会返回null
                mainStage = (Stage) mainRoot.getScene().getWindow();
                mainStage.close();
            } catch (Exception e) {
                System.out.println("打开loadfile窗口失败");
                System.exit(-1);
            }
        });
        // Connect Button
        connectButton.setOnAction((event) -> {
            Task<Void> clientControllerTask = new Task<Void>() {
                @Override
                protected Void call() {
                    client.clientController.gameBegin();
                    return null;
                }
            };
            processConnectButton();
            new Thread(clientControllerTask).start();
        });
    }

    public void processClientButton() {
        // 隐藏上一层
        serverButton.setVisible(false);
        clientButton.setVisible(false);
        loadButton.setVisible(false);
        label.setVisible(false);
        // 显示client连接层
        connectButton.setVisible(true);
        IPText.setVisible(true);
    }

    public void processConnectButton() {
        String IP = IPText.getText();
        if (IP.equals("")) {
            connectWrongLabel.setVisible(false);
            IPWrongLabel.setVisible(true);
        } else {
            client = new Client();
            if (client.connectToServer(IP)) {
                System.out.println("client connect ture");
                try {
                    client.start(new Stage());
                    // ！！！注意：getScene需要在initialize之后做，否则会返回null
                    mainStage = (Stage) mainRoot.getScene().getWindow();
                    mainStage.close();
                } catch (Exception e) {
                    System.out.println("打开client窗口失败");
                    System.exit(-1);
                }
            } else {
                IPWrongLabel.setVisible(false);
                connectWrongLabel.setVisible(true);
            }
        }
    }

    public void processServerButton() {
        server = new Server();
        try {
            server.start(new Stage());
            System.out.println("server create ture");
            // ！！！注意：getScene需要在initialize之后做，否则会返回null
            mainStage = (Stage) mainRoot.getScene().getWindow();
            mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("processServerButton 打开Server窗口失败");
            System.exit(-1);
        }
    }
}
