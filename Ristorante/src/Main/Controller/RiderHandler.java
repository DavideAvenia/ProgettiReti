package Controller;

import Model.Ordine;
import Model.Rider;
import PatternPC.GestioneOrdini;
import PatternPC.RiderDisponibili;
import Queries.ControllaID;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

/*
Questa classe si occupa di gestire la connessione tra il ristorante e il rider.
E' stata scelta la porta 32000.
Viene creato un thread per ogni rider che si collega, in modo da avere
concorrenza tra i rider connessi.
 */
public class RiderHandler extends Thread {
    private int port = 32000;
    private Socket socket;

    private String idRistorante;
    private Rider rider;

    /*
    Il costruttore ha come parametri una variabile di tipo Socket che viene salvata
    nella variabile 'socket'. Infine fa partire la funzione run().
     */
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        start();
    }

    public void run () {
        try {
//            Come prima cosa viene creato il canale stream di lettura con il rider,
//            viene letto l'id inviato dal Rider e viene controllato nella base di dati.
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            System.out.println("leggo il rider");
            rider = (Rider) ios.readObject();
            System.out.println("faccio la query con l'id del rider ricevuto");
            ControllaID check = new ControllaID();
            Model.Rider ret = check.controllaIDQuery(rider.getIdRider());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            Se il controllo non va a buon fine viene scritto sul canale di scrittura un oggetto 'null',
            if (ret == null) {
                oos.writeObject(null);
                System.out.println("Non ci sono rider con questo ID");
            } else {
//                Se il rider è stato confermato, viene inviato il rider
//                trovato come conferma...
                System.out.println("rider connesso[" + ret.getIdRider() + "]: " + ret.getNome() + " " + ret.getCognome());
                System.out.println("Invio del rider ottenuto dalla base di dati");
                oos.writeObject(ret);

//                ... e viene aggiunto alla lista di rider disponibili,
//                viene utilizzata la funzione 'produce' del pattern produttore-consumatore.
                System.out.println(">>>Sto producendo il rider disponibile");
                RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
                riderDisponibili.produceRider(ret);

//                A questo punto un ordine viene consumato dalla lista attraverso la funzione
//                'consumaOrdine'
//                del pattern produttore-consumatore, e viene
//                inviato al rider attraverso il canale di scrittura.
                GestioneOrdini gestioneOrdini = GestioneOrdini.getIstanza();
                System.out.println(">>>consumo un ordine");
                Ordine ordineConsumato = gestioneOrdini.consumaOrdine();

                System.out.println(ordineConsumato.getCliente().getIdCliente());
                System.out.println(ordineConsumato.getProdotti());
                System.out.println(ordineConsumato.getRistorante().getIdRistorante());

                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                System.out.println(">>>scrivo l'ordine al rider");
                oosOrdine.writeUnshared(ordineConsumato);

//                Infine il rider che ha confermato
//                viene inserito nella lista di rider che hanno confermato e dopo una pausa
//                la socket viene chiusa.
                System.out.println(">>>Rider inviato");
                System.out.println(">>>Rider rimosso dalla pool dei rider disponibili");
                riderDisponibili.consumaRider(rider);

                sleep(10000);

                socket.close();
            }

            } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

    }

};



