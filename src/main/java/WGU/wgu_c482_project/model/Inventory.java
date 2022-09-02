package WGU.wgu_c482_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public int newPartID = 1;
    private int newProductID = 1;
    /*public static void incrementPartID(){
        newPartID += 1;
    }
    public static void incrementProductID(){

    }

    public static int getNewPartId(){

    }*/

    ////////////////PARTS METHODS////////////////////
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    ////////////////////////////////////////////////
    //////////////PRODUCT METHODS//////////////////
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    ///////////////////////////////////////////////
}
