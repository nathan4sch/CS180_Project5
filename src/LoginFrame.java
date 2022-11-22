import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginFrame extends JComponent implements Runnable {
    Socket socket;
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

                        } else if (userRole.equals("Seller")) {

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
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();
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
        JFrame loginFrame = new JFrame("Account Frame");
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
        JLabel userRole = new JLabel("Buyer or Seller:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panel.add(userRole , gridBagConstraints);
        userRoleSelection = new JComboBox<>();
        userRoleSelection.addItem("Buyer");
        userRoleSelection.addItem("Seller");
        gridBagConstraints.gridx = 1;
        panel.add(userRoleSelection , gridBagConstraints);

        //SignIn Button
        JButton signInButton = new JButton("SignIn");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        signInButton.addActionListener(actionListener);
        panel.add(signInButton , gridBagConstraints);

        //CreateAccount Button
        JButton createAccountButton = new JButton("Create Account");
        gridBagConstraints.gridx = 4;
        createAccountButton.addActionListener(actionListener);
        panel.add(createAccountButton , gridBagConstraints);

        //Finalize frame
        loginFrame.add(panel);
        loginFrame.setSize(500 , 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }
}
