import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(30000);
        System.out.println("Server Socket: " + serverSocket);
        try {
            while(true) {
                // bloccante finchè non avviene una connessione:
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection accepted: "+ clientSocket);
            try {
                new ConnessioneServer(clientSocket);
            } catch(IOException e) {
                clientSocket.close();
            }
        }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("EchoMultiServer: closing...");
        serverSocket.close();
    }
}

