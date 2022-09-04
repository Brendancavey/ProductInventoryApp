package WGU.wgu_c482_project;

import WGU.wgu_c482_project.model.*;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private Button cancelButton;
    ////////////////CREATING NEW PRODUCT OBJECT TO BE USED FOR MODIFICATION////////////////////////////
    private Product newProduct = null; //new Product(defaultID,defaultString,defaultPrice,defaultStock,defaultMin,defaultMax);
    ///////////////////////////////////////////////////////////////////////
    ////////////////////////INITIALIZE//////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Products Scene Initialized");
        ///////////////INITIALIZING newProduct RECEIVED FROM MAIN MENU////////////////////////////
        newProduct = MainController.getNewProduct();
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
                partsTableView.setItems(MainController.filterPart(partsFilterText.getText()));
            }
        });
    }
    //////////////////WIDGET BUTTONS//////////////////////////////////////////
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
            //use temporary product object to save information into product
            newProduct.setId(id);
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setMin(min);
            newProduct.setMax(max);
            newProduct.setStock(stock);
            //update the product stored in product list with index containing same id. Since id is not modifiable,
            //then id is the parameter used to find appropriate item in product list.
            update(id, newProduct);
            //saves all info from text field and update is successful. Cancel button fires to get back to main menu.
            cancelButton.fireEvent(new ActionEvent());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setContentText("Make sure to enter valid entries into text field!");
            alert.showAndWait();
        }

    }
    public void onAdd(ActionEvent actionEvent) throws IOException{
        //create temporary part to be used as the selected part to add to associated part list
        Part selectPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        if(selectPart == null){return;}
        else{
            newProduct.addAssociatedPart(selectPart);
            System.out.println("Added!");
        }
        //associatePartsTableview is called here since any updated values get called after the scene has been initialized.
        //by calling the table view onAdd, it ensures that the newProduct associated with the table shows the appropriate-associatedPartList
        //associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
    }
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
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////HELPER METHODS//////////////////////////////////
    public void sendProductInformation(Product product){ //used to obtain product information when selecting from main menu product list
        IDText.setText(String.valueOf(product.getId()));
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        invText.setText(String.valueOf(product.getStock()));
        minText.setText(String.valueOf(product.getMin()));
        maxText.setText(String.valueOf(product.getMax()));
        //associatedPartsTableView.setItems(product.getAllAssociatedParts());
    }
    public boolean update(int id, Product product){
        int index = -1;
        //iterate through products list. If the item id matches with parameter product id,
        //then found the matching product to update
        for(Product item: Inventory.getAllProducts()){
            index += 1;
            if(item.getId() == id){
                //once matching product has been found, the current item gets replaced
                //with the new product at current item's index
                Inventory.getAllProducts().set(index, product);
                return true;
            }
        }
        return false;
    }
    ///////////////////////////////////////////////////////////////////////
}