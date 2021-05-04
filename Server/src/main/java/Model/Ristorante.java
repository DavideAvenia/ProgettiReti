package Model;

import java.util.ArrayList;

public class Ristorante {
    private String nome;
    private ArrayList<String> cibo;
    private ArrayList<String> bevande;

    public Ristorante(String nome, ArrayList<String> cibo, ArrayList<String> bevande){
        this.nome = nome;
        this.cibo = cibo;
        this.bevande = bevande;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setCibo(ArrayList<String> cibo){
        this.cibo = cibo;
    }

    public void setBevande(ArrayList<String> bevande){
        this.bevande = bevande;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getBevande() {
        return bevande;
    }

    public ArrayList<String> getCibo() {
        return cibo;
    }
}
