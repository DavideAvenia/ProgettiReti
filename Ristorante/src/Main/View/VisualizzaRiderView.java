package View;

import Controller.RiderHandler;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class VisualizzaRiderView extends Application implements Initializable {

    @FXML
    private ListView listaRider = new ListView();
    @FXML
    private Label labelRiderSelezionato;

    private String nomeRiderSelezionato;


    ArrayList<String> riderConnessi = new ArrayList<String>();
    ObservableList<String> nomiRider = FXCollections.observableArrayList();
    ArrayList<String> fatti = new ArrayList<String>();


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("Visualizza i rider");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
fatti.add("uno");
fatti.add("due");
    }

    public void refresh(){
        System.out.println("refresh view");
        riderConnessi = RiderHandler.getRiderConnessi();
        System.out.println("rider in lista1: " + riderConnessi);
        nomiRider.clear();
        nomiRider = FXCollections.observableArrayList(riderConnessi);
        System.out.println("nomi rider: " + nomiRider);
        listaRider.setItems(nomiRider);
        System.out.println("lista rider: " + listaRider.getItems());
        listaRider.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaRider.setEditable(true);
        riderConnessi = RiderHandler.getRiderConnessi();
        System.out.println("rider presi");
        nomiRider.addAll(fatti);
        //nomiRider = FXCollections.observableArrayList(riderConnessi);
        listaRider.setItems(nomiRider);

        labelRiderSelezionato.setText("Nessun selezionato");
        nomeRiderSelezionato = "Nessun selezionato";

        listaRider.setOnMouseClicked(mouseEvent -> {
            labelRiderSelezionato.setText(String.valueOf(listaRider.getSelectionModel().getSelectedItem()));
            nomeRiderSelezionato = labelRiderSelezionato.getText();
                });
    }




    public void procediOrdine(ActionEvent actionEvent) {
    }
}
