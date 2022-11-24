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

public class MainSellerFrame extends JComponent implements Runnable {
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame mainSellerFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JButton manageStoresButton;
    JButton salesListButton;
    JButton statisticsButton;
    JButton viewCartButton;
    JButton manageAccountButton;
    JButton signOutButton;
    //Manage Stores
    JLabel manageStoreMainLabel;
    JButton manageCatalogueButton;
    JButton createStoreButton;
    JButton renameStoreButton;
    JButton deleteStoreButton;
    JLabel newStoreName;
    JTextField inputStoreName;
    JComponent[] manageStoreGUI;

    //Manage Account
    JLabel manageAccountMainLabel;
    JButton editAccountButton;
    JButton deleteAccountButton;
    JTextField newEmail;
    JTextField newPassword;
    JLabel emailLabel;
    JLabel passwordLabel;
    JComponent[] manageAccountGUI;

    public MainSellerFrame(Socket socket) {
        this.socket = socket;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            //Main options from Right Panel
            if (source == manageStoresButton) {
                resetVisible();
                for (int i = 0; i < manageStoreGUI.length; i++) {
                    manageStoreGUI[i].setVisible(true);
                    currentlyVisible.add(manageStoreGUI[i]);
                }

            } else if (source == salesListButton) {

            } else if (source == statisticsButton) {

            } else if (source == viewCartButton) {

            } else if (source == manageAccountButton) {
                resetVisible();
                for (int i = 0; i < manageAccountGUI.length; i++) {
                    manageAccountGUI[i].setVisible(true);
                    currentlyVisible.add(manageAccountGUI[i]);
                }

            } else if (source == signOutButton) {
                SwingUtilities.invokeLater(new LoginFrame(socket));
                mainSellerFrame.dispose();
            }

            //Manage Account Buttons
            if (source == editAccountButton) {

            } else if (source == deleteAccountButton) {

            }

            //Manage Store Buttons
            if (source == createStoreButton) {
                try {
                    printWriter.println("Create Store");
                    printWriter.println(inputStoreName.getText());
                    printWriter.flush();

                    String successOrFailure = bufferedReader.readLine();
                    if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Store Created", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "Store Name Already Exists",
                                "Create Store Failure", JOptionPane.ERROR_MESSAGE);
                    }
                    inputStoreName.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                String[] userStoreList = null;
                try {
                    printWriter.println("Manage Store");
                    printWriter.flush();
                    String userStoresString = bufferedReader.readLine();
                    userStoresString = userStoresString.substring(1, userStoresString.length() - 1);
                    userStoreList = userStoresString.split(", ");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (source == deleteStoreButton) {
                    SwingUtilities.invokeLater(new ManageStoreFrame(socket, "Delete Store", userStoreList));
                    mainSellerFrame.dispose();
                } else if (source == manageCatalogueButton) {
                    SwingUtilities.invokeLater(new ManageStoreFrame(socket, "Manage Catalogue", userStoreList));
                    mainSellerFrame.dispose();
                } else if (source == renameStoreButton) {
                    SwingUtilities.invokeLater(new ManageStoreFrame(socket, "Rename Store", userStoreList));
                    mainSellerFrame.dispose();
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
        mainSellerFrame = new JFrame("Account Frame");
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

        //Manage Stores Button
        manageStoresButton = new JButton("Manage Stores");
        manageStoresButton.addActionListener(actionListener);
        rightPanel.add(manageStoresButton);

        //Sales List Button
        salesListButton = new JButton("Sales List");
        salesListButton.addActionListener(actionListener);
        rightPanel.add(salesListButton);

        //Statistics Dashboard Button
        statisticsButton = new JButton("Statistics Dashboard");
        statisticsButton.addActionListener(actionListener);
        rightPanel.add(statisticsButton);

        //View Current Carts Button
        viewCartButton = new JButton("View Current Carts");
        viewCartButton.addActionListener(actionListener);
        rightPanel.add(viewCartButton);

        //Manage Account Button
        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);
        rightPanel.add(manageAccountButton);

        //Sign Out Button
        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(actionListener);
        rightPanel.add(signOutButton);

        //Left Panel
        leftPanel.setLayout(null);

        //Manage Stores
        manageStoreMainLabel = new JLabel("Manage Stores");
        manageStoreMainLabel.setBounds(200, 10, 400, 60);
        manageStoreMainLabel.setFont(new Font(manageStoreMainLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(manageStoreMainLabel)));
        leftPanel.add(manageStoreMainLabel);
        manageStoreMainLabel.setVisible(false);

        newStoreName = new JLabel("Input New Store Name: ");
        newStoreName.setBounds(500, 300, 200, 40);
        int fontSize = fontSizeToUse(newStoreName);
        newStoreName.setFont(new Font(newStoreName.getFont().getName(), Font.PLAIN, fontSize));
        leftPanel.add(newStoreName);
        newStoreName.setVisible(false);

        inputStoreName = new JTextField(100);
        inputStoreName.setBounds(500, 350, 200, 40);
        leftPanel.add(inputStoreName);
        inputStoreName.setVisible(false);

        createStoreButton = new JButton("Create Store");
        createStoreButton.addActionListener(actionListener);
        createStoreButton.setBounds(500, 400, 200, 40);
        leftPanel.add(createStoreButton);
        createStoreButton.setVisible(false);

        manageCatalogueButton = new JButton("Manage Catalogue");
        manageCatalogueButton.addActionListener(actionListener);
        manageCatalogueButton.setBounds(100, 250, 200, 80);
        leftPanel.add(manageCatalogueButton);
        manageCatalogueButton.setVisible(false);

        renameStoreButton = new JButton("Rename Store");
        renameStoreButton.addActionListener(actionListener);
        renameStoreButton.setBounds(100, 350, 200, 80);
        leftPanel.add(renameStoreButton);
        renameStoreButton.setVisible(false);

        deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.addActionListener(actionListener);
        deleteStoreButton.setBounds(100, 450, 200, 80);
        leftPanel.add(deleteStoreButton);
        deleteStoreButton.setVisible(false);

        manageStoreGUI = new JComponent[]{manageStoreMainLabel, manageCatalogueButton, createStoreButton,
                renameStoreButton, deleteStoreButton, newStoreName, inputStoreName};


        //Manage Account
        manageAccountMainLabel = new JLabel("Manage Account");
        manageAccountMainLabel.setBounds(200, 10, 400, 60);
        manageAccountMainLabel.setFont(new Font(manageAccountMainLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(manageAccountMainLabel)));
        leftPanel.add(manageAccountMainLabel);
        manageAccountMainLabel.setVisible(false);

        passwordLabel = new JLabel("Input New Password: ");
        passwordLabel.setBounds(100, 250, 200, 40);
        fontSize = fontSizeToUse(passwordLabel);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, fontSize));
        leftPanel.add(passwordLabel);
        passwordLabel.setVisible(false);

