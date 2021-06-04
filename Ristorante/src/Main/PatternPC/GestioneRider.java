package PatternPC;

import Model.Rider;

import java.util.LinkedList;
/*
Questa classe si occupa di gestire la lista di rider connessi 'riderInviati'.
Anche per questa classe è stato implementato il pattern singleton.
 */
public class GestioneRider {

    private LinkedList<Rider> riderInviati;
    private static GestioneRider istanza = null;

    private GestioneRider(){
        riderInviati = new LinkedList<>();
    }

    public static GestioneRider getIstanza(){
        if(istanza == null)
            istanza = new GestioneRider();
        return istanza;
    }

    /*
    Il fine della funzione è quello di aggiungere il rider passato come parametro
    alla lista di rider connessi. Se la lista è piena però non può farlo, quindi si
    blocca. Quando il Rider viene aggiunto alla lista vengono notificati gli altri thread.
     */
    public synchronized boolean produceRiderInviati (Rider r) throws InterruptedException {
        while(riderInviati.size() >= 10){
            wait();
        }

        riderInviati.add(r);
        notifyAll();

        return true;
    }

    /*
    La funzione ha il fine di eliminare il rider specificato come parametro dalla
    lista di rider connessi. Questo non può avvenire se la lista è vuota, se c'è
    almeno un elemento nella lista allora viene rimosso il rider specificato come
    parametro e viene mandata una notifica ai thread.
    La funzione ritorna il Rider che viene rimosso.
     */
    public synchronized Rider consumaRiderInviati (Rider r) throws InterruptedException {
        while (riderInviati.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA RIDER");
        Rider riderDaInviare = riderInviati.remove(riderInviati.indexOf(r));
        notifyAll();
        return riderDaInviare;
    }

    /*
    Funzione per la stampa della lista dei rider.
     */
    public void visualizzaLista(){
        System.out.println(riderInviati);
    }

    /*
    Funzione di accesso alla lista dei rider.
     */
    public LinkedList<Rider> getRiderInviati() {
        return riderInviati;
    }
}
