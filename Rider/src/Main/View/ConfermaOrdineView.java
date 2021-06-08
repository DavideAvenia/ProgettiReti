package View;

import Controller.ConfermaOrdineController;
import Controller.ConnessioneController;
import Model.Ordine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/*
Questa classe si occupa di gestire l'interfaccia grafica della visualizzazione
della finestra dove il rider può confermare o annullare la consegna di un ordine.
 */
public class ConfermaOrdineView extends Application {

    @FXML
    private Button BottoneConferma;
    @FXML
    private Button BottoneAnnulla;
    @FXML
    private Label Text;

    private Ordine ordineDaConfermare;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConfermaOrdine.fxml"));
        stage.setTitle("Conferma ordine");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
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
}
