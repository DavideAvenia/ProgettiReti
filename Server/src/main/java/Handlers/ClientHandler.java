package Handlers;

import Model.Cliente;
import Model.Ordine;
import Model.Ristorante;
import Queries.ControllaID;
import Queries.MostraMenu;
import Queries.VisualizzaRistoranti;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private int port = 30000;
    private Socket socket;

    public ClientHandler (Socket s) {
        socket = s;
        start();
    }

    public void run(){
        try {
            ObjectInputStream iosCliente = new ObjectInputStream(socket.getInputStream());
            Cliente c = (Cliente) iosCliente.readUnshared();
            ControllaID check = new ControllaID();
            Cliente ret = check.controllaIDQuery(c.getIdCliente());

            ObjectOutputStream oosCliente = new ObjectOutputStream(socket.getOutputStream());

            if(ret != null) {
                //Manda l'oggetto cliente creato
                System.out.println("è stato richiesto l'utente["+ret.getIdCliente()+"]: "+ret.getNome()+" "+ret.getCognome());

                oosCliente.writeUnshared(ret);

                //Mandagli i ristoranti attivi
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
                //Lettura dell'ordine
                ObjectInputStream iosOrdine = new ObjectInputStream(socket.getInputStream());
                Ordine o = (Ordine) iosOrdine.readUnshared();

                //Scenario produttore consumatore, il client è il produttore e il ristorante è il consumatore

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

