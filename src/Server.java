import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server waiting for the connection");
        try {


            Socket socket = serverSocket.accept();
            System.out.println("Got a connection from " + socket.getInetAddress());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello! You are connected to the server.");

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}