package Model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 9176873029745254542L;
    private String idCliente;
    private String nome;
    private String cognome;

    public Cliente(){

    }

    public Cliente(String idCliente, String nome, String cognome){
        this.idCliente = idCliente;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }
}