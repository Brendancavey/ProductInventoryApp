module com.example.wgu_c482_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens WGU.wgu_c482_project to javafx.fxml;
    opens WGU.wgu_c482_project.model to javafx.fxml;
    opens WGU.wgu_c482_project.controller to javafx.fxml;
    exports WGU.wgu_c482_project;
    exports WGU.wgu_c482_project.model;
    exports WGU.wgu_c482_project.controller;
}