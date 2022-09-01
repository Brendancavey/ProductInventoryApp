module com.example.wgu_c482_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens WGU.wgu_c482_project to javafx.fxml;
    exports WGU.wgu_c482_project;
}