import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    public static ObjectOutputStream outStream;
    public static ObjectInputStream inStream;
    public static MainFrame mainFrame;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        socket = new Socket("127.0.0.1", 1111 );
        outStream = new ObjectOutputStream(socket.getOutputStream());
        inStream = new ObjectInputStream(socket.getInputStream());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public static void outStreamWithReset(PackageData pd) throws IOException {
        Client.outStream.writeObject(pd);
        Client.outStream.reset();
    }

    public static PackageData queryWithResponse(PackageData pd, Operation operation) throws IOException, ClassNotFoundException {
        pd.setOperation(operation);
        outStreamWithReset(pd);
        return (PackageData)Client.inStream.readObject();
    }
}
