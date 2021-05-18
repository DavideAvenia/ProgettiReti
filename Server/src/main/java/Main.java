import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] ports = {30000, 31000};
        Selector selector = Selector.open();

        for (int port: ports) {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);

            server.socket().bind(new InetSocketAddress(port));
            // we are only interested when accept evens occur on this socket
            server.register(selector, SelectionKey.OP_ACCEPT);
        }

        while(selector.isOpen()){

        }
        /*System.out.println(">Server Socket: " + serverSocketCliente);
        System.out.println(">Server: started");
        try {
            while(true) {
                // bloccante finchè non avviene una connessione:
                // Controllare da che porta viene da porter gestire le accettazioni
            Socket clientSocket = serverSocketCliente.accept();
            clientSocket.getPort()
            System.out.println("Connection accepted: "+ clientSocket);
            try {
                new ClientHandler(clientSocket);
            } catch(IOException e) {
                clientSocket.close();
            }
        }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("EchoMultiServer: closing...");
        serverSocketCliente.close();
        serverSocketRistorante.close();*/
    }
}

