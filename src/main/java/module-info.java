module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    opens com.example.demo.model to javafx.fxml;
    exports com.example.demo.db;
    opens com.example.demo.db to javafx.fxml;
}