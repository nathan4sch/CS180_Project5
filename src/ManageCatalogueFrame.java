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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
    String[] storeItemNames;
    //Right Panel
    JLabel selectProduct;
    JRadioButton radioButton;
    //Left Panel
    JLabel currentItem;
    JButton returnToDashButton;
    JButton deleteProductButton;
    JButton editProductButton;
    JButton exportFileButton;
    JButton importFileButton;
    //adding product
    JLabel productName;
    JLabel productDescription;
    JLabel productQuantity;
    JLabel productPrice;
    JTextField nameInput;
    JTextField descriptionInput;
    JTextField quantityInput;
    JTextField priceInput;
    JButton addProductButton;


    public ManageCatalogueFrame(Socket socket, String storeSelected, String[] storeItemNames) {
        this.socket = socket;
        this.storeSelected = storeSelected;
        if (storeItemNames[0].equals("No items")) {
            this.storeItemNames = new String[0];
        } else {
            this.storeItemNames = storeItemNames;
        }
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainSellerFrame(socket));
                manageCatalogueFrame.dispose();
            } else if (source == exportFileButton) {

            } else if (source == importFileButton) {

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
                        case "Missing Input":
                            JOptionPane.showMessageDialog(null, "Missing Input in Text Fields",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Product Name Already Exists":
                            JOptionPane.showMessageDialog(null, "Product Name Already Exists",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Invalid Quantity":
                            JOptionPane.showMessageDialog(null, "Inputted Quantity must be a " +
                                    "Positive Integer", "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Invalid Price":
                            JOptionPane.showMessageDialog(null, "Inputted Price must be " +
                                            "Positve and have Two Decimal Places", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Success":
                            String[] newStoreItemNames = new String[storeItemNames.length + 1];
                            for (int i = 0; i < storeItemNames.length; i++) {
                                newStoreItemNames[i] = storeItemNames[i];
                            }
                            newStoreItemNames[newStoreItemNames.length - 1] = name;
                            JOptionPane.showMessageDialog(null, "Item Added",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            manageCatalogueFrame.dispose();
                            SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected, newStoreItemNames));
                            break;
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (source == deleteProductButton) {

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
                        SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected, storeItemNames));
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "No Item Selected",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                itemSelected = e.getActionCommand();
                currentItem.setText("Product Selected: " + itemSelected);
                currentItem.setFont(new Font(currentItem.getFont().getName(),
                        Font.BOLD, fontSizeToUse(currentItem)));
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
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(new GridLayout(storeItemNames.length + 1, 1, 20, 20));

        if (storeItemNames.length == 0) {
            selectProduct = new JLabel("No Items in Store");
        } else {
            selectProduct = new JLabel("Select Product");
        }
        rightPanel.add(selectProduct);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < storeItemNames.length; i++) {
            radioButton = new JRadioButton(storeItemNames[i]);
            buttonGroup.add(radioButton);
            rightPanel.add(radioButton);
            radioButton.addActionListener(actionListener);
        }

        //leftPanel
        //Main title: does not change
        leftPanel.setLayout(null);
        currentItem = new JLabel("Select a Product");
        currentItem.setBounds(200, 10, 400, 40);
        currentItem.setFont(new Font(currentItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentItem)));
        leftPanel.add(currentItem);

        //Add products
        productName = new JLabel("Product Name: ");
        productName.setBounds(50, 200, 200, 40);
        int fontSize = fontSizeToUse(productName);
        String fontName = productName.getFont().getName();
        productName.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productName);

        productDescription = new JLabel("Description: ");
        productDescription.setBounds(50, 250, 200, 40);
        productDescription.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productDescription);

        productQuantity = new JLabel("Quantity: ");
        productQuantity.setBounds(50, 300, 200, 40);
        productQuantity.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productQuantity);

        productPrice = new JLabel("Price: ");
        productPrice.setBounds(50, 350, 200, 40);
        productPrice.setFont(new Font(fontName, Font.PLAIN, fontSize));
        leftPanel.add(productPrice);

        nameInput = new JTextField(100);
        nameInput.setBounds(250, 200, 200, 40);
        leftPanel.add(nameInput);

        descriptionInput = new JTextField(100);
        descriptionInput.setBounds(250, 250, 200, 40);
        leftPanel.add(descriptionInput);

        quantityInput = new JTextField(100);
        quantityInput.setBounds(250, 300, 200, 40);
        leftPanel.add(quantityInput);

        priceInput = new JTextField(100);
        priceInput.setBounds(250, 350, 200, 40);
        leftPanel.add(priceInput);

        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(actionListener);
        addProductButton.setBounds(150, 400, 180, 80);
        leftPanel.add(addProductButton);

        //Buttons to modify the selected Product
        editProductButton = new JButton("Edit Selected Item");
        editProductButton.addActionListener(actionListener);
        editProductButton.setBounds(550, 200, 180, 80);
        leftPanel.add(editProductButton);

        deleteProductButton = new JButton("Delete Selected Item");
        deleteProductButton.addActionListener(actionListener);
        deleteProductButton.setBounds(550, 300, 180, 80);
        leftPanel.add(deleteProductButton);


        //Buttons on bottom of left panel
        importFileButton = new JButton("Import Product File");
        importFileButton.addActionListener(actionListener);
        importFileButton.setBounds(100, 650, 180, 80);
        leftPanel.add(importFileButton);

        exportFileButton = new JButton("Export Product File");
        exportFileButton.addActionListener(actionListener);
        exportFileButton.setBounds(300, 650, 180, 80);
        leftPanel.add(exportFileButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(500, 650, 180, 80);
        leftPanel.add(returnToDashButton);


        //Finalize frame
        manageCatalogueFrame.add(splitPane);
        manageCatalogueFrame.setSize(1000, 800);
        manageCatalogueFrame.setLocationRelativeTo(null);
        manageCatalogueFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageCatalogueFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
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

        return fontSizeToUse - 3;
    }
}


//needs to be able to add a product with name, description, quantity, price
//needs to be able to edit the selected product
//both of these need to check if the product name already exists.

//Export and import the product file