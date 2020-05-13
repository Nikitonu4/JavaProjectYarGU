package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Main extends Application {
    private HBox hBox = new HBox(50);
    private FlowPane pane = new FlowPane();
    private static char[] Alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int alphabetLength = Alphabet.length;
    private final Font[] fonts = {
            new Font("Verdana Bold", 20),
            new Font("Arial Italic", 20),
            new Font("Tahoma", 20),
            new Font("Times New Roman", 20)
    };
    private String topic = "Animal"; //тема на настоящий момент
    private String word = ""; //слово которое загадываем
    private int numberOfLetter = 0;
    public Counting counting = new Counting(15);
    private int numberOfWords = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("2e1201"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setTop(createMenu());
        root.setBottom(createFlowPane());
        root.setCenter(createHbox(topic));
        root.setLeft(createVbox());
        root.setStyle("-fx-font-size: 20");
        primaryStage.setTitle("Guess the word");
        Scene scene = new Scene(root, 1280, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearRoot() {
        hBox.getChildren().clear();
        pane.getChildren().clear();
        try {
            createHbox(topic);
            createFlowPane();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MenuBar createMenu() {

        Menu TopicMenu = new Menu("Topic");
//        TopicMenu.setStyle("-fx-background-color: #2e1201; -fx-font-size: 20; -fx-text-fill: #e9d5c4");

        MenuItem animalTopic = new MenuItem("Animal");
        MenuItem cityTopic = new MenuItem("City");
        MenuItem flowerTopic = new MenuItem("Flower");
        MenuItem mathTopic = new MenuItem("Math");
//animalTopic.setStyle("-fx-background-color: #2e1201; -fx-font-size: 20; -fx-text-fill: #e9d5c4");
        TopicMenu.getItems().add(animalTopic);
        TopicMenu.getItems().add(cityTopic);
        TopicMenu.getItems().add(flowerTopic);
        TopicMenu.getItems().add(mathTopic);


        animalTopic.setOnAction((ActionEvent event) -> {
            topic = "Animal";
            clearRoot();
        });

        cityTopic.setOnAction((ActionEvent event) -> {
            topic = "City";
            clearRoot();
        });

        flowerTopic.setOnAction((ActionEvent event) -> {
            topic = "Flower";
            clearRoot();
        });

        mathTopic.setOnAction((ActionEvent event) -> {
            topic = "Math";
            clearRoot();
        });

        Menu editMenu = new Menu("Edit");
//        editMenu.setStyle("-fx-font-size: 20");
        MenuItem editWords = new MenuItem("Edit list of words");
//        editWords.setStyle("-fx-font-size: 20");
        editWords.setOnAction((ActionEvent event) -> {
//            ButtonEdit();
        });
        editMenu.getItems().add(editWords);

        Menu recordsMenu = new Menu("Records");
        MenuItem recordsItem = new MenuItem("Records");
        recordsMenu.getItems().add(recordsItem);

        recordsItem.setOnAction(ActionEvent -> {
            try {
                rewriteRecord();
                Scanner read = new Scanner(new FileReader("Record.txt"));

                String[] str = read.nextLine().split(" +");
                Integer[] inti = new Integer[3];
                inti[0] = Integer.parseInt(str[0]);
                inti[1] = Integer.parseInt(str[1]);
                inti[2] = Integer.parseInt(str[2]);
                viewRecords(inti);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        Menu exitMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            try {
                rewriteRecord();
                stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.exit();
        });
        exitMenu.getItems().add(exitItem);
        Menu viewMenu = createViewMenu();
        MenuBar bar = new MenuBar(editMenu, viewMenu, exitMenu, TopicMenu, recordsMenu);
        return bar;
    }

    private Menu createViewMenu() {
        Menu menuEdit = new Menu("View");
        Menu menuFont = new Menu("Font selection");
        ToggleGroup groupFont = new ToggleGroup();
        for (int i = 0; i < fonts.length - 1; i++) {
            RadioMenuItem itemFont = new RadioMenuItem(fonts[i].getName());
            itemFont.setUserData(fonts[i]);
            itemFont.setToggleGroup(groupFont);
            menuFont.getItems().add(itemFont);
        }
        MenuItem defaultFont = new MenuItem("Default Font");
        defaultFont.setDisable(true);

        defaultFont.setOnAction((ActionEvent t) -> {
//            .setFont(fonts[3]);
            groupFont.getSelectedToggle().setSelected(false);
            defaultFont.setDisable(true);
        });

        groupFont.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (groupFont.getSelectedToggle() != null) {
                    Font font = (Font) groupFont.getSelectedToggle().getUserData();
//                    description.setFont(font);
                    defaultFont.setDisable(false);
                } else {
                    defaultFont.setDisable(true);
                }
            }
        });

        menuEdit.getItems().addAll(menuFont, defaultFont);
//        menuEdit.setStyle("-fx-background-color: #231309; -fx-background-insets: 0,1,2,3,4; -fx-background-radius: 5; -fx-font-size: 15");
        return menuEdit;
    }

    private String returnedWord(String nameFile) throws FileNotFoundException { //метод возвращает рандомное слово из nameFile
        Scanner read = new Scanner(new FileReader(nameFile + ".txt"));
        String[] str = read.nextLine().split(" +");
        ArrayList<String> file = new ArrayList<>();
        Collections.addAll(file, str);
        Random rand = new Random();
        int numberR = rand.nextInt(file.size());
        return file.get(numberR);
    }

    private HBox createHbox(String nameFile) throws FileNotFoundException { //создание загаданного слова

        hBox.setPadding(new Insets(40));
        HBox.setMargin(hBox, new Insets(40.0, 40.0, 40.0, 40.0));
        word = returnedWord(nameFile);
        char empty = 0;

        char[] charWord = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            hBox.getChildren().add(createLabelsinWords(empty));
            hBox.getChildren().set(i, createLabelsinWords(empty));
            HBox.setHgrow(hBox, Priority.ALWAYS);
        }
        System.out.println("Новое слово:" + word);

        return hBox;
    }

    private VBox createVbox() { //вывод очков
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(40));

        Label label = new Label("Очков сейчас: " + counting.getCounting());
        label.setStyle("-fx-font-size: 20; -fx-text-fill: #e9d5c4;");
        counting.countingProperty().addListener((observable, oldValue, newValue) ->
                label.setText(counting.toString()));
//        label.setStyle("-fx-font-size: 20");
        vbox.getChildren().addAll(label);
//        pane.setStyle("-fx-font-size: 20; -fx-text-fill: #e9d5c4");
        return vbox;
    }

    private Label createLabelsinWords(char s) {
        Label label = new Label(s + "");
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        label.setAlignment(Pos.CENTER);
//        label.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null)));
//        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 50; -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 5; -fx-border-color: #d9ac8d; -fx-alignment: center");

        return label;
    }

    private FlowPane createFlowPane() {
//        InputStream input= getClass().getResourceAsStream("yk.jpg");
//        Image image = new Image(input);
//        ImageView imageView = new ImageView(image);
//потом разобраться в фотке

        pane.setPadding(new Insets(40));
        FlowPane.setMargin(pane, new Insets(0.0, 10.0, 20.0, 0.0));
        pane.setHgap(17);
        pane.setVgap(17);

//        pane.setStyle("-fx-font-size: ;" + "-fx-text-fill: #e9d5c4");
        for (int i = 0; i < alphabetLength; i++)
            pane.getChildren().add(createLabels(Alphabet[i]));
        return pane;
    }

    private Label createLabels(char s) {

        Label label = new Label(s + "");
        label.setPrefWidth(74);
        label.setPrefHeight(74);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 40; -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 10; -fx-border-color: #d9ac8d; -fx-alignment: center");

//        label.setBackground(new Background(new BackgroundFill(Color.web("e9d5c4"), null, null)));
//        label.setTextFill(Color.WHITE);
        addTranslateListener(label);
        return label;
    }

    private void showLetter(String letter) throws FileNotFoundException { //открытие буквы

        char[] charWord = word.toCharArray(); //делаем массив из строки
        int index = word.indexOf(letter); //первое вхождение
        hBox.getChildren().set(index, createLabelsinWords(charWord[index]));
        numberOfLetter++;

        if (word.indexOf(letter, index + 1) == -1) {  //если единственная буква
            if (numberOfLetter == word.length()) {  //если это был,а последняя буква -> новое слово
                System.out.println("Новое слово!");
                numberOfWords++;
                clearRoot();
                numberOfLetter = 0;
            }
            System.out.println("Количетво угаданных слов:" + numberOfWords);
            return;
        }

        while (true) {
            index = word.indexOf(letter, index + 1);
            if (index == -1)
                break;
            hBox.getChildren().set(index, createLabelsinWords(charWord[index]));
            numberOfLetter++;
        }

        if (numberOfLetter == word.length()) {  //если это была последняя буква -> новое слово
            System.out.println("Новое слово!");
            numberOfWords++;
            clearRoot();
            numberOfLetter = 0;
            System.out.println("Количетво угаданных слов:" + numberOfWords);
            return;
        }
    }


    private void rewriteRecord() throws FileNotFoundException {
        Scanner read = new Scanner(new FileReader("Record.txt"));
        String[] str = read.nextLine().split(" +");
        for (int i = 0; i < 3; i++)
            if (Integer.parseInt(str[i]) < numberOfWords) {
                str[i] = numberOfWords + "";
                break;
            }

        read.close();
        Arrays.sort(str);
        PrintWriter out = new PrintWriter("Record.txt");
        out.write("");
        for (int i = 0; i < str.length; i++)
            out.print(str[i] + " ");


        out.close();
    }

    private void addTranslateListener(Label node) {

        node.setOnKeyPressed(keyEvent -> {
            boolean indexOfLetter = word.contains(keyEvent.getText());
            if (indexOfLetter) {
                try {
                    showLetter(keyEvent.getText());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(keyEvent.getText());
            } else {
                        counting.setCounting(counting.getCounting() - 1);
//                        System.out.println(keyEvent.getText());

                    }
                    node.setVisible(false);
                }
        );

        node.addEventHandler(MouseEvent.ANY, (e) -> node.requestFocus());

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            if ((mouseEvent.getClickCount() == 1) && (mouseEvent.getButton() == MouseButton.PRIMARY)) {
                boolean indexOfLetter = word.contains(node.getText());
                if (indexOfLetter) {
                    try {
                        System.out.println(node.getText());
                        showLetter(node.getText());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    counting.setCounting(counting.getCounting() - 1);
                    if (counting.getCounting() == 0) {
                        try {
                            rewriteRecord();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Количетво угаданных слов:" + numberOfWords);
                        gameOver();
                        counting.setCounting(15);
                    }


//                    System.out.println("сделал");
                }
                node.setVisible(false);
            }
        });

        node.setOnMouseEntered((MouseEvent m) -> {
            node.setStyle("-fx-font-size: 50; -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 10; -fx-border-color: #d9ac8d; -fx-alignment: center");
            node.requestFocus();
        });

        node.setOnMouseExited((MouseEvent m) -> {
            node.setStyle("-fx-font-size: 40; -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 10; -fx-border-color: #d9ac8d; -fx-alignment: center");
        });
    }

    private void viewRecords(Integer[] inti) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Records");
        alert.setHeaderText(null);
        alert.setContentText("First record: " + inti[0] + "\n" + "Second record: " + inti[1] + "\n" + "Thid record: " + inti[2]);
        clearRoot();
        alert.showAndWait();
    }

    private void gameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("GAME OVER");
        clearRoot();
        alert.showAndWait();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 20px;");
        alert.showAndWait();
    }

}
