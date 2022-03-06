module hu.doggo.doggo_admininterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    opens hu.doggo.doggo_admininterface to javafx.fxml, com.google.gson;
    exports hu.doggo.doggo_admininterface;
    exports hu.doggo.doggo_admininterface.controllers;
    opens hu.doggo.doggo_admininterface.controllers to com.google.gson, javafx.fxml;
    exports hu.doggo.doggo_admininterface.classes;
    opens hu.doggo.doggo_admininterface.classes to com.google.gson, javafx.fxml;
    exports hu.doggo.doggo_admininterface.api;
    opens hu.doggo.doggo_admininterface.api to com.google.gson, javafx.fxml;
}