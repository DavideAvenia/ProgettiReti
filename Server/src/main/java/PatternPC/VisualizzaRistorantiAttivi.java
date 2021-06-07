package PatternPC;

import Model.Ordine;
import Model.Ristorante;

import java.util.LinkedList;

/*
Questa classe di occupa di gestire la lista di ristoranti attivi.
La lista è costruita come LinkedList.
Per questa classe è stato implementato il pattern singleton.
 */
public class VisualizzaRistorantiAttivi {
    private LinkedList<Ristorante> ristorantiAttivi;
    private static VisualizzaRistorantiAttivi istanza = null;

    private VisualizzaRistorantiAttivi(){
        ristorantiAttivi = new LinkedList<>();
    }

    public static VisualizzaRistorantiAttivi getIstanza(){
        if(istanza == null)
            istanza = new VisualizzaRistorantiAttivi();
        return istanza;
    }


    /*
    La funzione ha lo scopo di inserire l'elemento di tipo Ristorante specificato nella firma.
    Attende che la lista abbia meno di 10 elementi, e dopo aver inserito il ristorante
    notifica gli altri thread.
    La funzione viene richiamata dalla classe che gestisce la connessione con il ristorante.
     */
    public synchronized boolean produceRistorante (Ristorante ristorante) throws InterruptedException {
        while(ristorantiAttivi.size() >= 10){
            wait();
        }

        System.out.println("Ho prodotto un ristorante" + ristorante.getNome());
        ristorantiAttivi.add(ristorante);
        notifyAll();
        return true;
    }

    /*
    La funzione ha lo scopo di rimuovere il ristorante, specificato nella firma, dalla lista dei
    ristoranti attivi. Non è possibile farlo se la lista è vuota.
    Se il ristorante è presente nella lista allora viene rimosso e la funzione ritorna 'true',
    altrimenti non succede niente e la funzione ritorna 'false'.
    La funzione viene chiamata nella classe che gestisce la connessione con il ristorante.
     */
    public synchronized boolean consumaRistorante (Ristorante ristorante) throws InterruptedException {
        while (ristorantiAttivi.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando se il ristorante è presente dentro consumaRistorante");
        if(ristorantiAttivi.contains(ristorante)) {
            System.out.println("Il ristorante è presente, lo sto rimuovendo");
            ristorantiAttivi.remove(ristorante);
            notifyAll();
            return true;
        }else
            return false;

    }

    /*
    Funzione per la visualizzazione degli elementi presenti nella lista 'ristorantiAttivi'.
     */
    public void visualizzaLista(){
        System.out.println(ristorantiAttivi);
    }

    /*
    La funzione ha lo scopo di attendere la presenza nella lista 'ristorantiAttivi' del
    ristorante specificato nella firma.
     */
    public synchronized boolean controllaPresenzaRistorante(Ristorante r) throws InterruptedException {
        while(!ristorantiAttivi.contains(r))
            wait();

        boolean flag = ristorantiAttivi.contains(r);
        System.out.println("Il ristorante è presente " + r.getNome());
        notifyAll();
        return flag;
    }

}
