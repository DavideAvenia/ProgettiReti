import Handlers.ClientHandler;
import Handlers.ServerHandler;
import com.mysql.cj.xdevapi.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        /*{
        //ServerSocket serverSocketCliente = new ServerSocket(30000,10);
        //ServerSocket serverSocketRistorante = new ServerSocket(31000,10);

        //Devo fare la gestione del multi porta tramite più porte

        System.out.println(">Server Socket: " + serverSocketCliente);
        System.out.println(">Server: started");
        try {
            while(true) {
                // bloccante finchè non avviene una connessione:
                // Controllare da che porta viene da porter gestire le accettazioni
                Socket clientSocket = serverSocketCliente.accept();
                System.out.println("Connection accepted: "+ clientSocket);
                new ClientHandler(clientSocket);

            /*try {
                new ClientHandler(clientSocket);
            } catch(IOException e) {
                clientSocket.close();
            }
        }
        } catch (IOException e) {
            System.err.println(">Accept failed");
            System.exit(1);
        }
        System.out.println(">EchoMultiServer: closing...");
        serverSocketCliente.close();

        }*/

        System.out.println(">Aperta la porta 30000 \n");
        ServerHandler serverHandlerCliente = new ServerHandler(30000);

        System.out.println(">Aperta la porta 31000 \n");
        ServerHandler serverHandlerRistorante = new ServerHandler(31000);

        System.out.println(">Server: inizializzato con porta 30000 \n");
        serverHandlerCliente.startServer();

        System.out.println(">Server: inizializzato con porta 31000 \n");
        serverHandlerRistorante.startServer();

    }
}

