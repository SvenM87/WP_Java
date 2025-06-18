/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theApp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sven
 */
public class StatisticsAnalyzer implements IStatisticsAnalyzer {
    double seriesStart = 100.0;
    private List<Double> returns;
    private List<PriceEntry> prices;
    private int period;
    private List<List<Double>> series;
    private double avg;
    private double median;
    private double standardDeviation;
    private double drawdown;
    
    public StatisticsAnalyzer(List<PriceEntry> prices, int period) {
        prices.sort(Comparator.comparing(PriceEntry::getDate));
        this.series = new ArrayList<>();
        this.prices = prices;
        this.period = period;
        this.returns = this.calculateReturns();
        this.avg = this.calculateAvg();
        this.standardDeviation = this.calculateStdDev();
        this.median = this.calculateMed();
        this.drawdown = this.calculateMaxDrawdown();
    }    
    
    private List<Double> calculateReturns() {
        List<Double> returns = new ArrayList<>();
        
        iteration: {
            for(int i=0; i < prices.size(); i++) {
                List<Double> currentSeries = new ArrayList<Double>();
                PriceEntry current = prices.get(i);
                LocalDate targetDate = current.getDate().plusYears(period);

                PriceEntry future = null;
                currentSeries.add(seriesStart);
                for (int j=i+1; j < prices.size(); j++) {
                    if(!prices.get(j).getDate().isBefore(targetDate)) {
                        future = prices.get(j);
                        break;
                    } else {
                        double newPrice = prices.get(j).getClosePrice();
                        double oldPrice = prices.get(j-1).getClosePrice();
                        double diff = (newPrice - oldPrice) / oldPrice;
                        double last = currentSeries.get(currentSeries.size()-1);
                        currentSeries.add((diff+1)*last);
                    }
                }
                
                series.add(currentSeries);
                if(future == null) break iteration;
                
                returns.add((future.getClosePrice()- current.getClosePrice()) / current.getClosePrice());
            }
        }
        
        if(returns.isEmpty()) {
            throw new IllegalArgumentException("Es konnten keine Returns berechnet werden! Kursdaten zu gering für gewählte Periode!");
        }
        
        this.returns = returns;
        return returns;
    }
    
    public List<List<Double>> getSeries() {
        if (this.series == null || this.series.isEmpty()) this.calculateReturns();
        
        return this.series;
    }
    
    private double calculateAvg() {
        if(returns == null || returns.isEmpty()) this.calculateReturns();
        double sum = 0.0;
        for (Double return1 : returns) {
            sum += return1;
        }
        
        return sum / returns.size();
    }
    
    private double calculateStdDev() {
        double avg = this.calculateAvg();
        double sumSq = 0.0;
        for (double r : returns) {
            sumSq += Math.pow(r - avg, 2);
        }
        return Math.sqrt(sumSq / returns.size());
    }
    
    private double calculateMed() {
        if(returns == null || returns.isEmpty()) this.calculateReturns();
        List<Double> sortedReturns = this.returns;
        sortedReturns.sort(Double::compareTo);
        
        
        if(sortedReturns.size() % 2 == 1) {
            return sortedReturns.get(sortedReturns.size()/2);
        } else {
            double lower = sortedReturns.get(sortedReturns.size()/2);
            double upper = sortedReturns.get((sortedReturns.size()/2)+1);
            return (lower+upper) / 2;
        }        
    }

    private double calculateMaxDrawdown() {
        double min = Double.MAX_VALUE;
        
        for (List<Double> iseries : this.series) {
            double iseriesMin = Collections.min(iseries);
            if (iseriesMin < min) min = iseriesMin;
        }
        double result = (seriesStart-min)/seriesStart;
        return result;
    }

    @Override
    public List<Double> getReturns() {
        return this.returns;
    }

    @Override
    public double getMedian() {
        return this.median;
    }

    @Override
    public double getAverage() {
        return this.avg;
    }
    
    @Override
    public double getStdDev() {
        return this.standardDeviation;
    }
    
    public double getMaxDrawdown() {
        return this.drawdown;
    }
    
    public double getMinReturn() {
        return Collections.min(this.returns);
    }
    
    public double getMaxReturn() {
        return Collections.max(this.returns);
    }
    
    public int getSeriesCount() {
        return this.series.size();
    }
}
