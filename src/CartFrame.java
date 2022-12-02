import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Interface that allows users to see all their current items in cart, remove items from cart,
 * and checkout all items from their cart.
 *
 * @version 24/11/2022
 */
public class CartFrame extends JComponent implements Runnable {
    Socket socket;
    String[] userCarts;
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

    /**
     *  The constructor of CartFrame
     *
     * @param socket The socket that connect this local machine with the server
     * @param userCarts String Array of all cart items of current user
     */
    public CartFrame(Socket socket, String[] userCarts, String userEmail) {
        this.socket = socket;
        this.userCarts = userCarts;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                cartFrame.dispose();
            } else if (source == removeItemButton) {

            } else if (source == checkoutButton) {

            } else {
                try {
                    itemSelected = e.getActionCommand();
                    currentItem.setText("Item Selected: " + itemSelected.substring(0, itemSelected.indexOf(":") - 1));
                    currentItem.setFont(new Font(currentItem.getFont().getName(),
                            Font.BOLD, fontSizeToUse(currentItem)));
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
        itemsInCartLabel.setBounds(50, 50, 350, 80);
        itemsInCartLabel.setFont(new Font(itemsInCartLabel.getFont().getName(),
                Font.BOLD, fontSizeToUse(itemsInCartLabel)));
        leftPanel.add(itemsInCartLabel);

        selectItem = new JLabel("Format: Item Name, Quantity, Total Cost");
        selectItem.setBounds(40, 140, 370, 80);
        selectItem.setFont(new Font(selectItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(selectItem)));
        leftPanel.add(selectItem);

        // Cart Items
        try {
            ButtonGroup buttonGroup = new ButtonGroup();

            ArrayList<String> itemNameList = new ArrayList<>();
            ArrayList<String> quantityList = new ArrayList<>();
            ArrayList<String> priceList = new ArrayList<>();

            for (int i = 0; i < userCarts.length; i++) { // Get item name
                String[] fields = userCarts[i].split("!");
                itemNameList.add(fields[1]);
                quantityList.add(fields[2]);
                priceList.add(fields[3]);
            }

            for (int i = 0; i < itemNameList.size(); i++) { // Add to radioButton group
                Double totalCost = Integer.parseInt(quantityList.get(i)) * Double.parseDouble(priceList.get(i));
                //radioButton = new JRadioButton(itemNameList.get(i) + " : " + quantityList.get(i) + " : $" +);
                radioButton = new JRadioButton(String.format("%s : %s : $%.2f", itemNameList.get(i), quantityList.get(i), totalCost));
                buttonGroup.add(radioButton);
                radioButton.setBounds(50, 200 + (50 * i), 350, 30);
                radioButton.setFont(new Font (radioButton.getFont().getName(), Font.PLAIN, 18));
                //currentItem.getFont().getName(),
                //                Font.BOLD, fontSizeToUse(currentItem))
                leftPanel.add(radioButton);
                radioButton.addActionListener(actionListener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //right panel
        rightPanel.setLayout(null);
        currentItem = new JLabel("Cart Options");
        currentItem.setBounds(50, 50, 400, 50);
        currentItem.setFont(new Font(currentItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentItem)));
        rightPanel.add(currentItem);

        // Remove Item from Cart
        removeItemButton = new JButton("Remove Item From Cart");
        removeItemButton.addActionListener(actionListener);
        removeItemButton.setBounds(150, 300, 200, 50);
        rightPanel.add(removeItemButton);

        // Checkout all Items from Cart
        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(actionListener);
        checkoutButton.setBounds(150, 200, 200, 50);
        rightPanel.add(checkoutButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(150, 400, 200, 50);
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

    public int fontSizeToUse(JLabel label) {
        Font currentFont = label.getFont();
        String textInLabel = label.getText();
        int stringWidth = label.getFontMetrics(currentFont).stringWidth(textInLabel);
        int componentWidth = label.getWidth();
        double widthRatio = (double) componentWidth / (double) stringWidth;
        int newFontSize = (int) (currentFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        return fontSizeToUse;
    }

//    public static void main(String[] args) { // For testing
//        try {
//            Socket socket1 = new Socket("localhost", 4444);
//            String[] temp =  {"123", "#@", "2"};
//            CartFrame cart = new CartFrame(socket1, temp);
//            cart.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
