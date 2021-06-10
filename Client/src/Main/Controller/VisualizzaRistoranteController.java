package Controller;

import Model.Ristorante;
import View.VisualizzaRistoranteView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
Questa classe si occupa di gestire le funzionalità messe a disposizione
della view 'VisualizzaRistoranteView' per la visualizzazione dei ristoranti
da cui il cliente può scegliere per effettuare un ordine.
Nell'ArrayList 'listaRistoranti' vengono salvati i ristoranti che vengono inviati
dal server quando richiesti.
In questa classe è stato implementato il pattern singleton.
 */
public class VisualizzaRistoranteController {

    private ArrayList<Ristorante> listaRistoranti = new ArrayList<>();

    private Socket socket = ConnessioneController.getInstanza().getSocket();

    private static VisualizzaRistoranteController visualizzaRistoranteControllerInstanza = null;

    private VisualizzaRistoranteController() throws IOException {
    }

    public static VisualizzaRistoranteController getInstanza() throws IOException {
        if(visualizzaRistoranteControllerInstanza == null){
            visualizzaRistoranteControllerInstanza = new VisualizzaRistoranteController();
        }
        return visualizzaRistoranteControllerInstanza;
    }

    public void mostra() throws Exception {
        VisualizzaRistoranteView visualizzaRistoranteView = new VisualizzaRistoranteView();
        visualizzaRistoranteView.start(new Stage());
    }

    /*
    La funzione ha lo scopo di riempire l'ArrayList dei ristoranti.
    Viene letta una lista di nomi dal canale stream di lettura 'ois', viene scorsa questa
    lista e per ogni elemento il nome viene salvato in un'altra lista: 'listaNomi'.
    Infine viene fatta ritornare la lista dei nomi.
     */
    public List<String> caricaListaRistoranti() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        List<String> listaNomi = new ArrayList<>();
        listaRistoranti = (ArrayList) ois.readUnshared();

        for(Ristorante r: listaRistoranti){
            listaNomi.add(r.getNome());
        }

        return listaNomi;
    }

    /*
    Funzione di accesso alla variabile 'listaRistoranti'
     */
    public ArrayList<Ristorante> getListaRistoranti() {
        return listaRistoranti;
    }

}
