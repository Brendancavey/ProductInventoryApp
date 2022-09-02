package WGU.wgu_c482_project;


import WGU.wgu_c482_project.model.Inventory;
import WGU.wgu_c482_project.model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.application.Application;


import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private TableColumn<Part, Integer> partsIDCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInvLevelCol;

    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Menu Scene Initialized");
        partsTableView.setItems(Inventory.getAllParts());
        //calls get method from Parts class that corresponds to the parameter
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /////////////////////////PARTS BUTTONS/////////////////////////////////////////////////////////////
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("AddParts.fxml"));

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
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("ModifyParts.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //create the new scene
        Scene scene = new Scene(root, 650, 590);
        stage.setTitle("Modify Parts Menu");

        //set the scene on the stage
        stage.setScene(scene);

        //show the stage (raise the curtains)
        stage.show();
    }
    public void onDeletePart(ActionEvent actionEvent) throws IOException{
        System.out.println("Delete part was clicked!");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////PRODUCT BUTTONS/////////////////////////////////////////////////////////
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("AddProducts.fxml"));

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
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("ModifyProducts.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //create the new scene
        Scene scene = new Scene(root, 1089, 590);
        stage.setTitle("Modify Products Menu");

        //set the scene on the stage
        stage.setScene(scene);

        //show the stage (raise the curtains)
        stage.show();
    }
    public void onDeleteProduct(ActionEvent actionEvent) throws IOException{
        System.out.println("Delete part was clicked!");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onActionExit(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }
}