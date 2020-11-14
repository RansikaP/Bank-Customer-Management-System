package coe528.project;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Ransika Perera
 */
public class Project extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DisplayUI.welcome(primaryStage);
    }

    public static void main(String[] args) {        
        launch(args);        
    }   
}
