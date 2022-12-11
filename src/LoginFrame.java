import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * Interface that users use to log in to their accounts
 *
 * @author Nathan Schneider, Colin Wu, Ben Herrington, Andrei Deaconescu, Dakota Baldwin
 * @version 12/10/2022
 */
public class LoginFrame extends JComponent implements Runnable {
    Socket socket;
    JFrame loginFrame;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JButton signInButton;
    JButton createAccountButton;
    JTextField emailText;
    JTextField passwordText;
    JComboBox<String> userRoleSelection;

    String userEmail;
    String userPassword;
    String userRole;

    /**
     * The constructor of LoginFrame
     *
     * @param socket The socket that connect this local machine with the server
     */
    public LoginFrame(Socket socket) {
        this.socket = socket;
    }

    ActionListener actionListener = new ActionListener() {
        /**
         * @param e Invoked when any of the button in the frame is selected.
         *          signInButton - signs the user if the credentials match the information in FMCredentials.csv
         *          createAccountButton - creates a new account for user
         *                              Requires a valid email input which requires the email to have a "@" and
         *                              requires the password to be at least 6 characters long.
         *                              If the sign in credentials are correct, users will be redirected to either
         *                              MainBuyerFrame or MainSellerFrame depending on the account type.
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signInButton) {
                userEmail = emailText.getText();
                userPassword = passwordText.getText();
                try {
                    printWriter.println("SignIn");
                    printWriter.flush();
                    printWriter.println(userEmail);
                    printWriter.flush();
                    printWriter.println(userPassword);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();
                    if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Sign In Successful", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        String receivedUserRole = bufferedReader.readLine();
                        if (receivedUserRole.equals("Buyer")) {
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                            loginFrame.dispose();
                        } else if (receivedUserRole.equals("Seller")) {
                            SwingUtilities.invokeLater(new MainSellerFrame(socket, userEmail));
                            loginFrame.dispose();
                        }
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "No Account Found or Account " +
                                "Already Logged In", "Sign In Failure", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource() == createAccountButton) {
                userEmail = emailText.getText();
                userPassword = passwordText.getText();
                userRole = Objects.requireNonNull(userRoleSelection.getSelectedItem()).toString();
                try {
                    printWriter.println("Create Account");
                    printWriter.println(userEmail);
                    printWriter.println(userPassword);
                    printWriter.println(userRole);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();
                    switch (successOrFailure) {
                        case "Failure" -> JOptionPane.showMessageDialog(null, "This email " +
                                        "already owns an account", "Create Account Failure",
                                JOptionPane.ERROR_MESSAGE);

                        case "Input Short" -> JOptionPane.showMessageDialog(null, "Email and " +
                                        "Password must have " + "at Least 6 Characters", "Create Account Failure",
                                JOptionPane.ERROR_MESSAGE);

                        case "Invalid Characters" -> JOptionPane.showMessageDialog(null,
                                "Email and Password can not have a comma", "Create Account Failure",
                                JOptionPane.ERROR_MESSAGE);

                        case "Invalid Email" -> JOptionPane.showMessageDialog(null, "A Valid " +
                                        "Email has an @ Sign", "Create Account Failure",
                                JOptionPane.ERROR_MESSAGE);

                        case "Success" -> {
                            JOptionPane.showMessageDialog(null, "Account Created", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            if (userRole.equals("Buyer")) {
                                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                                loginFrame.dispose();
                            } else if (userRole.equals("Seller")) {
                                SwingUtilities.invokeLater(new MainSellerFrame(socket, userEmail));
                                loginFrame.dispose();
                            }
                        }
                    }
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
        loginFrame = new JFrame("Account Frame");
        JPanel panel = new JPanel(null);
        panel.setSize(new Dimension(500, 300));

        //Email
        JLabel email = new JLabel("Email:");
        email.setFont(new Font(email.getFont().getName(), Font.BOLD, 20));
        email.setHorizontalAlignment(SwingConstants.CENTER);
        email.setBounds(10, 40, 150, 50);
        panel.add(email);
        emailText = new JTextField(25);
        emailText.setFont(new Font(emailText.getFont().getName(), Font.PLAIN, 20));
        emailText.setBounds(185, 40, 250, 50);
        panel.add(emailText);

        //Password
        JLabel password = new JLabel("Password:");
        password.setFont(new Font(email.getFont().getName(), Font.BOLD, 20));
        password.setBounds(55, 120, 150, 50);
        panel.add(password);
        passwordText = new JTextField(25);
        passwordText.setFont(new Font(passwordText.getFont().getName(), Font.PLAIN, 20));
        passwordText.setBounds(185, 120, 250, 50);
        panel.add(passwordText);

        //Buyer or Seller
        JLabel userRoleLabel = new JLabel("Role:");
        userRoleLabel.setFont(new Font(userRoleLabel.getFont().getName(), Font.BOLD, 15));
        userRoleLabel.setHorizontalAlignment(JLabel.CENTER);
        userRoleLabel.setBounds(250, 203, 50, 30);
        panel.add(userRoleLabel);
        userRoleSelection = new JComboBox<>();
        userRoleSelection.setBounds(310, 205, 100, 30);
        userRoleSelection.setFont(new Font(userRoleSelection.getFont().getName(), Font.BOLD, 13));
        userRoleSelection.addItem("Buyer");
        userRoleSelection.addItem("Seller");
        panel.add(userRoleSelection);

        //SignIn Button
        signInButton = new JButton("SignIn");
        signInButton.addActionListener(actionListener);
        signInButton.setFont(new Font(signInButton.getFont().getName(), Font.BOLD, 17));
        signInButton.setBounds(55, 275, 180, 50);
        panel.add(signInButton);

        //CreateAccount Button
        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(actionListener);
        createAccountButton.setFont(new Font(createAccountButton.getFont().getName(), Font.BOLD, 17));
        createAccountButton.setBounds(255, 275, 180, 50);
        panel.add(createAccountButton);

        //Finalize frame
        loginFrame.add(panel);
        loginFrame.setSize(500, 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    loginFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        loginFrame.setVisible(true);
    }
}
