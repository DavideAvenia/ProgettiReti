package Controller;

import View.VisualizzaRistorantiView;
import javafx.stage.Stage;

public class VisualizzaRistorantiController {
    private static VisualizzaRistorantiController visualizzaRistorantiControllerInstanza = null;

    private VisualizzaRistorantiController(){
    }

    public static VisualizzaRistorantiController getInstanza() {
        if(visualizzaRistorantiControllerInstanza == null){
            visualizzaRistorantiControllerInstanza = new VisualizzaRistorantiController();
        }
        return visualizzaRistorantiControllerInstanza;
    }

    public void mostra() throws Exception {
        VisualizzaRistorantiView visualizzaRistorantiView = new VisualizzaRistorantiView();
        visualizzaRistorantiView.start(new Stage());
    }
}
