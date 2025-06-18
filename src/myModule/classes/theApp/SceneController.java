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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

//import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
        
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Sven
 */
public class SceneController implements Initializable {
    
    @FXML private Button btn_csv_choose;
    @FXML private Button btn_csv_load;
    @FXML private Button btn_start_statistics;
        
    @FXML private TextArea txtArea_CSV_preview;
    
    @FXML private TextField txtField_separator;
    @FXML private TextField txtField_date;
    @FXML private TextField txtField_price;    
    @FXML private TextField txtField_period;
    
    @FXML private CheckBox checkbox_csvHeader;
    
    @FXML private TableView table_csv;
    @FXML private TableColumn table_csv_date;
    @FXML private TableColumn table_csv_price;
    
    @FXML private TableView table_statKeys;
    @FXML private TableColumn table_statKeys_key;
    @FXML private TableColumn table_statKeys_value;
    
    @FXML private LineChart lineChart_chart;
    @FXML private AreaChart areaChart_distribution;
    @FXML private BarChart barChart_distribution;
    
    private boolean separatorChecked = false;
    private boolean dateColumnChecked = false;
    private boolean priceColumnChecked = false;
    private boolean csvReady = false;
    
    private File csvFile;
    
    private final ILoggingService logger = new FileLoggingService();
    private CsvImporter csvImporter = null;
    private StatisticsAnalyzer statAna = null;
    
//    private final FileChooser fileChooser = new FileChooser();
//    private final FileChooser.ExtensionFilter txtFilter 
//            = new FileChooser.ExtensionFilter("Text-Dateien", "*.txt");
    private double timeout;
    private Stage stage;
    private Alert alert;
    
    private String[] allowedSeparators = {",", ";"};
    
    public static class StatisticParam {
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty value;

        public StatisticParam(String name, double value) {
            this.name = new SimpleStringProperty(name);
            this.value = new SimpleDoubleProperty(value);
        }

        public String getName() { return name.get(); }
        public SimpleStringProperty nameProperty() { return name; }

        public double getValue() { return value.get(); }
        public SimpleDoubleProperty valueProperty() { return value; }
    }
    
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
                txtArea_CSV_preview.setText("");
                while ((line = br.readLine()) != null) {
                    // Überspringe leere Zeilen
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    // 
                    if (previewCount < 5) {
                        txtArea_CSV_preview.appendText(line + "\n");
                        previewCount++;
                    } else break;                    
                }
                this.csvFile = file;
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
    
//    @FXML
//    private void checkSeparator(ActionEvent e) {
//        String input = txtField_separator.getText();
//        
//        if (input != null && input.length() == 1 && Arrays.asList(allowedSeparators).contains(input)) {
//            separatorChecked = true;
//            if (separatorChecked && dateColumnChecked && priceColumnChecked) {
//                btn_csv_load.setDisable(false);
//            }
//        } else {
//            alert.setTitle("Ungültiger Separator");
//            alert.setHeaderText(null);
//            alert.setContentText("Bitte geben Sie einen gültigen Separator ein: ein Zeichen, ',' oder ';'");
//            alert.showAndWait();
//        }        
//    }

    
    @FXML
    private void readCsv(ActionEvent e) {
        alert = new Alert(Alert.AlertType.ERROR);
        
        String sepInput = txtField_separator.getText();
        String dateInput = txtField_date.getText();
        String priceInput = txtField_price.getText();
        int dateColumn, priceColumn;
        
        if (this.csvFile == null) {
            alert.setTitle("CSV zunächst laden");
            alert.setHeaderText(null);
            alert.setContentText("Bitte zuerst die CSV laden!");
            alert.showAndWait();
            return;
        }
        
        if (sepInput == null || sepInput.length() != 1 || !(Arrays.asList(allowedSeparators).contains(sepInput))) {
            alert.setTitle("Ungültiger Separator");
            alert.setHeaderText(null);
            alert.setContentText("Bitte geben Sie einen gültigen Separator ein: ein Zeichen, ',' oder ';'");
            alert.showAndWait();
            return;
        }
        
        try {
            dateColumn = Integer.parseInt(dateInput);
        } catch (NumberFormatException ex) {
            alert.setTitle("Ungültige Datum-Spalte");
            alert.setHeaderText(null);
            alert.setContentText("Datum: Bitte geben Sie einen gültigen Spaltenwert ein! (0-9)");
            alert.showAndWait();
            return;
        }
        
        try {
            priceColumn = Integer.parseInt(priceInput);
        } catch (NumberFormatException ex) {
            alert.setTitle("Ungültige Preis-Spalte");
            alert.setHeaderText(null);
            alert.setContentText("Preis: Bitte geben Sie einen gültigen Spaltenwert ein! (0-9)");
            alert.showAndWait();
            return;
        }
        
        this.csvImporter = new CsvImporter(this.csvFile, sepInput, checkbox_csvHeader.isSelected());
        csvImporter.parseColumns(dateColumn, priceColumn);
        //System.out.println(csvImporter.getEntrys());
        
        this.table_csv_date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        this.table_csv_price.setCellValueFactory(new PropertyValueFactory<>("ClosePrice"));
        ObservableList<PriceEntry> observableEntries = FXCollections.observableArrayList(csvImporter.getEntrys());
        this.table_csv.setItems(observableEntries);
        
        this.csvReady = true;
    }
    
