import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String args[]) {
        DatabaseConnection dbconn = new DatabaseConnection();
        try{
            ConnessioneServer connessioneServer = ConnessioneServer.getInstanza();
            connessioneServer.accettaConnessioni();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
