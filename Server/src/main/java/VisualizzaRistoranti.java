import Model.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaRistoranti {
    public ArrayList<Ristorante> VisualizzaRistorantiQuery() throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        ArrayList<Ristorante> lista = new ArrayList<>();
        String query = "SELECT * FROM `ristorante`";

        //prende anche i menu, ma deve essere fatto in due momenti diversi per il menu quindi non va bene inviarli direttamente al cliente
        //Quindi riempi
        //Select NomeRistorante, NomeOggetto
        //From ristorante inner join gestione inner join oggetto
        //Where ristorante.IDRistorante = gestione.IDRistorante AND gestione.IDOggetto = oggetto.IDOggetto
        //ORDER BY NomeRistorante;

        ResultSet rs = dbconn.eseguiQuery(query);

        while(rs.next()){
            String nome = rs.getString("NomeRistorante");
            //Dovrò vedere di estrapolare i menu
            //Magari faccio più query e poi uso i setter
            lista.add(new Ristorante(nome, null));
        }

        System.out.println(lista);
        return lista;
    }
}
