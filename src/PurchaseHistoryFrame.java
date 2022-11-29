import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Interface that allows users to manage their account. Users can either change their password or delete their account
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
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                purchaseHistoryFrame.dispose();
            } else if (source == viewHistoryButton) {

            } else if (source == exportHistoryButton) {
                printWriter.println("Export History");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String success = bufferedReader.readLine();
                    if (success.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "File Exported!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        SwingUtilities.invokeLater(new LoginFrame(socket));
                        purchaseHistoryFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Export Failed!", "Error",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Export Failed!", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
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
        purchaseHistoryFrame = new JFrame("Manage Account Frame");
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

        // Manage Account
        purchaseHistoryLabel = new JLabel("Purchase History");
        purchaseHistoryLabel.setBounds(300, 10, 400, 100);
        purchaseHistoryLabel.setFont(new Font(purchaseHistoryLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(purchaseHistoryLabel)));
        mainPanel.add(purchaseHistoryLabel);
        purchaseHistoryLabel.setVisible(false);

        purchaseHistoryGUI = new JComponent[]{purchaseHistoryLabel, viewHistoryButton, exportHistoryButton, returnToDashButton};

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

    /**
     * Sets currentlyVisible panel to false
     */
    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }
}