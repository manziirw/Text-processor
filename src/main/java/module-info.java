module application.textprocessingtool {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens application to javafx.fxml;
    exports application;
}