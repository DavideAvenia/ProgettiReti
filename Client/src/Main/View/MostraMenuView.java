package View;

import Controller.MostraMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MostraMenuView extends Application implements Initializable {

    @FXML
    private ListView listaProdotti;

    @FXML
    private Button buttoneProcediOrdine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MostraMenu.fxml"));
        primaryStage.setTitle("Mostra Menu del ristorante");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Salto qua");
            MostraMenuController mostraMenuController = MostraMenuController.getInstanza();
            System.out.println("Salto qua 2");
            List<String> listaMenu = mostraMenuController.caricaMenu();

            listaProdotti.setEditable(false);
            ObservableList<String> nomiProdotti = FXCollections.observableArrayList(listaMenu);
            listaProdotti.setItems(nomiProdotti);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void effettuaOrdine(ActionEvent actionEvent) {
    }
}
