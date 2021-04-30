import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ConnessioneServer extends Thread{
    private int port = 30000;
    private Socket socket;
    private ServerSocket ss = null;

    private BufferedReader in;
    private PrintWriter out;

    public ConnessioneServer(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
        System.out.println("Server: started");
    }

    public void run(){
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            System.out.println("CRASHO QUAAAAAAAAAAAA 1 SERVER");
            String str = bf.readLine();
            ControllaID check = new ControllaID();
            Cliente c = check.controllaIDQuery(str);

            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if(c == null) {
                //Manda l'oggetto cliente null
                oos.writeObject(null);
                System.out.println("Non ci sono clienti con questo ID");
            }else{
                //Manda l'oggetto cliente creato
                oos.writeObject(c);
                System.out.println("Inviato");
                //Mandagli i ristoranti attivi
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

