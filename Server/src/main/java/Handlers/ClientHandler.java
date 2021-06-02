package Handlers;

import Handlers.ComunicazioneHandler.OrdineHandler;
import Handlers.ComunicazioneHandler.VisualizzaRistorantiAttiviHandler;
import Handlers.ComunicazioneHandler.ConfermeRiderHandler;
import Model.Cliente;
import Model.Ordine;
import Model.Rider;
import Model.Ristorante;
import PatternPC.OrdiniDaEseguire;
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
                OrdiniDaEseguire ordiniDaEseguire = OrdiniDaEseguire.getIstanza();
                ordiniDaEseguire.produceOrdine(ordine);
                ordiniDaEseguire.visualizzaLista();

                //Controllare se il ristorante è online
                //Da mettere i ristoranti in una coda
                //La wait() sta nel metodo per controllare se il ristorante è attivo

                /*System.out.println(">In attesa del ristorante sia online");
                VisualizzaRistorantiAttiviHandler ristorantiAttiviHandler = new VisualizzaRistorantiAttiviHandler();
                while(ristorantiAttiviHandler.controllaPresenzaRistorante(ristorante)){
                    System.out.println(">In attesa di un rider accetti il tuo ordine");
                    ConfermeRiderHandler confermeRiderHandler = new ConfermeRiderHandler();
                    Rider rider = confermeRiderHandler.consumaRider();

                    //C'è qualcosa che non mi piace e forse dovrei mettere qualcosa qui in mezzo

                    //Consuma il primo rider alla testa
                    ObjectOutputStream oosIdRider = new ObjectOutputStream(socket.getOutputStream());
                    oosIdRider.writeUnshared(rider);

                    System.out.println("Chiusura socket e' cliente servito con successo");
                    //this.Thread.sleep(10000);
                    //Si deve creare una notifica al ristorante

                    socket.close();
                    this.interrupt();
                }*/

            }else{
                //Manda l'oggetto cliente null
                oosCliente.writeObject(null);
                System.out.println("Non ci sono clienti con questo ID");
                socket.close();
            }
        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

