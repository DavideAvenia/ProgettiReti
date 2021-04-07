package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class VisualizzaRistoranteView extends Application {

    @FXML
    private Label labelRistoranteSelezionato;

    @FXML
    private ListView listaRistorante;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRistorante.fxml"));
        primaryStage.setTitle("Visualizza i ristoranti");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }
}
