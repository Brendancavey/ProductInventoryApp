/**
 *
 * @author Brendan Thoeung
 */
package project.product_inventory.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.product_inventory.model.Inventory;
import project.product_inventory.model.Part;
import project.product_inventory.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for the modify products scene.*/
public class ModifyProductsController implements Initializable {
    //////////////////////INITIALIZING PRODUCT TEXTFIELDS///////////////////
    public TextField IDText;
    public TextField nameText;
    public TextField priceText;
    public TextField invText;
    public TextField minText;
    public TextField maxText;
    ////////////////////////////////////////////////////////////////////////
    ///////////////////SEARCH FILTER TEXT FIELD////////////////////////////
    @FXML
    private TextField partsFilterText;
    ///////////////////////////////////////////////////////////////////////
    ///////////////////PARTS TABLE VIEW////////////////////////////////////
    @FXML
    private TableView partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIDCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    //////////////////////////////////////////////////////////////////////
    //////////////////ASSOCIATED PARTS TABLE VIEW////////////////////////
    @FXML
    private TableView associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> linkedPartsIDCol;
    @FXML
    private TableColumn<Part, String> linkedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> linkedInvLevelCol;
    @FXML
    private TableColumn<Part, Double> linkedPriceCol;

    /////////////////////////////////////////////////////////////////////
    ////////////////////BUTTONS//////////////////////////////////////////
    @FXML
    private Button cancelButton; //decided against using due to different nature of cancel and save buttons
    ////////////////////////////////////////////////////////////////////
    ////////////////////CREATE TEMPORARY LIST TO HOLD DELETED ASSOCIATED PARTS//////
    //this tempList will hold all current items of the product.
    //If user decides to cancel, copy all items within tempList to the associated parts list to revert to original list
    //before any changes were made
    private ObservableList<Part> tempList = FXCollections.observableArrayList();
    ////////////////CREATING NEW PRODUCT OBJECT TO BE USED FOR MODIFICATION////////////////////////////
    private Product newProduct = null; //new Product(defaultID,defaultString,defaultPrice,defaultStock,defaultMin,defaultMax);
    private int indexOfNewProduct = -1;
    ///////////////////////////////////////////////////////////////////////
    ////////////////////////INITIALIZE//////////////////////////////////////
    /** This is the initialize method.
     This is a method that gets initialized when first landing on the modify products scene.
     @param url Method takes in an url to determine the location of the file.
     @param resourceBundle takes in the resource bundle required.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Products Scene Initialized");
        ///////////////INITIALIZING newProduct RECEIVED FROM MAIN MENU////////////////////////////
        try {
            newProduct = MainController.getNewProduct();
            indexOfNewProduct = Inventory.getAllProducts().indexOf(newProduct); //store index to use for update
            tempList.setAll(newProduct.getAllAssociatedParts());//initialize tempList to capture status of selected products associated parts list
        }catch(NullPointerException e){
            System.out.println("Not sure why the first try catch null pointer exception didnt work under main menu");

        }
        ///////////////INITIALIZING PARTS TABLE VIEW///////////////////////
        //calls get-method from Parts class that corresponds to the parameter
        partsTableView.setItems(Inventory.getAllParts());
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        /////////////////////////////////////////////////////////////////////
        /////////////////////INITIALIZING ASSOCIATED PARTS TABLE VIEW///////////
        //Need a second try catch exception for when user does not select an item to modify?
        try {
            associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
        }catch(NullPointerException e){
            System.out.println("Not sure why the first try catch null pointer exception didnt work under main menu");
        }
        linkedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        linkedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        linkedInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        linkedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        /////////////////////INITIALIZING SEARCH FILTER FUNCTION//////////////
        partsFilterText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //when the parts filter text field detects a change in text, set the table view
                //to where helper method filter is called with the current filter text
                //as the parameter
                partsTableView.setItems(Inventory.lookupPart(partsFilterText.getText()));
            }
        });
    }
    //////////////////WIDGET BUTTONS//////////////////////////////////////////
    /** This is the onSave method.
     This is a method that stores all information from the corresponding text fields into appropriate variables.
     A product object that was initialized sets their appropriate instance variables to the corresponding variables and
     the index of the selected item that the user wanted to modify gets replaced/updated to the new product.
     The user is then sent back to the main menu scene.
     LOGICAL ERROR: A logical error occured when saving the product if the user entered max values that were less than the minimum,
     and vice versa. Additionally, a logical error occured when the user entered an inventory value above the max value, or below
     the min value. Moreover, if the name field was left blank, then the item would be saved without a name.
     To correct this, I created a control flow statement to satisfy these conditions, and if the conditions
     were not satisfied, then the user will not be able to proceed.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onSave(ActionEvent actionEvent) throws IOException{
        try{
            System.out.println("Saved!");
            //get all information entered into text fields and store values into appropriate variables.
            int id = Integer.parseInt(IDText.getText());
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
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
                //use temporary product object to save information into product
                newProduct.setId(id);
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setMin(min);
                newProduct.setMax(max);
                newProduct.setStock(stock);
                //update the product stored in product list with index containing same id. Since id is not modifiable,
                //then id is the parameter used to find appropriate item in product list.
                Inventory.updateProduct(indexOfNewProduct, newProduct);
                //go back to main menu when product has been successfully updated.
                goBackToMainMenu(actionEvent);
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setContentText("Make sure to enter valid entries into text field!");
            alert.showAndWait();
        }

    }
    /** This is the onAdd method.
     This is a method that creates a part object that corresponds to the user selection from the parts table view.
     The part object gets added to the product associated parts list if the selection is not null.
     LOGICAL ERROR: A logical error occured when adding a part to the associated parts list. The user could continue
     to add the same parts to the associated parts list. In order to correct this, I created a control flow statement to
     check if the item was already contained within the associated parts list. If so, then part would not be added.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onAdd(ActionEvent actionEvent) throws IOException{
        //create temporary part to be used as the selected part to add to associated part list
        Part selectPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        if(selectPart == null){return;}
        //ensures to only add the part if the part doesnt exist within associated parts list
        else if(!(newProduct.getAllAssociatedParts().contains(selectPart))){
            newProduct.addAssociatedPart(selectPart);
            System.out.println("Added!");
        }
        //associatePartsTableview is called here since any updated values get called after the scene has been initialized.
        //by calling the table view onAdd, it ensures that the newProduct associated with the table shows the appropriate-associatedPartList
        //associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
    }
    /** This is the onRemove method.
     This is a method that creates a part object that corresponds to the user selection from the associated parts table view.
     If the user confirms to delete, then the part gets removed from the associated parts list that corresponds to the current product.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onRemove(ActionEvent actionEvent) throws IOException{
        //create a temporary part to be used as the selected associated part
        Part selectedAssociatedPart = (Part)associatedPartsTableView.getSelectionModel().getSelectedItem();
        //if nothing is selected, no need to pop up confirmation window
        if(selectedAssociatedPart == null){return;}
        //else if something is selected, confirm with user if the selected item is to be deleted.
        else{
            System.out.println("Removed!");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this associated part?");
            alert.setTitle("Confirmation Message");

            Optional<ButtonType> buttonClicked = alert.showAndWait();

            if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
                //if the confirmation window ok button has been selected, continue to delete the selected item.
                //call delete associated part to find matching ID to the selected associated part. If matching ID is found,
                //then the associated part is removed from the associated part list
                newProduct.deleteAssociatedPart(selectedAssociatedPart);
            }
        }
    }
    /** This is the toMain method.
     This is a method that reverts all changes made and takes the user back to the main menu when they confirm to cancel changes.
     LOGICAL ERROR: A logical error occured when attempting to cancel changes to the product. If the user attempted to add
     associated parts to the product and decided to cancel changes, the product would still contain the added items if the user
     were to attempt to modify the same product. In order to correct this, I had to create a temporary list that captured
     the status of associated parts list of the product as soon as the modify product scene was initialized. If the user
     decided to cancel, then the current associated parts list was cleared, and the temp list was copied into the associated
     parts list.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void toMain(ActionEvent actionEvent) throws IOException { //represents cancel button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go back to main menu and cancel all changes?");
        alert.setTitle("Confirmation Message");
        Optional<ButtonType> buttonClicked = alert.showAndWait();
        if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
            //if the confirmation window ok button has been selected, continue to cancel changes.
            //if user decided to cancel, clear the associated parts list for any changes done to associated list
            // add all items from tempList back into products associated list.
            newProduct.getAllAssociatedParts().clear();
            newProduct.getAllAssociatedParts().setAll(tempList);
            //after all items have been copied over. Make sure to clear tempList
            tempList.clear();
            //go back to main menu
            goBackToMainMenu(actionEvent);
        }
    }
    /** This is the onSearchPart method.
     This is a method that uses the corresponding text field as a search query and displays an error message if the search query is unable to find the desired item.
     @param actionEvent Method takes in an action event that gets triggered when the user interacts with the corresponding action (the enter key).
     */
    public void onSearchPart(ActionEvent actionEvent) throws IOException{
        System.out.println("Searching...");
        //lookupPart returns the original list if nothing is found. Therefore, show error message claiming nothing was wound if user fires action event onSearchPart or
        //press the enter button
        if(Inventory.lookupPart(partsFilterText.getText()) == Inventory.getAllParts()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Unable to find any items within search parameters");
            alert.showAndWait();
        }
    }
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////HELPER METHODS//////////////////////////////////
    /** This is the sendProducttInformation method.
     This is a helper method that captures product information from a different scene, and displays
     the information to the corresponding labels within the current scene.
     @param product Method takes in a product object
     */
    public void sendProductInformation(Product product){ //used to obtain product information when selecting from main menu product list
        IDText.setText(String.valueOf(product.getId()));
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        invText.setText(String.valueOf(product.getStock()));
        minText.setText(String.valueOf(product.getMin()));
        maxText.setText(String.valueOf(product.getMax()));
        //associatedPartsTableView.setItems(product.getAllAssociatedParts());
    }
    /** This is the goBackToMainMenu method.
     This is a helper method that reduces redundancy within the code. This method is called
     whenever it is required that the user go back to the main menu.
     */
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        //load widget hierarchy of next screen
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
    ///////////////////////////////////////////////////////////////////////
}