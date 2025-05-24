/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import java.net.URL;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Sven
 */
public class MainApp extends Application{
    

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Demo");
        stage.setResizable(false);
        URL icon = MainApp.class.getResource("/resources/BRB_Icon.png");
        Image img = new Image(icon.toString());
        stage.getIcons().add(img);
        SceneController controller = loader.getController();
        controller.setStage(stage);
        
        stage.show();
    }    
    
    public static void main(String[] args) {
        // System.setProperty("file.encoding", "UTF-8");
        // Locale.setDefault(Locale.UK);
        launch(args);
    }
        
}
