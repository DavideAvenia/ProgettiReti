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
Questa classe si occupa di gestire le funzionalità messe a disposizione
della view 'MostraMenuView' per la visualizzazione del menu in una listView.
Nella variabile 'ristoranteUtile' viene salvato il ristorante di cui
il cliente vuole visualizzare il menu.
In questa classe è stato implementato il pattern singleton.
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

    /*
    la funzione ha lo scopo di caricare il menu del ristorante richiesto
    nel campo 'menu' della variabile 'ristoranteUtile'.
    Viene scritto sul canale stream di scrittura 'oos' il ristorante di cui si richiede
    il menu. Viene letto il menu sul canale di lettura 'ios', e viene salvato nella
    variabile 'listaMenu'. Infine viene settato e ritornato il menu del ristorante.
     */
    public List<String> caricaMenu() throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(ristoranteUtile);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> listaMenu = (ArrayList) ois.readUnshared();

        System.out.println(listaMenu);
        ristoranteUtile.setMenu(listaMenu);
        return listaMenu;
    }

    /*
    La funzione ha lo scopo di inviare l'ordine al server.
    Viene creato un nuovo oggetto di tipo 'ordine' a cui vengono settati i campi relativi
    al cliente, la lista di prodotti scelti, e il nome del ristorante.
    La lista di prodotti scelti viene passata nella firma. Infine l'ordine viene
    inviato al server tramite il canale di scrittura 'oos'.
     */
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

    /*
    la funzione ha il compito di settare il ristorante a partire dal suo nome.
    Viene scorsa tutta la lista dei ristoranti finchè non si trova il ristorante il cui nome
    combacia con la stringa passata nella firma.
     */
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
