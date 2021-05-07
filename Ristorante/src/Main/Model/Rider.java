package Model;

import java.io.Serializable;

public class Rider implements Serializable {
    private String nome;
    private String cognome;
    private String idRider;

    public Rider(String idRider, String nome, String cognome){
        this.nome = nome;
        this.idRider = idRider;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }
    public String getCognome(){return cognome;}
    public String getIdRider(){return idRider;}

}