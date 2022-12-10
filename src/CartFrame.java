import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Interface that allows users to see all their current items in cart, remove items from cart,
 * and checkout all items from their cart.
 *
 * @author Nathan Schneider, Colin Wu, Ben Herrington, Andrei Deaconescu, Dakota Baldwin
 * @version 12/10/20222
 */
public class CartFrame extends JComponent implements Runnable {
    Socket socket;
    ArrayList<String> userCarts;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame cartFrame;
    JSplitPane splitPane;
    JPanel rightPanel;
    JPanel leftPanel;
    JButton returnToDashButton;
    String itemSelected = "";
    String userEmail;

    //Right Panel
    JLabel selectItem;
    JLabel itemsInCartLabel;
    JRadioButton radioButton;

    // Cart Options
    JButton removeItemButton;
    JButton checkoutButton;

    //Left Panel
    JLabel currentItem;
    JLabel cartOptions;

    /**
     * The constructor of CartFrame
     *
     * @param socket    The socket that connect this local machine with the server
     * @param userCarts String Array of all cart items of current user
     * @param userEmail Email of current user
     */
    public CartFrame(Socket socket, ArrayList<String> userCarts, String userEmail) {
        this.socket = socket;
        this.userCarts = userCarts;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        /**
         * @param e Invoked when any of the button in the frame is selected.
         *          returnToDashButton - user is redirected back to MainBuyerFrame.java
         *          removeItemButton - removes the selected item from cart.
         *          checkoutButton - checks out all items and adds them to the current user's purchase history.
         *                          If all items are successfully checked out users will be redirected back to
         *                          MainBuyerFrame.java.
         */
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                cartFrame.dispose();
            } else if (source == removeItemButton) {
                try {
                    String itemName = currentItem.getText();
                    itemName = itemName.substring(itemName.indexOf(":") + 2);
                    printWriter.println("Remove Cart Item");
                    printWriter.println(itemName);
                    printWriter.flush();

                    String success = bufferedReader.readLine();
                    if (success.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Item successfully removed from " +
                                        "cart", "Cart",
                                JOptionPane.INFORMATION_MESSAGE);

                        SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                        cartFrame.dispose();
                    } else if (success.equals("Cart Empty")) {
                        JOptionPane.showMessageDialog(null, "Cart is Empty - " +
                                        "Cart Action", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Item NOT Removed: " +
                                "Please Reload the Frame", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Item NOT Removed", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (source == checkoutButton) {
                printWriter.println("Checkout");
                printWriter.flush();

                String serverResponse;
                try {
                    serverResponse = bufferedReader.readLine();
                    switch (serverResponse) {
                        case "Success" -> {  // All items checked out successfully
                            JOptionPane.showMessageDialog(null,
                                    "Checkout Successful", "Checkout", JOptionPane.INFORMATION_MESSAGE);
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                            cartFrame.dispose();
                        }
                        case "Partial Success" -> {  // some items checked out
                            // number of success read
                            String[] checkoutSuccesses = new String[Integer.parseInt(bufferedReader.readLine())];
                            // number of failures read
                            String[] checkoutFailures = new String[Integer.parseInt(bufferedReader.readLine())];
                            // gathers descriptive data from server to display to user
                            for (int i = 0; i < checkoutSuccesses.length; i++) {
                                checkoutSuccesses[i] = bufferedReader.readLine();
                            } // checkout failures have format of [Item,quantity requested,reason for error]
                            for (int i = 0; i < checkoutFailures.length; i++) {
                                checkoutFailures[i] = bufferedReader.readLine();
                            }
                            String[] splitCheckoutSuccess = checkoutSuccesses[0].split(",");
                            String[] splitCheckoutFailure = checkoutFailures[0].split(",");
                            // appropriately formatting strings to be used in JOptionPane below
                            String formattedCheckoutSuccess = "1. " + splitCheckoutSuccess[0] + "; Quantity: " +
                                    splitCheckoutSuccess[1];
                            String formattedCheckoutFailure = "1. " + splitCheckoutFailure[0] + "; Quantity: " +
                                    splitCheckoutFailure[1] + "; Reason for Checkout Failure: " +
                                    splitCheckoutFailure[2];
                            for (int i = 1; i < checkoutSuccesses.length; i++) {
                                splitCheckoutSuccess = checkoutSuccesses[i].split(",");
                                formattedCheckoutSuccess = formattedCheckoutSuccess + "\n" + (i + 1) + ". " +
                                        splitCheckoutSuccess[0] + "; Quantity: " + splitCheckoutSuccess[1];
                            }
                            for (int i = 1; i < checkoutFailures.length; i++) {
                                splitCheckoutFailure = checkoutFailures[i].split(",");
                                formattedCheckoutFailure = formattedCheckoutFailure + "\n" + (i + 1) + ". " +
                                        splitCheckoutFailure[0] + "; Quantity: " + splitCheckoutFailure[1] +
                                        "; Reason For Checkout Failure: " + splitCheckoutFailure[2];
                            }
                            JOptionPane.showMessageDialog(null, "SUCCESSFULLY " +
                                            "CHECKED OUT ITEMS: \n" + formattedCheckoutSuccess + "\nUNSUCCESSFULLY " +
                                            "CHECKED OUT ITEMS: \n" + formattedCheckoutFailure, "Partial Checkout",
                                    JOptionPane.WARNING_MESSAGE);
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                            cartFrame.dispose();
                        }
                        case "Failure" -> {
                            System.out.println("test failure");
                            JOptionPane.showMessageDialog(null, "Unable to Checkout Items",
                                    "Checkout Failure", JOptionPane.ERROR_MESSAGE);
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                            cartFrame.dispose();
                        }
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else {
                try {
                    itemSelected = e.getActionCommand();
                    currentItem.setText("Item Selected: " + itemSelected.substring(0, itemSelected.indexOf(":") - 1));
                    currentItem.setFont(new Font(currentItem.getFont().getName(),
                            Font.BOLD, fontSizeToUse(currentItem)));
                    int xOffset = 130 - currentItem.getText().length();
                    currentItem.setBounds(xOffset, 175, 400, 20);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
    };

    @Override
    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cartFrame = new JFrame("View Cart Frame");
        splitPane = new JSplitPane();
        rightPanel = new JPanel();
        leftPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setLeftComponent(rightPanel);
        splitPane.setRightComponent(leftPanel);

        //left panel
        leftPanel.setLayout(null);

        itemsInCartLabel = new JLabel("Items in Cart");
        itemsInCartLabel.setBounds(50, 50, 350, 50);
        itemsInCartLabel.setFont(new Font(itemsInCartLabel.getFont().getName(),
                Font.BOLD, fontSizeToUse(itemsInCartLabel)));
        leftPanel.add(itemsInCartLabel);

        selectItem = new JLabel("Item Name : Quantity : Total Cost      ");
        selectItem.setBounds(50, 155, 350, 60);
        selectItem.setFont(new Font(selectItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(selectItem) - 1));
        leftPanel.add(selectItem);

        // Cart Items
        try {
            ButtonGroup buttonGroup = new ButtonGroup();

            ArrayList<String> itemNameList = new ArrayList<>();
            ArrayList<String> quantityList = new ArrayList<>();
            ArrayList<String> priceList = new ArrayList<>();

            for (String userCart : userCarts) { // Get item name
                String[] fields = userCart.split("!");
                itemNameList.add(fields[1]);
                quantityList.add(fields[2]);
                priceList.add(fields[3]);
            }

            for (int i = 0; i < userCarts.size(); i++) { // Add to radioButton group
                Double totalCost = Integer.parseInt(quantityList.get(i)) * Double.parseDouble(priceList.get(i));
                radioButton = new JRadioButton(String.format("%s : %s : $%.2f", itemNameList.get(i),
                        quantityList.get(i), totalCost));
                buttonGroup.add(radioButton);
                radioButton.setBounds(50, 250 + (50 * i), 350, 30);
                radioButton.setFont(new Font(radioButton.getFont().getName(), Font.PLAIN, 18));
                leftPanel.add(radioButton);
                radioButton.addActionListener(actionListener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cart is Empty - Cart Run exc", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        //right panel
        rightPanel.setLayout(null);
        cartOptions = new JLabel("Cart Options");
        cartOptions.setBounds(100, 50, 400, 50);
        cartOptions.setFont(new Font(cartOptions.getFont().getName(),
                Font.BOLD, fontSizeToUse(cartOptions)));
        rightPanel.add(cartOptions);

        currentItem = new JLabel("No item selected");
        currentItem.setBounds(170, 175, 400, 20);
        currentItem.setFont(new Font(currentItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentItem)));
        rightPanel.add(currentItem);

        // Checkout all Items from Cart
        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(actionListener);
        checkoutButton.setBounds(150, 250, 200, 50);
        rightPanel.add(checkoutButton);

        // Remove Item from Cart
        removeItemButton = new JButton("Remove Item From Cart");
        removeItemButton.addActionListener(actionListener);
        removeItemButton.setBounds(150, 350, 200, 50);
        rightPanel.add(removeItemButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(150, 450, 200, 50);
        rightPanel.add(returnToDashButton);

        //Finalize frame
        cartFrame.add(splitPane);
        cartFrame.setSize(1000, 800);
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        cartFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    cartFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cartFrame.setVisible(true);
    }

    /**
     * Calculates a scalable font size for JLabels in the GUI
     *
     * @param component The JLabel to get the font size of
     * @return an int to be used for the font size
     */
    public int fontSizeToUse(JLabel component) {
        Font fontOfLabel = component.getFont();
        String textInLabel = component.getText();
        int stringWidth = component.getFontMetrics(fontOfLabel).stringWidth(textInLabel);
        int componentWidth = component.getWidth();
        double widthRatio = (double) componentWidth / (double) stringWidth;
        int newFontSize = (int) (fontOfLabel.getSize() * widthRatio);
        int componentHeight = component.getHeight();

        return Math.min(newFontSize, componentHeight);
    }
}
