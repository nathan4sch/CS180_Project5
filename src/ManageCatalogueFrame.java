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

public class ManageCatalogueFrame extends JComponent implements Runnable{

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    JFrame manageCatalogueFrame;
    String storeSelected;

    public ManageCatalogueFrame (Socket socket, String storeSelected) {
        this.socket = socket;
        this.storeSelected = storeSelected;
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
        manageCatalogueFrame = new JFrame("Catalogue Frame");


        //Finalize frame
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
}
