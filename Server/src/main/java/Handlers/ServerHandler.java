package Handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread
{
    private ServerSocket serverSocket;
    private int port;

    public ServerHandler(int port) {
        this.port = port;
    }

    public void startServer(){
        try {
            serverSocket = new ServerSocket(port);
            this.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true) {
            try {
                System.out.println( ">In attesa di una connesione");
                Socket socket = serverSocket.accept();
                switch (port){
                    case 30000:
                        System.out.println(">Connessione accettata da "+ socket);
                        new ClientHandler(socket);
                        socket.close();
                    break;
                    case 31000:
                        System.out.println(">Connessione accettata da "+ socket);
                        new RistoHandler(socket);
                        socket.close();
                    break;
                    default:
                        System.out.println(">Non ci sono porte utili");
                        socket.close();
                    break;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}