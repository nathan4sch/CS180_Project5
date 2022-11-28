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
    JRadioButton radioButton;

    //Objective Manage Catalogue
    JButton deleteStoreButton;
    JButton modifyProductsButton;


    //Left Panel
    JLabel currentStore;

    public ManageStoreFrame(Socket socket, String[] userStores) {
        this.socket = socket;
        this.userStores = userStores;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainSellerFrame(socket));
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
                        SwingUtilities.invokeLater(new ManageCatalogueFrame(socket, storeSelected, storeItemNames));
                        manageStoreFrame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
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
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(new GridLayout(userStores.length + 1, 1, 20, 20));

        selectStore = new JLabel("Your Stores");
        rightPanel.add(selectStore);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < userStores.length; i++) {
            radioButton = new JRadioButton(userStores[i]);
            buttonGroup.add(radioButton);
            rightPanel.add(radioButton);
            radioButton.addActionListener(actionListener);
        }

        //leftPanel
        leftPanel.setLayout(null);
        currentStore = new JLabel("Select a Store to Modify");
        currentStore.setBounds(200, 10, 400, 120);
        currentStore.setFont(new Font(currentStore.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentStore)));
        leftPanel.add(currentStore);

        //Objective Manage Catalogue

        deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.addActionListener(actionListener);
        deleteStoreButton.setBounds(300, 300, 200, 80);
        leftPanel.add(deleteStoreButton);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(300, 400, 200, 80);
        leftPanel.add(returnToDashButton);

        modifyProductsButton = new JButton("Modify Products");
        modifyProductsButton.addActionListener(actionListener);
        modifyProductsButton.setBounds(300, 200, 200, 80);
        leftPanel.add(modifyProductsButton);

        //Finalize frame
        manageStoreFrame.add(splitPane);
        manageStoreFrame.setSize(1000, 800);
        manageStoreFrame.setLocationRelativeTo(null);
        manageStoreFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageStoreFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
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
