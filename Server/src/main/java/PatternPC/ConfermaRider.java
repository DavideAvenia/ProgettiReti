package PatternPC;

import Model.Rider;
import Model.Ristorante;

import java.util.LinkedList;

/*
Questa classe si occupa di gestire la lista di Rider 'riderDisponibili'.
La lista è costruita come LinkedList.
Anche per questa classe è stato implementato il pattern singleton.
 */
public class ConfermaRider {
    private LinkedList<Rider> riderDisponibili;
    private static ConfermaRider istanza = null;

    private ConfermaRider(){
        riderDisponibili = new LinkedList<>();
    }

    public static ConfermaRider getIstanza(){
        if(istanza == null)
            istanza = new ConfermaRider();
        return istanza;
    }

    /*
    La funzione ha lo scopo di aggiungere alla lista il rider specificato nella firma.
    Viene atteso il momento in cui il numero di elementi nella lista sia minore di 10.
    Con un numero maggiore o uguale di 10 la lista è considerata piena e non possono
    essere aggiunti altri elementi.
    Infine vengono notificati gli altri thread.
    La funzione viene invocata nella classe che gestisce la comunicazione tra il server
    e il ristorante.
     */
    public synchronized boolean produceRider (Rider r) throws InterruptedException {
        while(riderDisponibili.size() >= 10){
            wait();
        }

        riderDisponibili.add(r);
        notifyAll();

        return true;
    }

    /*
    La funzione ha lo scopo di prendere il primo rider della lista.
    Viene atteso il momento in cui il numero di elementi nella lista sia maggiore di 0,
    cioè che la lista non sia vuota.
    Infine vengono notificati gli altri thread e viene ritornato il rider preso dalla lista.
    La funzione viene invocata nella classe che gestisce la comunicazione tra il server
    e il client.
     */
    public synchronized Rider consumaRider () throws InterruptedException {
        while (riderDisponibili.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA RIDER");
        Rider r = riderDisponibili.peek();
        notifyAll();
        return r;
    }

    /*
    Funzione per la stampa della lista 'riderDisponibili.
     */
    public void visualizzaLista(){
        System.out.println(riderDisponibili);
    }

}
