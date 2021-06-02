package Queries;

import Model.Rider;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllaID implements Serializable {
    public Rider controllaIDQuery(String id) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();
        String query = "SELECT * FROM `rider` WHERE `IDRider` =  \"" + id + "\" ";

        ResultSet rs = dbconn.eseguiQuery(query);
        if (rs.next() == false) {
            System.out.println("Non ci sono id compatibili");
            return null;
        } else {
            //Prende la prima riga e crea il cliente
            String idc = rs.getString("IDRider");
            String nome = rs.getString("NomeRider");
            String cognome = rs.getString("CognomeRider");
            Rider c = new Rider(idc, nome, cognome);
            return c;
        }
    }
}