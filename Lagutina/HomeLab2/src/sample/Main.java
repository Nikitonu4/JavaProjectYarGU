package sample;

import javafx.application.Application;
import javafx.application.Platform;
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
    private static char[] Alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int alphabetLength = Alphabet.length;
    public Counting counting = new Counting(15);
    private HBox hBox = new HBox(50);
    private VBox vBox = new VBox();
    private FlowPane pane = new FlowPane();
    private String font = "Tahoma";
    private String back = "2e1201";
    private String topic = "Animal"; //тема на настоящий момент
    private String word = ""; //слово которое загадываем
    private int numberOfLetter = 0;
    private int numberOfWords = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web(back), CornerRadii.EMPTY, Insets.EMPTY)));
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
        vBox.getChildren().clear();
        try {
            createHbox(topic);
            createFlowPane();
            createVbox();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MenuBar createMenu() {
        Menu TopicMenu = new Menu("Topic");

        MenuItem animalTopic = new MenuItem("Animal");
        MenuItem cityTopic = new MenuItem("City");
        MenuItem flowerTopic = new MenuItem("Flower");
        MenuItem mathTopic = new MenuItem("Math");
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

        Menu fontMenu = createFontMenu();
        return new MenuBar(TopicMenu, fontMenu, recordsMenu, exitMenu);
    }

    private Menu createFontMenu() {
        Menu FontMenu = new Menu("Fonts");
        MenuItem font1 = new MenuItem("Verdana Bold");
        MenuItem font2 = new MenuItem("Arial Italic");
        MenuItem font3 = new MenuItem("Tahoma");
        MenuItem font4 = new MenuItem("Times New Roman");

        FontMenu.getItems().add(font1);
        FontMenu.getItems().add(font2);
        FontMenu.getItems().add(font3);
        FontMenu.getItems().add(font4);

        font1.setOnAction((ActionEvent event) -> {
            font = "Verdana Bold";
            clearRoot();
        });

        font2.setOnAction((ActionEvent event) -> {
            font = "Arial Italic";
            clearRoot();
        });

        font3.setOnAction((ActionEvent event) -> {
            font = "Tahoma";
            clearRoot();
        });

        font4.setOnAction((ActionEvent event) -> {
            font = "Times New Roman";
            clearRoot();
        });

        return FontMenu;
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

        for (int i = 0; i < word.length(); i++) {
            hBox.getChildren().add(createLabelsinWords(empty));
            hBox.getChildren().set(i, createLabelsinWords(empty));
            HBox.setHgrow(hBox, Priority.ALWAYS);
        }
        System.out.println("Новое слово:" + word);

        return hBox;
    }

    private VBox createVbox() {
        vBox.setPadding(new Insets(40));

        Label label = new Label("Очков сейчас: " + counting.getCounting());
        label.setStyle("-fx-font-size: 20; -fx-text-fill: #e9d5c4;");
        label.setFont(new Font(font, 20));
        counting.countingProperty().addListener((observable, oldValue, newValue) ->
                label.setText(counting.toString()));
        vBox.getChildren().addAll(label);
        return vBox;
    }

    private Label createLabelsinWords(char s) {
        Label label = new Label(s + "");
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        label.setFont(new Font(font, 50));
        label.setStyle("-fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 5; -fx-border-color: #d9ac8d; -fx-alignment: center");
        return label;
    }

    private FlowPane createFlowPane() {
        pane.setPadding(new Insets(40));
        FlowPane.setMargin(pane, new Insets(0.0, 10.0, 20.0, 0.0));
        pane.setHgap(17);
        pane.setVgap(17);

        for (int i = 0; i < alphabetLength; i++)
            pane.getChildren().add(createLabels(Alphabet[i]));
        return pane;
    }

    private Label createLabels(char s) {
        Label label = new Label(s + "");
        label.setPrefWidth(74);
        label.setPrefHeight(74);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(font, 40));
        label.setStyle(" -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 10; -fx-border-color: #d9ac8d; -fx-alignment: center");
        addTranslateListener(label);
        return label;
    }

    private void showLetter(String letter) { //открытие буквы

        char[] charWord = word.toCharArray(); //делаем массив из строки
        int index = word.indexOf(letter); //первое вхождение
        hBox.getChildren().set(index, createLabelsinWords(charWord[index]));
        numberOfLetter++;

        if (word.indexOf(letter, index + 1) == -1) {  //если единственная буква
            if (numberOfLetter == word.length()) {
                System.out.println("Новое слово!");
                numberOfWords++;
                System.out.println("Количество угаданных слов:" + numberOfWords);
                clearRoot();
                numberOfLetter = 0;
            }
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
            System.out.println("Количество угаданных слов:" + numberOfWords);
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
                        showLetter(keyEvent.getText());
//                        System.out.println(keyEvent.getText());
                    } else {
                        counting.setCounting(counting.getCounting() - 1);
                    }
                    node.setVisible(false);
                }
        );

        node.addEventHandler(MouseEvent.ANY, (e) -> node.requestFocus());

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            if ((mouseEvent.getClickCount() == 1) && (mouseEvent.getButton() == MouseButton.PRIMARY)) {
                boolean indexOfLetter = word.contains(node.getText());
                if (indexOfLetter) {
//                    System.out.println(node.getText());
                    showLetter(node.getText());
                } else {
                    counting.setCounting(counting.getCounting() - 1);
                    if (counting.getCounting() == 0) {
                        try {
                            rewriteRecord();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        gameOver();
                        counting.setCounting(15);
                    }
                }
                node.setVisible(false);
            }
        });

        node.setOnMouseEntered((MouseEvent m) -> {
            node.setFont(new Font(font, 50));
            node.setStyle("-fx-font-size: 50; -fx-text-fill: #e9d5c4; -fx-border-width: 4; -fx-border-radius: 10; -fx-border-color: #d9ac8d; -fx-alignment: center");
            node.requestFocus();
        });

        node.setOnMouseExited((MouseEvent m) -> {
            node.setFont(new Font(font, 40));
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
}
