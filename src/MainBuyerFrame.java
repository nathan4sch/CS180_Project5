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
    JTextField searchTextField = new JTextField(10);
    JButton sortButton;
    JButton reviewHistoryButton;
    JButton manageAccountButton;
    JButton logoutButton;

    JPopupMenu tablePopupMenu;
    JMenuItem addToCart;
    JMenuItem moreDetails;

    JPopupMenu sortPopupMenu;
    JMenuItem sortByPrice;
    JMenuItem sortByQuantity;

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
                //add to cart code
            } else if (choice == moreDetails) {
                //more details code
            } else if (choice == sortByPrice) {
                //tells Server which operation to perform
                printWriter.println("Sort Price");
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
                printWriter.println("Sort Quantity");
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
                    String[] buyerCarts = cartLine.split("~");

                    SwingUtilities.invokeLater(new CartFrame(socket, buyerCarts, userEmail));
                    mainBuyerFrame.dispose();
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
            } else if (source == statisticsButton) { // View Statistics

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

        mainBuyerFrame = new JFrame("Account Frame");
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

        leftPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        leftPanel.add(searchButton);

        tablePopupMenu = new JPopupMenu();
        addToCart = new JMenuItem("Add to Cart");
        moreDetails = new JMenuItem("Delete Friend");

        //Adds Popup Functionality to table
        addToCart.addActionListener(popupItemListener);
        moreDetails.addActionListener(popupItemListener);
        tablePopupMenu.add(addToCart);
        tablePopupMenu.add(moreDetails);

        //Initializes table of Items for user to view
        printWriter.println("Initial Table");
        int itemsInInitialTable = -1;
//        try {
//            itemsInInitialTable = Integer.parseInt(bufferedReader.readLine());
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
        tableModel = updateTable(itemsInInitialTable);
        jTable = new JTable(tableModel);
        //adds popups to table
        jTable.setComponentPopupMenu(tablePopupMenu);

        //Makes the table scrollable
        jScrollPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
                    rowData[i][2] = price;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return new DefaultTableModel(rowData, columnNames) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        return null;
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
