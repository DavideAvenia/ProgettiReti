package Queries;

import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
