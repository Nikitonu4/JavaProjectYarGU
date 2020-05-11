package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.Flow;

public class Main extends Application {

    private ObservableList<String> Animal = FXCollections.observableArrayList();
    private ObservableList<String> City = FXCollections.observableArrayList();
    private ObservableList<String> Flower = FXCollections.observableArrayList();
    private ObservableList<String> Math = FXCollections.observableArrayList();

    private static char[] Alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int alphabetLength = Alphabet.length;

    private HBox hBox = new HBox(20);

    private String topic = "Animal"; //тема на настоящий момент
    private String word = ""; //слово которое загадываем

    private ObservableList<String>  mainTopic = Animal;

    private final Font[] fonts = {
            new Font("Verdana Bold", 20),
            new Font("Arial Italic", 20),
            new Font("Tahoma", 20),
            new Font("Times New Roman", 20)
    };

//    public void starting(){
//        try{
//            Scanner read = new Scanner(new FileReader("Animal.txt"));
//            reading(Animal, read);
//            read = new Scanner(new FileReader("City.txt"));
//            reading(City, read);
//            read = new Scanner(new FileReader("Flower.txt"));
//            reading(Flower, read);
//            read = new Scanner(new FileReader("Math.txt"));
//            reading(Math, read);
//
//
//        } catch (IOException e) {
//            System.out.println("\nUnfortunately, the data from the file did not load.");
//        }
//    }
//    private void reading(ObservableList<String> file, Scanner read) {
//            String []str = read.nextLine().split(" +");
//        Collections.addAll(file, str);
//            read.close();
//    }


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        BorderPane root = new BorderPane();

        root.setTop(createMenu());
        root.setBottom(createFlowPane());
        root.setCenter(createHbox("Animal"));
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
        exitItem.setOnAction(e-> {
            try {
                stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.exit();
        });
        exitMenu.getItems().add(exitItem);
        Menu viewMenu = createViewMenu();
        return new MenuBar(editMenu, viewMenu,exitMenu);
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
        return menuEdit;
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

    private String returnedWord(String nameFile) throws FileNotFoundException { //штука возвращает рандомное слово из nameFile
        Scanner read = new Scanner(new FileReader(nameFile + ".txt"));
        String []str = read.nextLine().split(" +");
        ArrayList<String> file = new ArrayList<>();
        Collections.addAll(file, str);
        Random rand = new Random();
        int numberR = rand.nextInt(file.size());
       return file.get(numberR);
    }

    private HBox createHbox(String nameFile) throws FileNotFoundException {
        word = returnedWord(nameFile); //глобальшина

        char[] charWopic = word.toCharArray(); //делаем массив из строки

        for(int i = 0; i < word.length(); i++) {
            hBox.getChildren().add(createLabelsinWords(charWopic[i]));
            hBox.getChildren().set(i,createLabelsinWords(charWopic[i]));
        }

        System.out.println("Первая буква:" + hBox.getChildren().get(0).toString());

        for (int i = 0; i < word.length() ; i++) {
            System.out.println("Буква:"+  hBox.getChildren().get(i));
        }
        return hBox;
    }

    private Label createLabelsinWords(char s){

        Label label = new Label(s + "");
        label.setPrefWidth(60);
        label.setPrefHeight(60);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        System.out.println(label.getText());
        label.setTextFill(Color.WHITE);

        return label;
    }

    private FlowPane createFlowPane(){
//        InputStream input= getClass().getResourceAsStream("yk.jpg");
//        Image image = new Image(input);
//        ImageView imageView = new ImageView(image);
//потом разобраться в фотке
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(40));
        FlowPane.setMargin(pane, new Insets(0.0, 10.0, 20.0, 0.0));
        pane.setHgap(17);
        pane.setVgap(17);
        pane.setStyle("-fx-font-size: 20");
        for(int i = 0; i < alphabetLength; i++)
            pane.getChildren().add(createLabels(Alphabet[i]));
        return pane;
    }

//    private isLetterinWord(){
//
//    }

    private Label createLabels(char s){

        Label label = new Label(s+"");
        label.setPrefWidth(60);
        label.setPrefHeight(60);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
//        System.out.println(label.getText());
//        label.setGraphic(imageView);
        label.setTextFill(Color.WHITE);
        addTranslateListener(label);
        return label;
    }


//    private void showLetter(){
//
//    }

    private void addTranslateListener(Label node) {

//        scene.setOnKeyPressed(keyEvent -> {
//            if(arr.stream().anyMatch(t->{ return keyEvent.getText().equals(t.getText().toLowerCase()); }))
//                arr.stream().forEach(t->{
//                    if(keyEvent.getText().equals(t.getText().toLowerCase())) {
//                        t.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//                    }else
//                        t.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
//                });
//        });

        node.addEventHandler(MouseEvent.ANY, (e)->node.requestFocus());

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            if ((mouseEvent.getClickCount() == 1)&&(mouseEvent.getButton() == MouseButton.PRIMARY)){
                node.setTextFill(Color.RED);
//                if(node.setText() == )
//                if(буква есть в слове)
                    node.setVisible(false);
            }
        });

        node.setOnMouseEntered((MouseEvent me) -> {
            node.setStyle("-fx-font-size: 30");
            node.requestFocus();
        });

        node.setOnMouseExited((MouseEvent me) -> {
            node.setStyle("-fx-font-size: 20");
        });
    }

    private void ButtonEdit(){
//        String str = dataView.getSelectionModel().getSelectedItem();
        EditTopicDialog edit = new EditTopicDialog();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
