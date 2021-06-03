package Controller;

import Model.Rider;
import View.MostraMenuView;
import View.MostraRiderView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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

    public String ottieniRider() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        riderUtile = (Rider) ois.readUnshared();

        return riderUtile.getIdRider();
    }
}
