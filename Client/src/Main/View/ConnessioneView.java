package View;

import Controller.ConnessioneController;
import Controller.VisualizzaRistoranteController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;

/*
Questa classe si occupa di gestire l'interfaccia grafica di login dell'utente.
L'utente inserisce l'id e poi preme il pulsante 'Accedi'.
 */
public class ConnessioneView extends Application {

    @FXML
    private TextField textFieldIdCliente;

    @FXML
    private Button apriConnessione;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Connessione.fxml"));
        primaryStage.setTitle("Connessione");
        primaryStage.setScene(new Scene(root, 300, 190));
        primaryStage.show();
    }

    /*
    La funzione viene chiamata quando l'utente preme sul pulsante 'accedi'.
    Viene preso l'id inserito e salvato nella variabile 'id', per poterlo controllare
    all'interno della base di dati una volta inviato al server.
    Se non c'è corrispondenza viene fatto visualizzare all'utente un messaggio di errore,
    altrimenti viene chiusa la finestra di login e viene fatta visualizzare la finestra
    di visualizzazione dei ristoranti.
     */
    public void apriConnessione(javafx.event.ActionEvent actionEvent) {
        try {
            String id = textFieldIdCliente.getText();
            ConnessioneController connessioneController = ConnessioneController.getInstanza();

            if(!connessioneController.inviaIdCliente(id)){
                //Finestra di errore
                Messaggio m = new Messaggio("Errore","Non c'è il tuo ID nel database");
                m.start(new Stage());
            }else {
                //Chiude la finestra
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                VisualizzaRistoranteController visualizzaRistorantiController = VisualizzaRistoranteController.getInstanza();
                visualizzaRistorantiController.mostra();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
