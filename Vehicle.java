
public abstract class Vehicle implements Rentable {
    private String plateNumber;
    private String brand;
    private String model;
    private String pricePerDay;

    public Vehicle(String plateNumber, String brand, String model, String pricePerDay) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

   
    public abstract String getType();

    public String getDetails() {
        return "Plate: " + plateNumber + "\n"
                + "Brand: " + brand + "\n"
                + "Model: " + model + "\n"
                + "Type: " + getType() + "\n"
                + "Price per day: " + pricePerDay + " THB";
    }
    

@Override
public final double calculateRent(int days) {
    double price = Double.parseDouble(pricePerDay);
    return price * days;
}
}