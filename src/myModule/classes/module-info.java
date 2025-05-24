/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2SEModule/module-info.java to edit this template
 */

module myModule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    opens theApp to javafx.fxml;
    exports theApp;
}
