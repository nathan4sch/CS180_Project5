import java.io.*;
import java.util.*;

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

    /**
     * Seller constructor
     *
     * @param email    Seller email
     * @param password Seller password
     **/
    public Seller(String email, String password) {
        this.email = email;
        this.password = password;

        try {
            stores = new ArrayList<>();
            File fmStores = new File("../CS180_Project5/FMStores.csv");
            // Initialize store objects that this seller has created
            BufferedReader bfr = new BufferedReader(new FileReader(fmStores));
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
            File fmStores = new File("../CS180_Project5/FMStores.csv");
            PrintWriter printStore = new PrintWriter(new FileOutputStream(fmStores, true));
            printStore.println(store.getStoreName() + "," + store.getOwner() + ",x");
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
            if (stores.get(i).getStoreName().equals(storeName)) {
                currentStore = stores.get(i);
            }
        }
        stores.remove(currentStore);
        ArrayList<String> storesLines = new ArrayList<>();
        ArrayList<String> itemsLines = new ArrayList<>();
        try {
            // First, remove store belonging to this owner from stores file
            File fmStores = new File("../CS180_Project5/FMStores.csv");
            BufferedReader bfrOne = new BufferedReader(new FileReader(fmStores));
            String line;
            while ((line = bfrOne.readLine()) != null) {
                String[] splitLine = line.split(",");
                if (!Objects.requireNonNull(currentStore).getStoreName().equals(splitLine[0])) {
                    storesLines.add(line);
                }
            }
            bfrOne.close();
            PrintWriter pwOne = new PrintWriter(new FileOutputStream(fmStores, false));
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
            File fmItems = new File("../CS180_Project5/FMItems.csv");
            BufferedReader bfrTwo = new BufferedReader(new FileReader(fmItems));

            String line;
            while ((line = bfrTwo.readLine()) != null) {
                // Only saves items whose store doesn't match this store
                String[] splitLine = line.split(",");
                if (!Objects.requireNonNull(currentStore).getStoreName().equals(splitLine[0])) {
                    itemsLines.add(line);
                }
            }
            bfrTwo.close();
            PrintWriter pwTwo = new PrintWriter(new FileOutputStream(fmItems, false));
            for (int i = 0; i < itemsLines.size(); i++) {
                pwTwo.println(itemsLines.get(i));
            }
            pwTwo.close();
        } catch (Exception e) {
            System.out.println("Error deleting store items!");
            e.printStackTrace();
        }
    }

    /**
     * Deletes the current seller's account. This deletes all the sellers information from the csv files
     * as well. This includes FMCredentials, FMStores, and FMItems.
     */
    public void deleteAccount() {
        String line;
        StringBuilder storesFile = new StringBuilder();
        ArrayList<String> itemsFileOutput = new ArrayList<>();

        ArrayList<String> credentialsLines = new ArrayList<>();
        try {
            // First remove user from credentials file
            File credentials = new File("../CS180_Project5/FMCredentials.csv");
            BufferedReader bfrOne = new BufferedReader(new FileReader(credentials));
            while ((line = bfrOne.readLine()) != null) {
                // Only saves account to reprint to the file if they don't have the email belonging to this account
                String[] splitLine = line.split(",");
                //if (!email.equals(line.substring(0, line.indexOf(",")))) {
                if (!email.equals(splitLine[0])) {
                    // Also deletes items from seller's stores from shopping carts
                    String cart = splitLine[4];
                    if (cart.contains("~")) {
                        String[] cartItems = cart.split("~");
                        for (int i = 0; i < cartItems.length; i++) {
                            for (int j = 0; j < stores.size(); j++) {
                                if (cartItems[i].substring(0,
                                        cartItems[i].indexOf("!")).equals(stores.get(j).getStoreName())) {
                                    if (!cart.contains("~")) {
                                        cart = "x";
                                    } else if (cart.contains("~" + cartItems[i])) {
                                        cart = cart.replaceFirst("~" + cartItems[i], "");
                                    } else cart = cart.replaceFirst(cartItems[i] + "~", "");
                                }
                            }
                        }
                    } else if (cart.contains("!")) {
                        for (int i = 0; i < stores.size(); i++) {
                            if (cart.substring(0, cart.indexOf("!")).equals(stores.get(i).getStoreName())) {
                                cart = "x";
                            }
                        }
                    }
                    line = line.replaceAll(splitLine[4], cart);

                    credentialsLines.add(line);
                }
            }
            bfrOne.close();
            PrintWriter pwOne = new PrintWriter(new FileOutputStream(credentials, false));
            for (int i = 0; i < credentialsLines.size(); i++) {
                pwOne.println(credentialsLines.get(i));
            }
            pwOne.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Second, remove all stores belonging to this owner from stores file
            File fmStores = new File("../CS180_Project5/FMStores.csv");
            BufferedReader bfrTwo = new BufferedReader(new FileReader(fmStores));
            line = bfrTwo.readLine();
            int counter = 0;
            while (line != null) {
                // Only saves stores that don't use this users email
                String[] splitLine = line.split(",");
                if (!email.equals(splitLine[1])) {
                    if (counter >= 1) {
                        storesFile.append("\n");
                    }
                    storesFile.append(line);
                    counter++;
                }
                line = bfrTwo.readLine();
            }
            bfrTwo.close();
            PrintWriter pwTwo = new PrintWriter(new FileOutputStream(fmStores, false));
            if (storesFile.length() != 0) {
                pwTwo.println(storesFile);
            }
            pwTwo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Third, remove all items belonging to this owner's stores from items file
            File fmItems = new File("../CS180_Project5/FMItems.csv");
            BufferedReader bfrThree = new BufferedReader(new FileReader(fmItems));
            String itemLine;
            while ((itemLine = bfrThree.readLine()) != null) {
                boolean keep = true;
                // Only saves items whose store doesn't match any of this owner's stores
                for (int i = 0; i < stores.size(); i++) {
                    String[] splitLine = itemLine.split(",");
                    if (stores.get(i).getStoreName().equals(splitLine[0])) {
                        keep = false;
                    }
                }
                if (keep) {
                    itemsFileOutput.add(itemLine);
                }
            }
            bfrThree.close();
            PrintWriter pwThree = new PrintWriter(new FileOutputStream(fmItems, false));
            for (int i = 0; i < itemsFileOutput.size(); i++) {
                pwThree.println(itemsFileOutput.get(i));
            }
            pwThree.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: Prints customer shopping cart info for Sellers
     *
     * @param fileName Contains the file name that the imported files should be read from.
     * @param stores   Array containing all the stores of the current seller
     * @return int number of successfully imported items
     */
    public synchronized int importItems(String fileName, Store[] stores) { // Adds imported items to stores
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));
            String line;
            int numberSuccess = 0;
            while ((line = bfr.readLine()) != null) {
                String[] splitLine = line.split(",");
                for (int i = 0; i < stores.length; i++) {
                    // Tests against all store names until one works
                    if (splitLine[0].equals(stores[i].getStoreName())) {
                        try {
                            stores[i].addItem(splitLine[1], splitLine[2],
                                    Integer.parseInt(splitLine[3]), Double.parseDouble(splitLine[4]));
                            numberSuccess++;
                            break;
                        } catch (Exception e) {

                        }
                    }
                }
            }
            bfr.close();
            return numberSuccess;
        } catch (Exception e) {

        }
        return -1;
    }

    /**
     * Prints customer shopping cart info for Sellers
     *
     * @return String ArrayList of shopping cart
     */
    public static ArrayList<String> viewCustomerShoppingCart() {
        try {
            ArrayList<String> output = new ArrayList<>();
            // Read through CSV file
            File fmCredentials = new File("../CS180_Project5/FMCredentials.csv");
            BufferedReader fmReader = new BufferedReader(new FileReader(fmCredentials));

            ArrayList<String> credentials = new ArrayList<>();

            // Add existing items to ArrayList;
            String line;
            while ((line = fmReader.readLine()) != null) {
                credentials.add(line);
            }

            fmReader.close();

            ArrayList<String> shoppingCart = new ArrayList<>();
            ArrayList<String> customerNames = new ArrayList<>();

            // loop through arraylist and find the correct account
            int credSize = credentials.size();
            for (int i = 0; i < credSize; i++) {
                String[] credentialsSplit = credentials.get(i).split(",");
                String shoppingCartLine = credentialsSplit[4];
                if (credentialsSplit[2].equals("buyer")) {
                    shoppingCart.add(shoppingCartLine);
                    customerNames.add(credentialsSplit[0]);
                }
            }

            if (customerNames.size() == 0) {
                return null;
            }

            ArrayList<String> cartInfoLine = new ArrayList<>();

            // Get amount of items in cart for each customer
            for (int i = 0; i < shoppingCart.size(); i++) {
                if (!shoppingCart.get(i).equals("x")) {
                    String[] cartItems = shoppingCart.get(i).split("~");
                    for (int j = 0; j < cartItems.length; j++) {
                        try {
                            String[] itemFields = cartItems[j].split("!");
                            String storeName = itemFields[0];
                            String itemName = itemFields[1];
                            String quantity = itemFields[2];
                            String price = itemFields[3];
                            String itemInfoLine = "Store Name: " + storeName + ", Item Name: " + itemName +
                                    ", Quantity: " + quantity + ", Price: $" + price;
                            cartInfoLine.add(itemInfoLine);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            int y = 0;
            for (int i = 0; i < shoppingCart.size(); i++) {
                output.add(String.format("Customer Name: %s", customerNames.get(i)));
                String[] cartLine = shoppingCart.get(i).split("~");
                for (int j = 0; j < cartLine.length; j++) {
                    if (!cartLine[j].equals("x")) {
                        output.add("   " + cartInfoLine.get(y));
                        y++;
                    } else {
                        output.add("   Customer cart empty!");
                    }
                }
            }
            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all Stores owned by the user
     *
     * @return Store Array
     */
    public Store[] getStore() {
        Store[] userStores = new Store[this.stores.size()];
        for (int i = 0; i < this.stores.size(); i++) {
            userStores[i] = this.stores.get(i);
        }
        return userStores;
    }

    /**
     * Gets the specific Store object by storeName
     *
     * @return Store Object
     */
    public Store getSpecificStore(String storeName) {
        Store[] userStores = getStore();
        for (int i = 0; i < userStores.length; i++) {
            if (userStores[i].getStoreName().equals(storeName)) {
                return userStores[i];
            }
        }
        return null;
    }

    /**
     * Gets the email of Seller
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the password of Seller to a new value
     *
     * @param password String new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
