package View;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VisualizzaRiderView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("VisualizzaRider.fxml"));
        primaryStage.setTitle("LOGIN ristorante");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
