package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Flow;

public class Main extends Application {
    private ListView<String> dataView;
    private ObservableList<String> Animal = FXCollections.observableArrayList();
    private ObservableList<String> City = FXCollections.observableArrayList();
    private ObservableList<String> Flower = FXCollections.observableArrayList();
    private ObservableList<String> Math = FXCollections.observableArrayList();
    private static char[] Alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static Random sRandom = new Random();
    private static int alphabetLength = Alphabet.length;
    public static char getRandomChar() {
        return Alphabet[sRandom.nextInt(alphabetLength)];
    }

    @Override
    public void init(){
        try{
            Scanner read = new Scanner(new FileReader("Animal.txt"));
            reading(Animal, read);
//            read.close();
            read = new Scanner(new FileReader("City.txt"));
            reading(City, read);
            read = new Scanner(new FileReader("Flower.txt"));
            reading(Flower, read);
//            read.close();
            read = new Scanner(new FileReader("Math.txt"));
            reading(Math, read);
//            read.close();

        } catch (IOException e) {
            System.out.println("\nUnfortunately, the data from the file did not load.");
        }
    }
    private void reading(ObservableList<String> file, Scanner read) {
            String []str = read.nextLine().split(" +");
            for(int i = 0; i < str.length; i++)
                file.add(str[i]);
            read.close();
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Tab mainActivity = new Tab("Игра");
        BorderPane root = new BorderPane();
        root.setTop(createMenu());

        root.setBottom(createFlowPane());
//        TabPane root = new TabPane(mainActivity);
        root.setStyle("-fx-font-size: 20");

        primaryStage.setTitle("Guess the word");
        Scene scene = new Scene(root,1080,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenu(){
        Menu editMenu = new Menu("Edit");
        editMenu.setStyle("-fx-font-size: 20");
        MenuItem editWords = new MenuItem("Edit list of words");
        editWords.setStyle("-fx-font-size: 20");
        editWords.setOnAction((ActionEvent event) -> {
            ButtonEdit();
        });
        editMenu.getItems().add(editWords);
        MenuItem choiceTopic = new MenuItem("Choosing a topic for words");
        choiceTopic.setStyle("-fx-font-size: 20");
        editMenu.getItems().add(choiceTopic);

        Menu viewMenu = new Menu("View");
        viewMenu.setStyle("-fx-font-size: 20");
        MenuItem editFont = new MenuItem("Edit font");
        editFont.setStyle("-fx-font-size: 20");
        viewMenu.getItems().add(editFont);
        MenuItem editBackground = new MenuItem("Edit background");
        editBackground.setStyle("-fx-font-size: 20");
        viewMenu.getItems().add(editBackground);

        Menu exitMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e-> {
            try {
                stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.exit();
        });
        exitMenu.getItems().add(exitItem);

        return new MenuBar(editMenu,viewMenu,exitMenu);
    }

    private FlowPane createFlowPane(){
//        InputStream input= getClass().getResourceAsStream("yk.jpg");
//        Image image = new Image(input);
//        ImageView imageView = new ImageView(image);
//потом разобраться в фотке
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(40));
        pane.setMargin(pane, new Insets(0.0, 10.0, 20.0, 0.0));
        pane.setHgap(17);
        pane.setVgap(17);
        pane.setStyle("-fx-font-size: 20");
        for(int i = 0; i < alphabetLength; i++)
        pane.getChildren().add(createLabels(Alphabet[i]));

        return pane;
    }

    private Label createLabels(char s){

        Label label = new Label(s+"");
        label.setPrefWidth(60);
        label.setPrefHeight(60);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));

//        label.setGraphic(imageView);
        label.setTextFill(Color.WHITE);
        addTranslateListener(label);
        return label;
    }

    private Label createWord(){
return null;
    }

    private void addTranslateListener(Label node) {

        node.addEventHandler(MouseEvent.ANY, (e)->node.requestFocus());

//        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
//            if (mouseEvent.getButton() == MouseButton.SECONDARY)
//                node.setTextFill(Color.BLACK);
//
//        });

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            if ((mouseEvent.getClickCount() == 1)&&(mouseEvent.getButton() == MouseButton.PRIMARY)){
                node.setTextFill(Color.RED);
//                if(буква есть в слове)
                    node.setVisible(false);
            }
        });

//        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
//            if (mouseEvent.getClickCount() == 2){
//                node.setText(Character.toString(getRandomChar()));
//            }});

        node.setOnMouseEntered((MouseEvent me) -> {
//            System.out.println("Mouse entered");
            node.setStyle("-fx-font-size: 30");
            node.requestFocus();
        });

        node.setOnMouseExited((MouseEvent me) -> {
//            System.out.println("Mouse exited");
            node.setStyle("-fx-font-size: 20");
        });
    }

    private void ButtonEdit(){
//        String str = dataView.getSelectionModel().getSelectedItem();
        EditTopicDialog edit = new EditTopicDialog();



    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 20px;");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
