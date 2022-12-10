import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Interface that allows Buyers see Store and Buyer statistics.
 * Buyers can see a list of all stores from where their products were bought from.
 * Buyers can also see a list of all stores with the amount of products sold by each store.
 * <p>
 * These statistics can be sorted by most amount of products sold to least
 *
 * @author Nathan Schneider, Colin Wu, Ben Herrington, Andrei Deaconescu, Dakota Baldwin
 * @version 12/10/2022
 */
public class BuyerStatisticsFrame extends JComponent implements Runnable {
    Socket socket;
    String userEmail;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame buyerStatisticsFrame;
    JPanel mainPanel;
    JButton returnToDashButton;

    // Statistics
    JLabel mainStatLabel;
    JLabel buyerStatLabel;
    JLabel storeStatLabel;
    JLabel buyerStatSubLabel;
    JLabel storeStatSubLabel;
    JButton buyerShowStatButton;
    JButton buyerSortStatButton;
    JButton storeShowStatButton;
    JButton storeSortStatButton;
    JComponent[] buyerStatisticsGUI;

    /**
     * The constructor of Buyer Statistics Frame
     *
     * @param socket    The socket that connect this local machine with the server
     * @param userEmail Email of the current user
     */
    public BuyerStatisticsFrame(Socket socket, String userEmail) {
        this.socket = socket;
        this.userEmail = userEmail;
    }

