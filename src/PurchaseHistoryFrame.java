import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Interface that allows users to view their purchase history and export a file of their purchase history.
 *
 * @version 27/11/2022
 */
public class PurchaseHistoryFrame extends JComponent implements Runnable {
    Socket socket;
    String userEmail;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame purchaseHistoryFrame;
    JPanel mainPanel;
    JButton returnToDashButton;
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();

    // Purchase History
    JLabel purchaseHistoryLabel;
    JButton viewHistoryButton;
    JButton exportHistoryButton;
    JComponent[] purchaseHistoryGUI;

    /**
     * The constructor of Purchase History Frame
     *
     * @param socket    The socket that connect this local machine with the server
     * @param userEmail Email of the current user
     */
    public PurchaseHistoryFrame(Socket socket, String userEmail) {
        this.socket = socket;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        /**
         * @param e Invoked when any of the button in the frame is selected.
         *          returnToDashButton - user is redirected back to MainBuyerFrame.java
         *          exportFileButton - exports a file containing the purchase history of the current user.
         *                              The file format is "userEmailPurchaseHistory.csv".
         *          viewHistoryButton - shows a panel containing a list of all the items purchased by the current user.
         */
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                purchaseHistoryFrame.dispose();
            } else if (source == viewHistoryButton) { // View Purchase History
                printWriter.println("View History");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String line = bufferedReader.readLine();

                    if (!line.equals("Error")) {
                        ArrayList<String> historyList = new ArrayList<>();
                        String[] itemSplit = line.split("~");

                        for (int i = 0; i < itemSplit.length; i++) { // Loop through lines to add to historyList
                            String[] fields = itemSplit[i].split("!");
                            int num = i + 1;

                            // Fields
                            String storeName = fields[0];
                            String itemName = fields[1];
                            String quantity = fields[2];
                            double totalPrice = Double.parseDouble(fields[3]) * Double.parseDouble(quantity);

                            String historyLine = String.format("%d. %s purchased from %s. Quantity: %s Total: $%.2f",
                                    num, itemName, storeName, quantity, totalPrice);

                            historyList.add(historyLine);
                        }

                        // Formatted JOptionPane to show Purchase History
                        JScrollPane scrollPane;
                        String[] paneOptions = historyList.toArray(new String[0]);
                        JList<String> list = new JList<>(paneOptions);
                        scrollPane = new JScrollPane(list);

                        JPanel panel = new JPanel();
                        panel.add(scrollPane);

                        scrollPane.getViewport().add(list);
                        scrollPane.setSize(500, 400);

                        JOptionPane.showMessageDialog(null, scrollPane, "Purchase History",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Purchase History does not exist!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (source == exportHistoryButton) { // Export Purchase History
                printWriter.println("Export History");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String success = bufferedReader.readLine();
                    if (success.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "File Exported!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Export Failed!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    String errorMess = String.valueOf(ex);
                    JOptionPane.showMessageDialog(null, errorMess, "Error",
                            JOptionPane.ERROR_MESSAGE);
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
        purchaseHistoryFrame = new JFrame("Purchase History Frame");
        mainPanel = new JPanel();

        mainPanel.setLayout(null);

        // Return to dashboard button
        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(385, 675, 200, 50);
        mainPanel.add(returnToDashButton);

        // View History Button
        viewHistoryButton = new JButton("View Purchase History");
        viewHistoryButton.addActionListener(actionListener);
        viewHistoryButton.setBounds(285, 375, 200, 60);
        mainPanel.add(viewHistoryButton);
        viewHistoryButton.setVisible(false);

        // Export History Button
        exportHistoryButton = new JButton("Export Purchase History");
        exportHistoryButton.addActionListener(actionListener);
        exportHistoryButton.setBounds(495, 375, 200, 60);
        mainPanel.add(exportHistoryButton);
        exportHistoryButton.setVisible(false);

        // Purchase History
        purchaseHistoryLabel = new JLabel("Purchase History");
        purchaseHistoryLabel.setBounds(300, 10, 400, 100);
        purchaseHistoryLabel.setFont(new Font(purchaseHistoryLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(purchaseHistoryLabel)));
        mainPanel.add(purchaseHistoryLabel);
        purchaseHistoryLabel.setVisible(false);

        purchaseHistoryGUI = new JComponent[]{purchaseHistoryLabel, viewHistoryButton,
                exportHistoryButton, returnToDashButton};

        //Finalize frame
        purchaseHistoryFrame.add(mainPanel);
        purchaseHistoryFrame.setSize(1000, 800);
        purchaseHistoryFrame.setLocationRelativeTo(null);
        purchaseHistoryFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        purchaseHistoryFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    purchaseHistoryFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        purchaseHistoryFrame.setVisible(true);

        resetVisible();

        for (JComponent jComponent : purchaseHistoryGUI) {
            jComponent.setVisible(true);
            currentlyVisible.add(jComponent);
        }
    }


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

    /**
     * Sets currentlyVisible panel to false
     */
    public void resetVisible() {
        for (JComponent jComponent : currentlyVisible) {
            jComponent.setVisible(false);
        }
    }
}
