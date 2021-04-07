package Controller;

import View.VisualizzaRiderView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class VisualizzaRiderController {

    private static VisualizzaRiderController instanza = null;
    private ArrayList<String> idRider;


    private VisualizzaRiderController() throws IOException {
        idRider = new ArrayList<String>();
    }

    public static VisualizzaRiderController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new VisualizzaRiderController();
        }
        return instanza;
    }


    public void visualizzaRider() {
        for (String iter:idRider) {
            System.out.println(iter);
        }

    }

    public void mostra() throws Exception{
        VisualizzaRiderView visualizzaRistoranteView = new VisualizzaRiderView();
        visualizzaRistoranteView.start(new Stage());
    }
}
