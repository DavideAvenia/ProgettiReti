package Controller;

import Model.Ristorante;
import View.VisualizzaRiderView;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class VisualizzaRiderController {

    private static VisualizzaRiderController visualizzaRiderControllerInstanza = null;
    private VisualizzaRiderView visualizzarider;

    private VisualizzaRiderController() throws IOException {
    }

    public static VisualizzaRiderController getInstanza() throws IOException {
        if (visualizzaRiderControllerInstanza == null) {
            visualizzaRiderControllerInstanza = new VisualizzaRiderController();
        }
        return visualizzaRiderControllerInstanza;
    }

    public void mostra() throws Exception {

        visualizzarider = new VisualizzaRiderView();
        visualizzarider.start(new Stage());
    }

    public void refresh(){
        System.out.println("refresh controller");
        visualizzarider.refresh();
    }
}

