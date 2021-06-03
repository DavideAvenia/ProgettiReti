package Controller;

import Model.Cliente;
import Model.Ordine;
import View.ConfermaOrdineView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ConfermaOrdineController {

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static ConfermaOrdineController instanza = null;
    private Cliente c;

    private ConfermaOrdineController() throws IOException {
    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController();
        }
        return instanza;
    }

    public Ordine checkRichieste() throws IOException, ClassNotFoundException {
        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        Ordine ordine = (Ordine) ios.readObject();
        return ordine;
    }

    public void mostra() throws Exception {
        ConfermaOrdineView confermaordine = new ConfermaOrdineView();
        confermaordine.start(new Stage());
    }

    public String conferma() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("conferma");
        System.out.println("ordine confermato");
        String clienteDaServire = c.getIdCliente() + c.getNome() + c.getCognome();
        socket.close();
        return clienteDaServire;
    }

    public void annulla() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("annulla");
        System.out.println("ordine annullato");
        socket.close();
    }

}