    @FXML
    public void calculateStatistics() {
        alert = new Alert(Alert.AlertType.ERROR);

        ObservableList<StatisticParam> statParams;        
        String periodInput = txtField_period.getText();
        int period;
        
        if (csvImporter == null) {            
            alert.setTitle("CSV nicht geladen");
            alert.setHeaderText(null);
            alert.setContentText("Bitte zuerst eine CSV laden.");
            alert.showAndWait();
            return;
        }
        
        try {
            period = Integer.parseInt(periodInput);
        } catch (NumberFormatException ex) {
            alert.setTitle("Ungültige Periode");
            alert.setHeaderText(null);
            alert.setContentText("Bitte nur ganzzahlen für Periode verwenden");
            alert.showAndWait();
            return;
        }
        
        statAna = new StatisticsAnalyzer(csvImporter.getEntrys(), period);
        
        table_statKeys_key.setCellValueFactory(new PropertyValueFactory<>("Name"));
        table_statKeys_value.setCellValueFactory(new PropertyValueFactory<>("Value"));

        statParams = FXCollections.observableArrayList();
        table_statKeys.setItems(statParams);

        List<Double> returns = statAna.getReturns();
        statParams.clear();
        statParams.add(new StatisticParam("Durchschnitt", statAna.getAverage()));
        statParams.add(new StatisticParam("Standardabweichung", statAna.getStdDev()));
        statParams.add(new StatisticParam("Median", statAna.getMedian()));
        
        List<List<Double>> allSeries = statAna.getSeries();
        lineChart_chart.getData().clear();

        int index = 0;
        for (List<Double> ser : allSeries) {
            XYChart.Series<String, Number> fxSeries = new XYChart.Series<>();
            fxSeries.setName("Serie " + (++index));

            for (int i = 0; i < ser.size(); i++) {
                fxSeries.getData().add(new XYChart.Data<>(Integer.toString(i+1), ser.get(i)));
            }

            lineChart_chart.getData().add(fxSeries);
        }

        double min = Collections.min(returns);
        double max = Collections.max(returns);
        int bins = 20; // z. B. 20 Balken

        double binWidth = (max - min) / bins;
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (double r : returns) {
            int b = (int)((r - min) / binWidth);
            if (b == bins) b--; // Randfehler
            frequencies.merge(b, 1, Integer::sum);
        }

        XYChart.Series<String, Number> histSeries = new XYChart.Series<>();
        histSeries.setName("Histogramm");

        for (int b = 0; b < bins; b++) {
            String label = String.format("%.2f–%.2f",
                min + b * binWidth,
                min + (b + 1) * binWidth);
            double freq = frequencies.getOrDefault(b, 0);
            histSeries.getData().add(new XYChart.Data<>(label, freq));
        }

        double mean = statAna.getAverage();
        double std = statAna.getStdDev();

        XYChart.Series<String, Number> densitySeries = new XYChart.Series<>();
        densitySeries.setName("Normalverteilung");

        for (int b = 0; b < bins; b++) {
            double x = min + (b + 0.5) * binWidth;
            double density = (1 / (std * Math.sqrt(2 * Math.PI)))
                * Math.exp(-0.5 * Math.pow((x - mean) / std, 2));
            double scaled = density * returns.size() * binWidth;
            densitySeries.getData().add(new XYChart.Data<>(Double.toString(x), scaled));
        }

//        areaChart_distribution.getData().clear();
        barChart_distribution.getData().clear();
        ((CategoryAxis)barChart_distribution.getXAxis()).setAnimated(false);
//        areaChart_distribution.getData().addAll(densitySeries);
        barChart_distribution.getData().addAll(histSeries);

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
