package PatternPC;

import Model.Rider;

import java.util.LinkedList;

/*
Questa classe ha il compito di gestire la lista di rider che hanno
dato la conferma di poter consegnare un ordine. La lista viene creata
nel costruttore, che viene richiamato esclusivamente nella funzione
'getIstanza' per implementare il pattern singleton.

 */
public class RiderDisponibili {
    private LinkedList<Rider> riderDisponibili;
    private static RiderDisponibili istanza = null;

    private RiderDisponibili(){
        riderDisponibili = new LinkedList<>();
    }

    public static RiderDisponibili getIstanza(){
        if(istanza == null)
            istanza = new RiderDisponibili();
        return istanza;
    }

    /*
    La funzione aggiunge il rider specificato come parametro alla lista 'riderDisponibili',
    ma solo nel momento in cui la lista ha un numero di elementi all'interno minore di 10.
    Se la lista è piena, viene bloccata da una funzione 'wait'.
    Infine notifica gli altri thread.
     */
    public synchronized boolean produceRider (Rider r) throws InterruptedException {
        while(riderDisponibili.size() >= 10){
            wait();
        }

        riderDisponibili.add(r);
        System.out.println("Ho aggiunto il rider: " + r.getCognome() + " alla lista dei rider disponibili");
        notifyAll();
        return true;
    }

    /*
    La funzione ha il fine di prendere un rider della lista di rider disponibili.
    Se la lista è vuota oppure non contiene il rider atteso attende, altrimenti viene preso l'elemento.
    Infine vengono notificati gli altri thread. La funzione ritorna il rider che è stato preso dalla
    lista 'riderDisponibili'.
     */
    public synchronized Rider consumaRider (Rider r) throws InterruptedException {
        while (riderDisponibili.isEmpty() || !riderDisponibili.contains(r)) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA RIDER");
        int i = riderDisponibili.indexOf(r);
        Rider ret = riderDisponibili.remove(i);
        notifyAll();
        return ret;
    }

    /*
    Funzione per la stampa della lista 'riderDisponibili'.
     */
    public void visualizzaLista(){
        System.out.println(riderDisponibili);
    }

    /*
    Funzione di accesso alla lista 'riderDisponibili'.
     */
    public LinkedList<Rider> getRiderDisponibili() {
        return riderDisponibili;
    }
}
