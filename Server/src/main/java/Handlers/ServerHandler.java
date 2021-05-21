package Handlers;

import Model.Ordine;
import Model.Ristorante;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler extends Thread
{
    private int i = 0;
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
                i++;
                System.out.println( ">In attesa di una connesione: " + i);
                Socket socket = serverSocket.accept();
                switch (port){
                    case 30000:
                        System.out.println(">Connessione accettata da "+ socket);
                        new ClientHandler(socket);
                    break;
                    case 31000:
                        System.out.println(">Connessione accettata da "+ socket);
                        new RistoHandler(socket);
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