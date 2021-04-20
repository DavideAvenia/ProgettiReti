package Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController extends Thread {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress addr = InetAddress.getByName("localhost");

    public ConnessioneController() throws IOException {
    try{
        socket = new Socket(this.addr, 30000);
        System.out.println("Client Socket: "+ socket); //Qui dovrebbe stabilire la connessione
    } catch(IOException e) {}
        try {
            //Sta roba deve scomparire forse

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);

            start();
        } catch(IOException e1) {
            // in seguito ad ogni fallimento la socket deve essere chiusa, altrimenti
            // verrà chiusa dal metodo run() del thread
            try{
                socket.close();
            }catch(IOException e2){}
        }
    }

    public void run() {
        //Qui il client deve chiedere cose al server
        /*try {
            for(int i =0;i <10; i++) {
                out.println("client "+id +" msg "+i);
                System.out.println("Msg sent: client "+id+" msg"+i);
                String str = in.readLine();
                System.out.println("Echo: "+str);
            }      out.println("END");
        } catch(IOException e) {}
        try {
            System.out.println("Client "+id+" closing...");
            socket.close();
        } catch(IOException e) {}
        threadcount--;*/
    }

    static final int MAX_THREADS = 10;
    public void setConnessione() throws IOException,InterruptedException {
        //Qui deve avvenire la connessione tramite
        /*if (args.length == 0) addr = InetAddress.getByName(null);
        else addr = InetAddress.getByName(args[0]);
        while(true) {
            if (ConnessioneController.threadCount() < MAX_THREADS)
                new ConnessioneController(addr);*/
        }
    }
}