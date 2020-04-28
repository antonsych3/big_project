import java.io.IOException;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        try(ServerSocket server = new ServerSocket(1111)) {
            DBManager.connect();
            System.out.println("Waiting...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        }
    }
}
