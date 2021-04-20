package Controller;

import View.VisualizzaRiderView;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnessioneController extends Thread{
    private int port = 30000;
    private Socket socket;
    private ServerSocket serversocket;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;

    private static ConnessioneController instanza = null;

    public ConnessioneController(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        start();
        System.out.println("ServerThread "+id+": started");
    }

   /* private ConnessioneController() throws IOException {
        serversocket = new ServerSocket(port);

    }

    public static ConnessioneController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConnessioneController();
        }
        return instanza;
    }
*/
   public void run() {
       try {
           while (true) {
               String str = in.readLine();
               if (str.equals("END")) break;
               System.out.println("ServerThread "+id+": echoing -> " + str);
               out.println(str);

               //INVIO RICHIESTA AL RIDER
               if(socket.isConnected()){
                   out.write(1);
                   out.flush();
                   System.out.println("richiesta inviata");

                   System.out.println("attendo risposta di conferma");
                   // leggo solo il primo che accetta e bast
                   // se leggo che ha rifiutato continuo a leggere
                   int stringa = in.read();
                   while(stringa==0){
                       stringa = in.read();
                   }

                   // nel controllo se ha accettato o meno è meglio metterci
                   // id del rider (perchè il rider confermerà inviando il suo id)

                   System.out.println("rider: " + stringa);
                   //Se il rider conferma l'ordine il ristorante invia
                   //l'id dell'ordine corrispondente
                   System.out.println("ordine confermato");
                   out.write("1234\n");
                   out.flush();
                   System.out.println("id ordine inviato");
               } else {
                   System.out.println("connection problem");
               }
           }
           System.out.println("ServerThread "+id+": closing...");
       } catch (IOException e) {}

       try {
           socket.close();
       } catch(IOException e) {}
   }

   /*
    public boolean accettaConnessioni() {

        try {
            s = serversocket.accept();
            System.out.println("connesso");
            //idRider.add(id);
            return true;
        }catch (IOException err){
            System.err.println("errore");
            return false;
        }
    }

    public List<String> getRider(){
        return idRider;
    }

    public void inviaRichiesta() throws IOException{
        //for ( String r:idRider) {
        //rimozione rider dalla lista quando non sono più collegati

                if(s.isConnected()){
                    OutputStream os = s.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(1);
                    bw.flush();
                    System.out.println("richiesta inviata");

                } else {
                    client.close();
                    //idRider.remove(r);
                }

        //}

    }

    public void inviaIDOrdine() throws IOException{
        System.out.println("attendo risposta di conferma");
     //   String r = idRider.pollFirst();
     //   client = new Socket(r, port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int stringa = br.read();

        System.out.println(stringa);
        if(stringa==1){
            //riderOccupati.add(r);
           // VisualizzaRiderView visualizzaRider = new VisualizzaRiderView();
           // visualizzaRider.cambiaLabel("arrivato");
            System.out.println("ordine confermato");

        } else if (stringa==0){
            //idRider.add(r);
            System.out.println("ordine annullato");
        }
    }
*/

}
