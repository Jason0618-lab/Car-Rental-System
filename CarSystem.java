import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CarSystem implements ActionListener {

    private ArrayList<Rentable> cars = new ArrayList<Rentable>();

    private JFrame frame = new JFrame("Car System");
    private JDialog page;
    private JLabel message = new JLabel("");

    private JTextField plateField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField typeField;
    private JTextField priceField;

    private JCheckBox featureBox;

    private JTextField searchField;
    private JTextField daysField;
    private JTextArea resultArea;

    public CarSystem() {

        
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 330);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        showHome();
        frame.setVisible(true);
    }

    private void newPage(String title) {
        page = new JDialog(frame, title, true);
        page.setLayout(null);
        page.setSize(620, 580);
        page.setResizable(false);
        page.setLocationRelativeTo(frame);
        page.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel(title, JLabel.CENTER);
        heading.setBounds(0, 15, 600, 30);
        page.add(heading);

        message.setText("");
        message.setBounds(30, 490, 550, 30);
        page.add(message);
    }

    private JTextField input(String name, String value, int y) {
        JLabel label = new JLabel(name);
        label.setBounds(30, y, 180, 30);
        page.add(label);

        JTextField field = new JTextField(value);
        field.setBounds(220, y, 300, 30);
        page.add(field);

        return field;
    }

    private JButton button(String text, int x, int y) {
        JButton b = new JButton(text);
        b.setBounds(x, y, 160, 35);
        b.addActionListener(this);
        return b;
    }

    private void showHome() {
        JLabel heading = new JLabel("Main Menu", JLabel.CENTER);
        heading.setBounds(0, 30, 480, 30);
        frame.add(heading);

        JButton add = button("Add Vehicle", 130, 90);
        add.setSize(220, 45);
        frame.add(add);

        JButton search = button("Search Vehicle", 130, 160);
        search.setSize(220, 45);
        frame.add(search);
    }

    private void showAddPage() {
        newPage("Add Vehicle");

        plateField = input("Plate number:", "", 65);
        brandField = input("Brand:", "", 105);
        modelField = input("Model:", "", 145);
        typeField = input("Type: Sedan/SUV/MPV", "", 185);
        priceField = input("Daily price (THB):", "", 225);

        featureBox = new JCheckBox("Has this feature");
        featureBox.setBounds(220, 270, 250, 30);
        page.add(featureBox);

        JLabel note = new JLabel(
                "Sedan: Automatic | SUV: Sunroof | MPV: Electric door");
        note.setBounds(30, 315, 550, 30);
        page.add(note);

        page.add(button("Add", 120, 390));
        page.add(button("Back", 320, 390));

        page.setVisible(true);
    }

    private void showSearchPage() {
        newPage("Search Vehicle");

        searchField = input("Type: Sedan/SUV/MPV", "", 65);
        daysField = input("Rental days:", "1", 110);

        page.add(button("Search", 120, 160));
        page.add(button("Back", 320, 160));

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(30, 215, 550, 260);
        page.add(scroll);

        page.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Vehicle")) {
            showAddPage();

        } else if (command.equals("Search Vehicle")) {
            showSearchPage();

        } else if (command.equals("Back")) {
            page.dispose();

        } else if (command.equals("Add")) {
            addCar();

        } else if (command.equals("Search")) {
            searchCar();
        }
    }
    
  
    private void addCar() {
        String plate = plateField.getText().trim();
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String type = typeField.getText().trim();
        String price = priceField.getText().trim();

        boolean feature = featureBox.isSelected();
        

        if (plate.equals("") || brand.equals("") ||
                model.equals("") || type.equals("") ||
                price.equals("")) {
            message.setText("Please fill in all fields.");
            return;
        }
        if (!type.equals("SEDAN") &&
                !type.equals("SUV") &&
                !type.equals("MPV")) {
            message.setText("Type must be uppercase: SEDAN, SUV or MPV.");
            return;
        }
        if (plate.contains("-")) {
            message.setText("Plate number cannot contain a minus sign.");
            return;
        }

     
        if (!brand.matches("[a-zA-Z ]+") ||
                !model.matches("[a-zA-Z ]+")) {
            message.setText("Brand and model can contain letters and spaces only.");
            return;
        }
        for (int i = 0; i < cars.size(); i++) {
            Vehicle existing = (Vehicle) cars.get(i);

            if (existing.getPlateNumber().equalsIgnoreCase(plate)) {
                message.setText("This plate number already exists.");
                return;
            }
          
        }

        try {
            double dailyPrice = Double.parseDouble(price);

            if (!(dailyPrice > 0) ||
                    Double.isInfinite(dailyPrice)) {
                message.setText("Please enter a valid positive price.");
                return;
            }

        } catch (NumberFormatException e) {
            message.setText("Price must be a number.");
            return;
        }

        Rentable car;

        if (type.equalsIgnoreCase("Sedan")) {
            car = new Sedan(
                    plate, brand, model, price, feature);

        } else if (type.equalsIgnoreCase("SUV")) {
            car = new SUV(
                    plate, brand, model, price, feature);

        } else if (type.equalsIgnoreCase("MPV")) {
            car = new MPV(
                    plate, brand, model, price, feature);

        } else {
            message.setText("Type must be Sedan, SUV or MPV.");
            return;
        }

        cars.add(car);

        message.setText("Vehicle added successfully.");

        plateField.setText("");
        brandField.setText("");
        modelField.setText("");
        typeField.setText("");
        priceField.setText("");
        featureBox.setSelected(false);
    }

    private void searchCar() {
        String type = searchField.getText().trim();

        resultArea.setText("");
        message.setText("");

        if (!type.equalsIgnoreCase("Sedan") && !type.equalsIgnoreCase("SUV") && !type.equalsIgnoreCase("MPV")) {
            message.setText("Type must be Sedan, SUV or MPV.");
            return;
        }

        int days;

        try {
            days = Integer.parseInt(daysField.getText().trim());

        } catch (NumberFormatException e) {
            message.setText("Days must be a whole number.");
            return;
        }

        if (days <= 0) {
            message.setText("Days must be greater than 0.");
            return;
        }

        String results = "";
        int found = 0;

        for (int i = 0; i < cars.size(); i++) {
            Rentable car = cars.get(i);

            if (car.getType().equalsIgnoreCase(type)) {
                double total = car.calculateRent(days);
                found++;

                results = results
                        + "Vehicle " + found + "\n"
                        + car.getDetails() + "\n"
                        + "Rental days: " + days + "\n"
                        + "Total rent: " + total + " THB\n\n";
            }
        }

        resultArea.setText(results);
        resultArea.setCaretPosition(0);

        if (found == 0) {
            message.setText("No vehicles found.");
        } else {
            message.setText("Vehicles found: " + found);
        }
    }

    public static void main(String[] args) {
        new CarSystem();
    }
}