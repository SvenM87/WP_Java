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

    // Standard-Konstruktor mit Vorgabewerten
    public CsvImporter() {
        this.separator = ",";
        this.hasHeader = true;
        this.dataRows = new ArrayList<>();
    }

    // Optionaler Konstruktor, um Header-Flag zu setzen
    public CsvImporter(boolean hasHeader) {
        this();
        this.hasHeader = hasHeader;
    }

    @Override
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public List<String[]> loadFile(String path) {
        this.csvFile = new File(path);
        List<String[]> rows = new ArrayList<>();
        if (!csvFile.exists()) {
            System.err.println("Datei nicht gefunden: " + path);
            return rows; // leere Liste zurückgeben, falls Datei nicht existiert
        }
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
        return rows;
    }

    @Override
    public List<PriceEntry> parseColumns(int dateIndex, int priceIndex) {
        if (this.dataRows == null || this.dataRows.isEmpty()) {
            throw new IllegalStateException("Keine CSV-Daten geladen. Bitte zuerst loadFile() aufrufen.");
        }
        List<PriceEntry> priceEntries = new ArrayList<>();
        for (String[] columns : dataRows) {
            try {
                // Parse Datum und Preis aus den angegebenen Spalten
                String dateText = columns[dateIndex];
                String priceText = columns[priceIndex];
                LocalDate date = LocalDate.parse(dateText);           // erfordert Format YYYY-MM-DD
                double price = Double.parseDouble(priceText);
                priceEntries.add(new PriceEntry(date, price));
            } catch (Exception e) {
                // Bei Format-Problemen: Zeile überspringen (und ggf. Fehlermeldung ausgeben)
                System.err.println("Überspringe ungültige Zeile: " + e.getMessage());
            }
        }
        return priceEntries;
    }
}