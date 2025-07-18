module com.krieger {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.tentackle.fx;

    opens com.krieger to javafx.fxml;
    exports com.krieger;
}
