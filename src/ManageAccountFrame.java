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
    JPanel mainPanel;
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
     * The constructor of Manage Account Frame
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
            /**
             * @param e Invoked when any of the button in the frame is selected.
             *          returnToDashButton - user is redirected back to MainBuyerFrame.java
             *          editAccountButton - changes the user's password if it is valid
             *          deleteAccountButton - deletes the user's account
             */
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

                    switch (successOrFailure) {
                        case "No Changed Fields" -> JOptionPane.showMessageDialog(null,
                                "Input a New Password", "Error", JOptionPane.ERROR_MESSAGE);

                        case "Success" -> {
                            JOptionPane.showMessageDialog(null, "Password Changed",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            newPassword.setText("");
                        }
                        case "Invalid Format" -> JOptionPane.showMessageDialog(null,
                                "Invalid Format: Passwords cannot contain commas",
                                "Error", JOptionPane.ERROR_MESSAGE);

                        case "Invalid Length" -> JOptionPane.showMessageDialog(null,
                                "Passwords must be at least 6 characters long",
                                "Error", JOptionPane.ERROR_MESSAGE);
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
        mainPanel = new JPanel();

        mainPanel.setLayout(null);

        // Return to dashboard button
        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(385, 675, 200, 50);
        mainPanel.add(returnToDashButton);

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
        mainPanel.add(manageAccountMainLabel);
        manageAccountMainLabel.setVisible(false);

        passwordLabel = new JLabel("Input New Password: ");
        passwordLabel.setBounds(290, 325, 200, 40);
        int fontSize = fontSizeToUse(passwordLabel);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, fontSize));
        mainPanel.add(passwordLabel);
        passwordLabel.setVisible(false);

        newPassword = new JTextField(100);
        newPassword.setBounds(490, 325, 200, 40);
        mainPanel.add(newPassword);
        newPassword.setVisible(false);

        editAccountButton = new JButton("Change Password");
        editAccountButton.addActionListener(actionListener);
        editAccountButton.setBounds(285, 375, 200, 60);
        mainPanel.add(editAccountButton);
        editAccountButton.setVisible(false);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(actionListener);
        deleteAccountButton.setBounds(495, 375, 200, 60);
        mainPanel.add(deleteAccountButton);
        deleteAccountButton.setVisible(false);

        manageAccountGUI = new JComponent[]{manageAccountMainLabel, editAccountButton, deleteAccountButton,
                newPassword, passwordLabel, returnToDashButton};

        //Finalize frame
        accountFrame.add(mainPanel);
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

    /**
     * Sets currentlyVisible panel to false
     */
    public void resetVisible() {
        for (int i = 0; i < currentlyVisible.size(); i++) {
            currentlyVisible.get(i).setVisible(false);
        }
    }
}
