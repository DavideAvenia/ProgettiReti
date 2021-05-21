package Controller;

import Model.Rider;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 32000;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress addr = InetAddress.getByName("localhost");
    private ObjectInputStream ois;
    private  ObjectOutputStream oos;
    private static ConnessioneController instanza = null;

    public ConnessioneController() throws IOException {
        try{
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: "+ socket); //Qui dovrebbe stabilire la connessione
        } catch(IOException e) {}
    }

    public Socket getSocket(){
        return socket;
    }



    //Qui prendo l'id del cliente e vedo s'è giusto
    public boolean inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket

        Rider invRider = new Rider(idCliente,null,null);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(invRider);

        ois = new ObjectInputStream(socket.getInputStream());

        String ret;
        ret = (String) ois.readObject();
        if(ret.equals("ok")){
            return true;
        } else return false;
    }

}


