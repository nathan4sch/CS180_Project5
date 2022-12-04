import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class ManageStoreFrame extends JComponent implements Runnable {

    Socket socket;
    String[] userStores;
    String userEmail;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame manageStoreFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JButton returnToDashButton;
    String storeSelected = "";
    //Right Panel
    JLabel selectStore;
    JLabel optionsOfStore;
    JRadioButton radioButton;

    //Objective Manage Catalogue
    JButton deleteStoreButton;
    JButton modifyProductsButton;
    JButton importProductFile;
    JButton salesListButton;
    JButton statisticsButton;


    //Left Panel
    JLabel currentStore;

    public ManageStoreFrame(Socket socket, String[] userStores, String userEmail) {
        this.socket = socket;
        this.userStores = userStores;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainSellerFrame(socket, userEmail));
                manageStoreFrame.dispose();
            } else if (source == deleteStoreButton) {
                if (storeSelected.equals("")) {
                    JOptionPane.showMessageDialog(null, "No Store Selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    printWriter.println("Delete Store");
                    printWriter.println(storeSelected);
                    printWriter.flush();
                    try {
                        String userStoresString = bufferedReader.readLine();
                        userStoresString = userStoresString.substring(1, userStoresString.length() - 1);
                        userStores = userStoresString.split(", ");
                        storeSelected = "";
                        manageStoreFrame.dispose();
                        run();
                        JOptionPane.showMessageDialog(null, "Store Deleted",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (source == modifyProductsButton) {
                if (storeSelected.equals("")) {
                    JOptionPane.showMessageDialog(null, "No Store Selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    printWriter.println("Modify Product");
                    printWriter.println(storeSelected);
                    printWriter.flush();
                    try {
                        String storeItemsString = bufferedReader.readLine();
                        storeItemsString = storeItemsString.substring(1, storeItemsString.length() - 1);
                        String[] storeItemNames = storeItemsString.split(", ");
                        SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected, storeItemNames, userEmail));
                        manageStoreFrame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (source == importProductFile) {

                JOptionPane.showMessageDialog(null, "Ensure each line in the imported file is " +
                                "formatted correctly\nExample: storeName,itemName,description,quantity,price" +
                                "\nWalmart,Table,strong wooden table in great condition,10,99.99",
                        "How to Format Line in Text File", JOptionPane.INFORMATION_MESSAGE);
                String fileName = JOptionPane.showInputDialog(null, "What is the name of the " +
                        "file to Import?", "File Name", JOptionPane.QUESTION_MESSAGE);
                if (fileName != null) {
                    printWriter.println("Import Product File");
                    printWriter.println(fileName);
                    printWriter.flush();
                    try {
                        String successOrFailure = bufferedReader.readLine();

                        if (successOrFailure.equals("Success")) {
                            String numberOfProductsAdded = bufferedReader.readLine();

                            JOptionPane.showMessageDialog(null, "Added " +
                                            numberOfProductsAdded + " Items", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else if (successOrFailure.equals("Failure")) {
                            JOptionPane.showMessageDialog(null, "No Products were Added",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            } else if (source == salesListButton) {
                if (storeSelected.equals("")) {
                    JOptionPane.showMessageDialog(null, "No Store Selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    printWriter.println("Seller Sales List");
                    printWriter.println(storeSelected);
                    printWriter.flush();
                    try {
                        String salesListString = bufferedReader.readLine();
                        if (salesListString.equals("Failure")) {
                            JOptionPane.showMessageDialog(null, "No Sales",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Formatted JOptionPane to show Sales List
                            String[] salesData = salesListString.split("~");
                            ArrayList<String> saleList = new ArrayList<>();
                            for (int i = 0; i < salesData.length; i++) {
                                String[] individualSale = salesData[i].split("!");
                                String output = String.format("(%s) Customer: %s   Product: %s   Quantity Bought: %s   " +
                                        "Price Bought At: $%s", i + 1, individualSale[0], individualSale[1],
                                        individualSale[2], individualSale[3]);
                                saleList.add(output);
                            }

                            JScrollPane scrollPane;
                            String[] paneOptions = saleList.toArray(new String[0]);
                            JList<String> list = new JList<>(paneOptions);
                            scrollPane = new JScrollPane(list);

                            JPanel panel = new JPanel();
                            panel.add(scrollPane);

                            scrollPane.getViewport().add(list);
                            scrollPane.setSize(1000, 1000);

                            JOptionPane.showMessageDialog(null, scrollPane, "Sales List",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            } else if (source == statisticsButton) {
                if (storeSelected.equals("")) {
                    JOptionPane.showMessageDialog(null, "No Store Selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] options = {"Buyer Statistics", "Sorted Buyer Statistics", "Item Statistics", "Sorted Item Statistics"};
                    Object selectionObject = JOptionPane.showInputDialog(null,
                            "Select Statistics to View", "Statistics",
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (selectionObject != null) {
                        printWriter.println("Seller Statistics");
                        printWriter.println(selectionObject.toString());
                        printWriter.println(storeSelected);
                        printWriter.flush();

                        try {
                            String statisticsString = bufferedReader.readLine();

                            if (statisticsString.equals("Failure")) {
                                JOptionPane.showMessageDialog(null, "No Statistics",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String buyerOrItem = bufferedReader.readLine();
                                // Formatted JOptionPane to show Statistics
                                statisticsString = statisticsString.substring(1, statisticsString.length() - 1);
                                String[] statisticsData = statisticsString.split(", ");
                                ArrayList<String> statisticsList = new ArrayList<>();
                                String title = "Statistics";
                                for (int i = 0; i < statisticsData.length; i++) {

                                    String[] individualStatistic = statisticsData[i].split("~");
                                    String output = "";
                                    if (buyerOrItem.equals("buyer")) {
                                        output = String.format("Customer: %s   Items Purchased: %s",
                                                individualStatistic[0], individualStatistic[1]);
                                        title = "Buyer Statistics";
                                    } else if (buyerOrItem.equals("item")) {
                                        output = String.format("Item Name: %s   Number of Sales: %s",
                                                individualStatistic[0], individualStatistic[1]);
                                        title = "Item Statistics";
                                    }
                                    statisticsList.add(output);
                                }

                                JScrollPane scrollPane;
                                String[] paneOptions = statisticsList.toArray(new String[0]);
                                JList<String> list = new JList<>(paneOptions);
                                scrollPane = new JScrollPane(list);

                                JPanel panel = new JPanel();
                                panel.add(scrollPane);

                                scrollPane.getViewport().add(list);
                                scrollPane.setSize(1000, 1000);

                                JOptionPane.showMessageDialog(null, scrollPane, title,
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        // Formatted JOptionPane to show Purchase History
//                        JScrollPane scrollPane;
//                        String[] paneOptions = historyList.toArray(new String[0]);
//                        JList<String> list = new JList<>(paneOptions);
//                        scrollPane = new JScrollPane(list);
//
//                        JPanel panel = new JPanel();
//                        panel.add(scrollPane);
//
//                        scrollPane.getViewport().add(list);
//                        scrollPane.setSize(600, 500);
//
//                        JOptionPane.showMessageDialog(null, scrollPane, "Buyer Statistics",
//                                JOptionPane.PLAIN_MESSAGE);
                    }
                }

            } else {
                storeSelected = e.getActionCommand();
                currentStore.setText("Store Selected: " + storeSelected);
                currentStore.setFont(new Font(currentStore.getFont().getName(),
                        Font.BOLD, fontSizeToUse(currentStore)));
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
        manageStoreFrame = new JFrame("Manage Store Frame");
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(750);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(null);

        selectStore = new JLabel("Your Stores");
        selectStore.setBounds(10, 50, 200, 80);
        selectStore.setFont(new Font(selectStore.getFont().getName(),
                Font.BOLD, fontSizeToUse(selectStore)));
        rightPanel.add(selectStore);

        ButtonGroup buttonGroup = new ButtonGroup();
//        for (int i = 0; i < userStores.length; i++) {
//            radioButton = new JRadioButton(userStores[i]);
//            buttonGroup.add(radioButton);
//            rightPanel.add(radioButton);
//            radioButton.addActionListener(actionListener);
//        }

        for (int i = 0; i < userStores.length; i++) { // Add to radioButton group
            radioButton = new JRadioButton(userStores[i]);
            buttonGroup.add(radioButton);
            radioButton.setBounds(10, 150 + (50 * i), 200, 30);
            radioButton.setFont(new Font (radioButton.getFont().getName(), Font.PLAIN, 18));
            rightPanel.add(radioButton);
            radioButton.addActionListener(actionListener);
        }

        //leftPanel
        leftPanel.setLayout(null);
        currentStore = new JLabel("Select a Store");
        currentStore.setBounds(200, 10, 400, 120);
        currentStore.setFont(new Font(currentStore.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentStore)));
        leftPanel.add(currentStore);

        optionsOfStore = new JLabel("Options for the Selected Store");
        optionsOfStore.setBounds(260, 180, 400, 80);
        optionsOfStore.setFont(new Font(optionsOfStore.getFont().getName(),
                Font.PLAIN, 24));
        leftPanel.add(optionsOfStore);


        //Objective Manage Catalogue

        deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.addActionListener(actionListener);
        deleteStoreButton.setBounds(450, 350, 200, 80);
        leftPanel.add(deleteStoreButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(470, 600, 200, 80);
        leftPanel.add(returnToDashButton);

        importProductFile = new JButton("Import Product File");
        importProductFile.addActionListener(actionListener);
        importProductFile.setBounds(180, 600, 200, 80);
        leftPanel.add(importProductFile);

        modifyProductsButton = new JButton("Modify Products");
        modifyProductsButton.addActionListener(actionListener);
        modifyProductsButton.setBounds(450, 250, 200, 80);
        leftPanel.add(modifyProductsButton);

        //Sales List Button
        salesListButton = new JButton("Sales List");
        salesListButton.addActionListener(actionListener);
        salesListButton.setBounds(200, 250, 200, 80);
        leftPanel.add(salesListButton);

        //Statistics Dashboard Button
        statisticsButton = new JButton("Statistics of Store");
        statisticsButton.addActionListener(actionListener);
        statisticsButton.setBounds(200, 350, 200, 80);
        leftPanel.add(statisticsButton);

        //Finalize frame
        manageStoreFrame.add(splitPane);
        manageStoreFrame.setSize(1000, 800);
        manageStoreFrame.setLocationRelativeTo(null);
        manageStoreFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageStoreFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    manageStoreFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        manageStoreFrame.setVisible(true);
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
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        return fontSizeToUse;
    }
}


//work on the wonky girdlayout for the users stores. Probably do gridbaglayout
