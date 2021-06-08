package View;

import Controller.MostraRiderController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/*
Questa classe si occupa di gestire l'interfaccia grafice di visualizzazione
del rider che sta consegnando l'ordine.
 */
public class MostraRiderView extends Application {

    @FXML
    private Label testoRider;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MostraRider.fxml"));
        primaryStage.setTitle("Mostra Menu del ristorante");
        primaryStage.setScene(new Scene(root, 255, 137));
        primaryStage.show();
    }

    /*
    La funzione viene invocata quando il cliente preme sul bottore 'controlla Ordine'.
    Viene ottenuto l'id del rider dalla classe 'mostraRiderController' e settato come
    testo nella finestra.
     */
    public void ControllaOrdine(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        MostraRiderController mostraRiderController = MostraRiderController.getInstanza();

        String idRider = mostraRiderController.ottieniRider();
        testoRider.setText(idRider);
    }
}
