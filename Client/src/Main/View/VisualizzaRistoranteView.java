package View;

import Controller.VisualizzaRistoranteController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;

public class VisualizzaRistoranteView extends Application implements Initializable {

    @FXML
    private Label labelRistoranteSelezionato;

    @FXML
    public ListView listaRistoranti;

    private int indiceSelezionato;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRistorante.fxml"));
        primaryStage.setTitle("Visualizza i ristoranti");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VisualizzaRistoranteController visualizzaRistoranteController = VisualizzaRistoranteController.getInstanza();
            List<String> listaNomi = visualizzaRistoranteController.caricaListaRistoranti();

            listaRistoranti.setEditable(false);
            ObservableList<String> nomiRistoranti = FXCollections.observableArrayList(listaNomi);
            listaRistoranti.setItems(nomiRistoranti);

            listaRistoranti.getSelectionModel().select(indiceSelezionato);
            indiceSelezionato = 0;


            listaRistoranti.setOnMouseClicked(mouseEvent ->{
                labelRistoranteSelezionato.setText(String.valueOf(listaRistoranti.getSelectionModel().getSelectedItem()));
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
