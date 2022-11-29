import java.io.*;
import java.util.ArrayList;

/**
 * Item class, contains all relevant information for an item and print it when necessary
 *
 * @author Dakota Baldwin
 * @version 11/4/2022
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
     * Returns store
     **/
    public String getStore() {
        return store;
    }

    /**
     * Returns name
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Returns quantity
     **/
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns price
     **/
    public double getPrice() {
        return price;
    }

    /**
     * Changes field in of item FMItems.csv
     * returns if operation was successful or not
     *
     * @param field    : Field type to change
     * @param newValue : New value to change it to
     **/
    public boolean changeField(String field, String newValue) {
        // return false if quantity or price would be set to invalid value
        if (field.equals("quantity") && Integer.parseInt(newValue) < 0) { return false; }
        if (field.equals("price") && Double.parseDouble(newValue) <= 0) { return false; }
        // Write quantity change to csv file
        File f = new File("FMItems.csv");
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(String.format("%s,%s,%s,%d,%.2f", store, name, description, quantity, price))) {
                    if (field.equals("name")) {
                        line = String.format("%s,%s,%s,%d,%.2f", store, newValue, description, quantity, price);
                    } else if (field.equals("description")) {
                        line = String.format("%s,%s,%s,%d,%.2f", store, name, newValue, quantity, price);
                    } else if (field.equals("quantity")) {
                        line = String.format("%s,%s,%s,%d,%.2f", store, name, description,
                                Integer.parseInt(newValue), price);
                    } else if (field.equals("price")) {
                        line = String.format("%s,%s,%s,%d,%.2f", store, name, description, quantity,
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

