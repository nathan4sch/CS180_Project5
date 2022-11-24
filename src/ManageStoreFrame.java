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
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class ManageStoreFrame extends JComponent implements Runnable {

    Socket socket;
    String currentObjective;
    String[] userStores;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame manageStoreFrame;
    JSplitPane splitPane;
    JPanel leftPanel;
    JPanel rightPanel;
    JLabel selectStore;
    JLabel currentStore;

    public ManageStoreFrame(Socket socket, String currentObjective, String[] userStores) {
        this.currentObjective = currentObjective;
        this.socket = socket;
        this.userStores = userStores;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
        }
    };

    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        manageStoreFrame = new JFrame("Manage Store Frame");
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        //configure splitPane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        //rightPanel
        rightPanel.setLayout(new GridLayout(userStores.length + 1, 1, 20, 20));

        selectStore = new JLabel("Your Stores");
        rightPanel.add(selectStore);

        for (int i = 0; i < userStores.length; i++) {
            JButton button = new JButton(userStores[i]);
            button.addActionListener(actionListener);
            rightPanel.add(button);
        }

        //leftPanel
        currentStore = new JLabel("Select a Store to Modify");
        currentStore.setBounds(200, 10, 400, 60);
        currentStore.setFont(new Font(currentStore.getFont().getName(),
                Font.BOLD, fontSizeToUse(currentStore)));
        leftPanel.add(currentStore);


        //Finalize frame
        manageStoreFrame.add(splitPane);
        manageStoreFrame.setSize(1000, 800);
        manageStoreFrame.setLocationRelativeTo(null);
        manageStoreFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        manageStoreFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    manageStoreFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        manageStoreFrame.setVisible(true);
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


//work on the wonky girdlayout for the users stores. Probably do gridbaglayout
