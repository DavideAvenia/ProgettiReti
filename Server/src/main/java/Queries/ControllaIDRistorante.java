package Queries;

import Model.Ristorante;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllaIDRistorante implements Serializable {
    public Ristorante controllaIDQuery(String id) throws SQLException {
        DatabaseConnection dbconn = new DatabaseConnection();

        String queryRistorante = "SELECT * FROM `ristorante` WHERE `IDRistorante` =  \"" + id + "\" ";

        ResultSet rsRistorante = dbconn.eseguiQuery(queryRistorante);
        if (rsRistorante.next() != false) {
            //Prende la prima riga e crea il ristorante
            String idc = rsRistorante.getString("IDRistorante");
            String nome = rsRistorante.getString("NomeRistorante");
            Ristorante r = new Ristorante(idc, nome, null);

            ArrayList<String> listaMenu = new ArrayList<>();
            String queryMenu = "Select NomeRistorante, NomeOggetto\n" +
                    "       From ristorante inner join gestione inner join oggetto\n" +
                    "       Where ristorante.IDRistorante = gestione.IDRistorante AND gestione.IDOggetto = oggetto.IDOggetto\n" +
                    "       AND NomeRistorante = \"" + r.getIdRistorante() + "\"" +
                    "       ORDER BY NomeRistorante;";

            ResultSet rsMenu = dbconn.eseguiQuery(queryMenu);

            while(rsMenu.next()){
                listaMenu.add(rsMenu.getString("NomeOggetto"));
            }

            r.setMenu(listaMenu);
            return r;
        } else {
            System.out.println("Non ci sono id compatibili");
            return null;
        }
    }
}