import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Interface that allows users to manage products in their store.
 * Users can edit an existing product's information, create a new product, and deleted the selected product.
 * Users can also export a file with a list of all their products under the specific store
 *
 * @version 24/11/2022
 */
public class ManageCatalogueFrame extends JComponent implements Runnable {
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame manageCatalogueFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    String storeSelected;
    String itemSelected = "";
    String userEmail;
    String[] storeItemNames;

    //Right Panel
    JLabel selectProduct;
    JRadioButton radioButton;

    //Left Panel
    JLabel mainLabel;
    JLabel currentItemLabel;
    JButton returnToDashButton;
    JButton deleteProductButton;
    JButton editProductButton;
    JButton exportFileButton;
    // Adding product
    JLabel productName;
    JLabel productDescription;
    JLabel productQuantity;
    JLabel productPrice;
    JTextField nameInput;
    JTextField descriptionInput;
    JTextField quantityInput;
    JTextField priceInput;
    JButton addProductButton;

    /**
     * The constructor of ManageCatalogueFrame
     *
     * @param socket         The socket that connect this local machine with the server
     * @param storeSelected  The name of the selected store
     * @param storeItemNames String Array of all item names in the store
     * @param userEmail      Email of current user
     */
    public ManageCatalogueFrame(Socket socket, String storeSelected, String[] storeItemNames, String userEmail) {
        this.socket = socket;
        this.storeSelected = storeSelected;
        if (storeItemNames[0].equals("No items")) {
            this.storeItemNames = new String[0];
        } else {
            this.storeItemNames = storeItemNames;
        }
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        /**
         * @param e Invoked when any of the button in the frame is selected.
         *          returnToDashButton - user is redirected back to MainSellerFrame.java
         *          exportFileButton - exports a file containing all the product information of the store.
         *                              The file format is "storeName--Items.csv".
         *          addProductButton - creates a new item based on the information entered in the text fields.
         *          editProductButton - changes the selected items information to the new information entered
         *          in the text fields.
         *          deleteProductButton - asks the current user to confirm to delete the selected item.
         */
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainSellerFrame(socket, userEmail));
                manageCatalogueFrame.dispose();
            } else if (source == exportFileButton) {
                printWriter.println("Export Product File");
                printWriter.println(storeSelected);
                printWriter.flush();

                try {
                    String successOrFailure = bufferedReader.readLine();
                    if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "File Exported",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "You have no products in this store",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (source == addProductButton) {
                String name = nameInput.getText();
                String description = descriptionInput.getText();
                String quantity = quantityInput.getText();
                String price = priceInput.getText();

                printWriter.println("Add Product");
                printWriter.println(storeSelected);
                printWriter.println(name);
                printWriter.println(description);
                printWriter.println(quantity);
                printWriter.println(price);
                printWriter.flush();

                try {
                    String successOrFailure = bufferedReader.readLine();

                    switch (successOrFailure) {
                        case "Missing Input" -> JOptionPane.showMessageDialog(null,
                                "Missing Input in Text Fields", "Error", JOptionPane.ERROR_MESSAGE);

                        case "Product Name Already Exists" ->
                                JOptionPane.showMessageDialog(null, "Product Name Already Exists",
                                        "Error", JOptionPane.ERROR_MESSAGE);

                        case "Invalid Quantity" -> JOptionPane.showMessageDialog(null,
                                "Inputted Quantity must be a Positive Integer", "Error",
                                JOptionPane.ERROR_MESSAGE);

                        case "Invalid Price" -> JOptionPane.showMessageDialog(null,
                                "Inputted Price must be Positive and have Two Decimal Places", "Error",
                                JOptionPane.ERROR_MESSAGE);

                        case "Invalid Name Format" -> JOptionPane.showMessageDialog(null,
                                "Invalid Name Format: Item name cannot have a comma or Exclamation Mark.",
                                "Error", JOptionPane.ERROR_MESSAGE);

                        case "Invalid Description Format" -> JOptionPane.showMessageDialog(null,
                                "Invalid Description Format: Item description cannot " +
                                        "have a comma or Exclamation Mark.",
                                "Error", JOptionPane.ERROR_MESSAGE);

                        case "Success" -> {
                            String[] newStoreItemNames = new String[storeItemNames.length + 1];
                            for (int i = 0; i < storeItemNames.length; i++) {
                                newStoreItemNames[i] = storeItemNames[i];
                            }
                            newStoreItemNames[newStoreItemNames.length - 1] = name;
                            JOptionPane.showMessageDialog(null, "Item Added",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            manageCatalogueFrame.dispose();
                            SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected,
                                    newStoreItemNames, userEmail));
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (source == deleteProductButton) {
                int confirmDelete = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this product?", "Delete Product",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmDelete == 0) { // Confirm delete product
                    printWriter.println("Delete Item");
                    printWriter.println(storeSelected);
                    printWriter.println(itemSelected);
                    printWriter.flush();

                    try {
                        String successOrFailure = bufferedReader.readLine();
                        if (successOrFailure.equals("Success")) {
                            ArrayList<String> newStoreItemNames = new ArrayList<>();
                            for (int i = 0; i < storeItemNames.length; i++) {
                                if (!storeItemNames[i].equals(itemSelected)) {
                                    newStoreItemNames.add(storeItemNames[i]);
                                }
                            }
                            if (newStoreItemNames.size() == 0) {
                                newStoreItemNames.add("No items");
                            }
                            String[] storeItemNames = new String[newStoreItemNames.size()];
                            for (int i = 0; i < storeItemNames.length; i++) {
                                storeItemNames[i] = newStoreItemNames.get(i);
                            }

                            JOptionPane.showMessageDialog(null, "Product Deleted",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            manageCatalogueFrame.dispose();
                            SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected,
                                    storeItemNames, userEmail));
                        } else if (successOrFailure.equals("Failure")) {
                            JOptionPane.showMessageDialog(null, "No Item Selected",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (source == editProductButton) {
                String name = nameInput.getText();
                String description = descriptionInput.getText();
                String quantity = quantityInput.getText();
                String price = priceInput.getText();

                printWriter.println("Edit Product");
                printWriter.println(storeSelected);
                printWriter.println(itemSelected);

                printWriter.println(name);
                printWriter.println(description);
                printWriter.println(quantity);
                printWriter.println(price);
                printWriter.flush();

                try {
                    String successOrFailure = bufferedReader.readLine();

                    switch (successOrFailure) {
                        case "Name Change Success" -> {
                            ArrayList<String> newStoreItemNames = new ArrayList<>();
                            for (int i = 0; i < storeItemNames.length; i++) {
                                if (storeItemNames[i].equals(itemSelected)) {
                                    storeItemNames[i] = name;
                                }
                                newStoreItemNames.add(storeItemNames[i]);
                            }
                            String[] storeItemNames = new String[newStoreItemNames.size()];
                            for (int i = 0; i < storeItemNames.length; i++) {
                                storeItemNames[i] = newStoreItemNames.get(i);
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Product Name Successfully Changed", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            manageCatalogueFrame.dispose();
                            SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected,
                                    storeItemNames, userEmail));
                        }
                        case "Success" -> JOptionPane.showMessageDialog(null,
                                "Product Successfully Changed", "Success",
                                JOptionPane.INFORMATION_MESSAGE);

                        case "No Item Selected" -> JOptionPane.showMessageDialog(null,
                                "Select an Item", "Error", JOptionPane.ERROR_MESSAGE);

                        case "Missing Input" -> JOptionPane.showMessageDialog(null,
                                "Change One of the Input Fields", "Error", JOptionPane.ERROR_MESSAGE);

                        case "Invalid Name Format" -> JOptionPane.showMessageDialog(null,
                                "Invalid Name Format: Item name cannot have a ',' ",
                                "Error", JOptionPane.ERROR_MESSAGE);

                        case "Invalid Description Format" -> JOptionPane.showMessageDialog(null,
                                "Invalid Description Format: Item description cannot have a ',' ",
                                "Error", JOptionPane.ERROR_MESSAGE);

                        case "Changed More Than One Field" -> JOptionPane.showMessageDialog(null,
                                "Only Change One of the Input Fields", "Error",
                                JOptionPane.ERROR_MESSAGE);

                        case "This Product Name Already Exists" ->
                                JOptionPane.showMessageDialog(null, "Product Name Already Exists",
                                        "Invalid Name", JOptionPane.ERROR_MESSAGE);

                        case "Quantity Must be a Positive Integer" -> JOptionPane.showMessageDialog(null,
                                "Quantity must be a Positive Integer", "Error",
                                JOptionPane.ERROR_MESSAGE);

                        case "Price Must be a Two Decimal Number" -> JOptionPane.showMessageDialog(null,
                                "Price must be a Positive Number with Two Decimal Places",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                itemSelected = e.getActionCommand();
                currentItemLabel.setText("Product Selected: " + itemSelected);
                currentItemLabel.setFont(new Font(currentItemLabel.getFont().getName(),
                        Font.PLAIN, fontSizeToUse(currentItemLabel)));
                int xOffset = 240 - currentItemLabel.getText().length();
                currentItemLabel.setBounds(xOffset, 200, 400, 30);

                printWriter.println("Item Selected");
                printWriter.println(storeSelected);
                printWriter.println(itemSelected);
                printWriter.flush();

                try {
                    nameInput.setText(bufferedReader.readLine());
                    descriptionInput.setText(bufferedReader.readLine());
                    quantityInput.setText(bufferedReader.readLine());
                    priceInput.setText(bufferedReader.readLine());
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
        manageCatalogueFrame = new JFrame("Catalogue Frame");
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(750);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // rightPanel
        rightPanel.setLayout(null);

        if (storeItemNames.length == 0) {
            selectProduct = new JLabel("No Items in Store");
        } else {
            selectProduct = new JLabel("Current Products");
        }
        selectProduct.setBounds(10, 50, 200, 80);
        selectProduct.setFont(new Font(selectProduct.getFont().getName(),
                Font.BOLD, fontSizeToUse(selectProduct)));
        rightPanel.add(selectProduct);

        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < storeItemNames.length; i++) { // Add to radioButton group
            radioButton = new JRadioButton(storeItemNames[i]);
            buttonGroup.add(radioButton);
            radioButton.setBounds(10, 150 + (50 * i), 200, 30);
            radioButton.setFont(new Font(radioButton.getFont().getName(), Font.PLAIN, 18));
            rightPanel.add(radioButton);
            radioButton.addActionListener(actionListener);
        }

        //leftPanel
        //Main title: does not change
        leftPanel.setLayout(null);
        mainLabel = new JLabel("Store Products");
        mainLabel.setBounds(180, -10, 400, 120);
        mainLabel.setFont(new Font(mainLabel.getFont().getName(),
                Font.BOLD, fontSizeToUse(mainLabel)));
        leftPanel.add(mainLabel);

        currentItemLabel = new JLabel("Select a product");
        currentItemLabel.setBounds(280, 200, 400, 30);
        currentItemLabel.setFont(new Font(currentItemLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(currentItemLabel)));
        leftPanel.add(currentItemLabel);

        //Add products
        productName = new JLabel("Product Name: ");
        productName.setBounds(60, 275, 200, 40);
        int fontSize = fontSizeToUse(productName);
        String fontName = productName.getFont().getName();
        productName.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productName);

        productDescription = new JLabel("Description: ");
        productDescription.setBounds(60, 325, 200, 40);
        productDescription.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productDescription);

        productQuantity = new JLabel("Quantity: ");
        productQuantity.setBounds(60, 375, 200, 40);
        productQuantity.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productQuantity);

        productPrice = new JLabel("Price: ");
        productPrice.setBounds(60, 425, 200, 40);
        productPrice.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productPrice);

        nameInput = new JTextField(100);
        nameInput.setBounds(260, 275, 200, 40);
        leftPanel.add(nameInput);

        descriptionInput = new JTextField(100);
        descriptionInput.setBounds(260, 325, 200, 40);
        leftPanel.add(descriptionInput);

        quantityInput = new JTextField(100);
        quantityInput.setBounds(260, 375, 200, 40);
        leftPanel.add(quantityInput);

        priceInput = new JTextField(100);
        priceInput.setBounds(260, 425, 200, 40);
        leftPanel.add(priceInput);


        //Buttons to modify the selected Product
        editProductButton = new JButton("Edit Selected Item");
        editProductButton.addActionListener(actionListener);
        editProductButton.setBounds(510, 275, 180, 50);
        leftPanel.add(editProductButton);

        deleteProductButton = new JButton("Delete Selected Item");
        deleteProductButton.addActionListener(actionListener);
        deleteProductButton.setBounds(510, 345, 180, 50);
        leftPanel.add(deleteProductButton);

        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(actionListener);
        addProductButton.setBounds(510, 415, 180, 50);
        leftPanel.add(addProductButton);

        //Buttons on bottom of left panel
        exportFileButton = new JButton("Export Product File");
        exportFileButton.addActionListener(actionListener);
        exportFileButton.setBounds(140, 660, 200, 60);
        leftPanel.add(exportFileButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(420, 660, 200, 60);
        leftPanel.add(returnToDashButton);

        //Finalize frame
        manageCatalogueFrame.add(splitPane);
        manageCatalogueFrame.setSize(1000, 800);
        manageCatalogueFrame.setLocationRelativeTo(null);
        manageCatalogueFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageCatalogueFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    manageCatalogueFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        manageCatalogueFrame.setVisible(true);
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
