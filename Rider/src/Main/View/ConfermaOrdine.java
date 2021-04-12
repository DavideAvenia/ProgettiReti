package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ConfermaOrdine extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConfermaOrdine.fxml"));
        primaryStage.setTitle("Conferma ordine");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public void ConfermaPremuto(ActionEvent actionEvent) {
        //manda l'identificativo per confermare l'ordine al ristorante
    }
}
