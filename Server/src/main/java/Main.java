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

