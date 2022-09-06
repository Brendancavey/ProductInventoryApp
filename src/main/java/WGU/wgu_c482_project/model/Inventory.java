package WGU.wgu_c482_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is the Inventory class.*/
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
    /** This is the addPart method.
     * This method takes the parameter and adds it to the allParts list.
     * @param part The part to add to the parts list.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }
    /** This is the updatePart method.
     * This method takes the parameters and finds the part at the given index within the all parts list
     * and replaces it with the given selectedPart.
     * @param index The index of the part to be replaced within the all parts list.
     * @param selectedPart The part to replace the item at the given index
     */
    public static void updatePart(int index, Part selectedPart){
        //update the part at the given index with the selectedPart
        Inventory.getAllParts().set(index, selectedPart);
    }
    /** This is the deletePart method.
     * This method takes the given selectedPart and removes it from the all parts list.
     * @param selectedPart The part to be removed from the all parts list.
     */
    public static boolean deletePart(Part selectedPart){ //deletes an item from parts list
        //need to verify if the deleted part is also contained within filtered parts list. If so, also delete
        //from the filtered parts list.
        if(Inventory.getAllFilteredParts().contains(selectedPart)) {
            Inventory.getAllFilteredParts().remove(selectedPart);
        }
        return Inventory.getAllParts().remove(selectedPart);
    }
    /** This is the getAllParts method.
     * @return allParts Returns an observable list of the all parts list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** This is the getAllFilteredParts method.
     * @return allFilteredParts Returns an observable list of the all filtered parts list
     */
    public static ObservableList<Part> getAllFilteredParts(){
        return allFilteredParts;
    }
    /** This is the lookupPart method.
     * This method first checks if the filtered parts list is not empty, and if true, clears the filtered part
     * list to remove previous search queries. Then, the method iterates through the all parts list to check if
     * the name or the id of the part matches with the parameter. If so, then add that part to the filtered part list.
     * If the iteration loops through and the filtered part list is still empty, then return the original list to indicated
     * nothing was found, or return the filtered part list to show query was successful.
     * @param partName The string to compare items in the parts list with.
     * @return Inventory.getAllParts() returns the list of all parts if query did not find matching parts.
     * @return Inventory.getAllFilteredParts() returns the list of all filtered parts if the query was successful.
     */
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
    compared to parttName within the same method. Refer to line 87.
        return null;
    }*/

    ////////////////////////////////////////////////
    //////////////PRODUCT METHODS//////////////////
    /** This is the addProduct method.
     * This method takes the parameter and adds it to the allProducts list.
     * @param product The pproduct to add to the product list.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    /** This is the updateProduct method.
     * This method takes the parameters and finds the product at the given index within the all products list
     * and replaces it with the given selectedProduct
     * @param index The index of the product to be replaced within the all products list.
     * @param selectedProduct The product to replace the item at the given index
     */
    public static void updateProduct(int index, Product selectedProduct){
        //update the product at the given index with the selectedProduct
        Inventory.getAllProducts().set(index, selectedProduct);
    }
    /** This is the deleteProduct method.
     * This method takes the given selectedProduct and removes it from the all products list.
     * @param selectedProduct The product to be removed from the all products list.
     */
    public static boolean deleteProduct(Product selectedProduct){ //deletes an item from product list
        //need to verify if the deleted part is also contained within filtered products list. If so, also delete
        //from the filtered parts list.
        if(Inventory.getAllFilteredProducts().contains(selectedProduct)) {
            Inventory.getAllFilteredProducts().remove(selectedProduct);
        }
        return Inventory.getAllProducts().remove(selectedProduct);
    }
    /** This is the getAllProducts method.
     * @return allProducts Returns an observable list of the all products list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    /** This is the getAllFilteredProducts method.
     * @return allFilteredProducts Returns an observable list of the all filtered products list
     */
    public static ObservableList<Product> getAllFilteredProducts(){
        return allFilteredProducts;
    }
    /** This is the lookupProduct method.
     * This method first checks if the filtered products list is not empty, and if true, clears the filtered products
     * list to remove previous search queries. Then, the method iterates through the all products list to check if
     * the name or the id of the product matches with the parameter. If so, then add that product to the filtered products list.
     * If the iteration loops through and the filtered products list is still empty, then return the original list to indicated
     * nothing was found, or return the filtered products list to show query was successful.
     * @param productName The string to compare items in the products list with.
     * @return Inventory.getAllProducts() returns the list of all products if query did not find matching products.
     * @return Inventory.getAllFilteredProducts() returns the list of all filtered products if the query was successful.
     */
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
    compared to productName within the same method. Refer to line 165.
        return null;
    }*/

    ///////////////////////////////////////////////
}
