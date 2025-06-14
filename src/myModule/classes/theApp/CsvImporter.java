/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import theApp.PriceEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-Importer, der das ICsvImporter-Interface implementiert.
 * Liest CSV-Dateien ein und wandelt sie in PriceEntry-Listen um.
 */
public class CsvImporter implements ICsvImporter {
    private String separator;
    private File csvFile;
    private boolean hasHeader;
    private List<String[]> dataRows;  // zwischengespeicherte, geparste CSV-Daten
    private List<PriceEntry> priceEntries;
    
    // Formatter für Datum
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        // erstes Pattern: Tag.Monat.Jahr
        .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        // zweites Pattern: Monat.Jahr, Tag default = 1
        .appendOptional(
            new DateTimeFormatterBuilder()
                .appendPattern("MM.yyyy")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter()
        )
        .toFormatter();

    // Standard-Konstruktor mit Vorgabewerten
    public CsvImporter(File csvFile, String separator, boolean hasHeader) {
        this.csvFile = csvFile;
        this.separator = separator;
        this.hasHeader = hasHeader;
        this.dataRows = new ArrayList<>();
    }

    @Override
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public CsvImporter loadFile() {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Bei Bedarf die erste Zeile (Header) überspringen
                if (firstLine && hasHeader) {
                    firstLine = false;
                    continue;
                }
                firstLine = false;
                // Überspringe leere Zeilen
                if (line.trim().isEmpty()) {
                    continue;
                }
                // Zeile anhand des Separators aufteilen
                String[] columns = line.split(separator);
                rows.add(columns);
            }
            // Speichere die gelesenen Daten intern ab
            this.dataRows = rows;
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
        return this;
    }

    @Override
    public CsvImporter parseColumns(int dateIndex, int priceIndex) {
        if (this.dataRows == null || this.dataRows.isEmpty()) {
            this.loadFile();
        }
        this.priceEntries = new ArrayList<>();
        for (String[] columns : dataRows) {
            try {
                // Parse Datum und Preis aus den angegebenen Spalten
                String dateText = columns[dateIndex];
                String priceText = columns[priceIndex];
                LocalDate date = LocalDate.parse(dateText, formatter);
                double price = Double.parseDouble(priceText);
                price = Math.round(price * 100) / 100;      //rundet auf 2 Stellen
                priceEntries.add(new PriceEntry(date, price));
            } catch (Exception e) {
                // Bei Format-Problemen: Zeile überspringen (und ggf. Fehlermeldung ausgeben)
                System.err.println("Überspringe ungültige Zeile: " + e.getMessage());
            }
        }
        return this;
    }
    
    @Override
    public List<PriceEntry> getEntrys() {
        if (this.priceEntries == null || this.priceEntries.isEmpty()) {
            throw new IllegalStateException("Bitte zuerst parseColumns(int dateIndex, int priceIndex) aufrufen."); 
        }
        return this.priceEntries;
    }
}