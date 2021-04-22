import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaRistoranti {
    public List<String> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        List<String> L1 = new ArrayList<String>();
        String query = "SELECT * FROM `ristorante`";

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            String nome = rs.getString("NomeRistorante");
            L1.add(nome);
        }

        System.out.println(L1);
        return L1;
    }
}
