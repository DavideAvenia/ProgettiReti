package Controller;

import Model.Rider;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 32000;
    private Socket socket;
    private InetAddress addr = InetAddress.getByName("localhost");
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private static ConnessioneController instanza = null;

    private Rider riderAttuale;

    private ConnessioneController() throws IOException {
        try{
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: "+ socket); //Qui dovrebbe stabilire la connessione
        } catch(IOException e) {
            socket.close();
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return socket;
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public boolean inviaIdRider(String idRider) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al ristorante
        Rider invRider = new Rider(idRider, null, null);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(invRider);

        ois = new ObjectInputStream(socket.getInputStream());
        Rider ret;
        ret = (Rider) ois.readObject();

        if (ret != null) {
            riderAttuale = ret;
            return true;
        } else
            return false;
    }
}


