module com.krieger {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.krieger to javafx.fxml;
    exports com.krieger;
}
