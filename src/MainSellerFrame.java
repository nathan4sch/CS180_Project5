import javax.swing.*;
import java.net.Socket;

public class MainSellerFrame extends JComponent implements Runnable {
    Socket socket;

    public MainSellerFrame (Socket socket) {
        this.socket = socket;
    }


    public void run() {

    }
}
