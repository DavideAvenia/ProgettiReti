package Handlers;

import Model.Cliente;
import Model.Ristorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class RistoHandler extends Thread{
    private int port = 31000;
    private Socket socket;
    private ServerSocket ss = null;

    private BufferedReader in;
    private PrintWriter out;

    public RistoHandler (Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
    }

    public void run(){
        System.out.println("kitemmuort");
    }
}


