/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package theApp;

/**
 *
 * @author Sven
 */
public interface ILoggingService {
    public void logInfo(String msg);
    public void logError(String msg, Exception ex);
    public String getLogFilePath();
}
