package loadfile;

import creature.huluwa.Three;
import game.BattleField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoadFileController implements Initializable {
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

    @FXML public Button beginButton;
    @FXML public Button nextButton;

    public BattleField battleField;
    private File file;
    private List<String> operations;
    private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        battleField = new BattleField(mapPane, 0);
        battleField.addHuLuWa(hlw1, hlw2, hlw3, hlw4, hlw5, hlw6, hlw7);
        battleField.addYaoGuai(xiezijing, shejing, xiaoyao1, xiaoyao2);

        LoadFile.loadFileController = this;
        file = null;
        operations = new ArrayList<>();
        index = 0;

        beginButton.setOnAction((event) -> {
            beginButton.setVisible(false);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Txt");
            file = fileChooser.showOpenDialog(new Stage());
            System.out.println(file);

            try{
                File f = new File(file.getPath());
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String s;
                while((s = reader.readLine()) != null) {
                    operations.add(s);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });

        nextButton.setOnAction((event) -> {
            if(index < operations.size()) {
                display(operations.get(index));
                index++;
            } else {
                nextButton.setVisible(false);
            }
        });
    }

    private void display(String s) {

        battleField.battle(s);
    }

}
