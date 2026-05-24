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

    opens com.schimer.reportsapp.controllers.auth to javafx.fxml;
    opens com.schimer.reportsapp.controllers.admin to javafx.fxml;
    opens com.schimer.reportsapp.controllers.components to javafx.fxml;
    opens db.migration;
    opens com.schimer.reportsapp.domain.entities to org.hibernate.orm.core;

    exports com.schimer.reportsapp;
}