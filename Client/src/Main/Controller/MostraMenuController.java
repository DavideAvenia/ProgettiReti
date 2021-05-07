package Controller;

import View.VisualizzaRistoranteView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class MostraMenuController {

    private Socket socket = ConnessioneController.getInstanza().getSocket();

    public static MostraMenuController mostraMenuController = null;

    private MostraMenuController() throws IOException {
    }

    public static MostraMenuController getInstanza() throws IOException {
        if(mostraMenuController == null){
            mostraMenuController = new MostraMenuController();
        }
        return mostraMenuController;
    }

    public void mostra() throws Exception {
        VisualizzaRistoranteView visualizzaRistoranteView = new VisualizzaRistoranteView();
        visualizzaRistoranteView.start(new Stage());
    }
}
