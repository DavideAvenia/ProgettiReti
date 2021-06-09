package Queries;

import java.sql.*;

/*
URL è per la connessione al database
il tipo di connessione è di tcp
user e pass servono per eseguire la connessione (username e password)
getConnection effettua la connessione
createStatement istanzia la query che poi viene eseguita quando
si chiama execute che sta dentro in eseguiQuery
statement è un intermediario con il database, serve sopratutto
per quando si vuole passare da un linguaggio ad un altro
 */
public class DatabaseConnection {

    //private final String URL = "jdbc:mysql://8.tcp.ngrok.io:14466/progettoreti";
    private final String URL = "jdbc:mysql://localhost:3306/progettoreti";
    private final String USER = "root";
    private final String PASS = "";
    private Statement stmt;

    public DatabaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    RusultSet è un insieme che contiene il risultato della query
     */
    public ResultSet eseguiQuery(String query) throws SQLException{
        ResultSet resultSet = null;
        resultSet = stmt.executeQuery(query);
        return resultSet;
    }

    public int updateEntries(String query) throws SQLException{
        return stmt.executeUpdate(query);
    }
}
