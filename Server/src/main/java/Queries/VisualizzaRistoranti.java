package Queries;

import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
Questa classe si occupa di effettuare la query per prendere i ristoranti
presenti nella base di dati, e che quindi sono disponibili per effettuare un ordine.
La funzione ritorna un ArrayList di Ristornati.
 */
public class VisualizzaRistoranti {
    public ArrayList<Ristorante> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        ArrayList<Ristorante> listaRistoranti = new ArrayList<>();

        String queryRistoranti = "SELECT * FROM `ristorante`";

        ResultSet rsRistoranti = dbconn.eseguiQuery(queryRistoranti);

        while(rsRistoranti.next()){
            String id = rsRistoranti.getString("IDRistorante");
            String nome = rsRistoranti.getString("NomeRistorante");
            listaRistoranti.add(new Ristorante(id,nome,null));
        }

        System.out.println(listaRistoranti);
        return listaRistoranti;
    }
}
