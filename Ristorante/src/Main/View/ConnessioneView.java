package View;

import Controller.ConnessioneController;
import Controller.VisualizzaRiderController;
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

/*
Questa classe si occupa di gestire l'interfaccia grafica di login dell'utente.
L'utente inserisce l'id e poi preme il pulsante 'Accedi'.
 */

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

    /*
    La funzione viene invocata quando l'utente preme sul bottone accedi.
    Come prima cosa viene memorizzato l'id inserito dall'utente e viene stabilita la
    connessione con il server richiamando il 'getInstanza' della classe 'ConnessioneController'.
    Nel costrutto if viene chiama la funzione 'controllaIdristorante' della stessa classe,
    che si occupa di controllare la presenza dell'id passato come parametro se sia presente
    nella base di dati. Se non è presente viene visualizzato a schermo un messaggio di errore.
    Altrimenti la finestra di login viene chiusa e viene aperta quella che fa visualizzare
    i rider che sono connessi in quel momento.
     */
    public void AccediPremuto(ActionEvent actionEvent) throws Exception {

        String idRistorante = TextFieldIdRistorante.getText();
        ConnessioneController connessioneController = ConnessioneController.getInstanza();
        if(!connessioneController.controllaIdRistorante(idRistorante)){
            Messaggio m = new Messaggio("Errore", "Non c'è il tuo ID nel database");
            m.start(new Stage());
        }else{
            //Chiude la finestra e ne crea una nuova
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            VisualizzaRiderController visualizzarider = VisualizzaRiderController.getInstanza();
            visualizzarider.mostra();
        }
    }
}

