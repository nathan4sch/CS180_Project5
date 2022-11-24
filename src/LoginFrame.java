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


    public LoginFrame (Socket socket) {
        this.socket = socket;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == signInButton) {
                userEmail = emailText.getText();
                userPassword = passwordText.getText();
                try {
                    printWriter.println("SignIn");
                    printWriter.println(userEmail);
                    printWriter.println(userPassword);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();
                    if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Sign In Successful", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        String userRole = bufferedReader.readLine();
                        if (userRole.equals("Buyer")){
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket));
                            loginFrame.dispose();
                        } else if (userRole.equals("Seller")) {
                            SwingUtilities.invokeLater(new MainSellerFrame(socket));
                            loginFrame.dispose();
                        }
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "No Account Found",
                                "Sign In Failure", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource() == createAccountButton) {
                userEmail = emailText.getText();
                userPassword = passwordText.getText();
                userRole = userRoleSelection.getSelectedItem().toString();
                try {
                    printWriter.println("Create Account");
                    printWriter.println(userEmail);
                    printWriter.println(userPassword);
                    printWriter.println(userRole);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();
                    if (successOrFailure.equals("Success")) {
                        JOptionPane.showMessageDialog(null, "Account Created", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        if (userRole.equals("Buyer")){
                            SwingUtilities.invokeLater(new MainBuyerFrame(socket));
                            loginFrame.dispose();
                        } else if (userRole.equals("Seller")) {
                            SwingUtilities.invokeLater(new MainSellerFrame(socket));
                            loginFrame.dispose();
                        }
                    } else if (successOrFailure.equals("Failure")) {
                        JOptionPane.showMessageDialog(null, "This email already owns an account",
                                "Create Account Failure", JOptionPane.ERROR_MESSAGE);
                        return;
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
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;

        //Email
        JLabel email = new JLabel("Email:");
        panel.add(email , gridBagConstraints);
        emailText = new JTextField(20);
        gridBagConstraints.gridx = 1;
        panel.add(emailText , gridBagConstraints);

        //Password
        JLabel password = new JLabel("Password:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel.add(password , gridBagConstraints);
        passwordText = new JTextField(20);
        gridBagConstraints.gridx = 1;
        panel.add(passwordText , gridBagConstraints);

        //Buyer or Seller
        JLabel userRole = new JLabel("Role:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panel.add(userRole , gridBagConstraints);
        userRoleSelection = new JComboBox<>();
        userRoleSelection.addItem("Buyer");
        userRoleSelection.addItem("Seller");
        gridBagConstraints.gridx = 1;
        panel.add(userRoleSelection , gridBagConstraints);

        //SignIn Button
        signInButton = new JButton("SignIn");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        signInButton.addActionListener(actionListener);
        panel.add(signInButton , gridBagConstraints);

        //CreateAccount Button
        createAccountButton = new JButton("Create Account");
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        createAccountButton.addActionListener(actionListener);
        panel.add(createAccountButton , gridBagConstraints);

        //Finalize frame
        loginFrame.add(panel);
        loginFrame.setSize(500 , 400);
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
