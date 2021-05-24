package View;

import Controller.ConnessioneController;
import Controller.RiderHandler;
import Controller.ServerHandler;
import Controller.VisualizzaRiderController;
import Model.Ordine;
import Model.Rider;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConnessioneView extends Application {

    @FXML
    private Label text;
    @FXML
    private TextField TextFieldIdRistorante;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConnessioneView.fxml"));
        primaryStage.setTitle("LOGIN");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }

    // come prima cosa viene creata la serversocket per accetta le connessioni dei rider
    // il un loop infinito accetta le connessioni sulla porta specificata nella serversocket
    // poi crea una nuova connessione per ogni rider accettato e mostra la finestra di attesa
    public void AccediPremuto(ActionEvent actionEvent) throws Exception {

        String idRistorante = TextFieldIdRistorante.getText();
        new ConnessioneController(idRistorante);

            //Chiamo il metodo per vedere se l'id è presente
            //Il server andrà a dare o true o false in caso di presenza del'id
            //Se non c'è, messaggio di errore, altrimenti va avanti

        //Chiude la finestra
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        VisualizzaRiderController visualizzarider = VisualizzaRiderController.getInstanza();
        visualizzarider.mostra();
    }
}

