import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * The interface in which users with Buyer accounts can access their information
 * Lists the marketplace where Buyers can purchase products
 *
 * @version 24/11/2022
 */
public class MainBuyerFrame extends JComponent implements Runnable {
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    String userEmail;
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();
    String[] columnNames = {"Store", "Product Name", "Price"};
    String[][] rowData = new String[1][];
    //Used for testing until server works
    String[][] dummyRowData = {{"Joe's couches", "Couch", "199.99"}, {"Jim's Chairs", "chair", "29.99"}};
    String[][] dummyRowData2 = {{"Jim's Chairs", "chair", "29.99"}, {"Joe's couches", "Couch", "199.99"}};
    DefaultTableModel tableModel;
    JTable jTable;

    JFrame mainBuyerFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JScrollPane jScrollPane;
    JButton viewCartButton;
    JButton searchButton;
    JButton statisticsButton;
    JTextField searchTextField;
    JButton sortButton;
    JButton reviewHistoryButton;
    JButton manageAccountButton;
    JButton logoutButton;
    JButton refreshButton;

    JPopupMenu tablePopupMenu;
    JMenuItem addToCart;
    JMenuItem moreDetails;

    JPopupMenu sortPopupMenu;
    JMenuItem sortByPrice;
    JMenuItem sortByQuantity;

    JPopupMenu searchPopupMenu;
    JMenuItem searchByName;
    JMenuItem searchByStore;
    JMenuItem searchByDescription;
    JLabel mainLabel;

    JLabel informationMainLabel;
    JLabel storeLabel;
    JLabel nameLabel;
    JLabel descriptionLabel;
    JLabel quantityLabel;
    JLabel priceLabel;

    /**
     *  The constructor of MainBuyerFrame
     *
     * @param socket The socket that connect this local machine with the server
     */
    public MainBuyerFrame (Socket socket, String userEmail) {
        this.socket = socket;
        this.userEmail = userEmail;
    }

    ActionListener popupItemListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JMenuItem choice = (JMenuItem) e.getSource();

