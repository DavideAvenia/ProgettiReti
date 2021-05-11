package Controller;

import Model.Ristorante;
import View.MostraMenuView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MostraMenuController {

    private Ristorante ristoranteUtile;

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private static MostraMenuController mostraMenuController = null;

    private MostraMenuController() throws IOException {
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        ristoranteUtile = new Ristorante();
    }

    public static MostraMenuController getInstanza() throws IOException {
        if(mostraMenuController == null){
            mostraMenuController = new MostraMenuController();
        }
        return mostraMenuController;
    }

    public void mostra(String nomeRistorante) throws Exception {
        VisualizzaRistoranteController visualizzaRistoranteController = VisualizzaRistoranteController.getInstanza();
        ArrayList<Ristorante> listaRistoranti = visualizzaRistoranteController.getListaRistoranti();

        for (Ristorante r:listaRistoranti){
            if(r.getNome().equals(nomeRistorante))
                ristoranteUtile.setNome(nomeRistorante);
        }

        MostraMenuView mostraMenuView = new MostraMenuView();
        mostraMenuView.start(new Stage());
    }
}
