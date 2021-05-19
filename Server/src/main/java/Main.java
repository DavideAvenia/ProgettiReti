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
        /*System.out.println(">Server: started");

        int[] ports = {30000, 31000};
        Selector selector = Selector.open();

        for (int port : ports) {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        while (true) {
            selector.select();

            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey selectedKey = selectedKeys.next();

                if (selectedKey.isAcceptable()) {
                    SocketChannel socketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
                    socketChannel.configureBlocking(false);
                    switch (socketChannel.socket().getPort()) {
                        case 30000:
                            System.out.println("Connection accepted: " + socketChannel.socket());
                            new ClientHandler(socketChannel.socket());
                            break;
                        case 31000:
                            // handle connection for the secon port (4321)
                            break;
                    }
                }
            }
        }*/

        ServerSocket serverSocketCliente = new ServerSocket(30000);
        //Devo fare la gestione del multi porta tramite più porte con uno switch case
        System.out.println(">Server Socket: " + serverSocketCliente);
        System.out.println(">Server: started");
        try {
            while(true) {
                // bloccante finchè non avviene una connessione:
                // Controllare da che porta viene da porter gestire le accettazioni
                Socket clientSocket = serverSocketCliente.accept();
                System.out.println("Connection accepted: "+ clientSocket);
                /*switch (clientSocket.getPort()){
                    case 30000:
                        try {
                            new ClientHandler(clientSocket);
                        } catch(IOException e) {
                            clientSocket.close();
                        }
                        break;
                    case 31000:
                        break;
                }*/

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

    }
}

