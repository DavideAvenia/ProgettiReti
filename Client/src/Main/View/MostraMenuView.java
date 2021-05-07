package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MostraMenuView extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRistorante.fxml"));
        primaryStage.setTitle("Mostra Menu del ristorante");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void procediOrdine(ActionEvent actionEvent) {
    }
}
