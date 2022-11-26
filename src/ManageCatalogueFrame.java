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
import java.util.ArrayList;
import java.util.Arrays;

public class ManageCatalogueFrame extends JComponent implements Runnable{

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame manageCatalogueFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    String storeSelected;
    String itemSelected = "";
    String[] storeItemNames;
    //Right Panel
    JLabel selectProduct;
    JRadioButton radioButton;
    //Left Panel
    JLabel currentItem;
    JButton returnToDashButton;

    public ManageCatalogueFrame (Socket socket, String storeSelected, String[] storeItemNames) {
        this.socket = socket;
        if (storeItemNames[0].equals("No items")) {
            this.storeItemNames = new String[0];
        } else {
            this.storeItemNames = storeItemNames;
        }
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == returnToDashButton) {
                SwingUtilities.invokeLater(new MainSellerFrame(socket));
                manageCatalogueFrame.dispose();
            } else {
                itemSelected = e.getActionCommand();
                currentItem.setText("Product Selected: " + itemSelected);
                currentItem.setFont(new Font(currentItem.getFont().getName(),
                        Font.BOLD, fontSizeToUse(currentItem)));
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
        manageCatalogueFrame = new JFrame("Catalogue Frame");
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(new GridLayout(storeItemNames.length + 1, 1, 20, 20));

        if (storeItemNames.length == 0) {
            selectProduct = new JLabel("No Items in Store");
        } else {
            selectProduct = new JLabel("Select Product");
        }
        rightPanel.add(selectProduct);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < storeItemNames.length; i++) {
            radioButton = new JRadioButton(storeItemNames[i]);
            buttonGroup.add(radioButton);
            rightPanel.add(radioButton);
            radioButton.addActionListener(actionListener);
        }

        //leftPanel
        leftPanel.setLayout(null);
        currentItem = new JLabel("Select a Product");
        currentItem.setBounds(200, 10, 400, 40);
        currentItem.setFont(new Font(currentItem.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentItem)));
        leftPanel.add(currentItem);

        returnToDashButton = new JButton("Return to Dashboard");
        returnToDashButton.addActionListener(actionListener);
        returnToDashButton.setBounds(300, 400, 200, 80);
        leftPanel.add(returnToDashButton);


        //Finalize frame
        manageCatalogueFrame.add(splitPane);
        manageCatalogueFrame.setSize(1000 , 800);
        manageCatalogueFrame.setLocationRelativeTo(null);
        manageCatalogueFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageCatalogueFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    manageCatalogueFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        manageCatalogueFrame.setVisible(true);
    }
    //FOUND THIS ONLINE (will change)
    public int fontSizeToUse(JLabel label) {
        Font currentFont = label.getFont();
        String textInLabel = label.getText();
        int stringWidth = label.getFontMetrics(currentFont).stringWidth(textInLabel);
        int componentWidth = label.getWidth();
        double widthRatio = (double) componentWidth / (double) stringWidth;
        int newFontSize = (int) (currentFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        return fontSizeToUse;
    }
}
