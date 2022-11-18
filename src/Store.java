import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Store class, contains important information methods about the store of the items
 *
 * @author Dakota Baldwin
 * @version 11/16/2022
 */

public class Store {
    private String owner;
    private String storeName;
    //private ArrayList<Item> items;

    /**
     * Store constructor
     *
     * @param owner     Store owner
     * @param storeName Store name
     **/
    public Store(String owner, String storeName) {
        this.owner = owner;
        this.storeName = storeName;

//        try {
//            items = new ArrayList<>();
//            File f = new File("FMItems.csv");
//            // Initialize item objects that this store has created
//            BufferedReader bfr = new BufferedReader(new FileReader(f));
//            String line = "";
//            while ((line = bfr.readLine()) != null) {
//                String[] splitLine = line.split(",");
//                if (splitLine[0].equals(storeName)) {
//                    items.add(new Item(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]),
//                            Double.parseDouble(splitLine[4])));
//                }
//            }
//            bfr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Returns owner
     **/
    public String getOwner() {
        return owner;
    }

    /**
     * Returns store name
     **/
    public String getStoreName() {
        return storeName;
    }

    /**
     * Returns item ArrayList
     **/
//    public ArrayList<Item> getItems() {
//        return items;
//    }

    /**
     * Writes new item to FMItems.csv
     *
     * @param itemName    Name of item to be written
     * @param description Item description to be written
     * @param quantity    Quantity of items to be written
     * @param price       Price of item to be written
     **/
    public void addItem(String itemName, String description, int quantity, double price) {
        //items.add(new Item(storeName, itemName, description, quantity, price));
        // Also print item into csv file
        File f = new File("FMItems.csv");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
            pw.printf("%s,%s,%s,%d,%.2f\n", storeName, itemName, description, quantity, price);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves sale information for seller; Writes a copy of the information to FMStats.csv
     *
     * @param buyer      Buyer name
     * @param item       Item object to be accessed
     * @param amountSold Amount sold to be written
     **/
    public static void saveSale(String buyer, Item item, int amountSold) {
        try {
            // Read FMStores to find the correct store to add sale information to
            BufferedReader bfrOne = new BufferedReader(new FileReader("FMStores.csv"));
            String line = bfrOne.readLine();
            ArrayList<String> storeFile = new ArrayList<>();
            while (line != null) {
                String[] splitLine = line.split(",");
                // Detects if this is the store we need
                if (splitLine[0].equals(item.getStore())) {
                    // Creates sale section if it doesn't already exist, else adds new sale to end of sale section
                    if (splitLine.length == 2) {
                        line += String.format(",%s!%s!%d!%.2f", buyer, item.getName(), amountSold, item.getPrice());
                    } else {
                        line += String.format("~%s!%s!%d!%.2f", buyer, item.getName(), amountSold, item.getPrice());
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
                if (splitLine[0].equals(item.getStore())) {
                    if (buyer.equals(splitLine[1])) {
                        buyerFound = true;
                        splitLine[2] = Integer.toString((Integer.parseInt(splitLine[2]) + amountSold));
                    } else if (item.getName().equals(splitLine[1])) {
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
                statsFile.add(0, String.format("%s,%s,%s,buyer", item.getStore(), buyer, amountSold));
            }
            if (!itemFound) {
                statsFile.add(String.format("%s,%s,%s,item", item.getStore(), item.getName(), amountSold));
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
    public ArrayList<String> showSales() {
        try {
            // Read through CSV file
            BufferedReader storeReader = new BufferedReader(new FileReader("FMStores.csv"));
            //ArrayList<String> FMStores = new ArrayList<>();

            // Add existing stores to ArrayList;
            String line = storeReader.readLine();
            while (line != null) {
                //FMStores.add(line);
                if (line.contains(storeName)) {
                    String[] strSplit = FMStores.get(i).split(",");
                    String saleHistoryStr = strSplit[2];
                    String[] saleHistoryLine = saleHistoryStr.split("~");
                    return new ArrayList<>(Arrays.asList(saleHistoryLine));
                }
                line = storeReader.readLine();
            }
            storeReader.close();

//            // loop through arraylist and find the correct store
//            for (int i = 0; i < FMStores.size(); i++) {
//                // If arraylist index has correct store name
//                if (FMStores.get(i).contains(storeName)) {
//                    String[] strSplit = FMStores.get(i).split(",");
//                    String saleHistoryStr = strSplit[2];
//                    String[] saleHistoryLine = saleHistoryStr.split("~");
//                    return new ArrayList<>(Arrays.asList(saleHistoryLine));
//                }
//            }
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
                    stats.add(splitLine[1] + "," + splitLine[2]);
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
            amounts.add(Integer.parseInt(unsorted.get(i).substring(unsorted.get(i).indexOf(",") + 1)));
        }
        amounts.sort(Collections.reverseOrder());
        for (int i = 0; i < amounts.size(); i++) {
            for (int j = 0; j < unsorted.size(); j++) {
                if (amounts.get(i) == Integer.parseInt(unsorted.get(j).split(",")[1])) {
                    sorted.add(unsorted.get(j));
                    unsorted.remove(j);
                    break;
                }
            }
        }
        return sorted;
    }
}
