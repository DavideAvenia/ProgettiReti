package Controller;

import Model.Rider;
import Model.Ristorante;
import View.VisualizzaRiderView;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void procediOrdine(Rider r) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(r);

    }
}

