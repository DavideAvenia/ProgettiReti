package Controller;

import Model.Cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController implements java.io.Serializable {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;

    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream ois;

    private InetAddress addr = InetAddress.getByName("localhost");
    private Cliente cliente;

    public ConnessioneController() throws IOException {
        socket = new Socket(this.addr, 30000);
        System.out.println("Client Socket: "+ socket);
        try{
            //Qui dovrebbe stabilire la connessione
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

            osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);
        } catch(IOException e) {
            socket.close();
        }
    }

    //Qui prendo l'id del cliente e vedo s'è giusto
    public boolean inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket

        Cliente invCliente = new Cliente(idCliente,null,null);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(invCliente);

        ois = new ObjectInputStream(socket.getInputStream());

        Cliente ret = (Cliente) ois.readObject();
        cliente = ret;
        if(ret == null){
            return false;
        }

        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
