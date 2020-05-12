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
    public Record counting = new Record(15);
    private int numberOfWords = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BorderPane root = new BorderPane();
//root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setTop(createMenu());
        root.setBottom(createFlowPane());
        root.setCenter(createHbox(topic));
        root.setLeft(createVbox());
        root.setStyle("-fx-font-size: 20");
        primaryStage.setTitle("Guess the word");
        Scene scene = new Scene(root, 1080, 900);
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

        Menu editMenu = new Menu("Edit");
        editMenu.setStyle("-fx-font-size: 20");
        MenuItem editWords = new MenuItem("Edit list of words");
        editWords.setStyle("-fx-font-size: 20");
        editWords.setOnAction((ActionEvent event) -> {
//            ButtonEdit();
        });
        editMenu.getItems().add(editWords);

        Menu recordsMenu = new Menu("Records");
        MenuItem recordsItem = new MenuItem("Records");

        recordsMenu.getItems().add(recordsItem);

        recordsItem.setOnAction(ActionEvent -> {

                    Scanner read = null;
                    try {
                        read = new Scanner(new FileReader("Records.txt"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String[] str = read.nextLine().split(" +");
                    Integer[] inti = new Integer[3];
                    inti[0] = Integer.parseInt(str[0]);
                    inti[1] = Integer.parseInt(str[1]);
                    inti[2] = Integer.parseInt(str[2]);
                    Arrays.sort(inti);
                    viewRecords(inti);

                }
        );


//        MenuItem choiceTopic = new MenuItem("Choosing a topic for words");


//        editTopic.setStyle("-fx-font-size: 20");
//        MenuItem tCB = new MenuItem();
//
//        editTopic.getItems().add(tCB);

//        Menu viewMenu = new Menu("View");
//        viewMenu.setStyle("-fx-font-size: 20");
//        MenuItem editFont = new MenuItem("Edit font");
//        editFont.setStyle("-fx-font-size: 20");
//        viewMenu.getItems().add(editFont);
//        MenuItem editBackground = new MenuItem("Edit background");
//        editBackground.setStyle("-fx-font-size: 20");
//        viewMenu.getItems().add(editBackground);

        Menu exitMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            try {
                stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.exit();
        });
        exitMenu.getItems().add(exitItem);
        Menu viewMenu = createViewMenu();
        return new MenuBar(editMenu, viewMenu, exitMenu, TopicMenu, recordsMenu);
    }

//    Scanner read = new Scanner(new FileReader("Animal.txt"));
//    reading(Animal, read);
//    read = new Scanner(new FileReader("City.txt"));
//    reading(City, read);
//    read = new Scanner(new FileReader("Flower.txt"));
//    reading(Flower, read);
//    read = new Scanner(new FileReader("Math.txt"));
//    reading(Math, read);
//
//
//} catch (IOException e) {
//        System.out.println("\nUnfortunately, the data from the file did not load.");
//        }
//        }
//private void reading(ObservableList<String> file, Scanner read) {
//        String []str = read.nextLine().split(" +");
//        Collections.addAll(file, str);
//        read.close();
//        }

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
        return menuEdit;
    }

    private String returnedWord(String nameFile) throws FileNotFoundException { //штука возвращает рандомное слово из nameFile
        Scanner read = new Scanner(new FileReader(nameFile + ".txt"));
        String[] str = read.nextLine().split(" +");
        ArrayList<String> file = new ArrayList<>();
        Collections.addAll(file, str);
        Random rand = new Random();
        int numberR = rand.nextInt(file.size());
        return file.get(numberR);
    }

    private HBox createHbox(String nameFile) throws FileNotFoundException {
        hBox.setPadding(new Insets(40));
        HBox.setMargin(hBox, new Insets(40.0, 40.0, 40.0, 40.0));
        word = returnedWord(nameFile); //глобальщина
        char empty = 0;

        char[] charWord = word.toCharArray(); //делаем массив из строки
        for (int i = 0; i < word.length(); i++) {
            hBox.getChildren().add(createLabelsinWords(empty));
            hBox.getChildren().set(i, createLabelsinWords(empty));
            HBox.setHgrow(hBox, Priority.ALWAYS);
        }
        System.out.println("Слово:" + word);

        return hBox;
    }

    private VBox createVbox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(40));
        Label cNow = new Label("Очков сейчас:");
        Label label = new Label(counting.getCounting() + "");
        counting.countingProperty().addListener((observable, oldValue, newValue) ->
                label.setText(counting.toString()));
        label.setStyle("-fx-font-size: 20");
        vbox.getChildren().addAll(cNow, label);
//        vbox.getChildren().add(label);
        return vbox;
    }

    private Label createLabelsinWords(char s) {
        Label label = new Label(s + "");
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null)));
//        System.out.println(label.getText());
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 80");

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

        pane.setStyle("-fx-font-size: 20");
        for (int i = 0; i < alphabetLength; i++)
            pane.getChildren().add(createLabels(Alphabet[i]));
        return pane;
    }

    private Label createLabels(char s) {

        Label label = new Label(s + "");
        label.setPrefWidth(60);
        label.setPrefHeight(60);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        label.setTextFill(Color.WHITE);
        addTranslateListener(label);
        return label;
    }

    private void showLetter(String letter) throws FileNotFoundException { //открытие буквы
        char[] charWord = word.toCharArray(); //делаем массив из строки
        for (int i = 0; i < word.length(); i++) {
            int index = word.indexOf(letter, i); //если несколько вхождений, начинаем с индекса последнего вхождения
            if (index != -1) {
                hBox.getChildren().set(index, createLabelsinWords(charWord[index]));
                numberOfLetter++;
            } else {
//                System.out.println(numberOfLetter);
                if (numberOfLetter == word.length()) {
                    rewriteRecord();
                    System.out.println("новое слово!");
                    clearRoot();
                    numberOfLetter = 0;
                    return;
                }
                return;
            }
        }
    }

    private void rewriteRecord() throws FileNotFoundException {
        Scanner read = new Scanner(new FileReader("Records.txt"));
        String[] str = read.nextLine().split(" +");
        for (int i = 0; i < 3; i++)
            if (Integer.parseInt(str[i]) < counting.getCounting())
                str[i] = counting.toString();
        read.close();

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
                    if (counting.getCounting() == 0)
                        gameOver();
//                    System.out.println("сделал");
                }
                node.setVisible(false);
            }
        });

        node.setOnMouseEntered((MouseEvent m) -> {
            node.setStyle("-fx-font-size: 40");
            node.requestFocus();
        });

        node.setOnMouseExited((MouseEvent m) -> {
            node.setStyle("-fx-font-size: 20");
        });
    }

    private void viewRecords(Integer[] inti) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Records");
        alert.setHeaderText(null);
        alert.setContentText("First record: " + inti[0] + " слов" + "\n" + "Second record: " + inti[1] + " слов" + "\n" + "Thid record: " + inti[2] + " слов");
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
