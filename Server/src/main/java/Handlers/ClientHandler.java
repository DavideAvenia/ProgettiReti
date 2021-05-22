package Handlers;

import Main.Main.OrdineHandler;
import Model.Cliente;
import Model.Ordine;
import Model.Ristorante;
import Queries.ControllaIDCliente;
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
            ControllaIDCliente check = new ControllaIDCliente();
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
                Ristorante ristorante = (Ristorante) iosRistorante.readUnshared();
                System.out.println(ristorante.getNome());
                MostraMenu mm = new MostraMenu();
                ArrayList<String> listaMenu = mm.MostraMenuQuery(ristorante);
                ObjectOutputStream oosListaMenu = new ObjectOutputStream(socket.getOutputStream());
                oosListaMenu.writeUnshared(listaMenu);

                //Bisogna creare un oggetto ordine da inviare al rider e ricevere una risposta
                //Lettura dell'ordine
                ObjectInputStream iosOrdine = new ObjectInputStream(socket.getInputStream());
                Ordine ordine = (Ordine) iosOrdine.readUnshared();

                //Scenario produttore consumatore, il client è il produttore e il ristorante è il consumatore
                OrdineHandler ordineHandler = new OrdineHandler();
                ordineHandler.produceOrdine(ordine);
                boolean conferma = false;

                //Controllare se il ristorante è online
                //Da mettere i ristoranti in una coda

                while(!conferma){
                    //quando arriva la conferma cambia in true
                }

                String idRider = new String("da inserire qui l'id del rider");
                ObjectOutputStream oosIdRider = new ObjectOutputStream(socket.getOutputStream());
                oosIdRider.writeUnshared(idRider);


                socket.close();
                this.interrupt();
            }else{
                //Manda l'oggetto cliente null
                oosCliente.writeObject(null);
                System.out.println("Non ci sono clienti con questo ID");
                //socket.close();
                //this.interrupt();
            }
        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

