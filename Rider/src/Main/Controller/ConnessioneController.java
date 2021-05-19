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
    private Rider rider;
    private static ConnessioneController instanza = null;

    public ConnessioneController() throws IOException {
        try{
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: "+ socket); //Qui dovrebbe stabilire la connessione
        } catch(IOException e) {}
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);

        } catch(IOException e1) {
            // in seguito ad ogni fallimento la socket deve essere chiusa, altrimenti
            // verrà chiusa dal metodo run() del thread
            try{
                socket.close();
            }catch(IOException e2){}
        }
    }

    public Socket getSocket(){
        return socket;
    }



    //Qui prendo l'id del cliente e vedo s'è giusto
    public boolean inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket

        Rider invCliente = new Rider(idCliente,null,null);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(invCliente);

        ois = new ObjectInputStream(socket.getInputStream());

        String ret;
        ret = (String) ois.readObject();
        if(ret.equals("ok")){
            return true;
        } else return false;
    }

    public Rider getCliente() {
        return rider;
    }
}


