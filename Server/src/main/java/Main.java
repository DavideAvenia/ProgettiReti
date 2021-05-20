import Handlers.ServerHandler;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(">Aperta la porta 30000 \n");
        ServerHandler serverHandlerCliente = new ServerHandler(30000);

        System.out.println(">Aperta la porta 31000 \n");
        ServerHandler serverHandlerRistorante = new ServerHandler(31000);

        System.out.println(">Server: inizializzato con porta 30000 \n");
        serverHandlerCliente.startServer();

        System.out.println(">Server: inizializzato con porta 31000 \n");
        serverHandlerRistorante.startServer();
    }
}

