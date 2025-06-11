# 📈 WP_Java – Monte-Carlo-Simulation für Spar- und Entnahmepläne

Dieses Java-Projekt dient zur Durchführung von Monte-Carlo-Simulationen für finanzielle Spar- oder Entnahmepläne. Es bietet einen CSV-Import historischer Kursdaten, statistische Auswertungen und Visualisierungen.

---

## 🚀 Projektüberblick

- 📂 **CSV-Import:** Einlesen und Verarbeiten von historischen Finanzdaten
- 📊 **Statistik:** Berechnung von Renditen, Standardabweichung, Sharpe Ratio
- 🧠 **Simulation:** Monte-Carlo-Verfahren für Spar- und Entnahmepläne
- 📉 **Visualisierung:** Histogramme & Gauß-Kurven als Chart
- 🪵 **Logging:** Fehler- und Prozessprotokollierung in Logdatei

---

## 🛠️ Voraussetzungen & Build

- Java 17 oder höher
- Maven oder Gradle (optional)
- JavaFX installiert (für spätere Visualisierung)
- IDE empfohlen: IntelliJ IDEA, Eclipse oder VS Code mit Java Extension

**Build via Terminal:**

```bash
javac -d out/ src/theApp/**/*.java
java -cp out theApp.MainApp
```

---

## 🧩 Komponenten & Anwendungsbeispiele

Im Folgenden werden zentrale Klassen der Anwendung vorgestellt, die zur Datenverarbeitung und Protokollierung eingesetzt werden.

---

### 📄 `PriceEntry`

Diese Klasse repräsentiert einen einzelnen historischen Kurswert (z. B. aus einer CSV-Datei).

**Felder:**

- `LocalDate date` – das Datum
- `double closePrice` – Schlusskurs an diesem Datum

**Beispiel:**

```java
import theApp.model.PriceEntry;
import java.time.LocalDate;

PriceEntry entry = new PriceEntry(LocalDate.of(2023, 3, 15), 123.45);
System.out.println(entry.getDate());       // Ausgabe: 2023-03-15
System.out.println(entry.getClosePrice()); // Ausgabe: 123.45
System.out.println(entry);                 // Ausgabe: PriceEntry{date=2023-03-15, closePrice=123.45}
```

---

### 📊 `ConfidenceInterval`

Speichert ein Konfidenzintervall mit unterer und oberer Grenze.

**Felder:**

- `double lowerBound` – untere Schranke
- `double upperBound` – obere Schranke

**Beispiel:**

```java
import theApp.model.ConfidenceInterval;

ConfidenceInterval interval = new ConfidenceInterval(10000, 15000);
System.out.println(interval.getLowerBound()); // Ausgabe: 10000.0
System.out.println(interval.getUpperBound()); // Ausgabe: 15000.0
System.out.println(interval); // Ausgabe: ConfidenceInterval{lowerBound=10000.0, upperBound=15000.0}
```

---

### 📥 `CsvImporter`

Importiert Kursdaten aus CSV-Dateien und wandelt sie in `PriceEntry`-Objekte um.

**Typische Anwendung:**

```java
import theApp.services.CsvImporter;
import theApp.model.PriceEntry;
import java.util.List;

CsvImporter importer = new CsvImporter();
importer.setSeparator(";"); // Optional: falls CSV mit Semikolon statt Komma

// Datei laden (Pfad anpassen!)
List<String[]> rawData = importer.loadFile("daten.csv");

// Konkrete Spalten parsen: Spalte 0 = Datum, Spalte 1 = Preis
List<PriceEntry> entries = importer.parseColumns(0, 1);

// Beispiel-Ausgabe
for (PriceEntry entry : entries) {
    System.out.println(entry);
}
```

**Hinweis:**  
Das Datumsformat muss dem ISO-Format `YYYY-MM-DD` entsprechen. Fehlerhafte Zeilen werden automatisch übersprungen.

---

### 🪵 `FileLoggingService`

Einfacher Logging-Service, der Nachrichten in eine Logdatei schreibt.

**Verwendung:**

```java
import theApp.logging.FileLoggingService;
import theApp.logging.ILoggingService;

ILoggingService logger = new FileLoggingService("log.txt");

logger.logInfo("Import gestartet.");

try {
    // ... Anwendungscode ...
} catch (Exception e) {
    logger.logError("Fehler beim Importieren", e);
}

logger.logInfo("Simulation abgeschlossen.");
```

**Beispielausgabe in `log.txt`:**

```
2025-06-11 14:22:31 [INFO] Import gestartet.
2025-06-11 14:22:32 [ERROR] Fehler beim Importieren (Exception: IOException - Datei nicht gefunden)
2025-06-11 14:22:33 [INFO] Simulation abgeschlossen.
```

---

> 🔧 Diese Klassen bilden die Grundlage für Import, Analyse und Logging und lassen sich direkt in Controller oder Services der Anwendung einbinden.

---

## 📌 Nächste Schritte

- [x] Datenklassen implementieren ✅  
- [x] CSV-Importer fertigstellen ✅  
- [x] Logging-Service bereitstellen ✅  
- [ ] Statistik-Analyse umsetzen  
- [ ] Simulationslogik programmieren  
- [ ] Charts generieren und exportieren  
- [ ] Controller & Views verbinden  

---

## 📚 Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Siehe [LICENSE](LICENSE) für Details.
``` ```
