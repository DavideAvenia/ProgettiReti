import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

                    InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    String str = bf.readLine();

                    ControllaID check = new ControllaID();
                    Boolean b = check.controllaIDQuery(str);

                    PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
                    pr.println(b.toString());
                } catch(IOException e) {
                    clientSocket.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            }catch (IOException e) {
                System.err.println("Accept failed");
                System.exit(1);
            }
        System.out.println("EchoMultiServer: closing...");
        serverSocket.close();
    }
}

