package Handlers;

import Model.Ordine;
import Model.Ristorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class RistoHandler extends Thread{
    private int port = 31000;
    private Socket socket;
    private ServerSocket ss = null;

    private Map<Ristorante,Ordine> ordiniEseguiti = new HashMap<>();

    public RistoHandler (Socket s) throws IOException {
        socket = s;
        start();
    }

    public void run(){
        synchronized (ordiniEseguiti){
            //controlla da che ristorante viene e invia
        }
    }
}


