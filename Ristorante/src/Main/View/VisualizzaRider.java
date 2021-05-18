package View;

import Controller.ConnessioneServerController;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizzaRider extends Application implements Initializable {

    @FXML
    private ListView listaRider;
    @FXML
    private Label labelRiderSelezionato;



    private String nomeRistoranteSelezionato;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("Visualizza i rider");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<String> riderConnessi = ConnessioneServerController.getRiderConnessi();

        listaRider.setEditable(false);
        ObservableList<String> nomiRistoranti = FXCollections.observableArrayList(riderConnessi);
        listaRider.setItems(nomiRistoranti);

        labelRiderSelezionato.setText("Nessun selezionato");
        nomeRistoranteSelezionato = "Nessun selezionato";

        listaRider.setOnMouseClicked(mouseEvent ->{
            labelRiderSelezionato.setText(String.valueOf(listaRider.getSelectionModel().getSelectedItem()));
            nomeRistoranteSelezionato = labelRiderSelezionato.getText();
        });

    }

    public void procediOrdine(ActionEvent actionEvent) {
    }
}
