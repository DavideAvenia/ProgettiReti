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

    public void procediOrdine(Rider r) throws IOException {
        //Invia il rider per poterlo far leggere al server e poi inviarlo al client
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(r);

    }
}

