package Controller;

import View.ConfermaOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class ConfermaOrdineController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private static ConfermaOrdineController instanza = null;

    private ConfermaOrdineController() throws IOException {
        socket = new Socket(ip, port);

    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController();
        }
        return instanza;
    }


    @FXML
    private ListView<String> listOrdini;

    private Set<String> stringSet;
    ObservableList observableList = FXCollections.observableArrayList();

    public void setListOrdini() {
        listOrdini.getItems().add("uno");
        listOrdini.getItems().add("due");
        listOrdini.getItems().add("tre");
    }


    public void mostra() throws Exception {
        setListOrdini();
        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());

    }
}



