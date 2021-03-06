package View;

import Controller.MostraMenuController;
import Controller.MostraRiderController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*
Questa classe si occupa di gestire l'interfaccia grafica di visualizzazione del
menu del ristorante scelto dal cliente.
 */
public class MostraMenuView extends Application implements Initializable {

    @FXML
    private Label labelProdottiSelezionati;

    @FXML
    private ListView listaProdotti;

    @FXML
    private Button buttoneProcediOrdine;

    private List<String> listaProdottiDaInviare = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MostraMenu.fxml"));
        primaryStage.setTitle("Mostra Menu del ristorante");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MostraMenuController mostraMenuController = MostraMenuController.getInstanza();
            List<String> listaMenu = mostraMenuController.caricaMenu();

            listaProdotti.setEditable(false);
            ObservableList<String> nomiProdotti = FXCollections.observableArrayList(listaMenu);
            listaProdotti.setItems(nomiProdotti);

            listaProdotti.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            listaProdotti.getSelectionModel().selectedItemProperty()
                    .addListener((ov, old_val, new_val) -> {
                        ObservableList<String> selectedItems = listaProdotti.getSelectionModel().getSelectedItems();

                        StringBuilder builder = new StringBuilder();

                        for (String name : selectedItems) {
                            builder.append(name + "\n");
                            listaProdottiDaInviare.add(name);
                        }

                        labelProdottiSelezionati.setText(builder.toString());

                    });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    La funzione viene chiamata quando il cliente preme sul bottone 'effettua ordine'.
    Se la lista di prodotti ?? vuota viene fatto visualizzare un messaggio di errore,
    altrimenti viene inviato l'ordine al server. Se l'invio va a buon fine viene
    mostrata una finestra in cui viene visualizzato il rider che sta consegnando l'ordine.
     */
    public void effettuaOrdine(ActionEvent actionEvent) throws Exception {
        if(listaProdottiDaInviare.isEmpty()){
            new Messaggio("Errore","La lista ?? vuota, non puoi effettuare un ordine");
        }else{
            System.out.println(MostraMenuController.getInstanza().inviaOrdine(listaProdottiDaInviare));
            MostraMenuController mostraMenuController = MostraMenuController.getInstanza();
            if(mostraMenuController.inviaOrdine(listaProdottiDaInviare)){
                MostraRiderController mostraRiderController = MostraRiderController.getInstanza();
                mostraRiderController.mostra();
            }

        }
    }
}
