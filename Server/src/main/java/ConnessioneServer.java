import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnessioneServer extends Thread {
    private int port = 30000;
    private Socket socket;
    private ServerSocket ss = null;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;

    private static ConnessioneServer instanza = null;

    public ConnessioneServer(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
        System.out.println("ServerThread "+id+": started");
    }

    /*private ConnessioneServer(){
    }*/

    /*public static ConnessioneServer getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneServer();
        }
        return instanza;
    }*/

    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("END")) break;
                System.out.println("ServerThread "+id+": echoing -> " + str);
                out.println(str);
            }
            System.out.println("ServerThread "+id+": closing...");
        } catch (IOException e) {}

        try {
            socket.close();
        } catch(IOException e) {}
    }
}

