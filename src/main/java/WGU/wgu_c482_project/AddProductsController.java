package WGU.wgu_c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {System.out.println("Add Products Scene Initialized");}

    public void onSave(ActionEvent actionEvent) throws IOException{
        System.out.println("Saved!");
    }
    public void onAdd(ActionEvent actionEvent) throws IOException{
        System.out.println("Added!");
    }
    public void onRemove(ActionEvent actionEvent) throws IOException{
        System.out.println("Removed!");
    }
    public void toMain(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //create the new scene
        Scene scene = new Scene(root, 919, 544);
        stage.setTitle("Main Menu");

        //set the scene on the stage
        stage.setScene(scene);

        //show the stage (raise the curtains)
        stage.show();
    }
}
