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

    public boolean inviaIdRider(String idRider) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al ristorante

        Rider invRider = new Rider(idRider,null,null);
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


