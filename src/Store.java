import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Store class, contains important information methods about the store of the items
 *
 * @author Nathan Schneider
 * @version 13/11/2022
 */

public class Store {
    private String owner;
    private String storeName;
    private ArrayList<Item> items;

    /**
     * Store constructor
     *
     * @param owner     Store owner
     * @param storeName Store name
     **/
    public Store(String owner, String storeName) {
        this.owner = owner;
        this.storeName = storeName;

        try {
            items = new ArrayList<>();
            File f = new File("FMItems.csv");
            // Initialize item objects that this store has created
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                String[] splitLine = line.split(",");
                if (splitLine[0].equals(storeName)) {
                    items.add(new Item(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]),
                            Double.parseDouble(splitLine[4])));
                }
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes new item to FMItems.csv
     *
     * @param name    Name of item to be written
     * @param description Item description to be written
     * @param quantity    Quantity of items to be written
     * @param price       Price of item to be written
     **/
    public synchronized void addItem(String name, String description, int quantity, double price) {
        items.add(new Item(storeName, name, description, quantity, price));
        // Also print item into csv file
        File f = new File("FMItems.csv");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
            pw.printf("%s,%s,%s,%d,%.2f\n", storeName, name, description, quantity, price);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes Item from FMItems.csv and the stores item list
     * @param itemToDelete    Item object that was selected by the user
     **/
    public synchronized void deleteItem(Item itemToDelete) {
        //Remove the item from the store's item list
        items.remove(itemToDelete);
        ArrayList<String> lines = new ArrayList<>();
        try {
            // then remove item from FMItems.csv file
            BufferedReader bfrOne = new BufferedReader(new FileReader("FMItems.csv"));
            String line = "";
            while ((line = bfrOne.readLine()) != null) {
                String[] lineSplit = line.split(",");
                if (!itemToDelete.getName().equals(lineSplit[1])) {
                    lines.add(line);
                }
            }
            bfrOne.close();
            PrintWriter pwOne = new PrintWriter(new FileOutputStream("FMItems.csv", false));
            for (String s : lines) {
                pwOne.println(s);
            }
            pwOne.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Item getSpecificItem (String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                return items.get(i);
            }
        }
        return null;
    }

    public String getStoreName() {
        return storeName;
    }
    public String getOwner() {
        return owner;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public static void saveSale(String buyer, String store, String itemName, int amountSold, double price) {
        try {
            // Read FMStores to find the correct store to add sale information to
            BufferedReader bfrOne = new BufferedReader(new FileReader("FMStores.csv"));
            String line = bfrOne.readLine();
            ArrayList<String> storeFile = new ArrayList<>();
            while (line != null) {
                String[] splitLine = line.split(",");
                // Detects if this is the store we need
                if (splitLine[0].equals(store)) {
                    // Creates sale section if it doesn't already exist, else adds new sale to end of sale section
                    if (splitLine.length == 2) {
                        line += String.format(",%s!%s!%d!%.2f", buyer, itemName, amountSold, price);
                    } else {
                        line += String.format("~%s!%s!%d!%.2f", buyer, itemName, amountSold, price);
                    }
                }
                storeFile.add(line);
                line = bfrOne.readLine();
            }
            bfrOne.close();

            // Prints FMStores file with added sale
            PrintWriter pwOne = new PrintWriter(new FileOutputStream("FMStores.csv", false));
            for (int i = 0; i < storeFile.size(); i++) {
                pwOne.println(storeFile.get(i));
            }
            pwOne.close();
            // Read file holding statistics and update buyer and item numbers if already present
            File fmStats = new File("FMStats.csv");
            BufferedReader bfrTwo = new BufferedReader(new FileReader(fmStats));
            ArrayList<String> statsFile = new ArrayList<>();
            boolean buyerFound = false;
            boolean itemFound = false;
            line = bfrTwo.readLine();
            while (line != null) {
                String[] splitLine = line.split(",");
                if (splitLine[0].equals(store)) {
                    if (buyer.equals(splitLine[1])) {
                        buyerFound = true;
                        splitLine[2] = Integer.toString((Integer.parseInt(splitLine[2]) + amountSold));
                    } else if (itemName.equals(splitLine[1])) {
                        itemFound = true;
                        splitLine[2] = Integer.toString((Integer.parseInt(splitLine[2]) + amountSold));
                    }
                }
                statsFile.add(String.format("%s,%s,%s,%s", splitLine[0], splitLine[1], splitLine[2], splitLine[3]));
                line = bfrTwo.readLine();
            }
            /*
             File will have buyer statistics above item statistics, and this will make sure
             new buyers are printed at top of file
            */
            if (!buyerFound) {
                statsFile.add(0, String.format("%s,%s,%s,buyer", store, buyer, amountSold));
            }
            if (!itemFound) {
                statsFile.add(String.format("%s,%s,%s,item", store, itemName, amountSold));
            }
            bfrTwo.close();
            // Print updated statistics back to the file
            PrintWriter pwTwo = new PrintWriter(new FileOutputStream("FMStats.csv", false));
            for (int i = 0; i < statsFile.size(); i++) {
                pwTwo.println(statsFile.get(i));
            }
            pwTwo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an ArrayList to be printed as the store's sale history
     **/
    public synchronized String showSales() {
        try {
            // Read through CSV file
            BufferedReader storeReader = new BufferedReader(new FileReader("FMStores.csv"));
            ArrayList<String> FMStores = new ArrayList<>();

            // Add existing stores to ArrayList;
            String line = storeReader.readLine();
            while (line != null) {
                FMStores.add(line);
                line = storeReader.readLine();
            }
            storeReader.close();

            // loop through arraylist and find the correct store
            for (int i = 0; i < FMStores.size(); i++) {
                // If arraylist index has correct store name
                if (FMStores.get(i).contains(storeName)) {
                    String[] strSplit = FMStores.get(i).split(",");
                    String saleHistoryStr = strSplit[2];
                    return saleHistoryStr;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Returns arraylist to be printed as store's statistics
     *
     * @param storeName Name of store to search for
     * @param type      Type of statistic to show
     **/
    public static ArrayList<String> showStats(String storeName, String type) {
        // type should be either buyer or item
        ArrayList<String> stats = new ArrayList<>();
        try {
            // Read csv file, if line has correct store name, add to array list
            BufferedReader statsReader = new BufferedReader(new FileReader("FMStats.csv"));
            String line = statsReader.readLine();
            while (line != null) {
                String[] splitLine = line.split(",");
                if (splitLine[0].equals(storeName) && splitLine[3].equals(type)) {
                    stats.add(splitLine[1] + "~" + splitLine[2]);
                }
                line = statsReader.readLine();
            }
            return stats;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Returns arraylist to be printed as store's statistics sorted by amount from most to least
     *
     * @param storeName Name of store to search for
     * @param type      Type of statistic to show
     **/
    public static ArrayList<String> showSortedStats(String storeName, String type) {
        ArrayList<String> unsorted = showStats(storeName, type);
        ArrayList<Integer> amounts = new ArrayList<>();
        ArrayList<String> sorted = new ArrayList<>();
        for (int i = 0; i < unsorted.size(); i++) {
            amounts.add(Integer.parseInt(unsorted.get(i).substring(unsorted.get(i).indexOf("~") + 1)));
        }
        amounts.sort(Collections.reverseOrder());
        for (int i = 0; i < amounts.size(); i++) {
            for (int j = 0; j < unsorted.size(); j++) {
                if (amounts.get(i) == Integer.parseInt(unsorted.get(j).split("~")[1])) {
                    sorted.add(unsorted.get(j));
                    unsorted.remove(j);
                    break;
                }
            }
        }
        return sorted;
    }
}
