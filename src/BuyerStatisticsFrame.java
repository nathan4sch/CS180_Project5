import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Interface that allows Buyers see Store statistics.
 * They are able to see all stores with the amount of products sold by each store.
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
    JLabel buyerStatisticsLabel;
    JButton showStatButton;
    JButton sortStatButton;
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
            } else if (source == showStatButton) { // Show Statistics
                printWriter.println("Show Buyer Statistics");
                printWriter.println(userEmail);
                printWriter.flush();

            } else if (source == sortStatButton) { // Sort Statistics
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

        // Show Statistics Button
        showStatButton = new JButton("Show Statistics");
        showStatButton.addActionListener(actionListener);
        showStatButton.setBounds(285, 375, 200, 60);
        mainPanel.add(showStatButton);
        showStatButton.setVisible(false);

        // Sort Statistics Button
        sortStatButton = new JButton("Show Sorted Statistics");
        sortStatButton.addActionListener(actionListener);
        sortStatButton.setBounds(495, 375, 200, 60);
        mainPanel.add(sortStatButton);
        sortStatButton.setVisible(false);

        // Statistics
        buyerStatisticsLabel = new JLabel("Statistics");
        buyerStatisticsLabel.setBounds(300, 10, 400, 100);
        buyerStatisticsLabel.setFont(new Font(buyerStatisticsLabel.getFont().getName(),
                Font.PLAIN, fontSizeToUse(buyerStatisticsLabel)));
        mainPanel.add(buyerStatisticsLabel);
        buyerStatisticsLabel.setVisible(false);

        buyerStatisticsGUI = new JComponent[]{buyerStatisticsLabel, showStatButton, sortStatButton, returnToDashButton};

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
