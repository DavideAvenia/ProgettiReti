package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ristorante implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String nome;
    private List<String> menu;

    public Ristorante (){

    }

    public Ristorante(String nome, ArrayList<String> menu){
        this.nome = nome;
        this.menu = menu;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setMenu(List<String> menu){
        this.menu = menu;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getMenu() {
        return menu;
    }

}
