/**
 *
 * @author Brendan Thoeung
 */
package project.product_inventory.controller;

import project.product_inventory.model.InHousePart;
import project.product_inventory.model.Inventory;
import project.product_inventory.model.OutsourcedPart;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for add parts scene.*/
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

    /** This is the initialize method.
     This is a method that gets initialized when first landing on the add parts scene.
     @param url Method takes in an url to determine the location of the file.
     @param resourceBundle takes in the resource bundle required.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {System.out.println("Add Parts Scene Initialized");}
    /** This is the onSave method.
     This is a method that stores all information from the corresponding text fields into appropriate variables to create a part object.
     The part object then gets added into the Inventory list and the user is sent back to the main menu scene.
     LOGICAL ERROR: A logical error occured when saving the part if the user entered max values that were less than the minimum,
     and vice versa. Additionally, a logical error occured when the user entered an inventory value above the max value, or below
     the min value. Moreover, if the name field was left blank, then the item would be saved without a name.
     To correct this, I created a control flow statement to satisfy these conditions, and if the conditions
     were not satisfied, then the user will not be able to proceed.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onSave(ActionEvent actionEvent) throws IOException{

        try{
            //boolean used to verify which radio button was selected to create the correct object
            boolean inHouseSelected = true;
            if(inHouse.isSelected()){
                inHouseSelected = true;
            }
            else if(outsourced.isSelected()){
                inHouseSelected = false;
            }
            int id = Inventory.newPartID; //newPartId will increment at the end of the function call to ensure each id is unique
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            //verifying logical errors are in order so that max value cannot be less than min value, and
            //inventory levels within bounds
            if (max < min || stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Max, min, or inventory levels do not make sense. Please correct this before saving.");
                alert.showAndWait();
                //System.out.println("Max value is less than min value. Please correct before saving.");
            }
            else if(name.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please enter a name for the item.");
                alert.showAndWait();
            }
            else{
                if (inHouseSelected == true) {

                    int machineID = Integer.parseInt(machineIDText.getText()); //machineID is labeled but the fields are the same for inHouse or outsourced
                    Inventory.addPart(new InHousePart(id, name, price, stock, min, max, machineID));
                }
                else if (inHouseSelected == false){
                    String machineID = machineIDText.getText(); //machineID is labeled but the fields are the same for inHouse or outsourced
                    Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, machineID));

                }
                Inventory.incrementPartID(); //increment partID if save was successfull
                //information successfully saved. Go back to main menu.
                goBackToMainMenu(actionEvent);
            }

        //if invalid entries are entered into text field, pop up window claiming values are invalid
        }catch(NumberFormatException e){
            //System.out.println("Enter valid values in the provided text field. Thanks!");
            //System.out.println("Exception: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Enter valid values in the provided text fields. Thanks!");
            alert.showAndWait();

        }
    }
    /** This is the onInHouse method.
     This is a method that changes the name of the corresponding label to the appropriate name
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding radio button.
     */
    public void onInHouse(ActionEvent actionEvent) throws IOException{
        //System.out.println("in house was selected");
        machineID.setText("Machine ID");
    }
    /** This is the onOutsourced method.
     This is a method that changes the name of the corresponding label to the appropriate name
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding radio button.
     */
    public void onOutsourced(ActionEvent actionEvent) throws IOException{
        //System.out.println("outsourced was selected");
        machineID.setText("Company Name");
    }
    /** This is the toMain method.
     This is a method that reverts all changes made and takes the user back to the main menu when they confirm to cancel changes.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void toMain(ActionEvent actionEvent) throws IOException { //toMain represents the cancel button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go back to main menu and cancel all changes?");
        alert.setTitle("Confirmation Message");
        Optional<ButtonType> buttonClicked = alert.showAndWait();
        if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
            //if the confirmation window ok button has been selected, continue to cancel and go back to main menu
            goBackToMainMenu(actionEvent);
        }
    }
    ///////////////////HELPER METHODS/////////////////////////////////////////
    /** This is the goBackToMainMenu method.
     This is a helper method that reduces redundancy within the code. This method is called
     whenever it is required that the user go back to the main menu.
     */
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/project/product_inventory/MainMenu.fxml"));

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
    ////////////////////////////////////////////////////////////////////////
}
