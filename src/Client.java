import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Client class
 * 
 * Allows the user to access the FurnitureMarketplace
 *
 * @version 24/11/2022
 */
public class Client {
    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("localhost", 4444);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to Connect to Server", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(new LoginFrame(socket));
    }
}
