import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /*public static void main(String args[]) {
        try{
            ConnessioneServer connessioneServer = ConnessioneServer.getInstanza();

            VisualizzaRistoranti vs = new VisualizzaRistoranti();
            List<String> listaprova = new ArrayList<String>();
            listaprova = vs.VisualizzaRistorantiQuery();
        }catch(IOException e){
            System.out.println(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(30000);
        System.out.println("EchoMultiServer: started");
        System.out.println("Server Socket: " + serverSocket);
        try {
            while(true) {// bloccante finchè non avviene una connessione:
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection accepted: "+ clientSocket);try {
                new ConnessioneServer(clientSocket);
            } catch(IOException e) {
                clientSocket.close();
            }
        }
        }catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }    System.out.println("EchoMultiServer: closing...");
        serverSocket.close();
    }
}

