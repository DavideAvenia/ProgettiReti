import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MostraMenu {
    public ArrayList<String> MostraMenuQuery(Ristorante r) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        ArrayList<String> lista = new ArrayList<>();
        String query = "Select NomeRistorante, NomeOggetto\n" +
                "       From ristorante inner join gestione inner join oggetto\n" +
                "       Where ristorante.IDRistorante = gestione.IDRistorante AND gestione.IDOggetto = oggetto.IDOggetto\n" +
                "       AND NomeRistorante = \"" + r.getNome() + "\"" +
                "       ORDER BY NomeRistorante;";

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            lista.add(rs.getString());
        }

        System.out.println(lista);
        return lista;
    }
}
