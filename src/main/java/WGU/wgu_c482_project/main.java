/**
 *
 * @author Brendan Thoeung | ID: 007494550 | WGU
 */
package WGU.wgu_c482_project;

import WGU.wgu_c482_project.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

/** This class creates an application that stores and displays products and parts. */
public class main extends Application {
    /** This is the start method of the application.
     This method starts automatically right after default init method and runs to show main menu.
     @param stage Method takes in a stage object to load a scene onto.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml")); //
        Scene scene = new Scene(root, 919, 544);
        stage.setTitle("Part Details Menu");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
    }
    /** This is the main method. It is the first method to get called when running the Java Program. Launches the application to begin methods - init, start, stop.
     FUTURE ENHANCEMENTS: Enhancements I would make to the application to extend the functionality of the application if it were to be updated
     would include the following: I would create a login authorization page to ensure security is in place. I would
     add database functionality to pull in parts and product information from a SQL database or CSV files to import
     existing parts and products. I would improve the graphical user interface to take on an aesthetic that is more
     appeasing to the eye.*/
    public static void main(String[] args) {
        ////////////////////////TESTING////////////////////////////////////////
        /*InHousePart hammer = new InHousePart(1, "Hammer", 5.50, 10, 3, 20, 4441);
        InHousePart screw = new InHousePart(2, "screw", 5.90, 4, 1, 10, 4231);
        InHousePart pencil = new InHousePart(3, "Pencil", 100.00, 99, 98, 1000, 4432);
        Product cup = new Product(10, "cup", 2.00, 8, 1, 21);
        Product jello = new Product(5,"jello", 7.80, 2, 1, 5);
        OutsourcedPart glue = new OutsourcedPart(99, "glue", 200.00, 1, 1,1, "gluegone");
        Inventory.addPart(hammer);
        Inventory.addPart(screw);
        Inventory.addPart(pencil);
        Inventory.addProduct(cup);
        Inventory.addProduct(jello);
        Inventory.addPart(glue);*/
        ////////////////////////////////////////////////////////////////
        launch();
    }
}