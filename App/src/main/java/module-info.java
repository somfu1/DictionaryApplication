module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.kordamp.bootstrapfx.core;
    requires freetts;
    requires java.sql;
    requires software.amazon.awssdk.services.translate;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.polly;
    requires java.desktop;
    requires software.amazon.awssdk.core;


    opens App to javafx.fxml;
    exports App;
}