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

    opens hu.doggo.doggo_admininterface to javafx.fxml;
    exports hu.doggo.doggo_admininterface;
    exports hu.doggo.doggo_admininterface.controllers;
    opens hu.doggo.doggo_admininterface.controllers to javafx.fxml;
}