package Controller;

import Model.Ristorante;
import View.MostraMenuView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MostraMenuController {

    private Ristorante ristoranteUtile;

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static MostraMenuController mostraMenuController = null;

    private MostraMenuController() throws IOException {
    }

    public static MostraMenuController getInstanza() throws IOException {
        if(mostraMenuController == null){
            mostraMenuController = new MostraMenuController();
        }
        return mostraMenuController;
    }

    public List<String> caricaMenu() throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(ristoranteUtile);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        List<String> listaMenu = (ArrayList) ois.readUnshared();

        System.out.println(listaMenu);
        return listaMenu;
    }

    public void setRistoranteUtile(String nomeRistorante){
        ristoranteUtile = new Ristorante(nomeRistorante, null);
    }

    public void mostra() throws Exception {
        MostraMenuView mostraMenuView = new MostraMenuView();
        mostraMenuView.start(new Stage());
    }
}
