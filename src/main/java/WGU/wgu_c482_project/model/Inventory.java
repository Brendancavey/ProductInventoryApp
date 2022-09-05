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
    public static void updatePart(int index, Part selectedPart){
        //update the part at the given index with the selectedPart
        Inventory.getAllParts().set(index, selectedPart);

    }
    public static boolean deletePart(Part selectedPart){ //deletes an item from parts list
        //iterate through parts list. If the parameter ID matches
        //with an id found in parts list, remove that item
        int id = selectedPart.getId();
        int index = -1;
        int i = -1;
        for (Part item : Inventory.getAllParts()) {
            i += 1;
            if (item.getId() == id) {
                //need to verify if the deleted part is also contained within filtered parts list. If so, also delete
                //from the filtered parts list.
                if(Inventory.getAllFilteredParts().contains(item)) {
                    Inventory.getAllFilteredParts().remove(item);
                }
                return Inventory.getAllParts().remove(item);
            }
        }
        return false;
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Part> getAllFilteredParts(){
        return allFilteredParts;
    }
    public static ObservableList<Part> filterPart(String partName){
        //if the filtered parts list is not empty, it means that the user has queried a search previously,
        //and the list needs to be cleared before searching again
        if(!(Inventory.getAllFilteredParts()).isEmpty()){
            Inventory.getAllFilteredParts().clear();
        }
        //if a part exists within inventory parts list that contains either a substring of the part name or,
        //if the ID is equivalent to the inserted parameter, then add that part to the filteredParts list
        for(Part part : Inventory.getAllParts()){
            if((part.getName().contains(partName)) || Integer.toString(part.getId()).contains(partName)){
                Inventory.getAllFilteredParts().add(part);
            }
        }
        //if the filter did not add any parts to the filtered list, then return the normal list
        if(Inventory.getAllFilteredParts().isEmpty()){
            return Inventory.getAllParts();
        }
        return Inventory.getAllFilteredParts();
    }

    ////////////////////////////////////////////////
    //////////////PRODUCT METHODS//////////////////
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static void updateProduct(int index, Product selectedProduct){
        //update the product at the given index with the selectedProduct
        Inventory.getAllProducts().set(index, selectedProduct);
    }
    public static boolean deleteProduct(Product selectedProduct){ //deletes an item from product list
        int id = selectedProduct.getId();
        int index = -1;
        int i = -1;
        //need to verify if the deleted part is also contained within filtered products list. If so, also delete
        //from the filtered parts list.
        for (Product item : Inventory.getAllProducts()) {
            i += 1;
            if (item.getId() == id) {
                if(Inventory.getAllFilteredProducts().contains(item)) {
                    Inventory.getAllFilteredProducts().remove(item);
                }
                return Inventory.getAllProducts().remove(item);

            }
        }
        return false;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    public static ObservableList<Product> getAllFilteredProducts(){
        return allFilteredProducts;
    }
    public static ObservableList<Product> filterProduct(String productName){
        //if the filtered product list is not empty, it means that the user has queried a search previously,
        //and the list needs to be cleared before searching again
        if(!(Inventory.getAllFilteredProducts()).isEmpty()){
            Inventory.getAllFilteredProducts().clear();
        }
        //if a product exists within inventory products list that contains either a substring of the part name or,
        //if the ID is equivalent to the inserted parameter, then add that part to the filteredProducts list
        for(Product product : Inventory.getAllProducts()){
            if((product.getName().contains(productName)) || Integer.toString(product.getId()).contains(productName)){
                Inventory.getAllFilteredProducts().add(product);
            }
        }
        //if the filter did not add any products to the filtered list, then return the normal list
        if(Inventory.getAllFilteredProducts().isEmpty()){
            return Inventory.getAllProducts();
        }
        return Inventory.getAllFilteredProducts();
    }

    ///////////////////////////////////////////////
}
