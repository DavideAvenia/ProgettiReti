package View;

import Controller.ConnessioneController;
import Controller.RiderHandler;
import Model.Ordine;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Scanner;

public class VisualizzaRiderView extends Application implements Initializable {

    @FXML
    private ListView listaRider;
    @FXML
    private Label labelRiderSelezionato;

    private String nomeRiderSelezionato;


    ArrayList<String> riderConnessi = new ArrayList<String>();
    ObservableList<String> nomiRider = FXCollections.observableArrayList("attendi");

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("Visualizza i rider");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();

    }

    public void refresh(){
        System.out.println("refresh view");
        riderConnessi = RiderHandler.getRiderConnessi();

        nomiRider.clear();
        nomiRider.addAll(riderConnessi);
        System.out.println("nomi rider: " + nomiRider);
        listaRider.setItems(nomiRider);
        System.out.println("lista rider: " + listaRider.getItems());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaRider.setEditable(true);
        riderConnessi = RiderHandler.getRiderConnessi();
        System.out.println("rider presi");
        //nomiRider = FXCollections.observableArrayList(riderConnessi);
        //nomiRider.addAll(riderConnessi);
        listaRider.setItems(nomiRider);

        labelRiderSelezionato.setText("Nessun selezionato");
        nomeRiderSelezionato = "Nessun selezionato";

        listaRider.setOnMouseClicked(mouseEvent -> {
            labelRiderSelezionato.setText(String.valueOf(listaRider.getSelectionModel().getSelectedItem()));
            nomeRiderSelezionato = labelRiderSelezionato.getText();
                });
    }

    public void procediOrdine(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ConnessioneController cc = null;
        ArrayList<Ordine> Ordini = cc.getOrdiniRicevuti();
        RiderHandler.inviaOrdine(Ordini);
    }

    public void RefreshPremuto(ActionEvent actionEvent) {
        refresh();
    }
}
