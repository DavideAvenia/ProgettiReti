import Model.Cliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ConnessioneServer extends Thread{
    private int port = 30000;
    private Socket socket;
    private ServerSocket ss = null;

    private BufferedReader in;
    private PrintWriter out;

    public ConnessioneServer(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
    }

    public void run(){
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());

            Cliente c = (Cliente) ios.readObject();
            ControllaID check = new ControllaID();
            Model.Cliente ret = check.controllaIDQuery(c.getIdCliente());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if(ret == null) {
                //Manda l'oggetto cliente null
                oos.writeObject(null);
                System.out.println("Non ci sono clienti con questo ID");
            }else{
                //Manda l'oggetto cliente creato
                System.out.println("è stato richiesto l'utente["+ret.getIdCliente()+"]: "+ret.getNome()+" "+ret.getCognome());
                oos.writeObject(ret);
                System.out.println("Inviato");
                //Mandagli i ristoranti attivi
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