        emailLabel = new JLabel("Input New Email: ");
        emailLabel.setBounds(100, 200, 200, 40);
        emailLabel.setFont(new Font(emailLabel.getFont().getName(), Font.PLAIN, fontSize));
        leftPanel.add(emailLabel);
        emailLabel.setVisible(false);

        newEmail = new JTextField(100);
        newEmail.setBounds(300, 200, 200, 40);
        leftPanel.add(newEmail);
        newEmail.setVisible(false);

        newPassword = new JTextField(100);
        newPassword.setBounds(300, 250, 200, 40);
        leftPanel.add(newPassword);
        newPassword.setVisible(false);

        editAccountButton = new JButton("Edit Credentials");
        editAccountButton.addActionListener(actionListener);
        editAccountButton.setBounds(100, 300, 200, 70);
        leftPanel.add(editAccountButton);
        editAccountButton.setVisible(false);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(actionListener);
        deleteAccountButton.setBounds(310, 300, 200, 70);
        leftPanel.add(deleteAccountButton);
        deleteAccountButton.setVisible(false);

        manageAccountGUI = new JComponent[]{manageAccountMainLabel, editAccountButton, deleteAccountButton, newEmail, newPassword, emailLabel, passwordLabel};

        //Finalize frame
        mainSellerFrame.add(splitPane);
        mainSellerFrame.setSize(1000, 800);
        mainSellerFrame.setLocationRelativeTo(null);
        mainSellerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mainSellerFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    mainSellerFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainSellerFrame.setVisible(true);

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

    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }

}
