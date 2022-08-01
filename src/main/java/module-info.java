module com.pterapan.uts2072038 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;

    opens com.pterapan.uts2072038 to javafx.fxml;
    exports com.pterapan.uts2072038;
    exports com.pterapan.uts2072038.util;
    exports com.pterapan.uts2072038.model;
    exports com.pterapan.uts2072038.controller;
}