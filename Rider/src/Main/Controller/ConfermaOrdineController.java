package Controller;

import View.ConfermaOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
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
    private ListView<String> listOrdini = new ListView<String>();

    private Set<String> stringSet;

    public void setListOrdini() {
        //observableList.add("uno");
        //observableList.add("due");
        //observableList.add("tre");
        ObservableList observableList = FXCollections.observableArrayList("uno","due","tre");

        listOrdini.setItems(observableList);


    }


    public void mostra() throws Exception {

        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());


    }

    public void conferma() throws IOException{
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write("conferma");
        bw.flush();
    }

    public void annulla() throws IOException{
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write("annulla");
        bw.flush();
    }
}



