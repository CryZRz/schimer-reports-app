module com.schimer.reportsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires flyway.core;
    requires java.naming;
    requires java.sql;
    requires spring.security.crypto;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.desktop;
    requires commons.logging;
    requires javafx.base;
    requires dropbox.core.sdk;
    requires javafx.web;
    requires fr.opensagres.xdocreport.document;
    requires fr.opensagres.xdocreport.template;
    requires fr.opensagres.xdocreport.document.docx;
    requires fr.opensagres.xdocreport.core;
    requires fr.opensagres.xdocreport.converter;
    requires jakarta.mail;

    opens com.schimer.reportsapp.models;
    opens com.schimer.reportsapp.controllers.auth to javafx.fxml;
    opens com.schimer.reportsapp.controllers.admin to javafx.fxml;
    opens com.schimer.reportsapp.controllers.guest to javafx.fxml;
    opens com.schimer.reportsapp.controllers.guest.profile to javafx.fxml;
    opens com.schimer.reportsapp.controllers.guest.rawMaterial to javafx.fxml;
    opens com.schimer.reportsapp.controllers.components to javafx.fxml;
    opens db.migration;
    opens com.schimer.reportsapp.domain.entities to org.hibernate.orm.core, javafx.base;
    opens com.schimer.reportsapp.domain.entities.rawMaterial to org.hibernate.orm.core, javafx.base;

    exports com.schimer.reportsapp;
}