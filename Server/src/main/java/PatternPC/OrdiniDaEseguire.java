package PatternPC;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import java.util.HashMap;
import java.util.LinkedList;

/*
Questa classe si occupa della gestione degli ordini da eseguire con
la lista 'ordiniDaEseguire' e della gestione degli ordini eseguiti con
la lista 'ordiniEseguiti'.
Le liste sono costruite cone HashMap per facilitare la ricerca
attraverso la chiave.
In questa classe è stato implementato il pattern singleton per ottenere
una sola istanza della classe.
 */
public class OrdiniDaEseguire {
    private HashMap<Ristorante,Ordine> ordiniDaEseguire;
    private HashMap<Rider, Ordine> ordiniEseguiti;
    private LinkedList<Ordine> ordiniPerId = (LinkedList<Ordine>) ordiniDaEseguire.values();
    private static OrdiniDaEseguire istanza = null;

    private OrdiniDaEseguire(){
        ordiniDaEseguire = new HashMap<>();
        ordiniEseguiti = new HashMap<>();
    }

    public static OrdiniDaEseguire getIstanza(){
        if(istanza == null)
            istanza = new OrdiniDaEseguire();
        return istanza;
    }

    /*
    La funzione ha lo scopo di inserire nella lista 'ordiniDaEseguire' l'elemento
    passato nella firma. Questo può avvenire solo se solo presenti un numero
    di elementi minori di 10, altrimenti la lista è considerata piena.
    Infine vengono notificati gli altri thread.
     */
    public synchronized boolean produceOrdine (Ordine o) throws InterruptedException {
        while(ordiniDaEseguire.size() >= 10){
            wait();
        }

        ordiniDaEseguire.put(o.getRistorante(), o);
        System.out.println("HO PRODOTTO UN ORDINE AL "+ o.getRistorante() + " di " + o.getCliente());
        notifyAll();
        return true;
    }

    /*
    La funzione ha lo scopo di prendere dalla lista 'ordiniDaEseguire' l'ordine effettuato al
    ristorante passato nella firma. Questo può avvenire solo se la lista non
    è vuota e se esiste un elemento all'interno che ha come chiave il ristorante specificato.
    Quando viene trovato viene inviata una notifica agli altri thread.
    La funzione ritorna l'ordine trovato.
     */
    public synchronized Ordine consumaOrdine (Ristorante ristorante) throws InterruptedException {
        while (ordiniDaEseguire.isEmpty()) {
            wait();
        }

        boolean flag = false;
        Ordine ordineDaImportare = new Ordine();
        System.out.println("Sto consumando l'ordine al " + ristorante.getNome());
        for (Ordine o: ordiniPerId) {
            if(ristorante.getIdRistorante().equals(o.getRistorante().getIdRistorante())){
                ordineDaImportare = o;
                flag = true;
            }
        }
        System.out.println(flag);
        notifyAll();
        return ordineDaImportare;
    }

    /*
    Funzione per la stampa degli elementi della lista 'ordiniDaEseguire'
     */
    public void visualizzaListaOrdiniDaEseguire(){
        System.out.println("Stampa degli ordini da eseguire");
        System.out.println(ordiniDaEseguire);
    }

    /*
    La funzione ha lo scopo di inserire nella lista 'ordiniEseguiti' l'elemento
    passato nella firma. Questo può avvenire solo se solo presenti un numero
    di elementi minori di 10, altrimenti la lista è considerata piena e si attende.
    Infine vengono notificati gli altri thread.
     */
    public synchronized boolean produceOrdineEseguiti(Rider key, Ordine value) throws InterruptedException {
        while(ordiniEseguiti.size() >= 10){
            wait();
        }

        System.out.println("Ho prodotto un ordine eseguito: "+ key.getCognome() + " -> " + value.getCliente().getCognome());
        ordiniEseguiti.put(key,value);
        notifyAll();
        return true;
    }

    /*
    La funzione ha lo scopo di prendere l'ordine dalla lista 'ordiniEseguiti' effettuato al
    rider specificato, passato nella firma. Questo può avvenire solo se la lista non
    è vuota. Se esiste un elemento all'interno che ha come chiave il rider specificato,
    viene inviata una notifica agli altri thread.
     */
    public synchronized boolean consumaOrdineEseguiti(Rider r) throws InterruptedException {
        while(ordiniEseguiti.isEmpty() || !ordiniEseguiti.containsValue(r)){
            wait();
        }

        System.out.println("Sto rimuovendo il rider:" + r.getCognome());
        ordiniEseguiti.remove(r);
        notifyAll();
        return true;
    }

    /*
    la funzione serve a verificare che ci sia un ordine nella lista 'ordiniEseguiti'
    eseguito dal Rider specificato nella firma. Viene atteso che questo elemento ci sia,
    e fatto ritornare il risultato della ricerca (true o false).
     */
    public synchronized boolean controllaPresenzaOrdineEseguito(Rider r) throws InterruptedException {
        while(!ordiniEseguiti.containsKey(r)){
            wait();
        }
        boolean flag = ordiniEseguiti.containsKey(r);
        notifyAll();
        return flag;
    }
}
