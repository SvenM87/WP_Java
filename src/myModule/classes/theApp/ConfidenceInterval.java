/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

/**
 * Container für ein Konfidenzintervall mit unterem und oberem Wert.
 */
public class ConfidenceInterval {
    private double lowerBound;
    private double upperBound;

    // Standard-Konstruktor
    public ConfidenceInterval() {
    }

    // Konstruktor zur Initialisierung beider Grenzen
    public ConfidenceInterval(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    // Getter-Methoden
    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    @Override
    public String toString() {
        return "ConfidenceInterval{lowerBound=" + lowerBound 
             + ", upperBound=" + upperBound + "}";
    }
}