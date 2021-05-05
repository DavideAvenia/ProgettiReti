import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaRistoranti {
    public ArrayList<Ristorante> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        ArrayList<Ristorante> lista = new ArrayList<Ristorante>();
        String query = "SELECT * FROM `ristorante`";

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            String nome = rs.getString("NomeRistorante");
            //Dovrò vedere di estrapolare i menu
            //Magari faccio più query e poi uso i setter
            lista.add(new Ristorante(nome, null, null));
        }

        System.out.println(lista);
        return lista;
    }
}
