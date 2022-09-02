package WGU.wgu_c482_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

public class AddPartsController implements Initializable {

    //allowing widgets within fxml page to be used within code
    public RadioButton inHouse;
    public RadioButton outsourced;
    public Label machineID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {System.out.println("Add Parts Scene Initialized");}

    public void onSave(ActionEvent actionEvent) throws IOException{
        if(inHouse.isSelected()){
            System.out.println("in house was selected");
        }
        else if(outsourced.isSelected()){
            System.out.println("Outsourced was selected");
        }
    }
    public void onInHouse(ActionEvent actionEvent) throws IOException{
        System.out.println("in house was selected");
        machineID.setText("Machine ID");
    }
    public void onOutsourced(ActionEvent actionEvent) throws IOException{
        System.out.println("outsourced was selected");
        machineID.setText("Company Name");
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
