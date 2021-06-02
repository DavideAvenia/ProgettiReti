package Controller;

import Model.Cliente;
import Model.Ordine;
import Model.Rider;
import View.ConfermaOrdine;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ConfermaOrdineController {

    private String ip = "localhost";
    private int port = 30000;
    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static ConfermaOrdineController instanza = null;
    private Cliente c;

    private ConfermaOrdineController() throws IOException {
    }

    public boolean checkRichieste() throws IOException, ClassNotFoundException {
        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        Ordine ordine = (Ordine) ios.readObject();
        c = ordine.getCliente();
        return true;
    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController();
        }
        return instanza;
    }


    public void mostra() throws Exception {
        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());
    }

    public void conferma() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("conferma");
        System.out.println("ordine confermato");
        //sleep(1000);
        if(checkRichieste()) {
            mostra();
        }
    }

    public void annulla() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("annulla");
        System.out.println("ordine annullato");
        if(checkRichieste()){
            mostra();
        }
    }

    public String getidCliente(){
        return c.getIdCliente();
    }
}



