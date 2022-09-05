package WGU.wgu_c482_project.controller;


import WGU.wgu_c482_project.model.Inventory;
import WGU.wgu_c482_project.model.Part;
import WGU.wgu_c482_project.model.Product;
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
                partsTableView.setItems(Inventory.filterPart(partsFilterText.getText()));

            }
        });
        productsFilterText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                productsTableView.setItems(Inventory.filterProduct(productsFilterText.getText()));
            }
        });
        //////////////////////////////////////////////////////////////////////////////////

    }

    /////////////////////////PARTS WIDGETS ACTIONS/////////////////////////////////////////////////////////////
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(MainController.class.getResource("/WGU/wgu_c482_project/AddParts.fxml"));

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
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        try{
            //create fxml loader object to let loader object know which scene to view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/WGU/wgu_c482_project/ModifyParts.fxml"));
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
    public void onSearchPart(ActionEvent actionEvent) throws IOException{
        System.out.println("Searching...");
        //filterPart returns the original list if nothing is found. Therefore, show error message claiming nothing was wound if user fires action event onSearchPart or
        //press the enter button
        if(Inventory.filterPart(partsFilterText.getText()) == Inventory.getAllParts()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Unable to find any items within search parameters");
            alert.showAndWait();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////PRODUCT WIDGETS ACTIONS/////////////////////////////////////////////////////////
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/WGU/wgu_c482_project/AddProducts.fxml"));

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
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        try{
            //create fxml loader object to let loader object know which scene to view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/WGU/wgu_c482_project/ModifyProducts.fxml"));
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
    public void onSearchProduct(ActionEvent actionEvent) throws IOException{
        System.out.println("Searching...");
        //filterProduct returns the original list if nothing is found. Therefore, show error message claiming nothing was wound if user fires action event onSearchProduct or
        //press the enter button
        if(Inventory.filterProduct(productsFilterText.getText()) == Inventory.getAllProducts()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Unable to find any items within search parameters");
            alert.showAndWait();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onActionExit(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }


}