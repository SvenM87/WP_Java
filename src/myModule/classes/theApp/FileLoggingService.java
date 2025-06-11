/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Einfacher Logging-Service, der Nachrichten in eine Logdatei schreibt.
 */
public class FileLoggingService implements ILoggingService {
    private File logFile;

    // Standard-Konstruktor: legt eine Logdatei "application.log" im aktuellen Verzeichnis an
    public FileLoggingService() {
        this("application.log");
    }

    // Konstruktor mit Pfadangabe für Logdatei
    public FileLoggingService(String filePath) {
        this.logFile = new File(filePath);
        try {
            // Erstellt die Datei, falls sie noch nicht existiert
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Konnte Logdatei nicht erstellen: " + e.getMessage());
        }
    }

    @Override
    public void logInfo(String msg) {
        writeLog("INFO", msg, null);
    }

    @Override
    public void logError(String msg, Exception ex) {
        writeLog("ERROR", msg, ex);
    }

    // Zentrale Schreibmethode für Logs (append an Datei)
    private void writeLog(String level, String msg, Exception ex) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Baue die Log-Zeile zusammen
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(timestamp)
                .append(" [").append(level).append("] ")
                .append(msg);
        if (ex != null) {
            logEntry.append(" (Exception: ").append(ex.getClass().getSimpleName())
                    .append(" - ").append(ex.getMessage()).append(")");
        }
        // Schreiben in die Datei
        try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
            pw.println(logEntry.toString());
            // Optional: Exception-Stacktrace für ERROR ins Log schreiben
            if (ex != null) {
                ex.printStackTrace(pw);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Logdatei: " + e.getMessage());
        }
    }

    // Getter für den Log-Dateipfad (optional, z.B. für Tests)
    public String getLogFilePath() {
        return logFile.getAbsolutePath();
    }
}