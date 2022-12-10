import java.io.*;
import java.util.ArrayList;

/**
 * Item class, contains all relevant information for an item and print it when necessary
 *
 * @author Nathan Schneider, Colin Wu, Ben Herrington, Andrei Deaconescu, Dakota Baldwin
 * @version 12/10/2022
 */

public class Item {
    private String store;
    private String name;
    private String description;
    private int quantity;
    private double price;

    /**
     * Item constructor
     *
     * @param store       Item store name
     * @param name        Item name
     * @param description Item description
     * @param price       Item price
     * @param quantity    Item quantity
     **/
    public Item(String store, String name, String description, int quantity, double price) {
        this.store = store;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the Item's store
     *
     * @return store
     **/
    public String getStore() {
        return store;
    }

    /**
     * Gets the Item's name
     *
     * @return name
     **/
    public String getName() {
        return name;
    }

    /**
     * Gets the Item's description
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Gets the Item's quantity available
     *
     * @return quantity
     **/
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the Item's selling price
     *
     * @return price
     **/
    public double getPrice() {
        return price;
    }

    /**
     * Changes field in of item FMItems.csv
     *
     * @param field    : Field type to change
     * @param newValue : New value to change it to
     * @return true if operation was successful, false if not
     */
    public boolean changeField(String field, String newValue) {
        // Write quantity change to csv file
        File f = new File("../CS180_Project5/FMItems.csv");
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(String.format("%s,%s,%s,%d,%.2f", store, name, description, quantity, price))) {
                    switch (field) {
                        case "name" ->
                                line = String.format("%s,%s,%s,%d,%.2f", store, newValue, description, quantity, price);
                        case "description" ->
                                line = String.format("%s,%s,%s,%d,%.2f", store, name, newValue, quantity, price);
                        case "quantity" -> line = String.format("%s,%s,%s,%d,%.2f", store, name, description,
                                Integer.parseInt(newValue), price);
                        case "price" -> line = String.format("%s,%s,%s,%d,%.2f", store, name, description, quantity,
                                Double.parseDouble(newValue));
                    }
                }
                lines.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            for (String s : lines) {
                pw.println(s);
            }
            pw.close();

            switch (field) {
                case "name" -> this.name = newValue;
                case "description" -> this.description = newValue;
                case "quantity" -> this.quantity = Integer.parseInt(newValue);
                case "price" -> this.price = Double.parseDouble(newValue);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
