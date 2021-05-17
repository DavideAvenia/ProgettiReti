package Controller;

import Model.Ristorante;
import View.VisualizzaRistoranteView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> caricaListaRistoranti() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        List<String> listaNomi = new ArrayList<>();
        listaRistoranti = (ArrayList) ois.readUnshared();

        for(Ristorante r: listaRistoranti){
            listaNomi.add(r.getNome());
        }

        return listaNomi;
    }

    public ArrayList<Ristorante> getListaRistoranti() {
        return listaRistoranti;
    }

}
