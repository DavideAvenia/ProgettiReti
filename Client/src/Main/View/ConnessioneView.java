package View;

import Controller.ConnessioneController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ConnessioneView extends Application {

    @FXML
    private TextField textFieldIdCliente;

    @FXML
    private Button apriConnessione;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Connessione.fxml"));
        primaryStage.setTitle("Connessione");
        primaryStage.setScene(new Scene(root, 300, 190));
        primaryStage.show();
    }

    public void apriConnessione(ActionEvent event) throws IOException {
        //boolean islogged;
        ConnessioneController connessioneController = ConnessioneController.getInstanza();
    }
}
