package Controller;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;
import PatternPC.GestioneOrdini;
import View.VisualizzaRiderView;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/*
Questa classe si occupa di gestire le funzionalità messe a disposizione alla View
'VisualizzaRiderView'.
 */
public class VisualizzaRiderController {

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static VisualizzaRiderController visualizzaRiderControllerInstanza = null;

    private VisualizzaRiderController() throws Exception {
    }

    public static VisualizzaRiderController getInstanza() throws Exception {
        if (visualizzaRiderControllerInstanza == null) {
            visualizzaRiderControllerInstanza = new VisualizzaRiderController();
        }
        return visualizzaRiderControllerInstanza;
    }

    public void mostra() throws Exception {
        VisualizzaRiderView visualizzaRiderView = new VisualizzaRiderView();
        visualizzaRiderView.start(new Stage());
    }

    /*
    Una volta aperto il canale stream di scrittura 'oos' viene inviato il rider
    al server, che si occuperà di inviarlo al client
     */
    public void procediOrdine(Rider r) throws IOException, ClassNotFoundException, InterruptedException {
        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        System.out.println("ricevo l'ordine dal server");
        Ordine o = (Ordine) ios.readUnshared();
        System.out.println("produco l'ordine");
        GestioneOrdini gestioneordini = GestioneOrdini.getIstanza();
        gestioneordini.produceOrdine(o);
        System.out.println("HO PRODOTTO UN ORDINE AL " + o.getRistorante() + " DI " + o.getCliente());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Invio il rider al server");
        oos.writeUnshared(r);
    }
}

