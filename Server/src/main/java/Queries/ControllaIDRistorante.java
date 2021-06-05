package Queries;

import Model.Ristorante;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
Questa classe si occupa di effetturare la query per verificare la presenza
del ristorante, di cui si specifica l'id nella firma della funzione.
Se c'è corrispondenza viene creato un nuovo elemento di tipo Ristorante 'r'
con i campi dell'elemento trovato nella base di dati.
Viene effettuata un'altra query, per prendere le informazioni relative
al menu del ristorante. Il menu viene salvato in un ArrayList e viene
settato nel ristorante 'r' nel campo 'menu'. Viene fatto ritornare il
ristorante 'r'.
Se non c'è corrispondenza ritorna null.
 */
public class ControllaIDRistorante implements Serializable {
    public Ristorante controllaIDQuery(String id) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();

        String queryRistorante = "SELECT * FROM `ristorante` WHERE `IDRistorante` =  \"" + id + "\" ";

        ResultSet rsRistorante = dbconn.eseguiQuery(queryRistorante);
        if (rsRistorante.next() != false) {
            //Prende la prima riga e crea il ristorante
            String idc = rsRistorante.getString("IDRistorante");
            String nome = rsRistorante.getString("NomeRistorante");
            Ristorante r = new Ristorante(idc, nome, null);

            ArrayList<String> listaMenu = new ArrayList<>();
            String queryMenu = "Select NomeRistorante, NomeOggetto\n" +
                    "       From ristorante inner join gestione inner join oggetto\n" +
                    "       Where ristorante.IDRistorante = gestione.IDRistorante AND gestione.IDOggetto = oggetto.IDOggetto\n" +
                    "       AND NomeRistorante = \"" + r.getIdRistorante() + "\"" +
                    "       ORDER BY NomeRistorante;";

            ResultSet rsMenu = dbconn.eseguiQuery(queryMenu);

            while(rsMenu.next()){
                listaMenu.add(rsMenu.getString("NomeOggetto"));
            }

            r.setMenu(listaMenu);
            return r;
        } else {
            System.out.println("Non ci sono id compatibili");
            return null;
        }
    }
}