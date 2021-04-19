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
    private static int counter = 0;
    private int id = counter++;
    private static int threadcount = 0;

    public static int threadCount() {
        return threadcount;
    }

    /*private static ConnessioneController instanza = null;

    private ConnessioneController() throws IOException {
        Socket socket = new Socket(ip, port);
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }*/

    public ConnessioneController(InetAddress addr) throws IOException {
        threadcount++;
    try{
        socket = new Socket(addr, 30000);
        System.out.println("EchoClient n° "+id+": started");
        System.out.println("Client Socket: "+ socket);
    } catch(IOException e) {}
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);
            start();
        } catch(IOException e1) {
            // in seguito ad ogni fallimento la socket deve essere chiusa, altrimenti
            // verrà chiusa dal metodo run() del threadtry {
            try{
                socket.close();
            }catch(IOException e2){}
        }
    }

    public void run() {
        try {
            for(int i =0;i <10; i++) {
                out.println("client "+id +" msg "+i);
                System.out.println("Msg sent: client "+id+" msg"+i);
                String str = in.readLine();
                System.out.println("Echo: "+str);
            }      out.println("END");
        } catch(IOException e) {}
        try {
            System.out.println("Client "+id+" closing...");socket.close();
        } catch(IOException e) {}
        threadcount--;
    }

    public boolean inviaIdCliente(String id){
        try {
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println(id);
            pr.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
