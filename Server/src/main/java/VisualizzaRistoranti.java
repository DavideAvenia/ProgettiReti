import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaRistoranti {
    public ArrayList<Ristorante> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        ArrayList<Ristorante> lista = new ArrayList<>();
        String query = "SELECT * FROM `ristorante`";

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            String nome = rs.getString("NomeRistorante");
            lista.add(new Ristorante(nome, null));
        }

        System.out.println(lista);
        return lista;
    }
}
