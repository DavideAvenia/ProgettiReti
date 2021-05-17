package Main;

import Controller.ConnessioneServerController;
import View.ConnessioneView;
import javafx.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Application.launch(ConnessioneView.class, args);
        try {
            ServerSocket serverSocket = new ServerSocket(32000);
            System.out.println("EchoMultiServer: started");
            System.out.println("Server Socket: " + serverSocket);

            while (true) {
                // bloccante finchè non avviene una connessione:
                Socket clientSocket = serverSocket.accept();

                System.out.println("Connection accepted: " + clientSocket);
                try {
                    new ConnessioneServerController(clientSocket);

                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Accept failed");
            System.exit(1);
        }

    }
}
