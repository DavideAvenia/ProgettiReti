package View;

import Controller.ConfermaOrdineController;
import Controller.ConnessioneController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/*
Questa classe si occupa di gestire l'interfaccia grafica di login dell'utente.
L'utente inserisce l'id e poi preme il pulsante 'Accedi'.
 */
public class LoginView extends Application {

    @FXML
    private TextField TextfieldAccessoRider;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LoginView.fxml"));
        primaryStage.setTitle("LOGIN rider");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /*
    La funzione viene invocata quando l'utente Rider preme sul bottone 'Accedi'.
    Viene memorizzato l'id immesso dall'utente e passato alla funzione 'inviaRider'
    della classe 'ConnessioneController' per il controllo nella base di dati.
    Se non c'è una corrispondenza viene fatto visualizzare all'utente un messaggio
    di errore e l'utente dovrà effetturare nuovamente l'accesso.
    Se invece c'è corrispondenza nella base di dati viene chiusa la finestra di
    login e viene aperta quella per confermare l'ordine.
     */
    public void AccediPremuto(ActionEvent actionEvent) {
        System.out.println("bottone Accedi premuto");
        try {
            ConnessioneController connessioneController = ConnessioneController.getInstanza();
            String idRider = TextfieldAccessoRider.getText();

            System.out.println("vado a controllare se l'id del rider esiste nella base di dati");
            if(!connessioneController.inviaIdRider(idRider)){
                Messaggio m = new Messaggio("Errore","Non c'è il tuo ID nel database");
                m.start(new Stage());
            }else {
                System.out.println("id controllato");
                System.out.println("chiusura della finestra di login");
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
                System.out.println("vado a mostrare la finestra di conferma ordine");
                confermaOrdine.mostra();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}