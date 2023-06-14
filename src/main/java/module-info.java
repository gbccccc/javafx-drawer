module com.gbccccc.javafxdrawer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires lombok;

    opens com.gbccccc.javafxdrawer to javafx.fxml;
    exports com.gbccccc.javafxdrawer;
    exports com.gbccccc.javafxdrawer.gui;
    opens com.gbccccc.javafxdrawer.gui to javafx.fxml;
    exports com.gbccccc.javafxdrawer.gui.canvas.element;
    exports com.gbccccc.javafxdrawer.shape.util;
    exports com.gbccccc.javafxdrawer.gui.canvas.factory;
}