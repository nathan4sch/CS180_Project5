import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainBuyerFrame extends JComponent implements Runnable {
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    String[] columnNames = {"Store", "Product Name", "Price"};
    String[][] rowData = new String[3][3];
    //Used for testing until server works
    String[][] dummyRowData = {{"Joe's couches", "Couch", "199.99"}, {"Jim's Chairs",
    "chair", "29.99"}};
    DefaultTableModel tableModel;
    JTable jTable;
    JFrame mainBuyerFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JButton viewCartButton;
    JButton searchButton;
    JTextField searchTextField = new JTextField(10);
    JButton sortButton;
    JButton reviewHistoryButton;
    JButton manageAccountButton;
    JButton logoutButton;
    JScrollPane jScrollPane;
    JPopupMenu popupMenu;
    JMenuItem addToCart;
    JMenuItem moreDetails;


    public MainBuyerFrame (Socket socket) {
        this.socket = socket;
    }
    ActionListener popupItemListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JMenuItem choice = (JMenuItem) e.getSource();
            if (choice == addToCart) {
                //add to cart code
            } else if (choice == moreDetails) {
                //more details code
            }
        }
    };
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //Main options from Right Panel
            if (source == viewCartButton) { // (1) Select Product

            } else if (source == sortButton) {

            }else if (source == searchButton) { // (3) Search

            } else if (source == manageAccountButton) { // (5) Manage Account

            } else if (source == reviewHistoryButton) { // (6) View Statistics

            } else if (source == logoutButton) { // (7) Sign Out
                SwingUtilities.invokeLater(new LoginFrame(socket));
                mainBuyerFrame.dispose();
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

        popupMenu = new JPopupMenu();
        addToCart = new JMenuItem("Add to Cart");
        moreDetails = new JMenuItem("Delete Friend");

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
        rightPanel.setLayout(new GridLayout(5, 1, 20, 20));

        viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(actionListener);
        rightPanel.add(viewCartButton);

        sortButton = new JButton("Sort Dashboard");
        sortButton.addActionListener(actionListener);
        rightPanel.add(sortButton);

        reviewHistoryButton = new JButton("Review Purchase History");
        reviewHistoryButton.addActionListener(actionListener);
        rightPanel.add(reviewHistoryButton);

        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);
        rightPanel.add(manageAccountButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);
        rightPanel.add(logoutButton);

        leftPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        leftPanel.add(searchButton);

        popupMenu = new JPopupMenu();
        addToCart = new JMenuItem("Add to Cart");
        moreDetails = new JMenuItem("Delete Friend");

        addToCart.addActionListener(popupItemListener);
        moreDetails.addActionListener(popupItemListener);
        popupMenu.add(addToCart);
        popupMenu.add(moreDetails);

        tableModel = new DefaultTableModel(dummyRowData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable = new JTable(tableModel);
        jTable.setComponentPopupMenu(popupMenu);

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
     * This method requests the item listing from the server and updates the Table in the left panel with
     * this information.
     *
     * @return Updated item table.
     */
    public DefaultTableModel updateTable() {
        printWriter.println("Get Item list");
        printWriter.flush();
        //this variable serves two purposes. If there are no items, it will be empty. If there are items, it will say how many.
        String emptyChecker = null;
        try {
            emptyChecker = bufferedReader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assert emptyChecker != null;
        if (emptyChecker.equals("empty")) {
            rowData = null;
        } else {
            try {
                int count = Integer.parseInt(emptyChecker);
                rowData = new String[count][3];
                for (int i = 0; i < count; i++) {
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
        }
        return new DefaultTableModel(rowData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
