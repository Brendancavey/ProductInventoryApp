/**
 *
 * @author Brendan Thoeung
 */
package project.product_inventory.controller;

import project.product_inventory.model.Inventory;
import project.product_inventory.model.Part;
import project.product_inventory.model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for the main menu.*/
public class MainController implements Initializable {
    /////////////SEND PRODUCT OBJECT FOR MODIFY PRODUCT SCENE////////////
    private static Product newProduct = null;
    /** This is a method to return a Product object.
     This method is used to send a Product object initialized in this controller to another controller.
     @return newProduct Method returns a Product object that has been initialized in MainController.java.
     */
    public static Product getNewProduct(){
        return newProduct;
    }
    /////////////////////////////////////////////////////////////
    ///////////SEND PART OBJECT FOR MODIFY PART SCENE/////////////////
    private static Part modifyPart = null;
    /** This is a method to return a Part object.
     This method is used to send a Part object initialized in this controller to another controller.
     @return modifyPart Method returns a Part object that has been initialized in MainController.java.
     */
    public static Part getModifyPart(){return modifyPart;}
    /////////////////////////////////////////////////////////////
    //////////////INITIALIZING PARTS VARIABLES///////////////////
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIDCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    @FXML
    private TextField partsFilterText;


    ///////////////////////////////////////////////////////
    ////////////INITIALIZING PRODUCTS VARIABLES////////////////
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsIDCol;
    @FXML
    private TableColumn<Product, String> productsNameCol;
    @FXML
    private TableColumn<Product, Integer> productsInvLevelCol;
    @FXML
    private TableColumn<Product, Double> productsPriceCol;
    @FXML
    private TextField productsFilterText;
    /////////////////////////////////////////////////////////

