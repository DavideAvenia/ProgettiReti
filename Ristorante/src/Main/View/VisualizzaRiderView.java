package View;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VisualizzaRiderView extends Application {

    @FXML
    private Label label = new Label("attendo");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("LOGIN ristorante");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public void cambiaLabel(String s){
        label.setText(s);
    }

}
