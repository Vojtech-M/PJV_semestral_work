module cz.fel.cvut.pjv.semestral {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires java.logging;
    requires static lombok;

    opens cz.fel.cvut.pjv.semestral to javafx.fxml;
    exports cz.fel.cvut.pjv.semestral.view;
    opens cz.fel.cvut.pjv.semestral.view to javafx.fxml;
    exports cz.fel.cvut.pjv.semestral.model;
    opens cz.fel.cvut.pjv.semestral.model to javafx.fxml;
    exports cz.fel.cvut.pjv.semestral.controller;
    opens cz.fel.cvut.pjv.semestral.controller to javafx.fxml;
    exports cz.fel.cvut.pjv.semestral.model.utils;
    opens cz.fel.cvut.pjv.semestral.model.utils to javafx.fxml;


}