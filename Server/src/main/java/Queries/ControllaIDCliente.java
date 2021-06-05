package Queries;

import Model.Cliente;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
Questa classe si occupa di effettuare la query per verificare
la presenza del cliente nella base di dati.
Come parametro viene passato l'id del cliente che viene utilizzato per
scegliere nella query il cliente con quell'id.
Se non c'è corrispondenza, viene fatto ritornare un valore null.
Se c'è corrispondenza vengono presi i campi del cliente trovato
per creare un nuovo oggetto di tipo Cliente che viene fatto ritornare.
 */
public class ControllaIDCliente implements Serializable {
    public Cliente controllaIDQuery(String id) throws SQLException {
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
            Cliente c = new Cliente(idc, nome, cognome);
            return c;
        }
    }
}