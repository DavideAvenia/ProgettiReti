package View;

import Controller.MostraMenuController;
import Controller.VisualizzaRistoranteController;
import Model.Ristorante;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/*
Questa classe si occupa di gestire l'interfaccia grafica di
visualizzazione della lista dei ristoranti da cui è possibile ordinare.
 */
public class VisualizzaRistoranteView extends Application implements Initializable {

    @FXML
    private Button buttoneProcediOrdine;

    @FXML
    private Label labelRistoranteSelezionato;

    @FXML
    public ListView listaRistoranti;

    private String idRistoranteSelezionato;
    private String nomeRistoranteSelezionato;

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

            labelRistoranteSelezionato.setText("Nessun selezionato");
            nomeRistoranteSelezionato = "Nessun selezionato";

            listaRistoranti.setOnMouseClicked(mouseEvent ->{
                labelRistoranteSelezionato.setText(String.valueOf(listaRistoranti.getSelectionModel().getSelectedItem()));
                nomeRistoranteSelezionato = labelRistoranteSelezionato.getText();
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    La funzione viene chiamata quando l'utente preme sul bottone 'Procedi Ordine'.
    Viene settato il nome del ristorante selezionato e fatto visualizzare il menu
    del ristorante scelto.
     */
    public void procediOrdine(ActionEvent actionEvent) {
        try {
            MostraMenuController mostraMenuController = MostraMenuController.getInstanza();
            mostraMenuController.setRistoranteUtile(nomeRistoranteSelezionato);
            mostraMenuController.mostra();
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
