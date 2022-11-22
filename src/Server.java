import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Server implements Runnable {
    Socket socket;
    public Server(Socket socket) {
        this.socket = socket;
    }
    Object currentUser = null;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1112);
            /*
             * Use a while loop to accept all socket receives with multithreading
             * A thread would be start whenever the a socket is accepted
             * The socket would be pass to a thread to interacting with the client.
             */
            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server(socket);
                new Thread(server).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String nextFrame = bufferedReader.readLine();
                switch (nextFrame) {
                    case "SignIn" -> {
                        String emailInput = bufferedReader.readLine();
                        String passwordInput = bufferedReader.readLine();

                        if (!checkExistingCredentials(emailInput, passwordInput, "signIn").equals("No Duplicate Account Found")) {
                            currentUser = signInAccount(emailInput, passwordInput);
                            printWriter.println("Failure");
                            printWriter.flush();
                            if (currentUser instanceof Buyer) {

                            } else if (currentUser instanceof Seller) {

                            }

                        } else {
                            printWriter.println("Failure");
                        }
                    }
                    case "Create Account" -> {

                    }
                }
            }
        } catch (IOException e) {

        }
    }

    //Purpose: checks if the user has an account with the email and password and returns that Buyer or Seller object
    public static Object signInAccount(String signInEmail, String signInPassword) {
        while (true) {
            String accountSearch = checkExistingCredentials(signInEmail, signInPassword, "signIn");
            if (!accountSearch.equals("No Duplicate Account Found")) {
                accountSearch = accountSearch.substring(1, accountSearch.length() - 1);
                String[] accountDetails = accountSearch.split(", ");
                if (accountDetails[3].equals("buyer")) {
                    return new Buyer(accountDetails[1], accountDetails[0], accountDetails[2],
                            buyerDataArray(accountDetails[0], "hist"),
                            buyerDataArray(accountDetails[0], "cart"));
                } else if (accountDetails[3].equals("seller")) {
                    return new Seller(accountDetails[1], accountDetails[0], accountDetails[2]);
                }
            }
        }
    }

    public static String checkExistingCredentials(String email, String password, String purpose) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("FMCredentials.csv"));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                String[] currentLine = line.split(",");

                if (purpose.equals("signIn")) {
                    if (currentLine[0].equals(email) && currentLine[2].equals(password)) {
                        return Arrays.toString(currentLine);
                    }
                } else if (purpose.equals("newAccount")) {
                    if (currentLine[0].equals(email)) {
                        return "DuplicateEmail";
                    }
                }
            }
            bfr.close();
            return "No Duplicate Account Found";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * A function used to Get user data from FMCredentials to be used in the Buyer constructor
     *
     * @param userEmail  User email to make sure you get the right user data
     * @param cartOrHist If Cart, Cart list is returned. If Hist, Purchase History list is returned
     * @return
     */
    public static ArrayList<String> buyerDataArray(String userEmail, String cartOrHist) {
        try {
            ArrayList<String> buyerData = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader("FMCredentials.csv"));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                String[] currentLine = line.split(",");
                if (currentLine[0].equals(userEmail)) { // first checks for user creds to give correct history
                    if (cartOrHist.equals("hist")) { // if hist, get Purchase history
                        if (currentLine[4].equals("")) {
                            return null;
                        } else {
                            String[] initialData = currentLine[4].split("~"); // Split each purchase
                            Collections.addAll(buyerData, initialData);
                        }
                    } else { // if cart, get cart
                        if (currentLine[5].equals("")) {
                            return null;
                        } else {
                            String[] initialData = currentLine[5].split("~"); // Split each purchase
                            Collections.addAll(buyerData, initialData);
                        }
                    }
                }
            }
            return buyerData;
        } catch (Exception e) {
            return null;
        }
    }

}





//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//
//public class Server {
//    public static final int[] ports = {1111 , 2222, 3333, 4444};
//
//    public static void main(String[] args) {
//        Socket socket = null;
//        try {
//            ServerSocket serverSocket = new ServerSocket(ports[0]);
//
//            for (int i = 1; i <= 2; i++) {
//
//                try {
//                    System.out.printf("Waiting for client %d to connect...\n", i);
//                    socket = serverSocket.accept();
//                    System.out.printf("Client %d connected!\n" , i);
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
//
//                    String keyword = reader.readLine();
//                    System.out.printf("Received from client %d:\n%s\n", i, keyword);
//                    reader.close();
//                    writer.close();
//                } catch (IOException e) {
//                    System.out.println("I/O error: " + e);
//                }
//                // new thread for a client
//                new Client(socket).start();
//            }
//
//            serverSocket.close();
//
////            try {
////                ServerSocket serverSocket1 = new ServerSocket(ports[1]);
////                System.out.println("Waiting for the client to connect...");
////                Socket socket1 = serverSocket1.accept();
////                System.out.println("Client connected!");
////
////                BufferedReader reader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
////                PrintWriter writer1 = new PrintWriter(socket1.getOutputStream());
////
////                String keyword = reader1.readLine();
////                System.out.printf("Received from client 1:\n%s\n", keyword);
////
////                reader1.close();
////                writer1.close();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}