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


public class LoginView extends Application {

    private boolean ordineConsegnato  = false;
    @FXML
    private TextField TextfieldAccessoRider;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LoginView.fxml"));
        primaryStage.setTitle("LOGIN rider");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public void AccediPremuto(ActionEvent actionEvent) {
        try {
            ConnessioneController connessioneController = new ConnessioneController();
            String idRider = TextfieldAccessoRider.getText();

            //Chiamo il metodo per vedere se l'id è presente
            //Il server andrà a dare o true o false in caso di presenza del'id
            //Se non c'è, messaggio di errore, altrimenti va avanti

            if(!connessioneController.inviaIdCliente(idRider)){
                //Finestra di errore
                Messaggio m = new Messaggio("Errore","Non c'è il tuo ID nel database");
                m.start(new Stage());
            }else {
                //Chiude la finestra
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Socket s = connessioneController.getSocket();
                if(connessioneController.checkRichiesta()) {
                    System.out.println("accetti l'ordine?");
                    ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza(s, idRider);
                    confermaOrdine.mostra();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}