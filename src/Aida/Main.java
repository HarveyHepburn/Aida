package Aida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private FileHandler fileHandler = new FileHandler();
    private GridPane g1;
    private GridPane g2;
    private GridPane g3;
    TextArea prison;
    GridPane gridPane;
    PasswordField pwd;
    TextField title;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setFullScreen(true);
        prison = (TextArea) root.lookup("#textarea");
        gridPane = (GridPane) root.lookup("#gridP");
        pwd = (PasswordField) root.lookup("#pwd");
        title = (TextField) root.lookup("#title");

        g1 = (GridPane) root.lookup("#g1");
        g2 = (GridPane) root.lookup("#g2");
        g3 = (GridPane) root.lookup("#g3");
        Manager.fileHandler=fileHandler;
        gridPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        prison.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setTitle("Aida");
        primaryStage.addEventFilter(KeyEvent.KEY_TYPED,event -> {
            if(event.isMetaDown()&&Manager.logined){
                if(event.getCharacter().equals("l")){
                    clearEditior();
                    Manager.logined=false;
                    Encrypter.setKeyValue("");
                    g1.setVisible(false);
                    g2.setVisible(false);
                    g3.setVisible(true);
                }
            }
        });
        gridPane.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode().equals(KeyCode.ENTER) && g3.isVisible()) {
                if (Manager.login(pwd.getCharacters().toString())) {
                    LoadChapters();
                }
                pwd.clear();
                return;
            }
        });
        g1.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (event.isMetaDown()) {
                if (event.getCharacter().equals("s")) {
                    Manager.saveFile(prison.getText(), title.getText());
                    return;
                }
                if (event.getCharacter().equals("d") && g1.isVisible()) {
                    Manager.saveFile(prison.getText(), title.getText());
                    toggle();
                    return;
                }
                if (event.getCharacter().equals("e") && g1.isVisible()) {
                    toggle();
                    return;
                }
            }
        });

        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
        primaryStage.show();
    }

    private void LoadChapters() {
        Manager.loadChapter(g2, g1);
        g3.setVisible(false);
        g2.setVisible(true);
    }

    private void toggle() {
        clearEditior();
        Manager.loadChapter(g2, g1);
        g1.setVisible(false);
        g2.setVisible(true);
    }
    private void clearEditior(){
        prison.clear();
        title.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
