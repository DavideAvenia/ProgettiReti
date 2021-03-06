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
        stage.setScene(new Scene(root, 305, 185));
        stage.show();
    }

    /*
    La funzione viene attivata quando il Rider preme sul bottone 'conferma'.
    Viene aperta una finestra dove viene visualizzato l'ordine da conseggnare
    e viene chiusa la socket.
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
    Viene fatto visualizzare il messaggio di consegna rifiutata e infine
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
