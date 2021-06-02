package PatternPC;

import Model.Ordine;
import Model.Ristorante;

import java.util.LinkedList;

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

    public synchronized boolean produceRistorante (Ristorante ristorante) throws InterruptedException {
        while(ristorantiAttivi.size() >= 10){
            wait();
        }

        ristorantiAttivi.add(ristorante);
        notifyAll();
        return true;
    }

    public synchronized boolean consumaRistorante (Ristorante ristorante) throws InterruptedException {
        while (ristorantiAttivi.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando se il ristorante è presente DENTRO PC");
        if(ristorantiAttivi.contains(ristorante)) {
            System.out.println("Il ristorante è presente, lo sto rimuovendo");
            ristorantiAttivi.remove(ristorante);
            notifyAll();
            return true;
        }else
            return false;

    }

    public void visualizzaLista(){
        System.out.println(ristorantiAttivi);
    }

    public boolean controllaPresenzaRistorante(Ristorante r) throws InterruptedException {
        while(!ristorantiAttivi.add(r))
            wait();
        notifyAll();
        return ristorantiAttivi.contains(r);
    }

}
