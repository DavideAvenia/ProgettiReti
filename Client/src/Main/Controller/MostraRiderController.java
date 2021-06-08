package Controller;

import Model.Rider;
import View.MostraMenuView;
import View.MostraRiderView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*
Questa classe si occupa di gestire le funzionalità messe a disposizione
della view 'MostraRiderView' per la visualizzazione dell'id del rider al cliente.
Nella variabile 'riderUtile' viene salvato il rider che effettua l'ordine
che è stato inviato.
In questa classe è stato implementato il pattern singleton.
 */
public class MostraRiderController {

    private Rider riderUtile;

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static MostraRiderController instanza = null;

    private MostraRiderController() throws IOException {
    }

    public static MostraRiderController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new MostraRiderController();
        }
        return instanza;
    }

    public void mostra() throws Exception {
        MostraRiderView mostraRiderView = new MostraRiderView();
        mostraRiderView.start(new Stage());
    }

    /*
    La funzione legge dal canale di lettura il rider inviato dal server e
    lo assegna alla variabile 'riderUtile'. Viene fatto ritornare l'id del rider
    appena assegnato.
     */
    public String ottieniRider() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        riderUtile = (Rider) ois.readUnshared();

        return riderUtile.getIdRider();
    }
}
