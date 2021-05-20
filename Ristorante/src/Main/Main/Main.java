package Main;

import Controller.RiderHandler;
import Controller.ServerHandler;
import View.ConnessioneView;
import javafx.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerHandler serverHandlerRider = new ServerHandler(32000);
        serverHandlerRider.startServer();
        try {
            Application.launch(ConnessioneView.class, args);
            System.out.println("EchoMultiServer: started");
        } catch (Exception e) {
            System.err.println("Accept failed");
            System.exit(1);
        }

    }
}
