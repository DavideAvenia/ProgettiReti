package Controller;

import Model.Cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController{
    private int port = 31000;
    private Socket socket;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private BufferedReader in;
    private PrintWriter out;

    private InetAddress addr = InetAddress.getByName("localhost");
    private Cliente cliente;

    private static ConnessioneController connessioneController = null;

    private ConnessioneController() throws IOException {
        try{
            //Qui dovrebbe stabilire la connessione
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: " + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);
        } catch(IOException e) {
            ois.close();
            oos.close();
            socket.close();
        }
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(connessioneController == null){
            connessioneController = new ConnessioneController();
        }
        return connessioneController;
    }

    public boolean inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket
        Cliente invCliente = new Cliente(idCliente,null,null);

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(invCliente);

        ois = new ObjectInputStream(socket.getInputStream());
        Cliente ret = (Cliente) ois.readUnshared();
        cliente = ret;
        if(ret == null){
            return false;
        }

        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Socket getSocket() {
        return socket;
    }
}
