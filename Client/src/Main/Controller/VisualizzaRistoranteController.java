package Controller;

import View.VisualizzaRistoranteView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaRistoranteController {
    private static VisualizzaRistoranteController visualizzaRistoranteControllerInstanza = null;

    private VisualizzaRistoranteController(){
    }

    public static VisualizzaRistoranteController getInstanza() {
        if(visualizzaRistoranteControllerInstanza == null){
            visualizzaRistoranteControllerInstanza = new VisualizzaRistoranteController();
        }
        return visualizzaRistoranteControllerInstanza;
    }

    public void mostra() throws Exception {
        VisualizzaRistoranteView visualizzaRistoranteView = new VisualizzaRistoranteView();
        visualizzaRistoranteView.start(new Stage());
    }

    public List<String> VisualizzaRistoranti(){
        List<String> L1 = new ArrayList<String>();

        return L1;
    }
}
