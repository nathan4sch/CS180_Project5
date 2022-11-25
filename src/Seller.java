import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Seller class and all the variables and methods they may use.
 *
 * @author Colin Wu
 * @version 2022-3-11
 */
public class Seller {
    private final String email; // the user's email (cannot be changed)
    private String password; // the user's password
    private ArrayList<Store> stores; // a list of stores the seller owns

    public Seller(String email, String password) {
        this.email = email;
        this.password = password;

        try {
            stores = new ArrayList<>();
            File f = new File("FMStores.csv");
            // Initialize store objects that this seller has created
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = bfr.readLine();
            while (line != null) {
                String[] splitLine = line.split(",");
                if (splitLine[1].equals(email)) {
                    stores.add(new Store(splitLine[1], splitLine[0]));
                }
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: prints the new store to FMStores.csv
     *
     * @param store contains the object for store that is to be added
     */
    public void createStore(Store store) {
        stores.add(store);
        try {
            PrintWriter printStore = new PrintWriter(new FileOutputStream("FMStores.csv", true));
            printStore.println(store.getStoreName() + "," + store.getOwner());
            printStore.flush();
            printStore.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: deletes the store from the current users array of stores and deletes the stores from the csv along with
     * its items.
     *
     * @param storeName contains the name of the store to be deleted
     */
    public void deleteStore(String storeName) {
        Store currentStore = null;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)){
                currentStore = stores.get(i);
            }
        }
        stores.remove(currentStore);
        ArrayList<String> storesLines = new ArrayList<>();
        ArrayList<String> itemsLines = new ArrayList<>();
        try {
            // First, remove store belonging to this owner from stores file
            BufferedReader bfrOne = new BufferedReader(new FileReader("FMStores.csv"));
            String line = "";
            while ((line = bfrOne.readLine()) != null) {
                String[] splitLine = line.split(",");
                if (!currentStore.getStoreName().equals(splitLine[0])) {
                    storesLines.add(line);
                }
            }
            bfrOne.close();
            PrintWriter pwOne = new PrintWriter(new FileOutputStream("FMStores.csv", false));
            for (int i = 0; i < storesLines.size(); i++) {
                pwOne.println(storesLines.get(i));
            }
            pwOne.close();
        } catch (Exception e) {
            System.out.println("Error deleting current store!");
            e.printStackTrace();
        }
        try {
            // Second, remove all items belonging to this store from items file
            BufferedReader bfrTwo = new BufferedReader(new FileReader("FMItems.csv"));
            String line = "";

            while ((line = bfrTwo.readLine()) != null) {
                // Only saves items whose store doesn't match this store
                String[] splitLine = line.split(",");
                if (!currentStore.getStoreName().equals(splitLine[0])) {
                    itemsLines.add(line);
                }
            }
            bfrTwo.close();
            PrintWriter pwTwo = new PrintWriter(new FileOutputStream("FMItems.csv", false));
            for (int i = 0; i < itemsLines.size(); i++) {
                pwTwo.println(itemsLines.get(i));
            }
            pwTwo.close();
        } catch (Exception e) {
            System.out.println("Error deleting store items!");
            e.printStackTrace();
        }
    }

    public Store[] getStore() {
        Store[] userStores = new Store[this.stores.size()];
        for (int i = 0; i < this.stores.size(); i++) {
            userStores[i] = this.stores.get(i);
        }
        return userStores;
    }

    public String getEmail() {
        return email;
    }
}
