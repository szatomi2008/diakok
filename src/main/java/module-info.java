module hu.szatomi.diakok {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.szatomi.diakok to javafx.fxml;
    exports hu.szatomi.diakok;
}