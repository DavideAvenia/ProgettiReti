package Handlers;

import Model.Cliente;
import Model.Ordine;
import Model.Rider;
import Model.Ristorante;
import PatternPC.ConfermaRider;
import PatternPC.OrdiniDaEseguire;
import PatternPC.VisualizzaRistorantiAttivi;
import Queries.ControllaIDCliente;
import Queries.MostraMenu;
import Queries.VisualizzaRistoranti;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/*
Questa classe si occupa di gestire la connessione e la
comunicazione con i client sulla porta memorizzata nella varibile 'port'.
E' stata scelta la porta 30000.
Nella variabile 'socket' viene memorizzata la socket creata,
che viene passata nella firma del costruttore.
Viene creato un thread per ogni client che si collega, in modo da avere
concorrenza tra i client connessi.
 */

public class ClientHandler extends Thread{
    private int port = 30000;
    private Socket socket;


    public ClientHandler (Socket s) {
        socket = s;
        start();
    }

    /*
    la funzione viene avviata dal costruttore.
    Come prima cosa viene aperto il canale stream di lettura 'iosCliente', per poter
    leggere il cliente mandato dal Client. Viene controllato se esiste
    il cliente nella base di dati.
    Se il cliente è stato trovato, viene inviato al client tramite il
    canale stream di scrittura 'oosCliente'.
    Dopo di che vengono inviati al client i ristoranti disponibili,
    richiamati con la funzione 'VisualizzaRistoranteQuery' e salvati nella
    variabile 'nuovaLista'.
    A questo punto il server riceve dal client il ristorante che ha selezionato
    il client, e lo memorizza nella variabile 'ristorante'. Dopo averlo richiesto
    alla base di dati e memorizzato nella variabile 'listaMenu', il menù relativo al
    ristorante scelto dal client viene inviato al client nel canale stream di
    scrittura 'oosListaMenu'. Poi viene ricevuto l'ordine dal client nel canale
    di lettura 'iosOrdine' e viene aggiunto alla lista di ordini da eseguire
    attraverso la funzione 'produceOrdine' della classe 'OrdiniDaEseguire'.
    Viene atteso che il ristorante da cui fare l'ordine sia online tramite la
    funzione 'controllaPresenzaRistorante' della classe 'VisualizzaRistorantiAttivi',
    appena questo succede viene consumato il primo Rider della lista e viene
    inviato al Client nel canale stream di scrittura 'oosIdRider'.
    Per simulare il tempo di consegna dell'ordine, viene invocata la funzione
    'sleep' per 10 secondi.
    Infine viene inserita la coppia Rider-Ordine nella lista degli ordini
    eseguiti grazie alla funzione 'produceOrdiniEseguiti'.
    Se il cliente non è stato trovato nella base di dati, viene inviato al client
    un oggetto null e viene chiusa la socket.
     */
    public void run(){
        try {
            System.out.println(">Sto leggendo un cliente da Client");
            ObjectInputStream iosCliente = new ObjectInputStream(socket.getInputStream());
            Cliente c = (Cliente) iosCliente.readUnshared();

            System.out.println(">Sto controllando l'id del cliente che mi è stato mandato da Client");
            ControllaIDCliente check = new ControllaIDCliente();
            Cliente ret = check.controllaIDQuery(c.getIdCliente());

            ObjectOutputStream oosCliente = new ObjectOutputStream(socket.getOutputStream());

            if(ret != null){
                System.out.println(">È stato richiesto l'utente["+ret.getIdCliente()+"]: "+ret.getNome()+" "+ret.getCognome());
                oosCliente.writeUnshared(ret);

                System.out.println(">Prendo la lista dei ristoranti dal database e la mando al Client");
                VisualizzaRistoranti visualizzaRistoranti = new VisualizzaRistoranti();
                ArrayList<Ristorante> nuovaLista = visualizzaRistoranti.VisualizzaRistorantiQuery();

                System.out.println(">Scrivo la lista dei ristoranti nel Client");
                ObjectOutputStream oosListRistoranti = new ObjectOutputStream(socket.getOutputStream());
                oosListRistoranti.writeUnshared(nuovaLista);

                System.out.println(">Aspetto la lettura di un ristorante scelto dal Cliente");
                ObjectInputStream iosRistorante = new ObjectInputStream(socket.getInputStream());
                Ristorante ristorante = (Ristorante) iosRistorante.readUnshared();

                System.out.println(">Preso il ristorante " + ristorante.getNome());
                MostraMenu mm = new MostraMenu();

                System.out.println(">Invio il menu del ristorante al Client");
                ArrayList<String> listaMenu = mm.MostraMenuQuery(ristorante);
                ObjectOutputStream oosListaMenu = new ObjectOutputStream(socket.getOutputStream());
                oosListaMenu.writeUnshared(listaMenu);

                System.out.println(">Aspetto la lettura di un ordine da parte del Client");
                ObjectInputStream iosOrdine = new ObjectInputStream(socket.getInputStream());
                Ordine ordine = (Ordine) iosOrdine.readUnshared();

                //Scenario produttore consumatore, il client è il produttore e il ristorante è il consumatore
                System.out.println(">Sto per produrre un ordine da eseguire");
                OrdiniDaEseguire ordiniDaEseguire = OrdiniDaEseguire.getIstanza();
                ordiniDaEseguire.produceOrdine(ordine);
                ordiniDaEseguire.visualizzaListaOrdiniDaEseguire();

                System.out.println(">In attesa del ristorante sia online");
                VisualizzaRistorantiAttivi ristorantiAttivi = VisualizzaRistorantiAttivi.getIstanza();
                ristorantiAttivi.controllaPresenzaRistorante(ristorante);

                System.out.println(">In attesa di un rider accetti il tuo ordine");
                ConfermaRider confermaRider = ConfermaRider.getIstanza();
                Rider rider = confermaRider.consumaRider();

                System.out.println(">Sto scrivendo un rider al Client per la lettura dell'ID");
                ObjectOutputStream oosIdRider = new ObjectOutputStream(socket.getOutputStream());
                oosIdRider.writeUnshared(rider);

                sleep(10000);

                System.out.println(">Produco l'ordine eseguito");
                ordiniDaEseguire.produceOrdineEseguiti(rider, ordine);

                System.out.println(">Chiusura socket e' cliente servito con successo");

                socket.close();
                this.interrupt();
            }else{
                oosCliente.writeObject(null);
                System.out.println(">Non ci sono clienti con questo ID");
                socket.close();
            }
        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

