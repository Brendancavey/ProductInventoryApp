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

    public TextField nameText;
    public TextField priceText;
    public TextField invText;
    public TextField minText;
    public TextField maxText;

    private TextField partsFilterText;

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
        /////////////////////INITIALIZING SEARCH FILTER FUNCTION//////////////
       /* partsFilterText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //when the parts filter text field detects a change in text, set the table view
                //to where helper method filter is called with the current filter text
                //as the parameter
                partsTableView.setItems(MainController.filterPart(partsFilterText.getText()));
            }
        });*/
    }

    public void onSave(ActionEvent actionEvent) throws IOException{
        try{

            int id = Inventory.newPartID; //newPartId will increment at the end of the function call to ensure each id is unique
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            //verifying logical errors are in order so that max value cannot be less than min value.
            if (max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Max value is less than min value. Please correct this before saving.");
                alert.showAndWait();
                //System.out.println("Max value is less than min value. Please correct before saving.");
            }
            else{
                Inventory.addProduct(new Product(id, name, price, stock, min, max));

                Inventory.incrementPartID(); //increment partID if save was successfull
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
        System.out.println("Added!");
    }
    public void onRemove(ActionEvent actionEvent) throws IOException{
        System.out.println("Removed!");
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
