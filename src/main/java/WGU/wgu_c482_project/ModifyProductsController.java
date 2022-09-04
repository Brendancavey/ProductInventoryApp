package WGU.wgu_c482_project;

import WGU.wgu_c482_project.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    ////////////////CREATING NEW PRODUCT OBJECT////////////////////////////
    //initializing new product to use for creating associated parts list. Associated
    //instance variables will be set when product is saved. If the product is canceled and not saved
    //then newProduct is unused and will get picked up by garbage collection
    int defaultID = -1;
    String defaultString = ".";
    double defaultPrice = 1.00;
    int defaultStock = 0;
    int defaultMin = 0;
    int defaultMax = 1;
    Product newProduct = new Product(defaultID,defaultString,defaultPrice,defaultStock,defaultMin,defaultMax);
    ///////////////////////////////////////////////////////////////////////
    ////////////////////////INITIALIZE//////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Products Scene Initialized");
        ///////////////INITIALIZING PARTS TABLE VIEW///////////////////////
        //calls get-method from Parts class that corresponds to the parameter
        partsTableView.setItems(Inventory.getAllParts());
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        /////////////////////////////////////////////////////////////////////
        /////////////////////INITIALIZING ASSOCIATED PARTS TABLE VIEW///////////
        //associatedPartsTableView.setItems(newProduct.getAllAssociatedParts()); //already set from sending product info from main menu
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
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////HELPER METHODS//////////////////////////////////
    public void sendProductInformation(Product product){
        IDText.setText(String.valueOf(product.getId()));
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        invText.setText(String.valueOf(product.getStock()));
        minText.setText(String.valueOf(product.getMin()));
        maxText.setText(String.valueOf(product.getMax()));
        associatedPartsTableView.setItems(product.getAllAssociatedParts());
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