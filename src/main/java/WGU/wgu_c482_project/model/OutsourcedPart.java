/**
 *
 * @author Brendan Thoeung | ID: 007494550 | WGU
 */
package WGU.wgu_c482_project.model;

/** This class is a blueprint OutsourcedPart class that inherits from Part class*/
public class OutsourcedPart extends Part{
    private String companyName;

    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /** This is setMachineId Method.
     * @param companyName sets the companyName to the parameter
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
