package Controller;

import Model.Cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream ois;
    private InetAddress addr = InetAddress.getByName("localhost");

    public ConnessioneController() throws IOException {
    try{
        //Qui dovrebbe stabilire la connessione
        socket = new Socket(this.addr, 30000);
        System.out.println("Client Socket: "+ socket);
    } catch(IOException e) {}
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

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

    //Qui prendo l'id del cliente e vedo s'è giusto
    public Cliente inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket
        out.println(idCliente);
        ois = new ObjectInputStream(socket.getInputStream());
        Cliente c = (Cliente) ois.readObject();
        return c;
    }
}
