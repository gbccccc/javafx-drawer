module com.gbccccc.javafxdrawer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.gbccccc.javafxdrawer to javafx.fxml;
    exports com.gbccccc.javafxdrawer;
}