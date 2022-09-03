package WGU.wgu_c482_project;

import WGU.wgu_c482_project.model.InHousePart;
import WGU.wgu_c482_project.model.Inventory;
import WGU.wgu_c482_project.model.OutsourcedPart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

public class AddPartsController implements Initializable {

    //allowing widgets within fxml page to be used within code
    public RadioButton inHouse;
    public RadioButton outsourced;
    public Button cancelButton;
    public Label machineID;
    public TextField nameText;
    public TextField priceText;
    public TextField invText;
    public TextField minText;
    public TextField maxText;
    public TextField machineIDText;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {System.out.println("Add Parts Scene Initialized");}

    public void onSave(ActionEvent actionEvent) throws IOException{

        try{
            boolean inHouseSelected = true;
            if(inHouse.isSelected()){
                inHouseSelected = true;
            }
            else if(outsourced.isSelected()){
                inHouseSelected = false;
            }
            int id = 1;
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());


            if (inHouseSelected == true) {
                int machineID = Integer.parseInt(machineIDText.getText()); //machineID is labeled but the fields are the same for inHouse or outsourced
                Inventory.addPart(new InHousePart(id, name, price, stock, min, max, machineID));
            }
            else if (inHouseSelected == false){
                String machineID = machineIDText.getText(); //machineID is labeled but the fields are the same for inHouse or outsourced
                Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, machineID));

            }
            //Saves all information from text field and adds it into the inventory. cancelButton fires to get back to main menu.
            cancelButton.fireEvent(new ActionEvent());
        }catch(NumberFormatException e){
            System.out.println("Enter valid values in the provided text field. Thanks!");
            System.out.println("Exception: " + e);

        }



    }
    public void onInHouse(ActionEvent actionEvent) throws IOException{
        //System.out.println("in house was selected");
        machineID.setText("Machine ID");
    }
    public void onOutsourced(ActionEvent actionEvent) throws IOException{
        //System.out.println("outsourced was selected");
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
