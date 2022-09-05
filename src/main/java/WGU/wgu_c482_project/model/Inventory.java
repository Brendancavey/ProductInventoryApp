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
        //need to verify if the deleted part is also contained within filtered parts list. If so, also delete
        //from the filtered parts list.
        if(Inventory.getAllFilteredParts().contains(selectedPart)) {
            Inventory.getAllFilteredParts().remove(selectedPart);
        }
        return Inventory.getAllParts().remove(selectedPart);
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Part> getAllFilteredParts(){
        return allFilteredParts;
    }
    public static ObservableList<Part> lookupPart(String partName){
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
    /*public static Part lookupPart(int partId){
    I did not find it necessary to overload this method. parttId can be casted to a String and
    compared to parttName within the same method. Refer to line 56.
        return null;
    }*/

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
        //need to verify if the deleted part is also contained within filtered products list. If so, also delete
        //from the filtered parts list.
        if(Inventory.getAllFilteredProducts().contains(selectedProduct)) {
            Inventory.getAllFilteredProducts().remove(selectedProduct);
        }
        return Inventory.getAllProducts().remove(selectedProduct);
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    public static ObservableList<Product> getAllFilteredProducts(){
        return allFilteredProducts;
    }
    public static ObservableList<Product> lookupProduct(String productName){
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
    /*public static Product lookupProduct(int productId){
    I did not find it necessary to overload this method. productId can be casted to a String and
    compared to productName within the same method. Refer to line 104.
        return null;
    }*/

    ///////////////////////////////////////////////
}
