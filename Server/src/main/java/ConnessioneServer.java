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
            String str = bf.readLine();
            ControllaID check = new ControllaID();
            Cliente c = check.controllaIDQuery(str);

            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            if(c == null) {
                pr.println("Non ci sono clienti con questo ID");
            }else{
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //Manda l'oggetto cliente creato
                oos.writeObject(c);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

