package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EditTopicDialog {
    private String topic;
    private Stage dialog;
    private GridPane root;
    private Font font;

    public EditTopicDialog(){
        font = Font.font("Tahoma",20);
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
    }
}
