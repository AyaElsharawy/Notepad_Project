package notepad_project;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import notepad_project.NotepadGUIBase;


public class MyNotePad extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        NotepadGUIBase root = new NotepadGUIBase(primaryStage);
        
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Yoka NotePad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
