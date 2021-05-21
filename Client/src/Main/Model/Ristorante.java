package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Ristorante implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String idRistorante;
    private String nome;
    private ArrayList<String> menu;

    public Ristorante (){

    }

    public Ristorante(String idRistorante, String nome, ArrayList<String> menu){
        this.idRistorante = idRistorante;
        this.nome = nome;
        this.menu = menu;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setMenu(ArrayList<String> menu){
        this.menu = menu;
    }

    public String getIdRistorante(){return idRistorante;}

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

}
