package WGU.wgu_c482_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    ////////////////////////INITIALIZING LISTS///////////////////////////////////////////////
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();
    /////////////////SETTING UNIQUE IDs///////////////////////////////////////////////////////
    public static int newPartID = 1;
    public static int newProductID = 1;
    public static void incrementPartID(){
        newPartID += 1;
    }
    public static void incrementProductID(){
        newProductID += 1;
    }
    ///////////////////////////////////////////////////////////////////////////////////////


    ////////////////PARTS METHODS////////////////////
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Part> getAllFilteredParts(){
        return allFilteredParts;
    }
    ////////////////////////////////////////////////
    //////////////PRODUCT METHODS//////////////////
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    public static ObservableList<Product> getAllFilteredProducts(){
        return allFilteredProducts;
    }
    ///////////////////////////////////////////////
}
