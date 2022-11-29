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
public class ManageAccountFrame extends JComponent implements Runnable {
    Socket socket;
    String userEmail;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame accountFrame;
    JPanel rightPanel;
    JPanel leftPanel;
    JSplitPane splitPane;
    JButton returnToDashButton;
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();

    //Manage Account
    JLabel manageAccountMainLabel;
    JButton editAccountButton;
    JButton deleteAccountButton;
    JTextField newPassword;
    JLabel passwordLabel;
    JComponent[] manageAccountGUI;

    /**
     * The constructor of CartFrame
     *
     * @param socket    The socket that connect this local machine with the server
     * @param userEmail Email of the current user
     */
    public ManageAccountFrame(Socket socket, String userEmail) {
        this.socket = socket;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                accountFrame.dispose();
            } else if (source == editAccountButton) {
                String passwordInput = newPassword.getText();
                printWriter.println("Edit Credentials");
                printWriter.println(passwordInput);
                printWriter.flush();
                try {
                    String successOrFailure = bufferedReader.readLine();

                    if (successOrFailure.equals("No Changed Fields")) {
                        JOptionPane.showMessageDialog(null, "Input a New Password",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Password Changed",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        newPassword.setText("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (source == deleteAccountButton) {
                printWriter.println("Delete Account");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String success = bufferedReader.readLine();
                    JOptionPane.showMessageDialog(null, "Account Deleted", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.invokeLater(new LoginFrame(socket));
                    accountFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
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
        accountFrame = new JFrame("Manage Account Frame");
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        splitPane = new JSplitPane();

        leftPanel.setLayout(null);

        // Return to dashboard button
        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(385, 675, 200, 50);
        leftPanel.add(returnToDashButton);

        // Edit Account Button
        editAccountButton = new JButton("Change Password");
        editAccountButton.addActionListener(actionListener);

        // Delete Account Button
        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(actionListener);

        // Manage Account
        manageAccountMainLabel = new JLabel("Manage Account");
        manageAccountMainLabel.setBounds(300, 10, 400, 100);
        manageAccountMainLabel.setFont(new Font(manageAccountMainLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(manageAccountMainLabel)));
        leftPanel.add(manageAccountMainLabel);
        manageAccountMainLabel.setVisible(false);

        passwordLabel = new JLabel("Input New Password: ");
        passwordLabel.setBounds(290, 325, 200, 40);
        int fontSize = fontSizeToUse(passwordLabel);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, fontSize));
        leftPanel.add(passwordLabel);
        passwordLabel.setVisible(false);

        newPassword = new JTextField(100);
        newPassword.setBounds(490, 325, 200, 40);
        leftPanel.add(newPassword);
        newPassword.setVisible(false);

        editAccountButton = new JButton("Change Password");
        editAccountButton.addActionListener(actionListener);
        editAccountButton.setBounds(285, 375, 200, 60);
        leftPanel.add(editAccountButton);
        editAccountButton.setVisible(false);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(actionListener);
        deleteAccountButton.setBounds(495, 375, 200, 60);
        leftPanel.add(deleteAccountButton);
        deleteAccountButton.setVisible(false);

        manageAccountGUI = new JComponent[]{manageAccountMainLabel, editAccountButton, deleteAccountButton, newPassword, passwordLabel, returnToDashButton};

        //Finalize frame
        accountFrame.add(leftPanel);
        accountFrame.setSize(1000, 800);
        accountFrame.setLocationRelativeTo(null);
        accountFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        accountFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    accountFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        accountFrame.setVisible(true);

        resetVisible();

        for (JComponent jComponent : manageAccountGUI) {
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
     * */
    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }
}
