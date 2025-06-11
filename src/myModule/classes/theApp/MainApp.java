/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;


import java.net.URL;
import java.util.List;
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
        
        // Debug Code
        URL fxmlUrl = MainApp.class.getResource("/theApp/scene.fxml");
        System.out.println("scene.fxml URL = " + fxmlUrl);
        // Ende
        
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/theApp/scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Demo");
        stage.setResizable(true);
        URL icon = MainApp.class.getResource("/resources/Icon.png");
        Image img = new Image(icon.toString());
        stage.getIcons().add(img);
        SceneController controller = loader.getController();
        controller.setStage(stage);
        
        stage.show();
        
//        URL url = MainApp.class.getResource("/theApp/scene.fxml");
//        FXMLLoader loader = new FXMLLoader(url);
//        Parent root = loader.load();
//        SceneController controller = loader.getController();
//        System.out.println("Controller = " + controller);
//        controller.setStage(stage);
//        stage.setScene(new Scene(root));
//        stage.show();
    }    
    
    public static void main(String[] args) {
        // System.setProperty("file.encoding", "UTF-8");
        // Locale.setDefault(Locale.UK);
        launch(args);
        
//        CsvImporter importer = new CsvImporter(",");
//        // importer.setSeparator(","); // Optional: falls CSV mit Semikolon statt Komma
//
//        // Datei laden (Pfad anpassen!)
//        URL res = MainApp.class.getResource("/resources/MSCIWorld.csv");
//        // Pfad aus der URL
//        String csvPath = res.getPath();
//        List<String[]> rawData = importer.loadFile(csvPath);
//
//        // Konkrete Spalten parsen: Spalte 0 = Datum, Spalte 1 = Preis
//        List<PriceEntry> entries = importer.parseColumns(0, 1);

        // Beispiel-Ausgabe
        // for (PriceEntry entry : entries) {
        //     System.out.println(entry);
        // }
    }
        
}
