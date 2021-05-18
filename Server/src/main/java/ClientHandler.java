import Model.Cliente;
import Model.Ristorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private int port = 30000;
    private Socket socket;
    private ServerSocket ss = null;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler (Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
    }

    public void run(){
        try {
            ObjectInputStream iosCliente = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oosCliente = new ObjectOutputStream(socket.getOutputStream());

            Cliente c = (Cliente) iosCliente.readUnshared();
            ControllaID check = new ControllaID();
            Cliente ret = check.controllaIDQuery(c.getIdCliente());

            if(ret != null) {
                //Manda l'oggetto cliente creato
                System.out.println("è stato richiesto l'utente["+ret.getIdCliente()+"]: "+ret.getNome()+" "+ret.getCognome());
                oosCliente.writeUnshared(ret);

                //Mandagli i ristoranti attivi
                //Devo fare in modo di riempire anche gli arrayList dei menu dei determinati ristoranti
                //Devono esserci più oos per ogni "oggetto" da portare fuori dal server
                VisualizzaRistoranti visualizzaRistoranti = new VisualizzaRistoranti();
                ArrayList<Ristorante> nuovaLista = visualizzaRistoranti.VisualizzaRistorantiQuery();
                ObjectOutputStream oosListRistoranti = new ObjectOutputStream(socket.getOutputStream());
                oosListRistoranti.writeUnshared(nuovaLista);
                System.out.println("Inviato");

                //Devo inviare la lista del menu in base al ristorante che riceverò dal cliente
                ObjectInputStream iosRistorante = new ObjectInputStream(socket.getInputStream());
                Ristorante r = (Ristorante) iosRistorante.readUnshared();
                System.out.println(r.getNome());
                MostraMenu mm = new MostraMenu();
                ArrayList<String> listaMenu = mm.MostraMenuQuery(r);
                ObjectOutputStream oosListaMenu = new ObjectOutputStream(socket.getOutputStream());
                oosListaMenu.writeUnshared(listaMenu);

                //Bisogna creare un oggetto ordine da inviare al rider e ricevere una risposta

            }else{
                //Manda l'oggetto cliente null
                oosCliente.writeObject(null);
                System.out.println("Non ci sono clienti con questo ID");

            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

