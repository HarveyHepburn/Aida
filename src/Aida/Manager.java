package Aida;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class Manager {
    static List<String> list;
    static FileHandler fileHandler;
    static int currentID=-1;
    static boolean logined=false;
    public static boolean login(String pwd) {
        Encrypter.setKeyValue(pwd);
        try {
            Encrypter.decrypt(Encrypter.loginCode);
            logined=true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void loadChapter(GridPane g2, GridPane g1) {
        list = fileHandler.readChapterList();
        g2.getChildren().clear();
        if (list.size() <= 0) {
            TextFlow tf = getTextFlow("Welcome, click to add your first chapter.");
            tf.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                currentID=-1;
                g2.setVisible(false);
                g1.setVisible(true);
            });
            g2.add(tf, 0, 0);
            return;
        }
        int i = 1;
        for (String name : list) {
            TextFlow tf = getTextFlow((("Chapter " + i++) + ":  "), name);
            final int j = i - 2;
            tf.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                loadEditor(j, g1, name);
                currentID=j;
                g2.setVisible(false);
                g1.setVisible(true);
            });
            g2.add(tf, 0, i - 2);
        }
        TextFlow tf=getTextFlow("Click to add new chapter.");
        tf.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            currentID=-1;
            g2.setVisible(false);
            g1.setVisible(true);
        });
        g2.add(tf, 0, i - 1);
    }

    static private TextFlow getTextFlow(String A, String B) {
        TextFlow tf = new TextFlow();
        tf.getStyleClass().add("chapter");
        tf.getChildren().add(getText(A, "chapter1"));
        tf.getChildren().add(getText(B, "chapter2"));
        return tf;
    }

    static private TextFlow getTextFlow(String A) {
        TextFlow tf = new TextFlow();
        tf.getStyleClass().add("chapter");
        tf.getChildren().add(getText(A, "chapter2"));
        return tf;
    }

    static private Text getText(String A, String styleClass) {
        Text t1 = new Text();
        t1.getStyleClass().add(styleClass);
        t1.setText(A);
        return t1;
    }

    static private void loadEditor(int id, GridPane g1, String title) {
        String content = fileHandler.readChapter(id);
        ((TextArea) g1.lookup("#textarea")).appendText(content);
        ((TextField) g1.lookup("#title")).setText(title);
    }

    static void saveFile(String content, String  title){
        if(currentID>=0){
            if(list.get(currentID).equals(title))
                fileHandler.saveEdit(content,currentID);
            else
                fileHandler.saveEdit(content,currentID,title);
        }else {
            fileHandler.saveFile(content,title);
        }
    }
}
