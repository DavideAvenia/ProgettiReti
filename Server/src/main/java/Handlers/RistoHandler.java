package Handlers;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import PatternPC.ConfermaRider;
import PatternPC.OrdiniDaEseguire;
import PatternPC.VisualizzaRistorantiAttivi;
import Queries.ControllaIDRistorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

/*
Questa classe si occupa di gestire la comunicazione e la
connessione con i ristoranti sulla porta memorizzata nella
variabile 'port'. E' stata scelta la porta 31000.
Nella variabile 'socket' viene memorizzata la socket passata nella
firma del costruttore, mentre nella variabile 'ristoranteAttuale' viene
memorizzato il ristorante con il quale sta comunicando il server.
Viene creato un thread per ogni ristorante che si collega, in modo da avere
concorrenza tra i ristoranti connessi.
 */
public class RistoHandler extends Thread{
    private int port = 31000;
    private Socket socket;
    private ServerSocket ss = null;

    private Ristorante ristoranteAttuale;

    public RistoHandler (Socket s) {
        socket = s;
        start();
    }

/*
La funzione viene attivata dal costruttore.
Viene letto l'id inviato dal ristorante e viene salvato nella variabile 'r', e viene
controllata se esiste nella base di dati.
Se c'è corrispondenza, il ristorante trovato nella base di dati viene assegnato alla
variabile 'ristoranteAttuale' e viene inviato al ristorante come conferma della corrispondenza.
Il ristorante viene aggiunto alla lista dei ristoranti attivi tramite la procedura
'produceRistornate' della classe 'VisualizzaRistorantiAttivi'. Poi viene atteso
che tra gli ordini da eseguire ci sia un ordine per il ristorante con cui si sta
comunicando, quando viene trovato viene inviato al ristorante tramite il canale stream
di scrittura 'oosOrdine'. Viene ricevuto il rider nel canale di lettura 'iosRider'
e viene inserito nella lista dei Rider disponibili.
Per simulare il tempo di consegna dell'ordine, viene invocata la funzione
'sleep' per 10 secondi, dopo di che il ristorante viene rimosso dalla lista dei
ristoranti attivi con la funzione 'consumaRistorante'. Infine chiude la Socket.
Se non c'è corrispondenza nella base di dati, viene inviato un oggetto ristorante
null e viene chiusa la socket.
 */
    public void run(){
        try {
            //Leggere l'id del ristorante e salvarlo
            ObjectInputStream iosRistorante = new ObjectInputStream(socket.getInputStream());
            Ristorante r = (Ristorante) iosRistorante.readUnshared();

            ControllaIDRistorante check = new ControllaIDRistorante();
            Ristorante ret = check.controllaIDQuery(r.getIdRistorante());

            ObjectOutputStream oosRistorante = new ObjectOutputStream(socket.getOutputStream());

            if(ret != null){
                ristoranteAttuale = ret;
                System.out.println(">>è stato richiesto il ristorante ["+ret.getIdRistorante()+"]: "+ret.getNome());
                oosRistorante.writeUnshared(ret);

                System.out.println(">>Inserisco nei ristoranti attivi il ristorante attuale");
                VisualizzaRistorantiAttivi visualizzaRistorantiAttivi = VisualizzaRistorantiAttivi.getIstanza();
                visualizzaRistorantiAttivi.produceRistorante(ristoranteAttuale);

                System.out.println(">>Sto controllando gli ordini da eseguire FUORI");

                OrdiniDaEseguire ordiniDaEseguire = OrdiniDaEseguire.getIstanza();
                Ordine ordine = ordiniDaEseguire.consumaOrdine(ristoranteAttuale);

                ordiniDaEseguire.visualizzaListaOrdiniDaEseguire();

                System.out.println(">>Sto scrivendo l'ordine da eseguire");
                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                oosOrdine.writeUnshared(ordine);

                ObjectInputStream iosRider = new ObjectInputStream(socket.getInputStream());
                Rider rider = (Rider) iosRider.readUnshared();

                ConfermaRider confermaRider = ConfermaRider.getIstanza();
                confermaRider.produceRider(rider);

                System.out.println(">>Si controlla l'esistenza del rider all'interno di un ordine");
                ordiniDaEseguire.controllaPresenzaOrdineEseguito(rider);

                sleep(10000);
                System.out.println(">>Ordine eseguito con successo");

                //Quando ha completato l'ordine
                //Chiama consumaRistorante per rimuoverlo dai ristoranti online

                //Leva il commento quando finisco tutto
                visualizzaRistorantiAttivi.consumaRistorante(ret);

                socket.close();
                this.interrupt();
            }else{
                oosRistorante.writeUnshared(null);
                System.out.println("Non ci sono ristoranti con questo ID");
                socket.close();
            }

        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


