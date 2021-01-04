package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    public static ClientController clientController;
    public int port = 8888;
    private Parent root;

    public Client() {
        try {
            root = FXMLLoader.load(getClass().getResource("client.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建Client窗口失败！");
            System.exit(-1);
        }
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public boolean connectToServer(String IP) {
        try {
            clientController.clientSocket = new Socket(IP, port);
            clientController.out = new PrintWriter(clientController.clientSocket.getOutputStream(), true);
            clientController.in = new BufferedReader(new InputStreamReader(clientController.clientSocket.getInputStream()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
