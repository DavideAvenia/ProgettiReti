package View;

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

public class VisualizzaRiderView extends Application implements Initializable {

    @FXML
    private ListView listaRider;
    @FXML
    private Label labelRiderSelezionato;

    private String nomeRiderSelezionato;

    private LinkedList<Rider> riderConnessi = new LinkedList<>();
    private ObservableList<String> nomiRider = FXCollections.observableArrayList("attendi");

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("Visualizza i rider");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();

    }

    public void refresh(){
        System.out.println("refresh view");
        RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
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

    }

    public void RefreshPremuto(ActionEvent actionEvent) throws IOException {
        refresh();
    }
}
