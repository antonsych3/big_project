import java.io.IOException;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1111);
        System.out.println("Waiting...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Ok");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }

    }
}