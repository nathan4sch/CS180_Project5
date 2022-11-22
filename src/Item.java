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
}
