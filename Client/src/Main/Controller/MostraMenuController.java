package Controller;

import Model.Cliente;
import Model.Ordine;
import Model.Ristorante;
import View.MostraMenuView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
Questa classe si occupa di gestire
 */
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
        ArrayList<String> listaMenu = (ArrayList) ois.readUnshared();

        System.out.println(listaMenu);
        ristoranteUtile.setMenu(listaMenu);
        return listaMenu;
    }

    public boolean inviaOrdine(List<String> listaProdotti){
        try{
            Cliente cliente = ConnessioneController.getInstanza().getCliente();
            Ordine ordineEffettutato = new Ordine(cliente, listaProdotti, this.ristoranteUtile);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeUnshared(ordineEffettutato);
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void setRistoranteUtile(String nomeRistorante) throws IOException {
        List<Ristorante> listaRistoranti = VisualizzaRistoranteController.getInstanza().getListaRistoranti();

        for (Ristorante r:listaRistoranti) {
            if(r.getNome().equals(nomeRistorante)) {
                ristoranteUtile = r;
                break;
            }
        }
    }

    public void mostra() throws Exception {
        MostraMenuView mostraMenuView = new MostraMenuView();
        mostraMenuView.start(new Stage());
    }
}
