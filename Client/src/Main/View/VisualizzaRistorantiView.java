package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class VisualizzaRistorantiView extends Application {

    @FXML
    private Label labelRistoranteSelezionato;

    @FXML
    private ListView listaRistoranti;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRistoranti.fxml"));
        primaryStage.setTitle("Visualizza ristoranti");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }
}
