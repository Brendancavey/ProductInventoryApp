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
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductsController implements Initializable {
    //////////////////////INITIALIZING PRODUCT TEXTFIELDS///////////////////
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Products Scene Initialized");
        ///////////////INITIALIZING PARTS TABLE VIEW///////////////////////
        //calls get-method from Parts class that corresponds to the parameter
        partsTableView.setItems(Inventory.getAllParts());
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        /////////////////////////////////////////////////////////////////////
        /////////////////////INITIALIZING ASSOCIATED PARTS TABLE VIEW///////////
        associatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
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

    public void onSave(ActionEvent actionEvent) throws IOException{
        try{

            int id = Inventory.newProductID; //newPartId will increment at the end of the function call to ensure each id is unique
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
            else{
                newProduct.setId(id);
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setStock(stock);
                newProduct.setMin(min);
                newProduct.setMax(max);
                Inventory.addProduct(newProduct);

                Inventory.incrementProductID(); //increment partID if save was successfull
                //Saves all information from text field and adds it into the inventory. cancelButton fires to get back to main menu.
                //cancelButton.fireEvent(new ActionEvent());
                //Goes back to main menu. Running off of cancel button triggers alert. Don't want that.
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 919, 544);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }

        }catch(NumberFormatException e){
            //if invalid entries are entered into text field, pop up window claiming values are invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Enter valid values in the provided text fields. Thanks!");
            alert.showAndWait();
        }
    }
    public void onAdd(ActionEvent actionEvent) throws IOException{
        //create temporary part to be used as the selected part to add to associated part list
        Part selectPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        if(selectPart == null){return;}
        //ensures to only add the part if the part doesnt exist within associated parts list
        else if(!(newProduct.getAllAssociatedParts().contains(selectPart))){
            newProduct.addAssociatedPart(selectPart);
            System.out.println("Added!");
        }

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go back to main menu?");
        alert.setTitle("Confirmation Message");

        Optional<ButtonType> buttonClicked = alert.showAndWait();

        if (buttonClicked.isPresent() && buttonClicked.get() == ButtonType.OK) {
            //if the confirmation window ok button has been selected, continue to delete the selected item.
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
}
