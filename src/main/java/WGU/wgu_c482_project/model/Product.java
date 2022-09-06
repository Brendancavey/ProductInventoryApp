/**
 *
 * @author Brendan Thoeung | ID: 007494550 | WGU
 */
package WGU.wgu_c482_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is a blueprint Product class.*/
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    ///////////////SETTERS AND GETTERS///////////////////////////
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    ////////////////////////////////////////////////////////////
    /////////////////ASSOCIATED PARTS METHODS///////////////
    /**
     * @return associatedParts returns associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    /** This is the addAssociatedPart method.
     * This method takes the parameter and adds it to the associated parts list.
     * @param part The part to add to the associated parts list.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /** This is the deleteAssociatedPart method.
     * This method takes the parameter and deletes it from the associated parts list.
     * @param selectedAssociatedPart The part to delete from the associated parts list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        //iterate through associatedParts list. If the selected associated parts ID matches
        //with an id found in associated parts list, remove that item
        int index = -1;
        for(Part item: associatedParts){
            index += 1;
            if(item.getId() == selectedAssociatedPart.getId()){

                return associatedParts.remove(item);
            }
        }
        return false;
    }
}
