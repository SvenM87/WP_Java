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
public interface IStatisticsAnalyzer {
    public List<Double> getReturns();
    public List<List<Double>> getSeries();
    public double getMedian();
    public double getAverage();
    public double getStdDev();
}
    