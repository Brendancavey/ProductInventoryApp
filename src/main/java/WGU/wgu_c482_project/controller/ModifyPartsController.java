/**
 *
 * @author Brendan Thoeung | ID: 007494550 | WGU
 */
package WGU.wgu_c482_project.controller;

import WGU.wgu_c482_project.model.InHousePart;
import WGU.wgu_c482_project.model.Inventory;
import WGU.wgu_c482_project.model.OutsourcedPart;
import WGU.wgu_c482_project.model.Part;
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

/** This class is the controller for modify parts scene.*/
public class ModifyPartsController implements Initializable {

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
    public TextField IDText;
    //////////////////CREATING NEW PART OBJECT TO BE USED FOR MODIFICATION///////////////
    Part modifyPart = null;
    /////////////////////////////////////////////////////////////////////////////////
    /** This is the initialize method.
     This is a method that gets initialized when first landing on the modify parts scene.
     @param url Method takes in an url to determine the location of the file.
     @param resourceBundle takes in the resource bundle required.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Parts Scene Initialized");
        modifyPart = MainController.getModifyPart(); //initializing modifyPart with information sent from main controller
    }

    //////////////////////////WIDGET BUTTONS///////////////////////////////////////
    /** This is the onSave method.
     This is a method that stores all information from the corresponding text fields into appropriate variables to create a part object.
     The part object then replaces the object within the Inventory list with the same index. The user is then sent back to the main menu scene.
     LOGICAL ERROR: A logical error occured when saving the part if the user entered max values that were less than the minimum,
     and vice versa. Additionally, a logical error occured when the user entered an inventory value above the max value, or below
     the min value. Moreover, if the name field was left blank, then the item would be saved without a name.
     To correct this, I created a control flow statement to satisfy these conditions, and if the conditions
     were not satisfied, then the user will not be able to proceed.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onSave(ActionEvent actionEvent) throws IOException{
        try{
            boolean inHouseSelected = true;
            if(inHouse.isSelected()){
                inHouseSelected = true;
            }
            else if(outsourced.isSelected()){
                inHouseSelected = false;
            }
            int id = Integer.parseInt(IDText.getText());
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            //verifying logical errors are in order so that max value cannot be less than min value, and
            //inventory is within bounds
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
            else {
                //getting index of modified item to use for update within inventory updatePart method
                int indexOfModifyPart = Inventory.getAllParts().indexOf(modifyPart);
                if (inHouseSelected == true) {
                    int machineID = Integer.parseInt(machineIDText.getText()); //machineID is labeled but the fields are the same for inHouse or
                    InHousePart modifiedItem = new InHousePart(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(indexOfModifyPart, modifiedItem);
                } else if (inHouseSelected == false) {
                    String machineID = machineIDText.getText(); //machineID is labeled but the fields are the same for inHouse or outsourced
                    OutsourcedPart modifiedItem = new OutsourcedPart(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(indexOfModifyPart, modifiedItem);

                }
                //Saves all information from text field and adds it into the inventory. Go back to main menu
                goBackToMainMenu(actionEvent);
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Make sure to enter valid entries into text field.");
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
    public void toMain(ActionEvent actionEvent) throws IOException { //to main represents the cancel button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go back to main menu and cancel all changes?");
        alert.setTitle("Confirmation Message");
        Optional<ButtonType> buttonClicked = alert.showAndWait();
        if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
            //if the confirmation window ok button has been selected, continue cancel changes and go back to main menu
            goBackToMainMenu(actionEvent);
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////HELPER METHODS//////////////////////////////////////
    /** This is the sendPartInformation method.
     This is a helper method that captures part information from a different scene, and displays
     the information to the corresponding labels within the current scene.
     @param part Method takes in a part object
     */
    public void sendPartInformation(Part part){
        IDText.setText(String.valueOf(part.getId()));
        nameText.setText(part.getName());
        priceText.setText(String.valueOf(part.getPrice()));
        invText.setText(String.valueOf(part.getStock()));
        minText.setText(String.valueOf(part.getMin()));
        maxText.setText(String.valueOf(part.getMax()));
        //machineIDText.setText(String.valueOf(part.getMachineID()));
        if(part instanceof InHousePart){
            machineIDText.setText(String.valueOf(((InHousePart)part).getMachineID()));
            inHouse.fire();
        }
        else if(part instanceof OutsourcedPart){
            //machineIDText is the name of the text field even though I am getting the company name
            //due to the outsourced part.
            machineIDText.setText(((OutsourcedPart)part).getCompanyName());
            outsourced.fire();
        }
    }
    /** This is the goBackToMainMenu method.
     This is a helper method that reduces redundancy within the code. This method is called
     whenever it is required that the user go back to the main menu.
     */
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/WGU/wgu_c482_project/MainMenu.fxml"));

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
    /////////////////////////////////////////////////////////////////////////////////
}