    /** This is the initialize method.
     This is a method that gets initialized when first landing on the main menu scene.
     @param url Method takes in a url to determine the location of the file main menu.
     @param resourceBundle takes in the resource bundle required for main menu.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Menu Scene Initialized");
        ///////////////INITIALIZING PARTS TABLE VIEW///////////////////////
        //calls get-method from Parts class that corresponds to the parameter
        partsTableView.setItems(Inventory.getAllParts());
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        /////////////////////////////////////////////////////////////////////
        /////////////////////INITIALIZING PRODUCTS TABLE VIEW//////////////
        //calls get-method from Products class that corresponds to the parameter
        productsTableView.setItems((Inventory.getAllProducts()));
        productsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        ///////////////////////INITIALIZING SEARCH FILTER FUNCTIONs///////////////////////
        partsFilterText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //when the parts filter text field detects a change in text, set the table view
                //to where helper method filter is called with the current filter text
                //as the parameter
                partsTableView.setItems(Inventory.lookupPart(partsFilterText.getText()));

            }
        });
        productsFilterText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                productsTableView.setItems(Inventory.lookupProduct(productsFilterText.getText()));
            }
        });
        //////////////////////////////////////////////////////////////////////////////////

    }

    /////////////////////////PARTS WIDGETS ACTIONS/////////////////////////////////////////////////////////////
    /** This is the onAddPart method.
     This is a method takes the user to the add part scene.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(MainController.class.getResource("/project/product_inventory/AddParts.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //create the new scene
        Scene scene = new Scene(root, 650, 590);
        stage.setTitle("Add Parts Menu");

        //set the scene on the stage
        stage.setScene(scene);

        //show the stage (raise the curtains)
        stage.show();
    }
    /** This is the onModifyPart method.
     This is a method takes the user to the modify part scene.
     RUNTIME ERROR: A runtime error occured when attempting to modify a part without first selecting a part from the parts
     tableview. I corrected the issue by assigning a null pointer exception when clicking the corresponding button and verifying with the
     user to select a part to modify.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        try{
            //create fxml loader object to let loader object know which scene to view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/project/product_inventory/ModifyParts.fxml"));
            modifyPart = partsTableView.getSelectionModel().getSelectedItem(); //save the selection into a modifyPart object to send to the modify part scene
            loader.load();

            //allowing fxml loader know which controller to use. The controller allows the use of any
            //public methods within modify parts controller file
            ModifyPartsController mpcController = loader.getController();
            //pass information from current controller to modifyPartsController using getSelection method to fill in appropriate text fields
            mpcController.sendPartInformation(partsTableView.getSelectionModel().getSelectedItem());

            //go to modify Parts scene
            Parent root = loader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 650, 590);
            stage.setTitle("Modify Parts Menu");
            stage.setScene(scene);
            stage.show();
        //if no selection was made, throw an error message to select an item
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select the item you would like to modify.");
            alert.showAndWait();
        }



    }
    /** This is the onDeletePart method.
     This is a method that deletes a part that corresponds to the user's selection.
     RUNTIME ERROR: A runtime error occured when attempting to delete a part without first selecting a part from the parts
     tableview. I corrected the issue by assigning a null pointer exception when clicking the corresponding button and verifying with the
     user to select a part to delete.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onDeletePart(ActionEvent actionEvent) throws IOException{
        //try to delete, but catch the error if user attempts to delete an item without first selecting the item to delete.
        try {
            //if the user does select a valid item to delete, show a confirmation window with the ID of the selected item to confirm.
            int idToDelete = partsTableView.getSelectionModel().getSelectedItem().getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete item with ID: " + (idToDelete) + "?");
            alert.setTitle("Confirmation Message");

            Optional<ButtonType> buttonClicked = alert.showAndWait();

            if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
                //if the confirmation window ok button has been selected, continue to delete the selected item.
                Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
            }
        //if the user does not select an item and attempts to delete, then delete function causes a nullpointer exception error
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select the item you would like to delete.");
            alert.showAndWait();
        }
    }
    /** This is the onSearchPart method.
     This is a method that uses the corresponding text field as a search query and displays an error message if the search query is unable to find the desired item.
     @param actionEvent Method takes in an action event that gets triggered when the user interacts with the corresponding action (the enter key).
     */
    public void onSearchPart(ActionEvent actionEvent) throws IOException{
        System.out.println("Searching...");
        //filterPart returns the original list if nothing is found. Therefore, show error message claiming nothing was wound if user fires action event onSearchPart or
        //press the enter button
        if(Inventory.lookupPart(partsFilterText.getText()) == Inventory.getAllParts()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Unable to find any items within search parameters");
            alert.showAndWait();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////PRODUCT WIDGETS ACTIONS/////////////////////////////////////////////////////////
    /** This is the onAddProduct method.
     This is a method takes the user to the add product scene.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/project/product_inventory/AddProducts.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //create the new scene
        Scene scene = new Scene(root, 1089, 590);
        stage.setTitle("Add Products Menu");

        //set the scene on the stage
        stage.setScene(scene);

        //show the stage (raise the curtains)
        stage.show();
    }
    /** This is the onModifyProduct method.
     This is a method takes the user to the modify product scene.
     RUNTIME ERROR: A runtime error occured when attempting to modify a product without first selecting a product from the product
     tableview. I corrected the issue by assigning a null pointer exception when clicking the corresponding button and verifying with the
     user to select a product to modify.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        try{
            //create fxml loader object to let loader object know which scene to view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/product_inventory/ModifyProducts.fxml"));
            //setting newProduct before loader is loaded to save the information before loading loader scene
            //used so that modifyScreen can receive Product information from main menu screen
            newProduct = productsTableView.getSelectionModel().getSelectedItem(); //get selection and save into a product object to send to modify product scene

            //load fxmlloader
            loader.load();

            //allowing fxml loader know which controller to use. The controller allows the use of any
            //public methods within modify parts controller file
            ModifyProductsController mpcController = loader.getController();
            //pass information from current controller to modifyPartsController using getSelection method
            mpcController.sendProductInformation(productsTableView.getSelectionModel().getSelectedItem());

            //go to modify products scene
            Parent root = loader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1089, 590);
            stage.setTitle("Modify Products Menu");
            stage.setScene(scene);
            stage.show();

            //if no selection was made, throw an error message to select an item
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select the product you would like to modify.");
            alert.showAndWait();
        }
    }
    /** This is the onDeleteProduct method.
     This is a method that deletes a product that corresponds to the user's selection.
     RUNTIME ERROR: A runtime error occured when attempting to delete a product without first selecting a product from the product
     tableview. I corrected the issue by assigning a null pointer exception when clicking the corresponding button and verifying with the
     user to select a product to delete.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button.
     */
    public void onDeleteProduct(ActionEvent actionEvent) throws IOException{
        //try to delete, but catch the error if user attempts to delete an item without first selecting the item to delete.
        try {
            //will only show confirmation window to delete if the size of the associated part lists of the selected product is equal to 0
            if (productsTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() == 0) {
                //if the user does select a valid item to delete, show a confirmation window with the ID of the selected item to confirm.
                int idToDelete = productsTableView.getSelectionModel().getSelectedItem().getId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete item with ID: " + (idToDelete) + "?");
                alert.setTitle("Confirmation Message");

                Optional<ButtonType> buttonClicked = alert.showAndWait();

                if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
                    //if the confirmation window ok button has been selected, continue to delete the selected item.
                    Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Message");
                alert.setContentText("Will not delete selected product until all associated parts are removed from product.");
                alert.showAndWait();
            }
            //if the user does not select an item and attempts to delete, then delete function causes a nullpointer exception error
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Select the item you would like to delete.");
            alert.showAndWait();
        }

    }
    /** This is the onSearchProduct method.
     This is a method that uses the corresponding text field as a search query and displays an error message if the search query is unable to find the desired item.
     @param actionEvent Method takes in an action event that gets triggered when the user interacts with the corresponding action (the enter key).
     */
    public void onSearchProduct(ActionEvent actionEvent) throws IOException{
        System.out.println("Searching...");
        //filterProduct returns the original list if nothing is found. Therefore, show error message claiming nothing was wound if user fires action event onSearchProduct or
        //press the enter button
        if(Inventory.lookupProduct(productsFilterText.getText()) == Inventory.getAllProducts()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Unable to find any items within search parameters");
            alert.showAndWait();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    /** This is the onActionExit method.
     This is a method that cloes the program.
     @param actionEvent Method takes in an action event that gets triggered when the user clicks on the corresponding button..
     */
    public void onActionExit(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }


}