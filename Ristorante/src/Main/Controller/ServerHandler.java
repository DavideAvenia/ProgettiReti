package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
Questa classe si occupa di gestire le richieste di connessione
che arrivano dai rider.

 */
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

    /*
    Quando arriva una connessione viene accettata e passata nel costruttore
    del nuovo oggetto 'RiderHandler'. Finchè non ci sono nuove connessioni, la
    funzione accept è bloccante.
     */
    @Override
    public void run(){
        while(true) {
            try {
                i++;
                System.out.println( ">In attesa di una connesione " + i);
                Socket socket = serverSocket.accept();
                new RiderHandler(socket);
            } catch (IOException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}