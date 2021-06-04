package View;

import Controller.ConfermaOrdineController;
import Controller.ConnessioneController;
import Model.Ordine;
import com.sun.javafx.scene.shape.MeshHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
Questa classe ha il compito di gestire e supportare l'interfaccia
grafica di conferma dell'ordine.
L'utente rider ha la possibilità di confermare o annullare l'ordine
premendo sul relativo bottone.
La variabile 'ordineDaConfermare' serve per memorizzare l'ordine che
è stato ricevuto dal ristorante.
 */
public class ConfermaOrdineView extends Application implements Initializable {

    @FXML
    private Button BottoneConferma;
    @FXML
    private Button BottoneAnnulla;
    @FXML
    private Label Text;

    private Ordine ordineDaConfermare;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConfermaOrdine.fxml"));
        primaryStage.setTitle("Conferma ordine");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    /*
    La funzione viene attivata quando il Rider preme sul bottone 'conferma'.
    Viene creato un nuovo oggetto di tipo 'ConfermaOrdineController' attraverso
    la funzione 'getInstanza'. Poi viene creato un nuovo oggetto di tipo 'Messaggio'
    dove negli attributi viene chiamata la funzione 'conferma' della classe
    'confermaOrdine'. Viene fatto visualizzare il messaggio e infine viene chiusa la
    socket.
     */
    public void ConfermaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            System.out.println("conferma premuto");
            Messaggio m = new Messaggio("Consegna",confermaOrdine.conferma());
            m.start(new Stage());
            ConnessioneController.getInstanza().chiudiSocket();
            this.stop();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    la funzione viene attivata quando l'utente Rider preme sul bottone 'annulla'.
    Viene creato un nuovo oggetto di tipo 'ConfermaOrdineController' attraverso la
    funzione 'getInstanza' e viene chiamata la funzione 'annulla' della stessa
    classe. Viene fatto visualizzare e il messaggio di consegna rifiutata e infine
    viene chiusa la socket.
     */
    public void AnnullaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            confermaOrdine.annulla();
            System.out.println("annulla premuto");
            Messaggio m = new Messaggio("Consegna","Consegna rifiutata");
            m.start(new Stage());
            ConnessioneController.getInstanza().chiudiSocket();
            this.stop();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    La funzione inizializza la finestra che viene fatta visualizzare all'utente Rider.
    Viene richiamata la funzione 'checkRichieste' della
    stessa classe per attendere l'arrivo di un ordine inviato dal ristorante.
    L'ordine viene salvato nella variabile 'ordineDaConfermare'.
    Infine viene mostrato un messaggio con l'ordine ricevuto.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConfermaOrdineController confermaOrdineController = ConfermaOrdineController.getInstanza();
            ordineDaConfermare = confermaOrdineController.checkRichieste();

            Messaggio m = new Messaggio("Ordine da effettuare", ordineDaConfermare.getProdotti() + "\n" +
                    ordineDaConfermare.getRistorante());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
