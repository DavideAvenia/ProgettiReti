package Handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
Questa classe serve a smistare le connessioni con i ristoranti
e le connessioni con i client in base alla porta che viene passata
nella firma del costruttore.
 */
public class ServerHandler extends Thread
{
    private int i = 0;
    private ServerSocket serverSocket;
    private int port;

    public ServerHandler(int port) {
        this.port = port;
    }

    /*
    Viene avviata una serversocket sulla porta specificata nella firma.
     */
    public void startServer(){
        try {
            serverSocket = new ServerSocket(port);
            this.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    La funzione è chiusa in un loop infinito.
    In base alla porta viene invocata la creazione di un oggetto diverso.
    Se la porta è 30000 allora è un client che si vuole collegare al server, quindi
    viene creato un oggetto di tipo 'ClientHandler'.
    Se la porta è 31000 allora è un ristorante che si vuole collegare al server,
    quindi viene creato un oggetto di tipo 'RistoHandler'.
    Se la porta non corrisponde ad una di queste due viene chiusa la socket.
     */
    @Override
    public void run(){
        while(true) {
            try {
                i++;
                System.out.println( "In attesa di una connesione: " + i);
//                Come prima cosa accetta una connessione e la salva nella variabile 'socket'.
                        Socket socket = serverSocket.accept();
                switch (port){
                    case 30000:
                        System.out.println("Connessione accettata da "+ socket);
                        new ClientHandler(socket);
                    break;
                    case 31000:
                        System.out.println("Connessione accettata da "+ socket);
                        new RistoHandler(socket);
                    break;
                    default:
                        System.out.println("Non ci sono porte utili");
                        socket.close();
                    break;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}