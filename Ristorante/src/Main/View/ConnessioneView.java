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

