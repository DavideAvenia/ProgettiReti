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


    public void ConfermaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            System.out.println("conferma premuto");
            Messaggio m = new Messaggio("Consegna",confermaOrdine.conferma());
            m.start(new Stage());
            //Qua dovrebbe chiudere la connessione
            ConnessioneController.getInstanza().chiudiSocket();
            this.stop();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AnnullaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            confermaOrdine.annulla();
            System.out.println("annulla premuto");
            Messaggio m = new Messaggio("Consegna","Consegna rifiutata");
            m.start(new Stage());
            //Qua dovrebbe chiudere la connessione
            ConnessioneController.getInstanza().chiudiSocket();
            this.stop();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConfermaOrdineController confermaOrdineController = ConfermaOrdineController.getInstanza();
            //Qua si dovrebbe bloccare aspettando un ordine
            ordineDaConfermare = confermaOrdineController.checkRichieste();

            //Mostra un messaggio con l'ordine
            Messaggio m = new Messaggio("Ordine da effettuare", ordineDaConfermare.getProdotti() + "\n" +
                    ordineDaConfermare.getRistorante());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
