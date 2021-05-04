package Controller;

import Model.Rider;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllaID implements Serializable {
    public Rider controllaIDQuery(String id) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        String query = "SELECT * FROM `cliente` WHERE `IDCliente` =  \"" + id + "\" ";

        ResultSet rs = dbconn.eseguiQuery(query);
        if (rs.next() == false) {
            System.out.println("Non ci sono id compatibili");
            //Model.Cliente c = new Model.Cliente("null","null","null");
            return null;
        } else {
            //Prende la prima riga e crea il cliente
            String idc = rs.getString("IDCliente");
            String nome = rs.getString("NomeCliente");
            String cognome = rs.getString("CognomeCliente");
            Rider c = new Rider(idc, nome, cognome);
            return c;
        }
    }
}