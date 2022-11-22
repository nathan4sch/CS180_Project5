import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class Client {
    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("localhost", 1112);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to Connect to Server", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(new LoginFrame(socket));
    }
}
//
//public class Client extends Thread {
//    protected Socket socket;
//
//    public Client(Socket clientSocket) {
//        this.socket = clientSocket;
//    }
//
//    public static int[] ports = {1111, 2222, 3333, 4444};
//
//    public void run() {
//        Scanner scanner = new Scanner(System.in);
//        try {
//
////            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter writer = new PrintWriter(socket.getOutputStream());
//
//            System.out.println("Message to send: ");
//            String searchText = scanner.nextLine();
//
//            writer.write(searchText);
//            writer.println();
//            writer.flush();
//            System.out.printf("Sent to server:\n%s\n", searchText);
//
//            writer.close();
////            reader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            System.out.println("Client 1");
//            Socket clientSocket = new Socket("localhost", ports[0]);
//            Client userClient = new Client(clientSocket);
//
//            userClient.start();
//            userClient.join();
//
//            System.out.println("Client 2");
//            Socket clientSocket2 = new Socket("localhost", ports[0]);
//            Client userClient2 = new Client(clientSocket2);
//
//            userClient2.start();
//            userClient2.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}