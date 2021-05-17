package View;

import Controller.ConnessioneServerController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnessioneView extends Application {

    @FXML
    public javafx.scene.control.Label text;
    public TextField TextFieldIdRistorante;


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
    public void AccediPremuto(ActionEvent actionEvent) throws IOException {
        try {

            String idRistorante = TextFieldIdRistorante.getText();
            //Chiamo il metodo per vedere se l'id è presente
            //Il server andrà a dare o true o false in caso di presenza del'id
            //Se non c'è, messaggio di errore, altrimenti va avanti

            //Chiude la finestra
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();



        }  catch (Exception e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("EchoMultiServer: closing...");

    }
}

