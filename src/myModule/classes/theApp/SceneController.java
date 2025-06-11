/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

//import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
        
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Sven
 */
public class SceneController implements Initializable {
    
    @FXML
    private Button btn_csv_choose;
        
    @FXML
    private TextArea txtArea_CSV_preview;
    
    private final CsvImporter importer = new CsvImporter();
    private final ILoggingService logger = new FileLoggingService();
    
//    private final FileChooser fileChooser = new FileChooser();
//    private final FileChooser.ExtensionFilter txtFilter 
//            = new FileChooser.ExtensionFilter("Text-Dateien", "*.txt");
    private double timeout;
    private Stage stage;
    private Alert alert;
    
    
    @FXML
    private void quit(ActionEvent e) {
        Platform.exit();
    }
    
    @FXML
    private void showInfo(ActionEvent e) {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            Stage theStage = (Stage)alert.getDialogPane().getScene().getWindow();
            theStage.initOwner(stage);
            theStage.getIcons()
                    .add(new Image(this.getClass().getResource("/resources/BRB_Icon.png").toString()));
            alert.setTitle("Irgend ein Titel");
            alert.setHeaderText("Beta Version");
            alert.setContentText("Demo verschiedener Aspekte der JavaFX-Programmierung");
        }
        alert.show();
    }
    
    @FXML
    private void openCSV(ActionEvent e) {
        //File file = fileChooser.showOpenDialog(stage);
        // Dateiauswahl per FileChooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("CSV öffnen");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV-Dateien", "*.csv")
        );
        File file = chooser.showOpenDialog(btn_csv_choose.getScene().getWindow());
        int previewCount = 0;
        if (file == null) {
            logger.logInfo("Keine Datei ausgewählt.");
            return;
        }
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Überspringe leere Zeilen
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    // 
                    if (previewCount < 2) {
                        txtArea_CSV_preview.appendText(line + "\n");
                        previewCount++;
                    }
                }
            } catch (IOException er) {
                System.err.println("Fehler beim Lesen der Datei: " + er.getMessage());
            }
        } catch (Exception ex) {
            logger.logError("Fehler beim Laden der CSV", ex);
//            alert("Fehler", "Die CSV konnte nicht eingelesen werden:\n" + ex.getMessage());
        }
//        try {
//            // CSV einlesen
//            importer.setSeparator(";");  // falls nötig
//            List<String[]> raw = importer.loadFile(file.getAbsolutePath());
//            List<PriceEntry> entries = importer.parseColumns(0, 1);
//
//            // Ergebnisse in TableView anzeigen
//            tablePrices.getItems().setAll(entries);
//            logger.logInfo("CSV geladen: " + entries.size() + " Einträge.");
//
//        } catch (Exception ex) {
//            logger.logError("Fehler beim Laden der CSV", ex);
//            showAlert("Fehler", "Die CSV konnte nicht eingelesen werden:\n" + ex.getMessage());
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        fileChooser.getExtensionFilters().add(txtFilter);
        
        //slider
//        sliderDemo.valueProperty().addListener((obs,oldValue,newValue) -> {
//            timeout = newValue.doubleValue();
//            labelTimeout.setText(String.format("%.0f", timeout));
//        });
        
//        Path p = Paths.get("");
//        String pathToPropFile = p.toAbsolutePath().toString() + 
//                "/configuration/Demo.properties";
//        
//        try (FileInputStream fis = new FileInputStream(pathToPropFile)) {
//            ResourceBundle rbConfig = new PropertyResourceBundle(fis);
//            int intval = Integer.parseInt(rbConfig.getString("numkey"));
//            String stringval = rbConfig.getString("stringKey");
//            labelTimeout.setText(String.format("Zahl: %d%nText: %s%n", intval, stringval));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        
        //iconButton
//        final String url_send = "/resources/Send35_21.png";
//        final Image icon_send = new Image(getClass().getResourceAsStream(url_send));
//        final ImageView imageviewsend = new ImageView(icon_send);
//        iconButton.setText("");
//        iconButton.setGraphic(imageviewsend);
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
