public class MPV extends Vehicle {
    private boolean powerDoor;

    public MPV(String plateNumber, String brand, String model,
                 String pricePerDay, boolean powerDoor) {
        super(plateNumber, brand, model, pricePerDay);
        this.powerDoor = powerDoor;
    }

    public boolean hasPowerDoor() {
        return powerDoor;
    }

    @Override
    public String getType() {
        return "MPV";
    }

    @Override
    public String getDetails() {
        String details = super.getDetails();
        if (powerDoor) {
            return details + "\nElectric door: Yes";
        } else {
            return details + "\nElectric door: No";
        }
    }
}
