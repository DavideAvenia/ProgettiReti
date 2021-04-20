package View;

import Controller.ConnessioneController;
import Controller.VisualizzaRiderController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnessioneView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ConnessioneView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }


    public void AccediPremuto(ActionEvent actionEvent) throws IOException {

        ServerSocket serverSocket = new ServerSocket(30000);
        System.out.println("EchoMultiServer: started");
        System.out.println("Server Socket: " + serverSocket);
        try {

            //String id = textFieldIdCliente.getText();
            //Chiamo il metodo per vedere se l'id è presente
            //Il server andrà a dare o true o false in caso di presenza del'id
            //Se non c'è, messaggio di errore, altrimenti va avanti

            //Chiude la finestra
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            /*VisualizzaRiderController visualizzaRistorantiController = VisualizzaRiderController.getInstanza();
            visualizzaRistorantiController.mostra();*/

            while (true) {// bloccante finchè non avviene una connessione:
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);
                try {
                    new ConnessioneController(clientSocket);
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("EchoMultiServer: closing...");
        serverSocket.close();
    }
}

