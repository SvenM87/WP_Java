/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Repräsentiert einen Preis-Eintrag mit Datum und Schlusskurs.
 */
public class PriceEntry {
    private LocalDate date;
    private double closePrice;

    // Standard-Konstruktor (für Frameworks oder Initialisierung ohne Parameter)
    public PriceEntry() {
    }

    // Konstruktor zur Initialisierung aller Felder
    public PriceEntry(LocalDate date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    // Getter-Methoden
    public LocalDate getDate() {
        return date;
    }

    public double getClosePrice() {
        return closePrice;
    }

    @Override
    public String toString() {
        // Formatierung des Datums zur besseren Lesbarkeit (JJJJ-MM-TT)
        String dateStr = (date != null) 
            ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) 
            : "null";
        return "PriceEntry{date=" + dateStr + ", closePrice=" + closePrice + "}";
    }
}
