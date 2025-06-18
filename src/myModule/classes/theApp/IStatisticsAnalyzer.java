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
    public List<Double> calculateReturns();
    public double calculateAvg();
    public double calculateStdDev();
}