    ActionListener actionListener = new ActionListener() {
        /**
         * @param e Is invoked whenever a button in the frame is selected
         *          returnToDashButton - user is redirected back to MainBuyerFrame.java
         *
         *          Buyer Statistics: Buyers can see a list of all stores from where their products were bought from.
         *          buyerShowStatButton - shows a panel of all the stores the current user has bought from.
         *          buyerSortStatButton - shows a sorted panel of all the stores the current user has bought
         *                                  from by quantity of items bought from most to least.
         *
         *          Store Statistics: Buyers can also see a list of all stores with the amount of products sold
         *          by each store.
         *          storeShowStatButton - shows a panel of all the stores with the amount of items sold per store.
         *          storeSortStatButton - shows a sorted a panel of all the stores with the amount of items sold
         *          per store by quantity of items sold from most to least.
         */
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                buyerStatisticsFrame.dispose();
            } else if (source == buyerShowStatButton) { // Buyer Show Statistics
                printWriter.println("BSF - Show Buyer Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String line = bufferedReader.readLine();

                    if (!line.equals("Error")) {
                        ArrayList<String> historyList = new ArrayList<>();
                        String[] storeLines = line.split("~");

                        // Fields
                        for (String storeLine : storeLines) {
                            String[] fields = storeLine.split(",");

                            String storeName = fields[0];
                            String quantity = fields[1];

                            String historyLine = String.format("%s items purchased from %s ",
                                    quantity, storeName);

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
                        scrollPane.setSize(600, 500);

                        JOptionPane.showMessageDialog(null, scrollPane, "Buyer Statistics",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You have not purchased any items yet!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (source == buyerSortStatButton) { // Buyer Sort Statistics
                printWriter.println("BSF - Sort Buyer Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String line = bufferedReader.readLine();

                    if (!line.equals("Error")) {
                        ArrayList<String> historyList = new ArrayList<>();
                        String[] storeLines = line.split("~");

                        // Fields
                        for (String storeLine : storeLines) {
                            String[] fields = storeLine.split(",");

                            String storeName = fields[0];
                            String quantity = fields[1];

                            String historyLine = String.format("%s items purchased from %s ",
                                    quantity, storeName);

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
                        scrollPane.setSize(600, 500);

                        JOptionPane.showMessageDialog(null, scrollPane, "Sorted Buyer Statistics",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You have not purchased any items yet!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (source == storeShowStatButton) { // Store Sort Statistics
                printWriter.println("BSF - Show Store Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String line = bufferedReader.readLine();

                    if (!line.equals("Error")) {
                        ArrayList<String> historyList = new ArrayList<>();
                        String[] storeLines = line.split("~");

                        // Fields
                        for (String storeLine : storeLines) {
                            String[] fields = storeLine.split(",");

                            String storeName = fields[0];
                            String quantity = fields[1];

                            String historyLine = String.format("Total sales from %s: %s items ",
                                    storeName, quantity);

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
                        scrollPane.setSize(600, 500);

                        JOptionPane.showMessageDialog(null, scrollPane, "Store Statistics",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You have not purchased any items yet!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (source == storeSortStatButton) { // Store Sort Statistics
                printWriter.println("BSF - Sort Store Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

                try {
                    String line = bufferedReader.readLine();

                    if (!line.equals("Error")) {
                        ArrayList<String> historyList = new ArrayList<>();
                        String[] storeLines = line.split("~");

                        // Fields
                        for (String storeLine : storeLines) {
                            String[] fields = storeLine.split(",");

                            String storeName = fields[0];
                            String quantity = fields[1];

                            String historyLine = String.format("Total sales from %s: %s items ",
                                    storeName, quantity);

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
                        scrollPane.setSize(600, 500);

                        JOptionPane.showMessageDialog(null, scrollPane, "Sorted Store Statistics",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You have not purchased any items yet!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error", "Error",
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
        buyerStatisticsFrame = new JFrame("Buyer Statistics Frame");
        mainPanel = new JPanel();

        mainPanel.setLayout(null);

        // Return to dashboard button
        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(385, 675, 200, 50);
        mainPanel.add(returnToDashButton);

        // Buyer Statistics
        buyerShowStatButton = new JButton("Show Statistics");
        buyerShowStatButton.addActionListener(actionListener);
        buyerShowStatButton.setBounds(185, 380, 200, 50);
        mainPanel.add(buyerShowStatButton);

        buyerSortStatButton = new JButton("Show Sorted Statistics");
        buyerSortStatButton.addActionListener(actionListener);
        buyerSortStatButton.setBounds(185, 440, 200, 50);
        mainPanel.add(buyerSortStatButton);

        // Store Statistics
        storeShowStatButton = new JButton("Show Statistics");
        storeShowStatButton.addActionListener(actionListener);
        storeShowStatButton.setBounds(580, 377, 200, 50);
        mainPanel.add(storeShowStatButton);

        storeSortStatButton = new JButton("Show Sorted Statistics");
        storeSortStatButton.addActionListener(actionListener);
        storeSortStatButton.setBounds(580, 437, 200, 50);
        mainPanel.add(storeSortStatButton);

        // Buyer Labels
        buyerStatLabel = new JLabel("Buyer Statistics");
        buyerStatLabel.setBounds(175, 235, 250, 90);
        buyerStatLabel.setFont(new Font(buyerStatLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(buyerStatLabel)));
        mainPanel.add(buyerStatLabel);

        buyerStatSubLabel = new JLabel("List of all stores you've bought from");
        buyerStatSubLabel.setBounds(125, 310, 350, 50);
        buyerStatSubLabel.setFont(new Font(buyerStatSubLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(buyerStatSubLabel)));
        mainPanel.add(buyerStatSubLabel);

        // Store Labels
        storeStatLabel = new JLabel("Store Statistics");
        storeStatLabel.setBounds(565, 232, 250, 90);
        storeStatLabel.setFont(new Font(storeStatLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(storeStatLabel)));
        mainPanel.add(storeStatLabel);

        storeStatSubLabel = new JLabel("List of all stores by products sold");
        storeStatSubLabel.setBounds(530, 307, 350, 50);
        storeStatSubLabel.setFont(new Font(storeStatSubLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(storeStatSubLabel)));
        mainPanel.add(storeStatSubLabel);

        // Main Label
        mainStatLabel = new JLabel("Statistics");
        mainStatLabel.setBounds(360, 10, 300, 100);
        mainStatLabel.setFont(new Font(mainStatLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(mainStatLabel)));
        mainPanel.add(mainStatLabel);

        buyerStatisticsGUI = new JComponent[]{mainStatLabel,
                buyerStatLabel, buyerStatSubLabel,
                storeStatLabel, storeStatSubLabel,
                buyerShowStatButton, buyerSortStatButton,
                storeShowStatButton, storeSortStatButton,
                returnToDashButton};

        //Finalize frame
        buyerStatisticsFrame.add(mainPanel);
        buyerStatisticsFrame.setSize(1000, 800);
        buyerStatisticsFrame.setLocationRelativeTo(null);
        buyerStatisticsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        buyerStatisticsFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printWriter.println("Reset Login Status");
                    printWriter.println(userEmail);
                    printWriter.flush();
                    String successOrFailure = bufferedReader.readLine();

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    buyerStatisticsFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buyerStatisticsFrame.setVisible(true);
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
}
