import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllaID {
    public boolean controllaIDQuery(String id) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        String query = "SELECT * FROM `cliente` WHERE `IDCliente` =  ";

        ResultSet rs = dbconn.eseguiQuery(query);
        rs.wasNull()

        while(rs.next()){
            String nome = rs.getString("IDCliente");
            L1.add(nome);
        }

        System.out.println(L1);
        return L1;
    }
}