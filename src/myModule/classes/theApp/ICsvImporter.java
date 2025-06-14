/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package theApp;

import java.util.List;

/**
 *
 * @author Sven
 */
public interface ICsvImporter {
    public void setSeparator(String separator);
    public CsvImporter loadFile();
    public CsvImporter parseColumns(int dateIndex, int priceIndex);
    public List<PriceEntry> getEntrys();
}
