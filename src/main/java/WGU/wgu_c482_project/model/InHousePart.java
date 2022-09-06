package WGU.wgu_c482_project.model;

/** This class is a blueprint InHousePart class that inherits from Part class*/
public class InHousePart extends Part{
    private int machineID;

    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /**
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }
    /** This is setMachineId Method.
     * @param machineID sets the machine id to the parameter
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
