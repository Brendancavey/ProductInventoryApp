module com.example.product_inventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.product_inventory to javafx.fxml;
    opens project.product_inventory.model to javafx.fxml;
    opens project.product_inventory.controller to javafx.fxml;
    exports project.product_inventory;
    exports project.product_inventory.model;
    exports project.product_inventory.controller;
}