public class SUV extends Vehicle {
    private boolean sunroof;

    public SUV(String plateNumber, String brand, String model,
                 String pricePerDay, boolean sunroof) {
        super(plateNumber, brand, model, pricePerDay);
        this.sunroof = sunroof;
    }

    public boolean hasSunroof() {
        return sunroof;
    }

    @Override
    public String getType() {
        return "SUV";
    }

    @Override
    public String getDetails() {
        String details = super.getDetails();
        if (sunroof) {
            return details + "\nSunroof: Yes";
        } else {
            return details + "\nSunroof: No";
        }
    }
}
