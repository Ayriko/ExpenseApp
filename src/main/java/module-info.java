module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}