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
    public List<String[]> loadFile(String path);
    public List<PriceEntry> parseColumns(int dateIndex, int priceIndex);
}
