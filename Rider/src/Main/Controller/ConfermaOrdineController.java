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
    private ListView listOrdini;
    private Set<String> stringSet;
    ObservableList observableList = FXCollections.observableArrayList();

    public void setListView() {

        stringSet.add("String 1");
        stringSet.add("String 2");
        stringSet.add("String 3");
        stringSet.add("String 4");
        observableList.setAll(stringSet);
        listOrdini.setItems(observableList);
        listOrdini.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell<String> call(ListView listOrdini) {
                return null;
            }
        });
    }
        @FXML
        void initialize() {
        assert listOrdini != null : "fx:id=\"listOrdini\" was not injected: check your FXML file 'CustomList.fxml'.";

        setListView();
    }


    public void mostra() throws Exception{
        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());
        initialize();
    }
}
