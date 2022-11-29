import org.bouncycastle.jcajce.provider.symmetric.SEED;
import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

/**
 * Server class
 * <p>
 * Handles all the information sent to client
 *
 * @version 24/11/2022
 */
public class Server implements Runnable {
    Socket socket;
    Object currentUser = null;

    /**
     * Constructs Server object
     *
     * @param socket The socket that connect this computer connect with the server
     */
    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4444);
            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server(socket);
                new Thread(server).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run method that contains the main interface; is synchronized by threads
     */
    public void run() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String nextFrame = bufferedReader.readLine();
                switch (nextFrame) {
                    case "SignIn" -> {
                        String emailInput = bufferedReader.readLine();
                        String passwordInput = bufferedReader.readLine();

                        currentUser = signInAccount(emailInput, passwordInput);
                        if (currentUser == null) {
                            printWriter.println("Failure");
                            printWriter.flush();
                        } else {
                            printWriter.println("Success");
                            if (currentUser instanceof Buyer) {
                                printWriter.println("Buyer");
                            } else if (currentUser instanceof Seller) {
                                printWriter.println("Seller");
                            }
                            printWriter.flush();
                        }
                    }
                    case "Create Account" -> {
                        String emailInput = bufferedReader.readLine();
                        String passwordInput = bufferedReader.readLine();
                        String roleInput = bufferedReader.readLine();

                        currentUser = createAccount(emailInput, passwordInput, roleInput);
                        if (currentUser == null) {
                            printWriter.println("Failure");
                            printWriter.flush();
                        } else {
                            printWriter.println("Success");
                            printWriter.flush();
                        }
                    }
                    case "View Cart" -> {
                        ArrayList<String> buyerCartList = ((Buyer) currentUser).getCart();
                        String[] buyerCart = new String[buyerCartList.size()];
                        for (int i = 0; i < buyerCartList.size(); i++) {
                            if (i == buyerCartList.size() - 1) {
                                buyerCart[i] = buyerCartList.get(i);
                            } else {
                                buyerCart[i] = buyerCartList.get(i) + "~";
                            }
                        }

                        printWriter.println(Arrays.toString(buyerCart));
                        printWriter.flush();
                    }
                    case "Manage Store" -> {
                        Store[] userStoreList = ((Seller) currentUser).getStore();
                        String[] userStoreNames = new String[userStoreList.length];
                        for (int i = 0; i < userStoreList.length; i++) {
                            userStoreNames[i] = userStoreList[i].getStoreName();
                        }
                        printWriter.println(Arrays.toString(userStoreNames));
                        printWriter.flush();
                    }
                    case "Create Store" -> {
                        String storeName = bufferedReader.readLine();
                        String successOrFailure = validStoreName(storeName);
                        if (successOrFailure.equals("Failure")) {
                            printWriter.println("Failure");
                            printWriter.flush();
                        } else if (successOrFailure.equals("Success")) {
                            ((Seller) currentUser).createStore(new Store(((Seller) currentUser).getEmail(), storeName));
                            printWriter.println("Success");
                            printWriter.flush();
                        }
                    }
                    case "Delete Store" -> {
                        String deleteStoreName = bufferedReader.readLine();
                        ((Seller) currentUser).deleteStore(deleteStoreName);
                        Store[] userStoreList = ((Seller) currentUser).getStore();
                        String[] userStoreNames = new String[userStoreList.length];
                        for (int i = 0; i < userStoreList.length; i++) {
                            userStoreNames[i] = userStoreList[i].getStoreName();
                        }
                        printWriter.println(Arrays.toString(userStoreNames));
                        printWriter.flush();
                    }
                    case "Modify Product" -> {
                        String currentStoreString = bufferedReader.readLine();
                        Store[] userStoreList = ((Seller) currentUser).getStore();
                        Store currentStore = null;
                        for (int i = 0; i < userStoreList.length; i++) {
                            if (userStoreList[i].getStoreName().equals(currentStoreString)) {
                                currentStore = userStoreList[i];
                            }
                        }
                        ArrayList<Item> storeItems = currentStore.getItems();
                        if (storeItems.size() == 0) {
                            printWriter.println("[No items]");
                            printWriter.flush();
                        } else {
                            String[] itemNames = new String[storeItems.size()];
                            for (int i = 0; i < storeItems.size(); i++) {
                                itemNames[i] = storeItems.get(i).getName();
                            }
                            printWriter.println(Arrays.toString(itemNames));
                            printWriter.flush();
                        }
                    }
                    case "Add Product" -> {
                        String currentStoreString = bufferedReader.readLine();
                        String name = bufferedReader.readLine();
                        String description = bufferedReader.readLine();
                        String quantity = bufferedReader.readLine();
                        String price = bufferedReader.readLine();

                        //finds current store
                        Store currentStore = ((Seller) currentUser).getSpecificStore(currentStoreString);

                        //ensure each text field has text
                        if (name.equals("") || description.equals("") || quantity.equals("") || price.equals("")) {
                            printWriter.println("Missing Input");
                            printWriter.flush();
                        } else if (validItemName(name).equals("Failure")) {                //check valid name
                            printWriter.println("Product Name Already Exists");
                            printWriter.flush();
                        } else if (validItemQuantity(quantity).equals("Failure")) {       //check valid quantity
                            printWriter.println("Invalid Quantity");
                            printWriter.flush();
                        } else if (validItemPrice(price).equals("Failure")) {             //check valid price
                            printWriter.println("Invalid Price");
                            printWriter.flush();
                        } else {
                            currentStore.addItem(name, description, Integer.parseInt(quantity), Double.parseDouble(price));

                            printWriter.println("Success");
                            printWriter.flush();
                        }
                    }
                    case "Delete Item" -> {
                        String currentStoreString = bufferedReader.readLine();
                        String itemNameString = bufferedReader.readLine();

                        if (itemNameString.equals("")) {
                            printWriter.println("Failure");
                            printWriter.flush();
                        } else {
                            Store currentStore = ((Seller) currentUser).getSpecificStore(currentStoreString);
                            Item itemToDelete = currentStore.getSpecificItem(itemNameString);

                            currentStore.deleteItem(itemToDelete);
                            printWriter.println("Success");
                            printWriter.flush();
                        }
                    }
                    case "Edit Credentials" -> {
                        String passwordInput = bufferedReader.readLine();

                        if (passwordInput.equals("")) {
                            printWriter.println("No Changed Fields");
                            printWriter.flush();
                        } else {
                            if (changePassword(passwordInput, (currentUser)).equals("Success")) {
                                printWriter.println("Success");
                                printWriter.flush();
                            }
                        }
                    }
                    case "Delete Account" -> {
                        String userEmail = bufferedReader.readLine();
                        BufferedReader bfr = new BufferedReader(new FileReader("FMCredentials.csv"));
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            String[] splitLine = line.split(",");
                            if (splitLine[0].equals(userEmail)) {
                                Seller currentUser = new Seller(splitLine[0], splitLine[1]);
                                currentUser.deleteAccount();
                                printWriter.println("Success");
                                printWriter.flush();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the email and password to see if the user signs in.
     *
     * @param signInEmail    The entered email of the login user
     * @param signInPassword The entered password of the login user
     * @return Either a Buyer or Seller object
     */
    public synchronized static Object signInAccount(String signInEmail, String signInPassword) {
        if (checkExistingCredentials(signInEmail, signInPassword, "signIn").equals("No Account Found")) {
            return null;
        }
        String accountSearch = checkExistingCredentials(signInEmail, signInPassword, "signIn");
        accountSearch = accountSearch.substring(1, accountSearch.length() - 1);
        String[] accountDetails = accountSearch.split(", ");
        if (accountDetails[2].equals("buyer")) {
            return new Buyer(accountDetails[0], accountDetails[1], buyerDataArray(accountDetails[0], "hist"),
                    buyerDataArray(accountDetails[0], "cart"));
        } else if (accountDetails[2].equals("seller")) {
            return new Seller(accountDetails[0], accountDetails[1]);
        }
        return null;
    }

    /**
     * Checks user's existing credentials
     *
     * @param email    The entered email of the login user
     * @param password The entered password of the login user
     * @param purpose  The type of action to check: either checks if user is signed in
     * @return String of email and password of user, "DuplicateEmail" if the email already exists, or
     * "No Account Found" if the account doesn't exist
     */
    public synchronized static String checkExistingCredentials(String email, String password, String purpose) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("FMCredentials.csv"));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] currentLine = line.split(",");

                if (purpose.equals("signIn")) {
                    if (currentLine[0].equals(email) && currentLine[1].equals(password)) {
                        return Arrays.toString(currentLine);
                    }
                } else if (purpose.equals("newAccount")) {
                    if (currentLine[0].equals(email)) {
                        return "DuplicateEmail";
                    }
                }
            }
            bfr.close();
            return "No Account Found";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * A function used to Get user data from FMCredentials to be used in the Buyer constructor
     *
     * @param userEmail  User email to make sure you get the right user data
     * @param cartOrHist If Cart, Cart list is returned. If Hist, Purchase History list is returned
     * @return A string ArrayList of the Buyer's information from FMCredentials.csv
     */
    public synchronized static ArrayList<String> buyerDataArray(String userEmail, String cartOrHist) {
        try {
            ArrayList<String> buyerData = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader("FMCredentials.csv"));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] currentLine = line.split(",");
                if (currentLine[0].equals(userEmail)) { // first checks for user credentials to give correct history
                    if (cartOrHist.equals("hist")) { // if hist, get Purchase history
                        if (currentLine[3].equals("")) {
                            return null;
                        } else {
                            String[] initialData = currentLine[3].split("~"); // Split each purchase
                            Collections.addAll(buyerData, initialData);
                        }
                    } else { // if cart, get cart
                        if (currentLine[4].equals("")) {
                            return null;
                        } else {
                            String[] initialData = currentLine[4].split("~"); // Split each purchase
                            Collections.addAll(buyerData, initialData);
                        }
                    }
                }
            }
            return buyerData;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new account that's either a Buyer or Seller object based on the role,
     * and writes the account information to FMCredentials.csv
     *
     * @param email    The entered email of the account
     * @param password The entered password of the account
     * @param role     The type of account to create: Buyer or Seller
     * @return a Buyer or Seller object based on the role
     */
    public synchronized static Object createAccount(String email, String password, String role) {
        if (checkExistingCredentials(email, password, "newAccount").equals("DuplicateEmail")) {
            return null;
        }
        Buyer currentBuyer = null;
        Seller currentSeller = null;

        if (role.equals("Buyer")) {
            currentBuyer = new Buyer(email, password, null, null);
        } else if (role.equals("Seller")) {
            currentSeller = new Seller(email, password);
        }
        try {                                   //writes the new user's account to the csv file
            PrintWriter CredentialPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter("FMCredentials.csv", true)));
            CredentialPrintWriter.println(email + "," + password + "," + role.toLowerCase() + ",x,x");
            CredentialPrintWriter.flush();
            CredentialPrintWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (role.equals("Buyer")) {
            return currentBuyer;
        } else if (role.equals("Seller")) {
            return currentSeller;
        }
        return null;
    }

    /**
     * Checks if the store name is valid
     *
     * @param storeName The entered store name
     * @return a String denoting "Success" or "Failure", which is handled in run()
     */
    public synchronized static String validStoreName(String storeName) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("FMStores.csv"));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] storeInfo = line.split(",");
                if (storeName.equals(storeInfo[0])) {
                    bfr.close();
                    return "Failure";
                }
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success";
    }

    /**
     * Checks if the item name is valid
     *
     * @param itemName The entered item name
     * @return a String denoting "Success" or "Failure" to indicate whether the requested item name is valid
     */
    public synchronized static String validItemName(String itemName) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("FMItems.csv"));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] itemInfo = line.split(",");
                if (itemName.equals(itemInfo[1])) {
                    bfr.close();
                    return "Failure";
                }
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success";
    }

    /**
     * Checks if the item name is valid
     *
     * @param itemQuantity The user-inputted quantity for the item
     * @return a String denoting "Success" or "Failure" to indicate whether the requested itemQuantity is valid
     */
    public synchronized static String validItemQuantity(String itemQuantity) {
        try {
            if (Integer.parseInt(itemQuantity) <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return "Failure";
        }
        return "Success";
    }

    /**
     * Checks if the item name is valid
     *
     * @param itemPrice The user-inputted price for the item
     * @return a String denoting "Success" or "Failure" to indicate whether the requested itemPrice is valid
     */
    public synchronized static String validItemPrice(String itemPrice) {
        try {
            float floatItemPrice = Float.parseFloat(itemPrice);
            if (((floatItemPrice * 100) % 1 != 0) || (floatItemPrice < 0)) {
                throw new InputMismatchException();
            }
        } catch (Exception e) {
            return "Failure";
        }
        return "Success";
    }

    /**
     * changes the user's password in both the FMCredentials.csv and the password field in the Seller.java class
     *
     * @param passwordInput The user-inputted password
     * @param currentUser The Object the user is currently signed in
     * @return a String denoting "Success" or "Failure" to indicate whether the requested password is valid
     */
    public synchronized static String changePassword(String passwordInput, Object currentUser) {
        // Checks Object type
        if (currentUser instanceof Seller) {
            ((Seller) currentUser).setPassword(passwordInput);
        } else if (currentUser instanceof Buyer) {
            ((Buyer) currentUser).setPassword(passwordInput);
        } else {
            return "Failure";
        }

        try {
            File file = new File("FMCredentials.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter writer = new PrintWriter(fos);

            for (int i = 0; i < lines.size(); i++) {
                String userLine = lines.get(i);
                String email = userLine.split(",")[0];
                // Check currentUser type
                if (currentUser instanceof Seller) {
                    if (((Seller) currentUser).getEmail().equals(email)) {
                        String oldPassword = userLine.split(",")[1];
                        lines.set(i, userLine.replaceAll(oldPassword, passwordInput));
                    }
                } else {
                    if (((Buyer) currentUser).getEmail().equals(email)) {
                        String oldPassword = userLine.split(",")[1];
                        lines.set(i, userLine.replaceAll(oldPassword, passwordInput));
                    }
                }
            }

            for (String s : lines) {
                writer.println(s);
            }

            writer.close();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failure";
    }
}
