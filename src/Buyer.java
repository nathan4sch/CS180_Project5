import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Buyers class - contains all methods the buyers may use
 *
 * @author Colin Wu
 * @version 2022-3-11
 */
public class Buyer {
    private final String email; // Buyer email - This is the unique identifier (Cannot be changed)
    private String password; // Account Password
    private ArrayList<String> purchaseHistory; // ArrayList of purchase history
    private ArrayList<String> cart; // Buyer shopping cart

    /**
     * Buyer constructor
     *
     * @param email           Buyer email
     * @param password        Buyer password
     * @param purchaseHistory Buyer ArrayList storing purchase history
     * @param cart            Buyer ArrayList storing shopping cart items
     **/
    public Buyer(String email, String password, ArrayList<String> purchaseHistory, ArrayList<String> cart) {
        this.email = email;
        this.password = password;
        if (purchaseHistory == null) { //creating account
            this.purchaseHistory = new ArrayList<>();
        } else {                        //signing in
            this.purchaseHistory = returnPurchaseHistory(email);
        }
        if (cart == null) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add("x");
            this.cart = temp;
        } else {
            this.cart = showItemsInCart(email);
        }
    }

    /**
     * Reads through FMCredentials.csv and finds the currentUser's purchase history.
     * The purchase history is returned to be printed
     *
     * @param email Email to search for when adding to array list
     * @return String ArrayList of purchase history
     */
    public ArrayList<String> returnPurchaseHistory(String email) {
        try {
            // Read through CSV file
            BufferedReader purchasesReader = new BufferedReader(new FileReader("FMCredentials.csv"));

            ArrayList<String> FMCredentials = new ArrayList<>();

            // Add existing items to ArrayList;
            String line = purchasesReader.readLine();
            while (line != null) {
                FMCredentials.add(line);
                line = purchasesReader.readLine();
            }

            purchasesReader.close();

            // loop through arraylist and find the correct account
            for (int i = 0; i < FMCredentials.size(); i++) {
                // If arraylist index has email
                if (FMCredentials.get(i).contains(email)) {
                    String[] strSplit = FMCredentials.get(i).split(",");
                    String purchaseHistoryStr = strSplit[3];
                    if (purchaseHistoryStr.equals("x")) {
                        return new ArrayList<>();
                    }
                    String[] purchaseHistoryLine = purchaseHistoryStr.split("~");

                    // Return new ArrayList
                    return new ArrayList<>(Arrays.asList(purchaseHistoryLine));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new file of the user's purchase history
     *
     * @param email Email to search for when exporting
     * @return a string denoting if the file is successfully exported
     */
    public String exportPurchaseHistory(String email) {
        try {

            BufferedReader purchasesReader = new BufferedReader(new FileReader("FMCredentials.csv"));

            ArrayList<String> fmCredentials = new ArrayList<>();

            // Read through FMCredentials and append to arraylist
            String line = purchasesReader.readLine();
            while (line != null) {
                fmCredentials.add(line);
                line = purchasesReader.readLine();
            }

            purchasesReader.close();

            // loop through arraylist and find the correct account
            for (int i = 0; i < fmCredentials.size(); i++) {
                // If arraylist index has email
                if (fmCredentials.get(i).contains(email)) {
                    String[] strSplit = fmCredentials.get(i).split(",");
                    String purchaseHistoryStr = strSplit[3];
                    String[] purchaseHistoryLine = purchaseHistoryStr.split("~");

                    if (purchaseHistoryStr.contains("!")) {
                        // Create export file
                        try {
                            String[] emailSplit = email.split("~");
                            String fileName = emailSplit[0] + "PurchaseHistory.csv";
                            File export = new File(fileName);

                            FileOutputStream fos = new FileOutputStream(export, false);
                            PrintWriter purchaseWriter = new PrintWriter(fos);

                            // Write to file
                            for (int j = 0; j < purchaseHistoryLine.length; j++) {
                                purchaseWriter.println(purchaseHistoryLine[j]);
                            }

                            purchaseWriter.close();
                            return "Exported";
                        } catch (Exception e) {
                            return "Not Exported";
                        }
                    } else {
                        return "Not Exported";
                    }
                }
            }
        } catch (Exception e) {
            return "Not Exported";
        }
        return "Not Exported";
    }


    /**
     * Statistics storesFromBuyerProducts
     *
     * @param buyerEmail Email to search for when adding to array list
     * @return Arraylist of stores by the products purchased by that particular customer
     */
    public ArrayList<String> storesFromBuyerProducts(String buyerEmail) {
        try {
            ArrayList<String> stores = parseStore(); // parses store and get ArrayList
            ArrayList<String> relevantStores = new ArrayList<>();
            ArrayList<String> storesProductsList = new ArrayList<>();

            boolean containsUser = false;
            for (int i = 0; i < stores.size(); i++) { // check if stores has buyer email
                if (stores.get(i).contains(buyerEmail)) {
                    containsUser = true;
                    relevantStores.add(stores.get(i));
                }
            }

            if (!containsUser) {
                return null;
            }

            for (int i = 0; i < relevantStores.size(); i++) {
                String[] storeSplit = relevantStores.get(i).split(",");
                String storeName = storeSplit[0];
                String productsSold = storeSplit[2];
                storesProductsList.add(storeName + "," + productsSold);
            }

            ArrayList<String> productsPurchased = new ArrayList<>();

            for (int i = 0; i < storesProductsList.size(); i++) {
                String[] listSplit = storesProductsList.get(i).split(",");
                // add number of products sold to Integer list
                String[] productsSplit = listSplit[1].split("~");
                String storeName = listSplit[0];

                int quantity = 0;
                for (int j = 0; j < productsSplit.length; j++) {
                    if (productsSplit[j].contains(buyerEmail)) {
                        String[] formatProductSplit = productsSplit[j].split("!");
                        quantity += Integer.parseInt(formatProductSplit[2]);
                    }
                }
                if (quantity != 0) {
                    productsPurchased.add(storeName + "," + quantity);
                }
            }

            for (int i = 0; i < productsPurchased.size(); i++) {
                String[] productsPurchasedSplit = productsPurchased.get(i).split(",");
                if (productsPurchasedSplit[1].equals("0")) {
                    productsPurchased.remove(i);
                }
            }
            return productsPurchased;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Statistics sortStoresFromBuyerProducts
     *
     * @param buyerEmail Email to search for when adding to array list
     * @return Sorted Arraylist of stores by the products purchased by that particular customer sorted by products sold.
     */
    public ArrayList<String> sortStoresFromBuyerProducts(String buyerEmail) {
        try {
            ArrayList<String> unsortedList = storesFromBuyerProducts(buyerEmail);
            ArrayList<Double> prices = new ArrayList<>();
            ArrayList<String> sortedList = new ArrayList<>();

            for (int i = 0; i < unsortedList.size(); i++) {
                String[] listSplit = unsortedList.get(i).split(",");
                prices.add(Double.parseDouble(listSplit[1]));
            }

            prices.sort(Collections.reverseOrder());

            for (int i = 0; i < prices.size(); i++) {
                for (int j = 0; j < unsortedList.size(); j++) {
                    if (prices.get(i) == Double.parseDouble(unsortedList.get(j).split(",")[1])) {
                        sortedList.add(unsortedList.get(j));
                        unsortedList.remove(j);
                        break;
                    }
                }
            }
            return sortedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Statistics storesFromProductsSold
     *
     * @return String ArrayList of stores by number of products sold
     **/
    public ArrayList<String> storesFromProductsSold() {
        try {
            ArrayList<String> stores = parseStore(); // parses store and get ArrayList

            ArrayList<String> storesProductsList = new ArrayList<>();
            ArrayList<String> storesProductsSold = new ArrayList<>();

            for (int i = 0; i < stores.size(); i++) {
                String[] storeSplit = stores.get(i).split(",");
                String storeName = storeSplit[0];
                if (storeSplit.length == 2) {
                    storesProductsList.add(storeName);
                } else {
                    String productsSold = storeSplit[2];
                    storesProductsList.add(storeName + "," + productsSold);
                }
            }

            for (int i = 0; i < storesProductsList.size(); i++) {
                String[] listSplit = storesProductsList.get(i).split(",");
                String storeName = listSplit[0];
                // add number of products sold to Integer list
                if (listSplit.length == 1) {
                    storesProductsSold.add(storeName + "," + 0);
                } else {
                    String[] productsSplit = listSplit[1].split("~");
                    int quantity = 0;
                    for (int j = 0; j < productsSplit.length; j++) {
                        if (productsSplit[j].length() > 1) {
                            String[] formatProductSplit = productsSplit[j].split("!");
                            if (formatProductSplit.length > 1) {
                                quantity += Integer.parseInt(formatProductSplit[2]);
                            }
                        }
                    }
                    storesProductsSold.add(storeName + "," + quantity);
                }
            }

            return storesProductsSold;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Statistics Stores
     *
     * @return Sorted String ArrayList of stores by number of products sold from most to least
     **/
    public ArrayList<String> sortStoresProductsSold() {
        ArrayList<String> unsortedList = storesFromProductsSold();
        ArrayList<Integer> productAmt = new ArrayList<>();
        ArrayList<String> sortedList = new ArrayList<>();

        for (int i = 0; i < unsortedList.size(); i++) {
            String[] listSplit = unsortedList.get(i).split(",");
            productAmt.add(Integer.parseInt(listSplit[1]));
        }

        // Sort from most to least
        productAmt.sort(Collections.reverseOrder());

        for (int i = 0; i < productAmt.size(); i++) {
            for (int j = 0; j < unsortedList.size(); j++) {
                if (productAmt.get(i) == Integer.parseInt(unsortedList.get(j).split(",")[1])) {
                    sortedList.add(unsortedList.get(j));
                    unsortedList.remove(j);
                    break;
                }
            }
        }
        return sortedList;
    }

    /**
     * Reads through FMCredentials.csv and returns all items in the currentUser's cart
     *
     * @param email Email to search for when adding to array list
     * @return String ArrayList of all the items in a buyer's cart
     **/
    public static ArrayList<String> showItemsInCart(String email) {
        try {
            BufferedReader cartReader = new BufferedReader(new FileReader("FMCredentials.csv"));

            ArrayList<String> FMCredentials = new ArrayList<>();

            // Add existing items to ArrayList;
            String line;
            while ((line = cartReader.readLine()) != null) {
                FMCredentials.add(line);
            }
            cartReader.close();

            for (String fmCredential : FMCredentials) {
                // If arraylist index has email
                if (fmCredential.contains(email)) {
                    String[] strSplit = fmCredential.split(",");
                    String shoppingCartInfo = strSplit[4];
                    String[] shoppingCartLine = shoppingCartInfo.split("~");

                    return new ArrayList<>(Arrays.asList(shoppingCartLine));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads through FMStores.csv and returns a String ArrayList of items
     *
     * @return String ArrayList of items in the store
     **/
    public ArrayList<String> parseStore() {
        try {
            // Read through CSV file
            BufferedReader storeReader = new BufferedReader(new FileReader("FMStores.csv"));

            ArrayList<String> parsedList = new ArrayList<>();

            // Add existing items to ArrayList;
            String line;
            while ((line = storeReader.readLine()) != null) {
                parsedList.add(line);
            }

            storeReader.close();
            return parsedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets user's cart
     *
     * @return cart ArrayList
     */
    public ArrayList<String> getCart() {
        return cart;
    }

    /**
     * gets user's purchase history
     *
     * @return purchaseHistory ArrayList
     */
    public ArrayList<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * gets user's email
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets user's password to new String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * adds item to cart
     *
     * @return true if successful
     */
    public boolean addToCart(Item item, String quantityBuying) {
        try {
            if (Integer.parseInt(quantityBuying) <= 0 || Integer.parseInt(quantityBuying) > item.getQuantity()) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        String formatted = String.format("%s!%s!%s!%.2f", item.getStore(),
                item.getName(), quantityBuying, item.getPrice());
        try {
            BufferedReader cartReader = new BufferedReader(new FileReader("FMCredentials.csv"));
            ArrayList<String> fmCredentials = new ArrayList<>();
            // Add existing items to ArrayList;
            String line;
            while ((line = cartReader.readLine()) != null) {
                fmCredentials.add(line);
            }
            cartReader.close();

            PrintWriter pw = new PrintWriter(new FileWriter("FMCredentials.csv"));

            for (String fmCredential : fmCredentials) {
                if (fmCredential.contains(email)) {
                    String[] splitLine = fmCredential.split(",");
                    String shoppingCart = splitLine[5];
                    if (shoppingCart.equals("x")) {
                        shoppingCart = formatted;
                    } else {
                        shoppingCart = shoppingCart + "~" + formatted;
                        cart.add(formatted);
                    }
                    pw.printf("%s,%s,%s,%s,%s,%s\n", splitLine[0], splitLine[1], splitLine[2],
                            splitLine[3], splitLine[4], shoppingCart);
                } else {
                    pw.println(fmCredential);
                }
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * checks out items in cart
     * still needs lots of work
     *
     * @return null if successful, first failed checkout if unsuccessful
     */
    public String checkout() {
        try {
            String cartInfo = "";
            String historyInfo = "";
            String username = "";
            ArrayList<String> credentials = new ArrayList<>();
            BufferedReader cartReader = new BufferedReader(new FileReader("FMCredentials.csv"));
            // Add existing items to ArrayList;
            String line;
            while ((line = cartReader.readLine()) != null) {
                if (line.split(",")[0].equals(email)) {
                    cartInfo = line.split(",")[5];
                    historyInfo = line.split(",")[4];
                    username = line.split(",")[1];
                } else credentials.add(line);
            }
            cartReader.close();
            // String list holding info for each purchase in cart, boolean list to check if each purchase was accomplished
            String[] cartInfoList = cartInfo.split("~");
            boolean[] purchaseSuccessful = new boolean[cartInfoList.length];

            BufferedReader itemReader = new BufferedReader(new FileReader("FMItems.csv"));
            ArrayList<String> fmItems = new ArrayList<>();
            // Add existing items to ArrayList;
            while ((line = itemReader.readLine()) != null) {
                // Check if this item is in the cart
                for (int i = 0; i < cartInfoList.length; i++) {
                    String[] cartStuff = cartInfoList[i].split("!");
                    String[] itemStuff = line.split(",");
                    if (cartStuff[0].equals(itemStuff[0]) && cartStuff[1].equals(itemStuff[1])) {
                        // If in cart, check if amount being bought is still valid
                        if (Integer.parseInt(itemStuff[3]) >= Integer.parseInt(cartStuff[2])) {
                            // change quantity and confirm purchase if valid
                            line = line.replaceFirst(itemStuff[3], Integer.toString(Integer.parseInt(itemStuff[3]) - Integer.parseInt(cartStuff[2])));
                            purchaseSuccessful[i] = true;
                        } else {
                            // if invalid return the invalid purchase attempt
                            itemReader.close();
                            return cartInfoList[i] + " Quantity Low";
                        }
                    }
                }
                fmItems.add(line);
            }
            for (int i = 0; i < purchaseSuccessful.length; i++) {
                // Return the first purchase that could not be made if there are any
                if (!purchaseSuccessful[i]) {
                    return cartInfoList[i] + " Item not found";
                }
            }
            itemReader.close();

            // After all purchases shown to be valid, save sales to store histories and stats
            for (int i = 0; i < cartInfoList.length; i++) {
                String[] info = cartInfoList[i].split("!");
                Store.saveSale(email, info[0], info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]));
            }
            // Print items file with new quantities and move cart to history
            if (historyInfo.equals("x")) {
                historyInfo = cartInfo;
            } else historyInfo = historyInfo + "~" + cartInfo;
            cartInfo = "x";
            PrintWriter pwOne = new PrintWriter(new FileWriter("FMCredentials.csv", false));
            for (String credential : credentials) {
                pwOne.println(credential);
            }
            pwOne.printf("%s,%s,%s,buyer,%s,%s", email, username, password, historyInfo, cartInfo);
            pwOne.close();

            PrintWriter pwTwo = new PrintWriter(new FileWriter("FMItems.csv", false));
            for (String fmItem : fmItems) {
                pwTwo.println(fmItem);
            }
            pwTwo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
