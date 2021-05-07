package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Ristorante implements Serializable {
    private String nome;
    private ArrayList<String> menu;

    public Ristorante(String nome, ArrayList<String> menu){
        this.nome = nome;
        this.menu = menu;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setMenu(ArrayList<String> menu){
        this.menu = menu;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getBevande() {
        return menu;
    }

}
