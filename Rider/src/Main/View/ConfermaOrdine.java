package View;

import Controller.ConfermaOrdineController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ConfermaOrdine extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConfermaOrdine.fxml"));
        primaryStage.setTitle("Conferma ordine");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public void ConfermaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            confermaOrdine.conferma();
            System.out.println("conferma premuto");
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AnnullaPremuto(ActionEvent actionEvent) {
        try {
            ConfermaOrdineController confermaOrdine = ConfermaOrdineController.getInstanza();
            confermaOrdine.annulla();
            System.out.println("annulla premuto");
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}