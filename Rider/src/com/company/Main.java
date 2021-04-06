package com.company;

import java.net.*;
import java.io.*;

public class Main {


    public static void main(String[] args) {
        Socket s = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try
        {
            s = new Socket("localhost", 1111);
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            br = new BufferedReader(isr);
            OutputStreamWriter osr = new OutputStreamWriter(s.getOutputStream());
            bw = new BufferedWriter(osr);
        }
        catch (IOException e)
        {
            System.err.println("errore");
        }
        System.out.println("Socket creata: " + s);

        try
        {
            String line;
            while( (line=br.readLine())!=null )
            {
                System.out.println("Ricevuto dal ristorante: " + line);
                bw.write("conferma richiesta da parte di: " + s.getPort());
                bw.flush();
            }

        }
        catch (IOException e)
        {
            System.err.println("errore");
        }


    }
}



