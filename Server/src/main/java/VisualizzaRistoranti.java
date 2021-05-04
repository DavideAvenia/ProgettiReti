import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaRistoranti {
    public List<Ristorante> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        List<Ristorante> lista = new ArrayList<>();
        String query = "SELECT * FROM `ristorante`";

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            String nome = rs.getString("NomeRistorante");
            lista.add(new Ristorante(nome, null, null));
        }

        System.out.println(lista);
        return lista;
    }
}
