package Controller;

import Model.Ristorante;
import View.VisualizzaRistoranteView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaRistoranteController {
    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();

    private static VisualizzaRistoranteController visualizzaRistoranteControllerInstanza = null;

    private VisualizzaRistoranteController() throws IOException {
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
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
        List<String> listaNomi = new ArrayList<String>();
        listaRistoranti = (ArrayList) ois.readObject();

        for(Ristorante r: listaRistoranti)
            listaNomi.add(r.getNome());

        return listaNomi;
    }
}
