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
import javafx.stage.Stage;

import java.io.IOException;

public class ConnessioneView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConnessioneView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }


    public void AccediPremuto(ActionEvent actionEvent) {
        try {
            ConnessioneController connessioneController = ConnessioneController.getInstanza();

            //String id = textFieldIdCliente.getText();
            //Chiamo il metodo per vedere se l'id è presente
            //Il server andrà a dare o true o false in caso di presenza del'id
            //Se non c'è, messaggio di errore, altrimenti va avanti

            //Chiude la finestra
            /*Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();*/

            connessioneController.accettaConnessioni();
            connessioneController.inviaRichiesta();
            connessioneController.inviaIDOrdine();

            /*VisualizzaRiderController visualizzaRistorantiController = VisualizzaRiderController.getInstanza();
            visualizzaRistorantiController.mostra();*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
