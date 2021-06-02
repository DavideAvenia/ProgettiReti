package Queries;

import java.sql.*;

public class DatabaseConnection {

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

    public ResultSet eseguiQuery(String query) throws SQLException{
        ResultSet resultSet = null;
        resultSet = stmt.executeQuery(query);
        return resultSet;
    }

    public int updateEntries(String query) throws SQLException{
        return stmt.executeUpdate(query);
    }
}
