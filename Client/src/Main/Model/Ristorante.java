package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Ristorante implements Serializable {
    private String nome;
    private ArrayList<String> menu;
    private String idRistorante;

    public Ristorante(){

    }

    public Ristorante(String idRistorante, String nome, ArrayList<String> menu){
        this.nome = nome;
        this.menu = menu;
        this.idRistorante = idRistorante;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setMenu(ArrayList<String> menu){
        this.menu = menu;
    }

    public void setIdRistorante(String idRistorante){this.idRistorante = idRistorante;}

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public String getIdRistorante(){return idRistorante;}

}
