import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        try{
            ConnessioneServer connessioneServer = ConnessioneServer.getInstanza();

            VisualizzaRistoranti vs = new VisualizzaRistoranti();
            List<String> listaprova = new ArrayList<String>();
            listaprova = vs.VisualizzaRistorantiQuery();
            //connessioneServer.accettaConnessioni();
        }catch(IOException e){
            System.out.println(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
