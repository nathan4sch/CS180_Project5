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

/**
 * MainBuyerFrame class
 *
 * The interface in which users with Buyer accounts can access their information
 * Lists the marketplace where Buyers can purchase products
 *
 * @version 24/11/2022
 */
public class MainBuyerFrame extends JComponent implements Runnable {
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame mainBuyerFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JButton selectProductButton; // (1) Select Product
    JButton viewCartButton; // (2) View Cart
    JButton searchButton; // (3) Search
    JButton purchaseHistoryButton; // (4) Review Purchase History
    JButton viewStatisticsButton; // (5) Manage Account
    JButton manageAccountButton; // (6) View Statistics
    JButton signOutButton; // (7) Sign Out

    //Manage Account
    JLabel manageAccountMainLabel;
    JButton editAccountButton;
    JButton deleteAccountButton;
    JTextField newEmail;
    JTextField newPassword;
    JLabel emailLabel;
    JLabel passwordLabel;
    JComponent[] manageAccountGUI;

    public MainBuyerFrame(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) { // testing
        try {
            Socket socket1 = new Socket("localhost", 4444);
            MainBuyerFrame buyer = new MainBuyerFrame(socket1);
            buyer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //Main options from Right Panel
            if (source == selectProductButton) { // (1) Select Product

            } else if (source == viewCartButton) { // (2) View Cart

            } else if (source == searchButton) { // (3) Search

            } else if (source == purchaseHistoryButton) { // (4) Review Purchase History

            } else if (source == manageAccountButton) { // (5) Manage Account

            } else if (source == viewStatisticsButton) { // (6) View Statistics

            } else if (source == signOutButton) { // (7) Sign Out
                SwingUtilities.invokeLater(new LoginFrame(socket));
                mainBuyerFrame.dispose();
            }

            //Manage Account Buttons
            if (source == editAccountButton) {

            } else if (source == deleteAccountButton) {

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
        rightPanel.setLayout(new GridLayout(7, 1, 20, 20));

        // Select Product Button
        selectProductButton = new JButton("Select Product");
        selectProductButton.addActionListener(actionListener);
        rightPanel.add(selectProductButton);

        // View Cart Button
        viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(actionListener);
        rightPanel.add(viewCartButton);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        rightPanel.add(searchButton);

        // Purchase History Button
        purchaseHistoryButton = new JButton("Review Purchase History");
        purchaseHistoryButton.addActionListener(actionListener);
        rightPanel.add(purchaseHistoryButton);

        //Manage Account Button
        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);
        rightPanel.add(manageAccountButton);

        // View Statistics Button
        viewStatisticsButton = new JButton("View Statistics");
        viewStatisticsButton.addActionListener(actionListener);
        rightPanel.add(viewStatisticsButton);

        //Sign Out Button
        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(actionListener);
        rightPanel.add(signOutButton);

        //Left Panel
        leftPanel.setLayout(null);

        //Manage Account
//        int fontSize = fontSizeToUse;
//        manageAccountMainLabel = new JLabel("Manage Account");
//        manageAccountMainLabel.setBounds(200, 10, 400, 60);
//        manageAccountMainLabel.setFont(new Font(manageAccountMainLabel.getFont().getName(),
//                Font.PLAIN, fontSizeToUse(manageAccountMainLabel)));
//        leftPanel.add(manageAccountMainLabel);
//        manageAccountMainLabel.setVisible(false);
//
//        passwordLabel = new JLabel("Input New Password: ");
//        passwordLabel.setBounds(100, 250, 200, 40);
//        fontSize = fontSizeToUse(passwordLabel);
//        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, fontSize));
//        leftPanel.add(passwordLabel);
//        passwordLabel.setVisible(false);
//
//        emailLabel = new JLabel("Input New Email: ");
//        emailLabel.setBounds(100, 200, 200, 40);
//        emailLabel.setFont(new Font(emailLabel.getFont().getName(), Font.PLAIN, fontSize));
//        leftPanel.add(emailLabel);
//        emailLabel.setVisible(false);
//
//        newEmail = new JTextField(100);
//        newEmail.setBounds(300, 200, 200, 40);
//        leftPanel.add(newEmail);
//        newEmail.setVisible(false);
//
//        newPassword = new JTextField(100);
//        newPassword.setBounds(300, 250, 200, 40);
//        leftPanel.add(newPassword);
//        newPassword.setVisible(false);
//
//        editAccountButton = new JButton("Edit Credentials");
//        editAccountButton.addActionListener(actionListener);
//        editAccountButton.setBounds(100, 300, 200, 70);
//        leftPanel.add(editAccountButton);
//        editAccountButton.setVisible(false);
//
//        deleteAccountButton = new JButton("Delete Account");
//        deleteAccountButton.addActionListener(actionListener);
//        deleteAccountButton.setBounds(310, 300, 200, 70);
//        leftPanel.add(deleteAccountButton);
//        deleteAccountButton.setVisible(false);
//
//        manageAccountGUI = new JComponent[]{manageAccountMainLabel, editAccountButton, deleteAccountButton, newEmail, newPassword, emailLabel, passwordLabel};

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

//    //FOUND THIS ONLINE (will change)
//    public int fontSizeToUse(JLabel label) {
//        Font currentFont = label.getFont();
//        String textInLabel = label.getText();
//        int stringWidth = label.getFontMetrics(currentFont).stringWidth(textInLabel);
//        int componentWidth = label.getWidth();
//        double widthRatio = (double) componentWidth / (double) stringWidth;
//        int newFontSize = (int) (currentFont.getSize() * widthRatio);
//        int componentHeight = label.getHeight();
//        int fontSizeToUse = Math.min(newFontSize, componentHeight);
//
//        return fontSizeToUse;
//    }

    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }
}
