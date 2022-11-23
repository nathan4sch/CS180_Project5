import javax.swing.*;
import java.net.Socket;

public class MainBuyerFrame extends JComponent implements Runnable {
    Socket socket;

    public MainBuyerFrame (Socket socket) {
        this.socket = socket;
    }


    public void run() {

    }
}
