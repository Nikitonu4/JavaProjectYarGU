package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class Topic {
    private String nameOfTopic;
    private String[] words;

    public String getNameOfTopic() {
        return nameOfTopic;
    }

    public void setNameOfTopic(String nameOfTopic) {
        this.nameOfTopic = nameOfTopic;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

public Topic(String nameOfTopic, String[] words){
    ObservableList<String> langs = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
    ComboBox<String> langsComboBox = new ComboBox<String>(langs);
    langsComboBox.setValue("Java"); // устанавливаем выбранный элемент по умолчанию

    Label lbl = new Label();

    // получаем выбранный элемент
    langsComboBox.setOnAction(event -> lbl.setText(langsComboBox.getValue()));

    FlowPane root = new FlowPane(10, 10, langsComboBox, lbl);
}


}
