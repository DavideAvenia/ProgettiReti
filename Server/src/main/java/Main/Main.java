package Main;

import Handlers.ServerHandler;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Aperta la porta 30000");
        ServerHandler serverHandlerCliente = new ServerHandler(30000);

        System.out.println("Aperta la porta 31000");
        ServerHandler serverHandlerRistorante = new ServerHandler(31000);

        System.out.println("Server: inizializzato con porta 30000");
        serverHandlerCliente.startServer();

        System.out.println("Server: inizializzato con porta 31000");
        serverHandlerRistorante.startServer();
    }
}



