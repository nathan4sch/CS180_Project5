import javax.swing.*;
import java.net.Socket;

public class CartFrame extends JComponent implements Runnable {
    Socket socket;
    public CartFrame(Socket socket){this.socket = socket;}

    @Override
    public void run() {

    }
}
