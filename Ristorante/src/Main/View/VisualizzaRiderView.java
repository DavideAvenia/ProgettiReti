package View;

import Controller.VisualizzaRiderController;
import Model.Rider;
import PatternPC.RiderDisponibili;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
/*
La classe si occupa di gestire l'interfaccia grafica di visualizzazione
dei rider connessi.
L'utente può premere sul pulsante 'refresh' per aggiornare la lista.
 */
public class VisualizzaRiderView extends Application implements Initializable {

    @FXML
    private ListView listaRider;
    @FXML
    private Label labelRiderSelezionato;

    private Rider riderDaInviare;
    private String nomeRiderSelezionato;
    private LinkedList<Rider> riderConnessi = new LinkedList<>();
    private ObservableList<String> nomiRider = FXCollections.observableArrayList("clicca su aggiorna");

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("Visualizza i rider");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();

    }

    /*
    In una linkedList 'riderConnessi' vengono salvati i rider disponibili
    memorizzati della lista 'riderDisponibili' della omonima classe.
    L'observable List 'nomiRider' viene svuotata e riempita attraverso un for
    con i nomi appena memorizzati in 'riderConnessi'.
    Infine viene settata la listView con i nomi inseriti nella obervableList.
     */
    public void refresh(){
        RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
        System.out.println("prendo il rider disponibili");
        riderConnessi = riderDisponibili.getRiderDisponibili();
        nomiRider.clear();
        for(Rider i:riderConnessi){
            nomiRider.add(i.getNome());
        }
        System.out.println("nomi rider: " + nomiRider);
        listaRider.setItems(nomiRider);
        System.out.println("lista rider: " + listaRider.getItems());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaRider.setEditable(true);
        listaRider.setItems(nomiRider);

        labelRiderSelezionato.setText("Nessun selezionato");
        nomeRiderSelezionato = "Nessun selezionato";

        listaRider.setOnMouseClicked(mouseEvent -> {
            labelRiderSelezionato.setText(String.valueOf(listaRider.getSelectionModel().getSelectedItem()));
            nomeRiderSelezionato = labelRiderSelezionato.getText();
                });
    }

    /*
    La funzione viene attivata quando l'utente preme il bottone 'Procedi Ordine'.
    Come prima cosa viene identificato il Rider selezionato dall'utente, e poi
    viene richiamata la funzione 'procediOrdine' della classe 'VisualizzaRiderController'
    che si occupa di inviare il Rider selezionato al server.
     */
    public void procediOrdine(ActionEvent actionEvent) throws Exception {
        System.out.println("Bottone procedi ordine premuto");
        int index = listaRider.getSelectionModel().getSelectedIndex();
        riderDaInviare = riderConnessi.get(index);
        VisualizzaRiderController.getInstanza().procediOrdine(riderDaInviare);
    }

    /*
    La funzione viene chiamata nel momento in cui l'utente preme sul bottone
    'Refresh'. Viene richiamata la funzione refresh specificata prima.
     */
    public void RefreshPremuto(ActionEvent actionEvent) throws IOException {
        System.out.println("bottone refresh premuto");
        refresh();
    }
}
