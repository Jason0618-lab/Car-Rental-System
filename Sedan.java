public class Sedan extends Vehicle {
    private boolean automatic;

    public Sedan(String plateNumber, String brand, String model,
                 String pricePerDay, boolean automatic) {
        super(plateNumber, brand, model, pricePerDay);
        this.automatic = automatic;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    @Override
    public String getType() {
        return "Sedan";
    }

    @Override
    public String getDetails() {
        String details = super.getDetails();
        if (automatic) {
            return details + "\nAutomatic: Yes";
        } else {
            return details + "\nAutomatic: No";
        }
    }
}
