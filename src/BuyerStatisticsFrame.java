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
 * @version 27/11/2022
 */
public class BuyerStatisticsFrame extends JComponent implements Runnable {
    Socket socket;
    String userEmail;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame buyerStatisticsFrame;
    JPanel mainPanel;
    JButton returnToDashButton;
    ArrayList<JComponent> currentlyVisible = new ArrayList<>();

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
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainBuyerFrame(socket, userEmail));
                buyerStatisticsFrame.dispose();
            } else if (source == buyerShowStatButton) { // Show Statistics
                printWriter.println("Show Buyer Statistics");
                printWriter.println(userEmail);
                printWriter.flush();


            } else if (source == buyerSortStatButton) { // Sort Statistics
                printWriter.println("Sort Buyer Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

                //TODO
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

        resetVisible();

        for (JComponent jComponent : buyerStatisticsGUI) {
            jComponent.setVisible(true);
            currentlyVisible.add(jComponent);
        }
    }

//    public static void main(String[] args) { // For testing
//        try {
//            Socket socket1 = new Socket("localhost", 4444);
//            BuyerStatisticsFrame buy = new BuyerStatisticsFrame(socket1, "aa");
//            buy.run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
