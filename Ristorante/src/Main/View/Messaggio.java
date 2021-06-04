package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/*
Questa classe si occupa di supportare e gestire l'interfaccia grafica
di una finestra di messaggio.
Attraverso il costruttore è possibile impostare il messaggio da far
visualizzare all'utente.
 */
public class Messaggio extends Application implements Initializable {

    private static String title;
    private static String messaggio;

    @FXML
    public Label messaggioLabel;


    public Messaggio(String title, String messaggio) {
        this.title = title;
        this.messaggio = messaggio;
    }

    public Messaggio() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Messaggio.fxml"));
        stage.setTitle(title);
        stage.setScene(new Scene(root, 348, 188));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioLabel.setText(messaggio);
    }


    /*
    La funzione viene attivata quando l'utente preme sul bottone.
    Quando questo avviene la finestra viene chiusa.
     */
    public void bottoneChiudiPremuto(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
