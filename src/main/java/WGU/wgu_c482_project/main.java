package WGU.wgu_c482_project;

import WGU.wgu_c482_project.model.InHousePart;
import WGU.wgu_c482_project.model.Inventory;
import WGU.wgu_c482_project.model.Part;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml")); //
        Scene scene = new Scene(root, 919, 544);
        stage.setTitle("Part Details Menu");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        InHousePart hammer = new InHousePart(1, "Hammer", 5.50, 10, 3, 20, 4441);
        InHousePart screw = new InHousePart(2, "screw", 5.90, 4, 1, 10, 4231);
        InHousePart pencil = new InHousePart(3, "Pencil", 100.00, 99, 98, 1000, 4432);

        Inventory.addPart(hammer);
        Inventory.addPart(screw);
        Inventory.addPart(pencil);
        System.out.println(Inventory.getAllParts());

        launch();
    }
}