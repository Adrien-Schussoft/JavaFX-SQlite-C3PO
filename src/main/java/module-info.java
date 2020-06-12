module org.adrien {

    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires junit;
    requires c3p0;
    requires java.desktop;
    requires java.naming;

    opens org.adrien.model to javafx.base;
    opens org.adrien.view to javafx.fxml;
    opens org.adrien.controller to javafx.base,javafx.fxml;
    opens org.adrien to javafx.fxml;
    opens org.adrien.model.entity to javafx.base;

    exports org.adrien;
    exports org.adrien.controller;
    exports test.org.adrien.model to junit;
}