            if (choice == addToCart) {
                boolean invalidChoice = true;
                int selectedRow = jTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null,
                            "First select a row using the left click", "No Item Selected", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String quantityAdded = null;
                while (invalidChoice) {
                    quantityAdded = (JOptionPane.showInputDialog(null,
                            "Please input the quantity of " + jTable.getValueAt(selectedRow, 1) + " that you would like to add to your cart",
                            "Add Item To Cart", JOptionPane.QUESTION_MESSAGE));
                    if (quantityAdded == null) {
                        return;
                    }

                    try { // makes sure that user inputted an integer
                        int integerTest = Integer.parseInt(quantityAdded);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Input An Integer!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    invalidChoice = false;
                }
                printWriter.println("Add Item To Cart");
                printWriter.println(jTable.getValueAt(selectedRow, 1));
                printWriter.println(quantityAdded);
                printWriter.flush();

                String serverResponse = null;
                try {
                    serverResponse = bufferedReader.readLine();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                assert serverResponse != null; // Panes below inform user of outcome
                if (serverResponse.equals("Success")) {
                    JOptionPane.showMessageDialog(null, "Item Successfully Added To Cart",
                            "Cart Success", JOptionPane.INFORMATION_MESSAGE);
                } else if (serverResponse.equals("Same Name")) {
                    JOptionPane.showMessageDialog(null, "You can not add the same item twice",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (serverResponse.equals("Quantity error")) {
                    JOptionPane.showMessageDialog(null,
                            "Not enough in stock to match quantity requested", "Cart Error", JOptionPane.ERROR_MESSAGE);
                } else if (serverResponse.equals("Item Not Found")) {
                    JOptionPane.showMessageDialog(null,
                            "Item Not Found, Please Refresh Dashboard", "Cart Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (choice == moreDetails) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null,
                            "First select a row using the left click", "No Item Selected", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                printWriter.println("More Details");
                printWriter.println(jTable.getValueAt(jTable.getSelectedRow(), 1)); // send server item name
                printWriter.flush();

                String serverResponse;
                try {
                    serverResponse = bufferedReader.readLine();
                    if (serverResponse.equals("Success")) {
                        String storeName = bufferedReader.readLine(); // receives all descriptive info
                        String description = bufferedReader.readLine();
                        String price = bufferedReader.readLine();
                        String quantity = bufferedReader.readLine();

                        storeLabel.setText("Store Name: " + storeName);
                        nameLabel.setText("Item Name: " + jTable.getValueAt(jTable.getSelectedRow(), 1));
                        descriptionLabel.setText("Description: " + description);
                        quantityLabel.setText("Quantity: " + quantity);
                        priceLabel.setText(String.format("Price: $%.2f", Double.parseDouble(price)));

                        informationMainLabel.setVisible(true);
                        storeLabel.setVisible(true);
                        nameLabel.setVisible(true);
                        descriptionLabel.setVisible(true);
                        quantityLabel.setVisible(true);
                        priceLabel.setVisible(true);

                    } else if (serverResponse.equals("Failure")) { // Informs User that item could not be found
                        JOptionPane.showMessageDialog(null, "Could not find item on server, " +
                                "please refresh", "Detail Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else if (choice == sortByPrice) {
                //tells Server which operation to perform
                printWriter.println("Sort By Price");
                printWriter.flush();
                try {
                    //gets number of items
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    //resets Table for user
                    jTable.setModel(tableModel);
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (choice == sortByQuantity) {
                //Tells server the operation to perform
                printWriter.println("Sort By Quantity");
                printWriter.flush();
                try {
                    //Gets number of items
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    //resets table for user
                    jTable.setModel(tableModel);
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (choice == searchByName) {
                printWriter.println("Search By Name");
                printWriter.println(searchTextField.getText());
                printWriter.flush();
                try {
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    jTable.setModel((tableModel));
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (choice == searchByStore) {
                printWriter.println("Search By Store");
                printWriter.println(searchTextField.getText());
                printWriter.flush();
                try {
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    jTable.setModel((tableModel));
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (choice == searchByDescription) {
                printWriter.println("Search By Description");
                printWriter.println(searchTextField.getText());
                printWriter.flush();
                try {
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    jTable.setModel((tableModel));
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    };

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //Main options from Right Panel
            if (source == viewCartButton) { // Select Product
                printWriter.println("View Cart");
                printWriter.flush();

                try {
                    String cartLine = bufferedReader.readLine();
                    if (cartLine.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "You have no items in your cart",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (cartLine.equals("Cart Empty")) {
                        JOptionPane.showMessageDialog(null, "Cart is Empty", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        String[] buyerCartsArr = cartLine.split("~");
                        ArrayList<String> buyerCarts = new ArrayList<>();
                        for (int i = 0; i < buyerCartsArr.length; i++) {
                            buyerCarts.add(buyerCartsArr[i]);
                        }

                        SwingUtilities.invokeLater(new CartFrame(socket, buyerCarts, userEmail));
                        mainBuyerFrame.dispose();
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else if (source == searchButton) { // Search
                printWriter.println("Search");
                //prints the text that the user is searching for to server
                printWriter.println(searchTextField.getText());
                printWriter.flush();
                try {
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    jTable.setModel(tableModel);
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (source == manageAccountButton) { // Manage Account
                resetVisible();

                SwingUtilities.invokeLater(new ManageAccountFrame(socket, userEmail));
                mainBuyerFrame.dispose();
            } else if (source == reviewHistoryButton) { // Review Purchase History
                resetVisible();

                SwingUtilities.invokeLater(new PurchaseHistoryFrame(socket, userEmail));
                mainBuyerFrame.dispose();
            } else if (source == refreshButton) {
                printWriter.println("Initial Table"); // reuse Initial Table code because it is functionally identical.
                printWriter.flush();
                try {
                    //Gets number of items
                    int numItems = Integer.parseInt(bufferedReader.readLine());
                    tableModel = updateTable(numItems);
                    //resets table for user
                    jTable.setModel(tableModel);
                    mainBuyerFrame.repaint();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (source == statisticsButton) { // View Statistics
                resetVisible();

                SwingUtilities.invokeLater(new BuyerStatisticsFrame(socket, userEmail));
                mainBuyerFrame.dispose();
            } else if (source == logoutButton) { // Sign Out
                printWriter.println("Reset Login Status");
                printWriter.println(userEmail);
                printWriter.flush();
                try {
                    String successOrFailure = bufferedReader.readLine();
                    SwingUtilities.invokeLater(new LoginFrame(socket));
                    mainBuyerFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainBuyerFrame = new JFrame("Buyer Frame");
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(new GridLayout(6, 1, 20, 20));
        leftPanel.setLayout(null);

        //Adds view Cart Button
        viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(actionListener);
        rightPanel.add(viewCartButton);

        //Creates Sort button with popup menu functionality
        sortPopupMenu = new JPopupMenu();
        sortByPrice = new JMenuItem("Sort By Price");
        sortByQuantity = new JMenuItem("Sort By Quantity");
        sortByPrice.addActionListener(popupItemListener);
        sortByQuantity.addActionListener(popupItemListener);
        sortPopupMenu.add(sortByPrice);
        sortPopupMenu.add(sortByQuantity);
        sortButton = new JButton("Sort Dashboard");
        sortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                sortPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        sortButton.setComponentPopupMenu(sortPopupMenu);
        rightPanel.add(sortButton);

        //adds review History Button
        reviewHistoryButton = new JButton("Purchase History");
        reviewHistoryButton.addActionListener(actionListener);
        rightPanel.add(reviewHistoryButton);

        //adds manage account button
        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);
        rightPanel.add(manageAccountButton);

        //adds statistics button
        statisticsButton = new JButton("Statistics");
        statisticsButton.addActionListener(actionListener);
        rightPanel.add(statisticsButton);

        //adds logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);
        rightPanel.add(logoutButton);

        searchTextField = new JTextField(20);
        searchTextField.setBounds(550, 575, 200, 40);
        searchTextField.setFont(new Font(searchTextField.getFont().getName(), Font.PLAIN, 16));
        leftPanel.add(searchTextField);

        searchPopupMenu = new JPopupMenu();
        searchByName = new JMenuItem("Search By Name");
        searchByStore = new JMenuItem("Search By Store");
        searchByDescription = new JMenuItem("Search By Description");
        searchByName.addActionListener(popupItemListener);
        searchByStore.addActionListener(popupItemListener);
        searchByDescription.addActionListener(popupItemListener);
        searchPopupMenu.add(searchByName);
        searchPopupMenu.add(searchByDescription);
        searchPopupMenu.add(searchByStore);

        searchButton = new JButton("Search for Product");
        searchButton.setBounds(550, 510, 200, 60);
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                searchPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        searchButton.setComponentPopupMenu(searchPopupMenu);
        leftPanel.add(searchButton);
        
        refreshButton = new JButton("Refresh Dashboard");
        refreshButton.setBounds(50,510,200,60);
        refreshButton.addActionListener(actionListener);
        leftPanel.add(refreshButton);

        mainLabel = new JLabel("Products on Furniture Marketplace");
        mainLabel.setBounds(125, 15, 550, 80);
        mainLabel.setFont(new Font(mainLabel.getFont().getName(), Font.BOLD, fontSizeToUse(mainLabel)));
        leftPanel.add(mainLabel);

        Font itemInfoFont = new Font(mainLabel.getFont().getName(), Font.PLAIN, 16);
        informationMainLabel = new JLabel("Item Selected Information");
        informationMainLabel.setBounds(50, 500, 350, 35);
        informationMainLabel.setFont(new Font(mainLabel.getFont().getName(), Font.BOLD, 18));
        leftPanel.add(informationMainLabel);
        informationMainLabel.setVisible(false);

        storeLabel = new JLabel("Store Name: ");
        storeLabel.setBounds(30, 535, 350, 35);
        storeLabel.setFont(itemInfoFont);
        leftPanel.add(storeLabel);
        storeLabel.setVisible(false);

        nameLabel = new JLabel("Item Name: ");
        nameLabel.setBounds(30, 570, 350, 35);
        nameLabel.setFont(itemInfoFont);
        leftPanel.add(nameLabel);
        nameLabel.setVisible(false);

        descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setBounds(30, 605, 450, 35);
        descriptionLabel.setFont(itemInfoFont);
        leftPanel.add(descriptionLabel);
        descriptionLabel.setVisible(false);

        quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setBounds(30, 640, 350, 35);
        quantityLabel.setFont(itemInfoFont);
        leftPanel.add(quantityLabel);
        quantityLabel.setVisible(false);

        priceLabel = new JLabel("Price: $");
        priceLabel.setBounds(30, 675, 350, 35);
        priceLabel.setFont(itemInfoFont);
        leftPanel.add(priceLabel);
        priceLabel.setVisible(false);

        tablePopupMenu = new JPopupMenu();
        addToCart = new JMenuItem("Add to Cart");
        moreDetails = new JMenuItem("See More Details");

        //Adds Popup Functionality to table
        addToCart.addActionListener(popupItemListener);
        moreDetails.addActionListener(popupItemListener);
        tablePopupMenu.add(addToCart);
        tablePopupMenu.add(moreDetails);

        //Initializes table of Items for user to view
        printWriter.println("Initial Table");
        printWriter.flush();
        int itemsInInitialTable = -1;
        try {
            itemsInInitialTable = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        tableModel = updateTable(itemsInInitialTable);
        jTable = new JTable(tableModel);
        //adds popups to table
        jTable.setComponentPopupMenu(tablePopupMenu);

        //Makes the table scrollable
        jScrollPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(25, 100, 750, 350);
        leftPanel.add(jScrollPane);

        //Finalize frame
        mainBuyerFrame.add(splitPane);
        mainBuyerFrame.setSize(1000, 800);
        mainBuyerFrame.setLocationRelativeTo(null);
        mainBuyerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mainBuyerFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    mainBuyerFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainBuyerFrame.setVisible(true);
    }

    /**
     * Returns sorted Table Model
     * @param numItems how many rows the table will contain
     * @return Updated Item Table
     */
    public DefaultTableModel updateTable(int numItems) {
        if (numItems == 0) {
            rowData = null;
        } else if (numItems == -1) {
            JOptionPane.showMessageDialog(null, "Failed to Fetch server Data",
                    "Server Issue", JOptionPane.ERROR_MESSAGE);
        } else {
            rowData = new String[numItems][3];
            try {
                for (int i = 0; i < numItems; i++) {
                    String store = bufferedReader.readLine();
                    String productName = bufferedReader.readLine();
                    String price = bufferedReader.readLine();
                    rowData[i][0] = store;
                    rowData[i][1] = productName;
                    rowData[i][2] = String.format("$%.2f", Double.parseDouble(price));
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return new DefaultTableModel(rowData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Sets currentlyVisible panel to false
     * */
    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }

    //FOUND THIS ONLINE (will change)
    public int fontSizeToUse(JLabel label) {
        Font currentFont = label.getFont();
        String textInLabel = label.getText();
        int stringWidth = label.getFontMetrics(currentFont).stringWidth(textInLabel);
        int componentWidth = label.getWidth();
        double widthRatio = (double) componentWidth / (double) stringWidth;
        int newFontSize = (int) (currentFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        return Math.min(newFontSize, componentHeight);
    }
}
